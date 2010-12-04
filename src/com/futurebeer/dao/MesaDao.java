package com.futurebeer.dao;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.futurebeer.dao.interfaces.IMesaDao;
import com.futurebeer.dto.MesaDTO;
import com.futurebeer.entity.ItemPedido;
import com.futurebeer.entity.Mesa;
import com.futurebeer.entity.MesaOcupacao;
import com.futurebeer.entity.Pedido;
import com.futurebeer.entity.PersistenceManager;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.LoggerApp;
import com.futurebeer.util.StatusMesa;

public class MesaDao implements IMesaDao{

	public List<MesaDTO> getMesas() throws BaseException{
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager em = null;
//		Session session = null;
		List<MesaDTO> mesas = null;
		
		try {
			em = emf.createEntityManager();
			//TODO Refactoring para retornar apenas a ultima ocupacao, onde a data do fechamento Ž diferente de nulo
//			session = (Session)em.getDelegate();
//			List<Mesa> lista = session.createCriteria(Mesa.class).list();
			List<Mesa> lista = em.createQuery("select mesa from Mesa mesa", Mesa.class).getResultList();
			mesas = new LinkedList<MesaDTO>();
			for (Mesa item : lista) {
				MesaDTO mesa = new MesaDTO();
				mesa.setIdMesa(item.getId());
				mesa.setNumero(item.getNumero());
				mesa.setStatus(item.getStatus().ordinal());
				
				switch (item.getStatus()){
					case LIVRE:{   
						mesa.setCor("green");
						mesa.setOcupada(false);
						break;
					}
					case OCUPADA: {
						mesa.setCor("red");
						mesa.setOcupada(true);
						break;
					}
				}
				
				List<MesaOcupacao> ocupacoes = item.getOcupacoes();
				
				for (MesaOcupacao mesaOcupacao : ocupacoes) {
					if (mesaOcupacao.getFechamento() == null){
						mesa.setIdOcupacao(mesaOcupacao.getId());
					}
				}
				
				mesas.add(mesa);
			}
		} catch (Exception e) {
			throw new BaseException("Erro ao recuperar mesas.", e);
		}finally{
//			session.close();
			em.close();
		}

		return mesas;
	}

	public List<MesaDTO> abrirMesa(MesaDTO mesaDTO) throws BaseException {
		LoggerApp.debug("Abrindo mesa: " + mesaDTO.getIdMesa());
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			MesaOcupacao ocupacao = new MesaOcupacao();
			
			Mesa mesa = em.find(Mesa.class, Integer.valueOf(mesaDTO.getIdMesa()));
		
			ocupacao.setMesa(mesa);
			ocupacao.setAbertura(Calendar.getInstance().getTime());

			em.persist(ocupacao);
			mesa.setStatus(StatusMesa.OCUPADA);
			em.merge(mesa);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new BaseException("Erro ao abrir mesa.", e);
		}finally{
			em.close();
		}
		
		return this.getMesas();
	}

	public List<MesaDTO> fecharMesa(MesaDTO mesaDTO) throws BaseException {
		LoggerApp.debug("Fechando mesa: " + mesaDTO.getIdMesa());
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			MesaOcupacao ocupacao = em.find(MesaOcupacao.class, Integer.valueOf(mesaDTO.getIdOcupacao()));
			
			Mesa mesa = em.find(Mesa.class, Integer.valueOf(mesaDTO.getIdMesa()));
		
			ocupacao.setFechamento(Calendar.getInstance().getTime());
			ocupacao.setTotal(calculaTotalMesa(ocupacao));

			em.merge(ocupacao);
			mesa.setStatus(StatusMesa.LIVRE);
			em.merge(mesa);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new BaseException("Erro ao fechar mesa.", e);
		}finally{
			em.close();
		}
		return this.getMesas();
	}
	
	private double calculaTotalMesa(MesaOcupacao ocupacao) throws BaseException{
		List<Pedido> pedidos = ocupacao.getPedidos();
		double total = 0;
		for (Pedido pedido : pedidos) {
			List<ItemPedido> itens = pedido.getItens();
			for (ItemPedido itemPedido : itens) {
				total += itemPedido.getQtdade() * itemPedido.getProduto().getValor();
			}
		}
		return total;
	}
}

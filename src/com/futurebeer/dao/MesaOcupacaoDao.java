package com.futurebeer.dao;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.futurebeer.dao.interfaces.IMesaOcupacaoDao;
import com.futurebeer.dto.ItemPedidoDTO;
import com.futurebeer.dto.MesaDTO;
import com.futurebeer.entity.ItemPedido;
import com.futurebeer.entity.MesaOcupacao;
import com.futurebeer.entity.Pedido;
import com.futurebeer.entity.PersistenceManager;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.LoggerApp;

public class MesaOcupacaoDao implements IMesaOcupacaoDao{

	public List<MesaDTO> getOcupacoes() throws BaseException {
		return null;
	}

	public MesaOcupacao findById(Integer id) throws BaseException {
		LoggerApp.debug("mesaOcupacao - findById: " + id);
		MesaOcupacao ocupacao = null;
		EntityManager em = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			em.getTransaction().begin();
			ocupacao = em.find(MesaOcupacao.class, id);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new BaseException("Erro ao recuperar mesaOcupacao pelo id :" + id, e);
		}finally{
			em.close();
		}

		return ocupacao;		
	}
	
	public List<ItemPedidoDTO> getPedidosMesa(Integer idOcupacao) throws BaseException{
		if (idOcupacao == null){
			return null;
		}
		LoggerApp.debug("Recuperando pedidos de mesa ocupada: " + idOcupacao);
		
		List<ItemPedidoDTO> pedidosDTO = new LinkedList<ItemPedidoDTO>();
		
		EntityManager em = null;
				
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			em.getTransaction().begin();
			MesaOcupacao ocupacao = em.find(MesaOcupacao.class, idOcupacao);

			if (ocupacao == null){
				return null;
			}
			
			List<Pedido> pedidos = ocupacao.getPedidos();
			
			for (Pedido pedido : pedidos) {
				List<ItemPedido> itens = pedido.getItens();
				
				for (ItemPedido item : itens) {
					ItemPedidoDTO itemDTO = new ItemPedidoDTO();
					itemDTO.setQtdade(item.getQtdade());
					itemDTO.setDescricao(item.getProduto().getDescricao());
					itemDTO.setValorPedido(item.getQtdade() * item.getProduto().getValor());
					
					pedidosDTO.add(itemDTO);
				}
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new BaseException("Erro ao recuperar pedidos da mesa : " + idOcupacao, e);
		}finally{
			em.close();
		}
		
		return pedidosDTO;
	}
}

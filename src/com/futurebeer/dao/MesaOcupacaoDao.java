package com.futurebeer.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.futurebeer.dao.interfaces.IMesaOcupacaoDao;
import com.futurebeer.dto.ItemPedidoDTO;
import com.futurebeer.dto.MesaDTO;
import com.futurebeer.entity.ItemPedido;
import com.futurebeer.entity.MesaOcupacao;
import com.futurebeer.entity.Pedido;
import com.futurebeer.entity.PersistenceManager;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.MesaUtil;

public class MesaOcupacaoDao implements IMesaOcupacaoDao{
	private static final Logger logger = LoggerFactory.getLogger(MesaOcupacaoDao.class);

	public List<MesaDTO> getOcupacoes() throws BaseException {
		logger.debug("Listando as ocupacoes.");
		List<MesaDTO> ocupacoes = new ArrayList<MesaDTO>();
		EntityManager em = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			List<MesaOcupacao> lista = em.createQuery(" from MesaOcupacao where fechamento != null order by abertura desc", MesaOcupacao.class).getResultList();
			for (MesaOcupacao ocupacao : lista) {
				MesaDTO mesa = new MesaDTO();
				mesa.setAbertura(ocupacao.getAbertura());
				mesa.setIdOcupacao(ocupacao.getId());
				mesa.setFechamento(ocupacao.getFechamento());
				mesa.setNumero(ocupacao.getMesa().getNumero());
				mesa.setId(ocupacao.getMesa().getId());
				mesa.setValor(MesaUtil.calculaTotalMesa(ocupacao));
				ocupacoes.add(mesa);
			}
		} catch (Exception e) {
			throw new BaseException("Erro ao recuperar ocupacoes.", e);
		}finally{
			em.close();
		}

		return ocupacoes;		
	}

	public MesaOcupacao findById(Integer id) throws BaseException {
		logger.debug("mesaOcupacao - findById: " + id);
		MesaOcupacao ocupacao = null;
		EntityManager em = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			ocupacao = em.find(MesaOcupacao.class, id);
		} catch (Exception e) {
			throw new BaseException("Erro ao recuperar mesaOcupacao pelo id da ocupacao:" + id, e);
		}finally{
			em.close();
		}

		return ocupacao;		
	}
	
	public List<ItemPedidoDTO> getPedidosMesa(Integer idOcupacao) throws BaseException{
		if (idOcupacao == null){
			return null;
		}
		logger.debug("Recuperando pedidos de mesa ocupada [id : " + idOcupacao + "]");
		
		List<ItemPedidoDTO> pedidosDTO = new LinkedList<ItemPedidoDTO>();
		
		EntityManager em = null;
				
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			MesaOcupacao ocupacao = em.find(MesaOcupacao.class, idOcupacao);

			if (ocupacao == null){
				return null;
			}
			
			List<Pedido> pedidos = ocupacao.getPedidos();
			
			for (Pedido pedido : pedidos) {
				List<ItemPedido> itens = pedido.getItens();
				
				for (ItemPedido item : itens) {
					ItemPedidoDTO itemDTO = new ItemPedidoDTO();
					itemDTO.setIdItemPedido(item.getId());
					itemDTO.setQtdade(item.getQtdade());
					itemDTO.setDescricao(item.getProduto().getDescricao());
					itemDTO.setValorUnitario(item.getProduto().getValor());
					
					pedidosDTO.add(itemDTO);
				}
			}
		} catch (Exception e) {
			throw new BaseException("Erro ao recuperar pedidos da mesa : " + idOcupacao, e);
		}finally{
			em.close();
		}
		
		return pedidosDTO;
	}

	public void removeItemPedido(Integer idItemPedido) throws BaseException {
		logger.debug("Removendo o item do pedido: " + idItemPedido);
		EntityManager em = null;
		ItemPedido item = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			em.getTransaction().begin();
			item = em.find(ItemPedido.class, idItemPedido);
			em.remove(item);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new BaseException("Erro ao remover o item do pedido pelo id :" + idItemPedido, e);
		}finally{
			em.close();
		}		
	}
}

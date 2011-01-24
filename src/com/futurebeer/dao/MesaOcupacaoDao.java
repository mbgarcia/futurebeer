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
			ocupacao = em.find(MesaOcupacao.class, id);
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
			MesaOcupacao ocupacao = em.find(MesaOcupacao.class, idOcupacao);

			if (ocupacao == null){
				return null;
			}
			
			List<Pedido> pedidos = ocupacao.getPedidos();
			
			for (Pedido pedido : pedidos) {
				List<ItemPedido> itens = pedido.getItens();
				
				for (ItemPedido item : itens) {
					if (item.getExcluido() == null){
						ItemPedidoDTO itemDTO = new ItemPedidoDTO();
						itemDTO.setIdItemPedido(item.getId());
						itemDTO.setQtdade(item.getQtdade());
						itemDTO.setDescricao(item.getProduto().getDescricao());
						itemDTO.setValorPedido(item.getQtdade() * item.getProduto().getValor());
						
						pedidosDTO.add(itemDTO);
					}
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
		LoggerApp.debug("Removendo o item do pedido: " + idItemPedido);
		EntityManager em = null;
		ItemPedido item = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			em.getTransaction().begin();
			item = em.find(ItemPedido.class, idItemPedido);
			item.setExcluido(1);
			em.merge(item);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new BaseException("Erro ao remover o item do pedido pelo id :" + idItemPedido, e);
		}finally{
			em.close();
		}		
	}
}

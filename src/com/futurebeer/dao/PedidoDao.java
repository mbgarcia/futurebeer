package com.futurebeer.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.futurebeer.dao.interfaces.IPedidoDao;
import com.futurebeer.dto.ItemPedidoDTO;
import com.futurebeer.dto.PedidoDTO;
import com.futurebeer.entity.ItemPedido;
import com.futurebeer.entity.MesaOcupacao;
import com.futurebeer.entity.Pedido;
import com.futurebeer.entity.PersistenceManager;
import com.futurebeer.entity.Produto;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.LoggerApp;

public class PedidoDao implements IPedidoDao{

	public Pedido addPedido(PedidoDTO pedidoDTO) throws BaseException {
		LoggerApp.debug("Adicionando pedido...");
		MesaOcupacao ocupacao = FactoryDao.getInstance().getMesaOcupacaoDao().findById(Integer.valueOf(pedidoDTO.getIdOcupacao()));
		
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		Pedido pedido = new Pedido();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			pedido.setMesaOcupacao(ocupacao);
			
			List<ItemPedido> itens = new ArrayList<ItemPedido>();
			
			List<ItemPedidoDTO> listaItensDTO = pedidoDTO.getItens();
			
			LoggerApp.debug("adicionando itens ao pedido.....");
			
			for (ItemPedidoDTO itemPedidoDTO : listaItensDTO) {
				ItemPedido item = new ItemPedido();
				item.setPedido(pedido);
				item.setQtdade(itemPedidoDTO.getQtdade());
				
				Produto produto = FactoryDao.getInstance().getProdutoDao().findById(itemPedidoDTO.getIdProduto());
				item.setProduto(produto);
				LoggerApp.debug("produto recuperado " + (produto!=null?produto.getDescricao():""));
				
				itens.add(item);
				LoggerApp.debug("item adicionado : " + item.toString());
			}
			pedido.setItens(itens);
			
			em.persist(pedido);
			em.getTransaction().commit();

			LoggerApp.debug("Pedido adicionado!!!!");
		} catch (Exception e) {
			e.printStackTrace();
			LoggerApp.error("Erro ao inserir pedido", e);
		}finally{
			em.close();
		}
		return pedido;
	}
}

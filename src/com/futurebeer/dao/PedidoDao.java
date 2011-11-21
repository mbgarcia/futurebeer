package com.futurebeer.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;

import com.futurebeer.dao.interfaces.IPedidoDao;
import com.futurebeer.dto.ItemPedidoDTO;
import com.futurebeer.dto.PedidoDTO;
import com.futurebeer.entity.ItemPedido;
import com.futurebeer.entity.MesaOcupacao;
import com.futurebeer.entity.Pedido;
import com.futurebeer.entity.PersistenceManager;
import com.futurebeer.entity.Produto;
import com.futurebeer.exception.BaseException;

public class PedidoDao implements IPedidoDao{
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PedidoDao.class);
	

	public Pedido addPedido(PedidoDTO pedidoDTO) throws BaseException {
		logger.debug("Adicionando pedido...");

		List<ItemPedido> itens = new ArrayList<ItemPedido>();

		MesaOcupacao ocupacao = FactoryDao.getInstance().getMesaOcupacaoDao().findById(Integer.valueOf(pedidoDTO.getIdOcupacao()));
		
		Pedido pedido = new Pedido();
		EntityManager em = null;
		try {
			List<ItemPedidoDTO> listaItensDTO = pedidoDTO.getItens();
			logger.debug("adicionando itens ao pedido.....");
			for (ItemPedidoDTO itemPedidoDTO : listaItensDTO) {
				ItemPedido item = new ItemPedido();
				item.setPedido(pedido);
				item.setQtdade(itemPedidoDTO.getQtdade());
				
				Produto produto = FactoryDao.getInstance().getProdutoDao().findById(itemPedidoDTO.getIdProduto());
				item.setProduto(produto);
				logger.debug("produto recuperado " + (produto!=null?produto.getDescricao():""));
				
				itens.add(item);
				logger.debug("item adicionado : " + item.toString());
			}

			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			em.getTransaction().begin();
			pedido.setItens(itens);
			pedido.setMesaOcupacao(ocupacao);
			em.persist(pedido);
			em.getTransaction().commit();

			logger.debug("Pedido adicionado!!!!");
		} catch (Exception e) {
			logger.error("Erro ao inserir pedido", e);
		}finally{
			if (em != null){
				em.close();
			}
		}
		
		return pedido;
	}
}

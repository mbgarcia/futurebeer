package com.futurebeer.dao;

import java.util.ArrayList;
import java.util.List;

import com.futurebeer.dao.interfaces.IPedidoDao;
import com.futurebeer.dto.ItemPedidoDTO;
import com.futurebeer.dto.PedidoDTO;
import com.futurebeer.entity.ItemPedido;
import com.futurebeer.entity.MesaOcupacao;
import com.futurebeer.entity.Pedido;
import com.futurebeer.entity.Produto;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.HibernateUtil;

public class PedidoDao implements IPedidoDao{

	@Override
	public void addPedido(PedidoDTO pedidoDTO) throws BaseException {
		MesaOcupacao ocupacao = FactoryDao.getInstance().getMesaOcupacaoDao().findById(pedidoDTO.getIdOcupacao());
		
		Pedido pedido = new Pedido();
		pedido.setMesaOcupacao(ocupacao);

		List<ItemPedido> itens = new ArrayList<ItemPedido>();
		
		List<ItemPedidoDTO> listaItensDTO = pedidoDTO.getItens();

		for (ItemPedidoDTO itemPedidoDTO : listaItensDTO) {
			ItemPedido item = new ItemPedido();
			item.setPedido(pedido);
			item.setQtdade(itemPedidoDTO.getQtdade());
			
			Produto produto = FactoryDao.getInstance().getProdutoDao().findById(itemPedidoDTO.getIdProduto());
			item.setProduto(produto);
			
			itens.add(item);
		}
		pedido.setItens(itens);
		
		HibernateUtil.getInstance().getSession().persist(pedido);
	}
}

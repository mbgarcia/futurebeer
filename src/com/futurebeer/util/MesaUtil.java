package com.futurebeer.util;

import java.util.List;

import com.futurebeer.entity.ItemPedido;
import com.futurebeer.entity.MesaOcupacao;
import com.futurebeer.entity.Pedido;
import com.futurebeer.exception.BaseException;

public class MesaUtil {
	public static double calculaTotalMesa(MesaOcupacao ocupacao) throws BaseException{
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

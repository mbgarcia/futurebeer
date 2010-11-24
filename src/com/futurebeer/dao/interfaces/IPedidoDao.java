package com.futurebeer.dao.interfaces;

import com.futurebeer.dto.PedidoDTO;
import com.futurebeer.entity.Pedido;
import com.futurebeer.exception.BaseException;

public interface IPedidoDao {
	public Pedido addPedido(PedidoDTO pedido) throws BaseException;
}

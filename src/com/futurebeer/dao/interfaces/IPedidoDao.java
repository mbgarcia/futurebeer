package com.futurebeer.dao.interfaces;

import com.futurebeer.dto.PedidoDTO;
import com.futurebeer.exception.BaseException;

public interface IPedidoDao {
	public void addPedido(PedidoDTO pedido) throws BaseException;
}

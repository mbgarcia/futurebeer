package com.futurebeer.dao;

import com.futurebeer.dao.interfaces.IPedidoDao;
import com.futurebeer.dto.PedidoDTO;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.HibernateUtil;

public class PedidoDao implements IPedidoDao{

	@Override
	public void addPedido(PedidoDTO pedido) throws BaseException {
		 HibernateUtil.getInstance().getSession().persist(pedido);
	}	
}

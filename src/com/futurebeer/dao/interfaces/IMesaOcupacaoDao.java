package com.futurebeer.dao.interfaces;

import java.util.List;

import com.futurebeer.dto.ItemPedidoDTO;
import com.futurebeer.dto.MesaDTO;
import com.futurebeer.entity.MesaOcupacao;
import com.futurebeer.exception.BaseException;

public interface IMesaOcupacaoDao {
	public List<MesaDTO> getOcupacoes() throws BaseException;
	
	public MesaOcupacao findById(Integer id) throws BaseException;
	
	public List<ItemPedidoDTO> getPedidosMesa(Integer id) throws BaseException;

	public void removeItemPedido(Integer idItemPedido) throws BaseException;
}

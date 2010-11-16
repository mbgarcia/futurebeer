package com.futurebeer.dto;

import java.io.Serializable;
import java.util.List;

public class PedidoDTO implements Serializable{
	
	private static final long serialVersionUID = -7373256931515612573L;

	private int idOcupacao;
	
	private List<ItemPedidoDTO> itens;
		
	public PedidoDTO() {}

	public int getIdOcupacao() {
		return idOcupacao;
	}

	public void setIdOcupacao(int idOcupacao) {
		this.idOcupacao = idOcupacao;
	}

	public List<ItemPedidoDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedidoDTO> itens) {
		this.itens = itens;
	}
}

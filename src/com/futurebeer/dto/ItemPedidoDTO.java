package com.futurebeer.dto;

import java.io.Serializable;

public class ItemPedidoDTO implements Serializable{
	private static final long serialVersionUID = 3580443685613565340L;
	
	private int idProduto;
	
	private int qtdade;
	
	public ItemPedidoDTO() {}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public int getQtdade() {
		return qtdade;
	}

	public void setQtdade(int qtdade) {
		this.qtdade = qtdade;
	}
}

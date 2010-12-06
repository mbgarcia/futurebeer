package com.futurebeer.dto;

import java.io.Serializable;

import com.futurebeer.util.TipoProduto;

public class ItemPedidoDTO implements Serializable{
	private static final long serialVersionUID = 3580443685613565340L;
	
	private int idProduto;
	
	private String descricao;
	
	private TipoProduto tipoProduto;
	
	private int qtdade;
	
	//valor do produto x qtdade
	private double valorPedido;
	
	public ItemPedidoDTO() {}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public int getQtdade() {
		return qtdade;
	}

	public void setQtdade(int qtdade) {
		this.qtdade = qtdade;
	}

	public double getValorPedido() {
		return valorPedido;
	}

	public void setValorPedido(double valorPedido) {
		this.valorPedido = valorPedido;
	}
}

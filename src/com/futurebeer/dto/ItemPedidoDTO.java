package com.futurebeer.dto;

import java.io.Serializable;

import com.futurebeer.util.Formatter;
import com.futurebeer.util.TipoProduto;

public class ItemPedidoDTO implements Serializable{
	private static final long serialVersionUID = 3580443685613565340L;
	
	private int indice;
	
	private int idItemPedido;

	private int idProduto;
	
	private String descricao;
	
	private TipoProduto tipoProduto;
	
	private int qtdade;
	
	private double valorUnitario;
	
	//valor do produto x qtdade

	public ItemPedidoDTO() {}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public int getIdItemPedido() {
		return idItemPedido;
	}

	public void setIdItemPedido(int idItemPedido) {
		this.idItemPedido = idItemPedido;
	}

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
		return valorUnitario * qtdade;
	}

	public String getValorTotalFormatado() {
		return Formatter.INSTANCE.formataDecimal(getValorPedido());
	}
	
	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public double getValorUnitario() {
		return valorUnitario;
	}

	public String getValorUnitarioFormatado() {
		return Formatter.INSTANCE.formataDecimal(valorUnitario);
	}

	public String toString() {
		return "[ItemPedidoDTO - descricao: " + descricao + ", qtd:" + qtdade+ "]";
	}
}

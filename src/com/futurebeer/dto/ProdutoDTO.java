package com.futurebeer.dto;

import java.io.Serializable;

import com.futurebeer.util.TipoProduto;

public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 354182493537537073L;
	
	private Integer idProduto;
	
	private String descricao;
	
	private TipoProduto tipo;
	
	private double valor;
	
	public ProdutoDTO() {}

	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public TipoProduto getTipo() {
		return tipo;
	}

	public void setTipo(TipoProduto tipo) {
		this.tipo = tipo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String toString() {
		return "produtoDTO - [id: "  +this.getIdProduto() + " , descricao: " + this.getDescricao() + " , "  + this.getTipo() + " , " + this.getValor() + "]";
	}
}

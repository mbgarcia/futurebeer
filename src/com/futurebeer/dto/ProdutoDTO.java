package com.futurebeer.dto;

import java.io.Serializable;

public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 354182493537537073L;
	
	private Integer idProduto;
	
	private String descricao;
	
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
	
	public String toString() {
		return "produtoDTO - [id: "  +this.getIdProduto() + " , descricao: " + this.getDescricao() + "]";
	}
}

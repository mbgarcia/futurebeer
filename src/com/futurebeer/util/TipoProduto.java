package com.futurebeer.util;

public enum TipoProduto {
	ALIMENTO(0, "Alimento"), BEBIDA(1, "Bebida"), COZINHA(2, "Cozinha");

	private int idTipo;

	private String descricao;

	TipoProduto(int x, String desc) {
		idTipo = x;
		descricao = desc;
	}

	public int toInt() {
		return idTipo;
	}
	
	public int getIdTipo() {
		return idTipo;
	}

	public String getDescricao(){
		return descricao;
	}
	
	public TipoProduto getByDescricao(String descricao){
		return TipoProduto.valueOf(descricao);
	}
	
	public String toString() {
		return descricao;
	}
	
	public static void main(String[] args) {
		System.out.println(TipoProduto.valueOf("BEBIDA"));
	}
}

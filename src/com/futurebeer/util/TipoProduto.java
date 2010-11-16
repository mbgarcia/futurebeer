package com.futurebeer.util;

public enum TipoProduto {
	ALIMENTO(1), BEBIDA(2);

	private int idTipo;
	
	private TipoProduto(int x){
		idTipo = x;
	}
	
	public String toString() {
		return String.valueOf(idTipo);
	}
}

package com.futurebeer.util;

public enum TipoProduto {
	ALIMENTO(0), BEBIDA(1), COZINHA(2);

	private int idTipo;

	TipoProduto(int x) {
		idTipo = x;
	}

	public int toInt() {
		return idTipo;
	}
}

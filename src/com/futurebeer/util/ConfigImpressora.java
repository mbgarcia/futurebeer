package com.futurebeer.util;

import java.io.Serializable;

public class ConfigImpressora implements Serializable{
	private static final long serialVersionUID = -4770872255455974607L;

	private String porta;
	
	private String categoria;

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String destino) {
		this.categoria = destino;
	}
}
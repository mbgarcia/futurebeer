package com.futurebeer.dto;

import java.io.Serializable;
import java.util.Date;

public class MesaDTO implements Serializable{
	private static final long serialVersionUID = -2971076177028793158L;
	
	private String numero;
	
	private int status;
	
	private Date abertura;
	
	private Date fechamento;
	
	public MesaDTO() {}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public int isStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getAbertura() {
		return abertura;
	}

	public void setAbertura(Date abertura) {
		this.abertura = abertura;
	}

	public Date getFechamento() {
		return fechamento;
	}

	public void setFechamento(Date fechamento) {
		this.fechamento = fechamento;
	}
}

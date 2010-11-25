package com.futurebeer.dto;

import java.io.Serializable;
import java.util.Date;

public class MesaDTO implements Serializable{
	private static final long serialVersionUID = -2971076177028793158L;
	
	private String numero;
	
	private int status;

	private boolean ocupada;
	
	private int idOcupacao;
	
	private Date abertura;
	
	private Date fechamento;
	
	private String cor;
	
	public MesaDTO() {}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public boolean isOcupada() {
		return ocupada;
	}

	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}

	public int getIdOcupacao() {
		return idOcupacao;
	}

	public void setIdOcupacao(int idOcupacao) {
		this.idOcupacao = idOcupacao;
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

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}
}

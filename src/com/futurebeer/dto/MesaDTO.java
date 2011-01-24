package com.futurebeer.dto;

import java.io.Serializable;
import java.util.Date;

public class MesaDTO implements Serializable{
	private static final long serialVersionUID = -2971076177028793158L;
	
	private Integer id;

	private String numero;
	
	private Integer status;
	
	private boolean ocupada;

	private Integer idOcupacao;
	
	private Date abertura;
	
	private Date fechamento;
	
	private String cor;
	
	private Integer extra;
	
	private Integer ativa;

	public MesaDTO() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer idMesa) {
		this.id = idMesa;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public boolean isOcupada() {
		return ocupada;
	}

	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}

	public Integer getIdOcupacao() {
		return idOcupacao;
	}

	public void setIdOcupacao(Integer idOcupacao) {
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

	public Integer getExtra() {
		return extra;
	}

	public void setExtra(Integer extra) {
		this.extra = extra;
	}

	public Integer getAtiva() {
		return ativa;
	}

	public void setAtiva(Integer ativa) {
		this.ativa = ativa;
	}
}

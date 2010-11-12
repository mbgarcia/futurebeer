package com.futurebeer.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="mesa_ocupacao")
public class MesaOcupacao implements Serializable{
	private static final long serialVersionUID = 4638466045499897385L;
	
	@Id
	@Column(name="mesa_ocupacao_id")
	private int id;
	
	private Date abertura;
	
	private Date fechamento;
	
	private double total;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="mesa_id", referencedColumnName="mesa_id")
	private Mesa mesa;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}
}

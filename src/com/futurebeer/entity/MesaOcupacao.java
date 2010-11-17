package com.futurebeer.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="mesa_ocupacao")
public class MesaOcupacao implements Serializable{
	private static final long serialVersionUID = 4638466045499897385L;
	
	@Id
	@Column(name="mesa_ocupacao_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="abertura", nullable=true)
	private Date abertura;
	
	@Column(name="fechamento", nullable=true)
	private Date fechamento;
	
	@Column(name="total", nullable=true)
	private Double total;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="mesa_id", referencedColumnName="mesa_id")
	private Mesa mesa;
	
	@OneToMany(mappedBy="mesaOcupacao", fetch=FetchType.LAZY)
	private List<Pedido> pedidos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
}

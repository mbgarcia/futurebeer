package com.futurebeer.entity;

import java.io.Serializable;
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
@Table(name="pedido")
public class Pedido implements Serializable{
	private static final long serialVersionUID = 7388551601651182947L;
	
	@Id
	@Column(name="pedido_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="mesa_ocupacao_id", referencedColumnName="mesa_ocupacao_id")
	private MesaOcupacao mesaOcupacao;
	
	@OneToMany(mappedBy="pedido", fetch=FetchType.EAGER)
	private List<ItemPedido> itens;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MesaOcupacao getMesaOcupacao() {
		return mesaOcupacao;
	}

	public void setMesaOcupacao(MesaOcupacao mesaOcupacao) {
		this.mesaOcupacao = mesaOcupacao;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}
}

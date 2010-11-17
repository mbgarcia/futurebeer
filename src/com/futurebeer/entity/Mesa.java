package com.futurebeer.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="mesa")
public class Mesa implements Serializable{
	private static final long serialVersionUID = 5619517649372105544L;
	
	@Id
	@Column(name="mesa_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="numero", nullable=false)
	private Integer numero;
	
	@Column(name="status", nullable=true)
	private Integer status;
	
	@OneToMany(mappedBy="mesa", fetch=FetchType.LAZY)
	private List<MesaOcupacao> ocupacoes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<MesaOcupacao> getOcupacoes() {
		return ocupacoes;
	}

	public void setOcupacoes(List<MesaOcupacao> ocupacoes) {
		this.ocupacoes = ocupacoes;
	}
}
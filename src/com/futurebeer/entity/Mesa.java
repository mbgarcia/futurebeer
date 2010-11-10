package com.futurebeer.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="mesa")
public class Mesa implements Serializable{
	private static final long serialVersionUID = 5619517649372105544L;
	
	@Id
	@Column(name="mesa_id")
	private int id;
	
	@Column(name="numero")
	private String numero;
	
	@OneToMany(mappedBy="mesa", fetch=FetchType.LAZY)
	private List<MesaOcupacao> ocupacoes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public List<MesaOcupacao> getOcupacoes() {
		return ocupacoes;
	}

	public void setOcupacoes(List<MesaOcupacao> ocupacoes) {
		this.ocupacoes = ocupacoes;
	}
}
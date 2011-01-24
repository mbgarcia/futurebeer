package com.futurebeer.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.futurebeer.util.StatusMesa;

@Entity
@Table(name="mesa")
public class Mesa implements Serializable{
	private static final long serialVersionUID = 5619517649372105544L;
	
	@Id
	@Column(name="mesa_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="numero", nullable=false)
	private String numero;
	
	@Column(name="status", nullable=false)
	@Enumerated(EnumType.ORDINAL)
	private StatusMesa status;
	
	@Column(name="ativa", nullable=false)
	private Integer ativa;

	@Column(name="extra", nullable=false)
	private Integer extra;
	
	@OneToMany(mappedBy="mesa", fetch=FetchType.LAZY)
	private List<MesaOcupacao> ocupacoes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public StatusMesa getStatus() {
		return status;
	}

	public void setStatus(StatusMesa status) {
		this.status = status;
	}

	public Integer getAtiva() {
		return ativa;
	}

	public void setAtiva(Integer ativa) {
		this.ativa = ativa;
	}

	public Integer getExtra() {
		return extra;
	}

	public void setExtra(Integer extra) {
		this.extra = extra;
	}

	public List<MesaOcupacao> getOcupacoes() {
		return ocupacoes;
	}

	public void setOcupacoes(List<MesaOcupacao> ocupacoes) {
		this.ocupacoes = ocupacoes;
	}
}
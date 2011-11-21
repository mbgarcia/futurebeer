package com.futurebeer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.futurebeer.util.TipoProduto;

@Entity
@Table(name="produto")
public class Produto implements Serializable{
	private static final long serialVersionUID = 1460212825337298014L;
	
	@Id
	@Column(name="produto_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false, unique=true)
	private Integer codigo;
	
	@Column(name="descricao")
	private String descricao;
	
	@Column(name="valor")
	private Double valor;
	
	@Column(name="tipo")
	@Enumerated(EnumType.ORDINAL)
	private TipoProduto tipo;
	
	@Column(name="ativo")
	private Integer ativo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public TipoProduto getTipo() {
		return tipo;
	}

	public void setTipo(TipoProduto tipo) {
		this.tipo = tipo;
	}
	
	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public String toString() {
		return "[" + "id: " + this.getId() + " , descricao: " + this.getDescricao() + " , tipo: " + this.getTipo() + " , valor: " + this.getValor() + "]";
	}
}

package com.futurebeer.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.futurebeer.dao.FactoryDao;
import com.futurebeer.dto.ProdutoDTO;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.TipoProduto;
import com.futurebeer.util.TipoProdutoComparator;

@ManagedBean(name="cadProdutoBean")
@RequestScoped
public class CadProdutoBean implements Serializable{
	private static final long serialVersionUID = -5581568217031954250L;
	
	private String descricao;
	
	private Integer id;

	private int tipo;
	
	private Double valor;
	
	private TipoProduto[] tiposProduto = TipoProduto.values();

	private List<ProdutoDTO> produtosCadastrados = null;
	
	public CadProdutoBean() {
		updateProdutosCadastrados();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public TipoProduto[] getTiposProdutos() {
		return tiposProduto;
	}
	
	public List<ProdutoDTO> getProdutosCadastrados() {
		return produtosCadastrados;
	}

	//-----------------------------------------------------------
	//-- Metodos acionados por command
	//-----------------------------------------------------------
	public void setSelectedProduto(ProdutoDTO selectedProduto) {
		this.id        = selectedProduto.getIdProduto();
		this.tipo      = selectedProduto.getTipo().getIdTipo();
		this.descricao = selectedProduto.getDescricao();
		this.valor     = selectedProduto.getValor();
	}

	public String editProduto() {
		return null;
	}
	
	public String addProduto(){
		try {
			ProdutoDTO produto = new ProdutoDTO();
			produto.setIdProduto(this.getId());
			produto.setDescricao(this.getDescricao());
			produto.setValor(this.getValor());
			TipoProduto[] tipos = TipoProduto.values();
			for (TipoProduto tipoProduto : tipos) {
				if (tipoProduto.getIdTipo() == this.getTipo()){
					produto.setTipo(tipoProduto);
				}
			}
			if (this.getId() == null || this.getId() == 0){
				FactoryDao.getInstance().getProdutoDao().cadastrarProduto(produto);
			}else{
				FactoryDao.getInstance().getProdutoDao().atualizarProduto(produto);
			}
			
			this.setId(null);
			this.setValor(null);
			this.setTipo(0);
			this.setDescricao("");
			
			updateProdutosCadastrados();
		} catch (BaseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Metodo recupera
	private Collection<ProdutoDTO> updateProdutosCadastrados(){
		try {
			produtosCadastrados = FactoryDao.getInstance().getProdutoDao().getProdutos();
			Collections.sort(produtosCadastrados, new TipoProdutoComparator());
		} catch (BaseException e) {
			e.printStackTrace();
		}
		return produtosCadastrados;
	}
}
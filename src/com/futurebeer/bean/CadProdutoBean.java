package com.futurebeer.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.futurebeer.dao.FactoryDao;
import com.futurebeer.dto.ProdutoDTO;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.LoggerApp;
import com.futurebeer.util.MessagesUtil;
import com.futurebeer.util.TipoProduto;
import com.futurebeer.util.TipoProdutoComparator;

@ManagedBean(name="cadProdutoBean")
@RequestScoped
public class CadProdutoBean implements Serializable{
	private static final long serialVersionUID = -5581568217031954250L;
	
	private Integer codigo;
	
	private String descricao;
	
	private Integer id;

	private int tipo;
	
	private Double valor;
	
	private TipoProduto[] tiposProduto = TipoProduto.values();

	private List<ProdutoDTO> produtosCadastrados = null;
	
	public CadProdutoBean() {
		try {
			updateProdutosCadastrados();
		} catch (BaseException e) {
			LoggerApp.error("Erro ao inicializar lista de produtos", e);
		}
	}
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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
	
	public String novoProduto(){
		limparCampos();
		return "cadProdutos";
	}

	public String refresh() {
		return "cadProdutos";
	}
	
	public String addProduto(){
		String mensagem = "";
		Severity severity = null;
		try {
			ProdutoDTO produto = new ProdutoDTO();
//			BeanUtils.copyProperties(produto, this.produto);
			
			produto.setCodigo(this.getCodigo());
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
						
			mensagem = MessagesUtil.getInstance().getWebMessage(MessagesUtil.SUCESSO_SALVAR_PROD);
			severity = FacesMessage.SEVERITY_INFO;
		} catch (Exception e) {
			mensagem = e.getMessage();
			severity = FacesMessage.SEVERITY_ERROR;
			LoggerApp.error(mensagem, e);
		}finally{
			try {
				limparCampos();			
				updateProdutosCadastrados();
			} catch (Exception e) {
				LoggerApp.error(e);
			}
		}
		
		FacesMessage message = new FacesMessage(severity, "Mensagem",  mensagem);  
		FacesContext.getCurrentInstance().addMessage(null, message);
		
		return "cadProdutos";
	}
	
	public String deleteProduto(){
		String mensagem = "";
		Severity severity = null;

		try {
			if (this.getId() != null && this.getId() != 0){
				FactoryDao.getInstance().getProdutoDao().deleteProduto(this.getId());
				mensagem = MessagesUtil.getInstance().getWebMessage(MessagesUtil.SUCESSO_DEL_PROD);
				severity = FacesMessage.SEVERITY_INFO;
			}else{
				mensagem = MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_ID_PRODUTO_NULL);
				severity = FacesMessage.SEVERITY_ERROR;
			}
		} catch (Exception e) {
			mensagem = e.getMessage();
			severity = FacesMessage.SEVERITY_ERROR;
			LoggerApp.error(mensagem, e);
		}finally{
			try {
				limparCampos();			
				updateProdutosCadastrados();
			} catch (Exception e) {
				LoggerApp.error(e);
			}	
		}
		FacesMessage message = new FacesMessage(severity, "Mensagem",  mensagem);  
		FacesContext.getCurrentInstance().addMessage(null, message);
		
		return "cadProdutos";
	}
	
	//Metodo recupera
	private Collection<ProdutoDTO> updateProdutosCadastrados() throws BaseException{
		produtosCadastrados = FactoryDao.getInstance().getProdutoDao().getProdutos();
		Collections.sort(produtosCadastrados, new TipoProdutoComparator());
		return produtosCadastrados;
	}
	
	private void limparCampos(){
		this.setCodigo(null);
		this.setId(null);
		this.setValor(null);
		this.setTipo(0);
		this.setDescricao("");		
	}
}
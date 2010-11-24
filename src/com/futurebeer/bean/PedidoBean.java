package com.futurebeer.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.futurebeer.dao.FactoryDao;
import com.futurebeer.dto.ItemPedidoDTO;
import com.futurebeer.dto.PedidoDTO;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.LoggerApp;

@ManagedBean(name="pedidoBean")
@SessionScoped
public class PedidoBean implements Serializable{
	private static final long serialVersionUID = 7708544055015828412L;
	
	private int idOcupacao;

	private int idProduto;
	
	private String descricao;
	
	private int qtdade;
	
	private List<ItemPedidoDTO> itens = new ArrayList<ItemPedidoDTO>();
	
	public int getIdOcupacao() {
		return idOcupacao;
	}

	public void setIdOcupacao(int idOcupacao) {
		this.idOcupacao = idOcupacao;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getQtdade() {
		return qtdade;
	}

	public void setQtdade(int qtdade) {
		this.qtdade = qtdade;
	}

	public List<ItemPedidoDTO> getItens() {
		if (itens == null){
			itens = new ArrayList<ItemPedidoDTO>();
		}
		return itens;
	}

	public void setItens(List<ItemPedidoDTO> itens) {
		this.itens = itens;
	}
	
	public void addItem(){
		LoggerApp.debug("Item adicionado [ " + getIdProduto() + " , " + getQtdade() + "]" );
		if (itens == null){
			itens = new ArrayList<ItemPedidoDTO>();
		}
		
		try {
			String descricao = FactoryDao.getInstance().getProdutoDao().findById(getIdProduto()).getDescricao();
			ItemPedidoDTO item = new ItemPedidoDTO();
			item.setIdProduto(getIdProduto());
			item.setDescricao(descricao);
			item.setQtdade(getQtdade());
			
			itens.add(item);
			this.qtdade = 0;
		} catch (BaseException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensagem",  "Erro ao processar pedido.");  
			
			FacesContext.getCurrentInstance().addMessage(null, message);  
			
			e.printStackTrace();
		}
		
	}
	
	public void concluirPedido(){
		try {
			PedidoDTO dto = new PedidoDTO();
			dto.setIdOcupacao(getIdOcupacao());
			List<ItemPedidoDTO> novosItens = new ArrayList<ItemPedidoDTO>();
			for (ItemPedidoDTO itemDTO : getItens()) {
				ItemPedidoDTO novoItem = new ItemPedidoDTO();
				novoItem.setIdProduto(itemDTO.getIdProduto());
				novoItem.setQtdade(itemDTO.getQtdade());
				novosItens.add(novoItem);
			}
			dto.setItens(novosItens);
			FactoryDao.getInstance().getPedidoDao().addPedido(dto);
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem",  "Pedido realizado.");  
	          
	        FacesContext.getCurrentInstance().addMessage(null, message);  
		} catch (BaseException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensagem",  "Erro ao processar pedido.");  
			
			FacesContext.getCurrentInstance().addMessage(null, message);  
			e.printStackTrace();
		}finally{
			limpaPedido();
		}
	}

	public void cancelaPedido(){
		limpaPedido();
	}
	
	private void limpaPedido(){
		this.itens.clear();
		this.qtdade = 0;
	}
}

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

@ManagedBean(name="pedidoBean")
@SessionScoped
public class PedidoBean implements Serializable{
	private static final long serialVersionUID = 7708544055015828412L;
	
	private int idOcupacao;

	private int idProduto;
	
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

	public int getQtdade() {
		return qtdade;
	}

	public void setQtdade(int qtdade) {
		this.qtdade = qtdade;
	}

	public List<ItemPedidoDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedidoDTO> itens) {
		this.itens = itens;
	}
	
	public void addItem(){
		if (itens == null){
			itens = new ArrayList<ItemPedidoDTO>();
		}
		
		ItemPedidoDTO item = new ItemPedidoDTO();
		item.setIdProduto(getIdProduto());
		item.setQtdade(getQtdade());
		
		itens.add(item);
	}
	
	public void concluirPedido(){
		try {
			PedidoDTO dto = new PedidoDTO();
			dto.setIdOcupacao(getIdOcupacao());
			dto.setItens(getItens());
			itens.clear();
			FactoryDao.getInstance().getPedidoDao().addPedido(dto);
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem",  "Pedido realizado.");  
	          
	        FacesContext.getCurrentInstance().addMessage(null, message);  
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}

	public void cancelaPedido(){
		itens.clear();
	}
}

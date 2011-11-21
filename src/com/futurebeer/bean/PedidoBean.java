package com.futurebeer.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;

import com.futurebeer.dao.FactoryDao;
import com.futurebeer.dto.ItemPedidoDTO;
import com.futurebeer.dto.MesaDTO;
import com.futurebeer.dto.PedidoDTO;
import com.futurebeer.entity.Produto;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.MessagesUtil;
import com.futurebeer.util.SerialPrinterUtil;
import com.futurebeer.util.TipoProduto;

@ManagedBean(name="pedidoBean")
@ViewScoped
public class PedidoBean implements Serializable{
	private static final long serialVersionUID = 7708544055015828412L;
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PedidoBean.class);
	
	private MesaDTO selectedMesa;

	private Integer codProduto;
	
	private String descProduto;
	
	private int qtdade;
	
	private List<ItemPedidoDTO> itens = new ArrayList<ItemPedidoDTO>();
	
	private ItemPedidoDTO selectedItem = null;
	
	private boolean addItem = false;
	
	public MesaDTO getSelectedMesa() {
		return selectedMesa;
	}

	public void setSelectedMesa(MesaDTO selectedMesa) {
		this.selectedMesa = selectedMesa;
	}

	public Integer getCodProduto() {
		return codProduto;
	}

	public void setCodProduto(Integer codProduto) {
		this.codProduto = codProduto;
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
	
	
	public ItemPedidoDTO getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(ItemPedidoDTO itemPedido) {
		this.selectedItem = itemPedido;
	}	
	
	public void addItem(){
		logger.debug("Item adicionado [ " + getCodProduto() + " , " + getQtdade() + "]" );
		if (itens == null){
			itens = new ArrayList<ItemPedidoDTO>();
		}
		
		try {
			Produto produto = FactoryDao.getInstance().getProdutoDao().findByCodigo(getCodProduto());
			TipoProduto tipoProduto = produto.getTipo();
			ItemPedidoDTO item = new ItemPedidoDTO();
			item.setIndice(itens.size() + 1);
			item.setIdProduto(produto.getId());
			item.setDescricao(produto.getDescricao());
			item.setTipoProduto(tipoProduto);
			item.setQtdade(getQtdade());
			
			itens.add(item);
			this.qtdade = 0;
			this.codProduto = 0;
		} catch (BaseException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensagem",  "Erro ao processar pedido.");  
			FacesContext.getCurrentInstance().addMessage(null, message);  
			e.printStackTrace();
		}
		
	}
	
	public String deleteItemPedido(){
		String mensagem = "";
		Severity severity = null;
		try {
			for (ItemPedidoDTO item : itens) {
				if (item.getIndice() == selectedItem.getIndice()){
					itens.remove(item);
					break;
				}
			}
			
			mensagem = MessagesUtil.getInstance().getWebMessage(MessagesUtil.SUCESSO_EXCLUIR_ITEM);
			severity = FacesMessage.SEVERITY_INFO;			
		} catch (Exception e) {
			mensagem = MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_EXCLUIR_ITEM);
			severity = FacesMessage.SEVERITY_ERROR;
			logger.error(mensagem, e);
		}
		FacesMessage message = new FacesMessage(severity, "Mensagem",  mensagem);  
		FacesContext.getCurrentInstance().addMessage(null, message);
		
		return "dashboard";
	}
	
	public String concluirPedido(){
		String mensagem = "";
		Severity severity = null;
		
		PedidoDTO dto = new PedidoDTO();
		
		try {
			dto.setIdOcupacao(getSelectedMesa().getIdOcupacao());
			List<ItemPedidoDTO> novosItens = new ArrayList<ItemPedidoDTO>();
			List<ItemPedidoDTO> itensDoPedido = getItens();
			if (itensDoPedido.size() == 0){
				mensagem = MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_PEDIDO_VAZIO);
				severity = FacesMessage.SEVERITY_ERROR;				
			}else{				
				for (ItemPedidoDTO itemDTO : itensDoPedido) {
					ItemPedidoDTO novoItem = new ItemPedidoDTO();
					novoItem.setDescricao(itemDTO.getDescricao());
					novoItem.setIdProduto(itemDTO.getIdProduto());
					novoItem.setQtdade(itemDTO.getQtdade());
					novoItem.setTipoProduto(itemDTO.getTipoProduto());
					novosItens.add(novoItem);
				}
				dto.setItens(novosItens);
				FactoryDao.getInstance().getPedidoDao().addPedido(dto);
				mensagem = MessagesUtil.getInstance().getWebMessage(MessagesUtil.SUCESSO_SALVAR_PEDIDO);
				severity = FacesMessage.SEVERITY_INFO;
			}
		} catch (BaseException e) {
			mensagem = MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_SALVAR_PEDIDO);
			severity = FacesMessage.SEVERITY_ERROR;
			logger.error(mensagem, e);
		}finally{
			limpaPedido();
		}
		
		//so busca a impressao se o pedido foi feito com sucesso.
		if (severity != FacesMessage.SEVERITY_ERROR){
			try {
				logger.debug("Impressao do pedido");
				SerialPrinterUtil.getInstance().imprimePedido(dto);
			} catch (BaseException e) {
				mensagem = e.getMessage();  
				severity = FacesMessage.SEVERITY_ERROR;
			}
			
		}
		
		
		FacesMessage message = new FacesMessage(severity, "Mensagem",  mensagem);  
		FacesContext.getCurrentInstance().addMessage(null, message);
		

		
		return null;
	}

	public void cancelaPedido(){
		limpaPedido();
	}
	
	private void limpaPedido(){
		this.itens.clear();
		this.qtdade = 0;
	}
	
	List<ItemPedidoDTO> pedidos = null;

	public List<ItemPedidoDTO> getPedidosMesa(){
		try {
			if (getSelectedMesa() != null){
				pedidos = FactoryDao.getInstance().getMesaOcupacaoDao().getPedidosMesa(getSelectedMesa().getIdOcupacao());
			}
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensagem",  "Erro ao recuperar pedidos da mesa.");  
			FacesContext.getCurrentInstance().addMessage(null, message);
			logger.error("Erro ao recuperar pedidos da mesa." , e);
		}
		
		return pedidos;
	}
	
	public double getTotalPedidos(){
		if (pedidos == null){
			return 0;
		}
		double total = 0;
		for (ItemPedidoDTO pedido: pedidos) {
			total += pedido.getValorPedido();
		}
		return total;
	}
	
	public String deleteItemPedidoMesa(){
		String mensagem = "";
		Severity severity = null;
		try {
			for (ItemPedidoDTO item : itens) {
				if (item.getIndice() == selectedItem.getIndice()){
					itens.remove(item);
					break;
				}
			}
			
			mensagem = MessagesUtil.getInstance().getWebMessage(MessagesUtil.SUCESSO_EXCLUIR_ITEM);
			severity = FacesMessage.SEVERITY_INFO;			
		} catch (Exception e) {
			mensagem = MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_EXCLUIR_ITEM);
			severity = FacesMessage.SEVERITY_ERROR;
			logger.error(mensagem, e);
		}
		FacesMessage message = new FacesMessage(severity, "Mensagem",  mensagem);  
		FacesContext.getCurrentInstance().addMessage(null, message);
		
		return "dashboard";
	}

	public void setDescProduto(String descProduto) {
		this.descProduto = descProduto;
	}

	public String getDescProduto() {
		descProduto = "----";
		addItem = false;

		try {
			Produto produto = FactoryDao.getInstance().getProdutoDao().findByCodigo(this.codProduto);
			if (produto != null){
				descProduto = produto.getDescricao();
				addItem = true;
			}
		} catch (BaseException e) {
			String mensagem = MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_FIND_PROD_ID);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensagem",  mensagem);  
			FacesContext.getCurrentInstance().addMessage(null, message);
			logger.error(mensagem, e);
		}
	
		return descProduto;
	}

	public void setAddItem(boolean addItem) {
		this.addItem = addItem;
	}

	public boolean isAddItem() {
		return addItem;
	}	
}

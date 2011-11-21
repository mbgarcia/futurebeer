package com.futurebeer.bean;

import java.io.Serializable;
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
import com.futurebeer.util.Formatter;
import com.futurebeer.util.LoggerApp;
import com.futurebeer.util.MessagesUtil;

@ManagedBean(name="resumoPedidoBean")
@ViewScoped
public class ResumoPedidoBean implements Serializable{
	private static final long serialVersionUID = 7708544055015828412L;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ResumoPedidoBean.class);

	private MesaDTO selectedMesa;

	private ItemPedidoDTO selectedItem = null;
	
	private List<ItemPedidoDTO> pedidosMesa = null;
	
	public MesaDTO getSelectedMesa() {
		return selectedMesa;
	}

	public void setSelectedMesa(MesaDTO selectedMesa) {
		this.selectedMesa = selectedMesa;
	}

	public ItemPedidoDTO getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(ItemPedidoDTO itemPedido) {
		this.selectedItem = itemPedido;
	}	

	public List<ItemPedidoDTO> getPedidosMesa(){
		try {
			if (getSelectedMesa() != null){
				pedidosMesa = FactoryDao.getInstance().getMesaOcupacaoDao().getPedidosMesa(getSelectedMesa().getIdOcupacao());
			}
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensagem",  "Erro ao recuperar pedidos da mesa.");  
			FacesContext.getCurrentInstance().addMessage(null, message);
			logger.error("Erro ao recuperar pedidos da mesa." , e);
		}
		
		return pedidosMesa;
	}
	
	public String getTotalPedidos(){
		if (pedidosMesa == null){
			return "0";
		}
		double total = 0;
		for (ItemPedidoDTO pedido: pedidosMesa) {
			total += pedido.getValorPedido();
		}
		
		return Formatter.INSTANCE.formataDecimal(total);
	}
	
	public String deleteItemPedidoMesa(){
		String mensagem = "";
		Severity severity = null;
		try {
			FactoryDao.getInstance().getMesaOcupacaoDao().removeItemPedido(selectedItem.getIdItemPedido());

			mensagem = MessagesUtil.getInstance().getWebMessage(MessagesUtil.SUCESSO_EXCLUIR_ITEM);
			severity = FacesMessage.SEVERITY_INFO;			
		} catch (Exception e) {
			mensagem = MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_EXCLUIR_ITEM);
			severity = FacesMessage.SEVERITY_ERROR;
			LoggerApp.error(mensagem, e);
		}
		FacesMessage message = new FacesMessage(severity, "Mensagem",  mensagem);  
		FacesContext.getCurrentInstance().addMessage(null, message);
		
		return null;
	}	
}

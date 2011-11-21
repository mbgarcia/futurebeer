package com.futurebeer.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;

import com.futurebeer.dao.FactoryDao;
import com.futurebeer.dto.MesaDTO;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.MessagesUtil;
import com.futurebeer.util.SerialPrinterUtil;

@ManagedBean(name="mesaBean")
@SessionScoped
public class MesaBean implements Serializable{
	private static final long serialVersionUID = 6787583383231299067L;
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MesaBean.class);

	private MesaDTO selectedMesa;
	
	private int count;
	
	private List<MesaDTO> mesas;
	
	public MesaBean() {
		//chama a DAO
		logger.debug("Construtor de MesaBean");
		// Begin unit of work
		try {
			mesas = FactoryDao.getInstance().getMesaDao().getMesas();
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	public List<MesaDTO> getMesas() {
		logger.debug("getMesas count: " + count++);
		
		return mesas;
	}
	
	public void setMesas(List<MesaDTO> mesas) {
		this.mesas = mesas;
	}

	public MesaDTO getSelectedMesa() {
		return selectedMesa;
	}

	public void setSelectedMesa(MesaDTO selectedMesa) {
		this.selectedMesa = selectedMesa;
	}
	
	public String abrirMesa(){
		try {
			logger.info("Abrindo mesa : " + getSelectedMesa().getNumero());
			List<MesaDTO> lista = FactoryDao.getInstance().getMesaDao().abrirMesa(getSelectedMesa()); 
			setMesas(lista);
		} catch (BaseException e) {
			logger.error("Erro ao abrir mesa", e);
			e.printStackTrace();
		}
		return null;
	}

	public String fecharMesa(){
		try {
			logger.debug("Metodo fechar Mesa - MesaBean");
			List<MesaDTO> lista = FactoryDao.getInstance().getMesaDao().fecharMesa(getSelectedMesa()); 
			setMesas(lista);
		} catch (BaseException e) {
			logger.error("Erro ao fechar mesa", e);
			e.printStackTrace();
		}
		
		return null;
	}

	public String addMesaExtra(){
		String mensagem = "";
		Severity severity = null;
		
		try {
			logger.debug("Metodo add Mesa Extra - MesaBean");

			MesaDTO mesa = getSelectedMesa();
			mesa.setExtra(1);
			FactoryDao.getInstance().getMesaDao().addMesaExtra(mesa); 
			mensagem = MessagesUtil.getInstance().getWebMessage(MessagesUtil.SUCESSO_SALVAR_MESA);
			severity = FacesMessage.SEVERITY_INFO;
		} catch (BaseException e) {
			mensagem = e.getMessage();
			severity = FacesMessage.SEVERITY_ERROR;
			logger.error(mensagem, e);
		}finally{
			try {
				List<MesaDTO> lista = FactoryDao.getInstance().getMesaDao().getMesas();
				setMesas(lista);			
			} catch (BaseException e) {} 
		}

		FacesMessage message = new FacesMessage(severity, "Mensagem",  mensagem);
		FacesContext.getCurrentInstance().addMessage(null, message);
		
		return "dashboard";
	}

	public String excluirMesaExtra(){
		String mensagem = "";
		Severity severity = null;
		
		try {
			MesaDTO mesa = getSelectedMesa();
			FactoryDao.getInstance().getMesaDao().excluirMesa(mesa); 
			mensagem = MessagesUtil.getInstance().getWebMessage(MessagesUtil.SUCESSO_DEL_MESA);
			severity = FacesMessage.SEVERITY_INFO;
		} catch (BaseException e) {
			mensagem = e.getMessage();
			severity = FacesMessage.SEVERITY_ERROR;
			logger.error(mensagem, e);
		}finally{
			try {
				List<MesaDTO> lista = FactoryDao.getInstance().getMesaDao().getMesas();
				setMesas(lista);			
			} catch (BaseException e) {} 
		}

		FacesMessage message = new FacesMessage(severity, "Mensagem",  mensagem);  
		FacesContext.getCurrentInstance().addMessage(null, message);
		
		return "dashboard";
	}
	
	public String abrirDashboard(){
		return "dashboard?faces-redirect=true";
	}
	
	public String imprimirConta(){
		try {
			logger.debug("Metodo imprimir conta - MesaBean");
			SerialPrinterUtil.getInstance().imprimirComanda(getSelectedMesa()); 
		} catch (BaseException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensagem",  e.getMessage());  
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return "dashboard";
	}
}
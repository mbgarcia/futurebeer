package com.futurebeer.bean;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.futurebeer.util.ConfigImpressora;
import com.futurebeer.util.LoggerApp;

@ManagedBean
@RequestScoped
public class ImpressoraBean implements Serializable{
	private static final long serialVersionUID = 3768175965399016433L;
	
	private final ResourceBundle configFile = ResourceBundle.getBundle("com.futurebeer.configImpressora");

	private ConfigImpressora[] configs = null;
	
	public ImpressoraBean() {
		configs = new ConfigImpressora[configFile.keySet().size()];
		int indice = 0;
		for (String categoria: configFile.keySet()){
			ConfigImpressora config = new ConfigImpressora();
			config.setCategoria(categoria);
			config.setPorta(configFile.getString(categoria));
			configs[indice++] = config;
		}
	}
	
	public ConfigImpressora[] getConfigs() {
		return configs;
	}

	public void saveConfig(RowEditEvent obj) {
		ConfigImpressora conf = (ConfigImpressora)  obj.getObject();
		System.out.println(conf);
		String mensagem = "Co";
		Severity severity = FacesMessage.SEVERITY_INFO;

		try {
			
		} catch (Exception e) {
			mensagem = e.getMessage();
			severity = FacesMessage.SEVERITY_ERROR;
			LoggerApp.error(mensagem, e);
		}finally{
		}
		FacesMessage message = new FacesMessage(severity, "Mensagem",  mensagem);  
		FacesContext.getCurrentInstance().addMessage(null, message);		
	}

	public String abrirImpressoras(){
		return "configImpressoras?faces-redirect=true";
	}	
}
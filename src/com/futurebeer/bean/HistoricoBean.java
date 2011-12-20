package com.futurebeer.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.futurebeer.dao.FactoryDao;
import com.futurebeer.dto.MesaDTO;
import com.futurebeer.exception.BaseException;

@ManagedBean
@RequestScoped
/**
 * ManagedBean que lista o historico de atendimentos do restaurante
 */
public class HistoricoBean implements Serializable {
	private static final long serialVersionUID = 8406085990132923296L;

	private List<MesaDTO> mesas = null;

	public HistoricoBean() {
		try {
			mesas = FactoryDao.getInstance().getMesaOcupacaoDao().getOcupacoes();
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	public List<MesaDTO> getMesas() {
		return mesas;
	}

	public void setMesas(List<MesaDTO> mesas) {
		this.mesas = mesas;
	}
	
	public String abrirHistorico(){
		return "historico?faces-redirect=true";
	}	
}

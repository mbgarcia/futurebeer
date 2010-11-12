package com.futurebeer.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.futurebeer.dao.MesaDao;
import com.futurebeer.dao.interfaces.IMesaDao;
import com.futurebeer.dto.MesaDTO;
import com.futurebeer.exception.BaseException;

@ManagedBean(name="mesaBean")
@SessionScoped
public class MesaBean implements Serializable{
	private static final long serialVersionUID = 6787583383231299067L;

	private MesaDTO selectedMesa;
	
	IMesaDao mesaDao = new MesaDao();

	public List<MesaDTO> getMesas() {
		//chama a DAO
		System.out.println("getMesas");
		// Begin unit of work
		try {
			return mesaDao.getMesas();
		} catch (BaseException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public MesaDTO getSelectedMesa() {
		return selectedMesa;
	}

	public void setSelectedMesa(MesaDTO selectedMesa) {
		this.selectedMesa = selectedMesa;
	}
}
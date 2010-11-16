package com.futurebeer.dao;

import java.util.LinkedList;
import java.util.List;

import com.futurebeer.dao.interfaces.IMesaDao;
import com.futurebeer.dto.MesaDTO;
import com.futurebeer.entity.Mesa;
import com.futurebeer.util.HibernateUtil;

public class MesaDao implements IMesaDao{

	public List<MesaDTO> getMesas() {
		List<Mesa> lista = HibernateUtil.getInstance().getSession().createCriteria(Mesa.class).list();
		List<MesaDTO> mesas = new LinkedList<MesaDTO>();
		for (Mesa item : lista) {
			MesaDTO mesa = new MesaDTO();
			mesa.setNumero(item.getNumero());
			mesa.setStatus(item.getStatus());
			mesas.add(mesa);
		}

		return mesas;
	}
	
//	public MesaOcupacao getOcupacaoMesa(int idMesa){
//		
//	}
}

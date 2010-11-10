package com.futurebeer.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.futurebeer.entity.Mesa;
import com.futurebeer.util.HibernateUtil;

@ManagedBean(name="mesaBean")
@SessionScoped
public class MesaBean implements Serializable{
	private static final long serialVersionUID = 6787583383231299067L;

	public List<Mesa> getMesas() {
		System.out.println("getMesas");
		// Begin unit of work
		List<Mesa> result = HibernateUtil.getInstance().getSession().createCriteria(Mesa.class).list();
		
		return result;
	}
}
package com.futurebeer.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.futurebeer.dao.FactoryDao;
import com.futurebeer.dto.ProdutoDTO;
import com.futurebeer.exception.BaseException;

@ManagedBean(name="produtoBean")
@RequestScoped
public class ProdutoBean implements Serializable{
	private static final long serialVersionUID = 7708544055015828412L;
	
	public List<ProdutoDTO> getProdutos(){
		
		try {
			return FactoryDao.getInstance().getProdutoDao().getProdutos();
		} catch (BaseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}

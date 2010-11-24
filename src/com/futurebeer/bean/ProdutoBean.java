package com.futurebeer.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.futurebeer.dao.FactoryDao;
import com.futurebeer.dto.ProdutoDTO;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.LoggerApp;

@ManagedBean(name="produtoBean")
@SessionScoped
public class ProdutoBean implements Serializable{
	private static final long serialVersionUID = 7708544055015828412L;
	
	Map<Integer, ProdutoDTO> map = null;
	
	public ProdutoBean() {
		try {
			List<ProdutoDTO> produtos = FactoryDao.getInstance().getProdutoDao().getProdutos();
			map = new LinkedHashMap<Integer, ProdutoDTO>();
			for (ProdutoDTO produtoDTO : produtos) {
				LoggerApp.debug("produto : " + produtoDTO.toString());
				map.put(produtoDTO.getIdProduto(), produtoDTO);
			}
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	public Collection<ProdutoDTO> getProdutos(){
		return map.values();
	}
}

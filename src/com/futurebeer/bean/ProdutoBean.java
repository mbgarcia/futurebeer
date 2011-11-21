package com.futurebeer.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import com.futurebeer.dao.FactoryDao;
import com.futurebeer.dto.ProdutoDTO;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.LoggerApp;
import com.futurebeer.util.TipoProduto;

@ManagedBean(name="produtoBean")
@SessionScoped
public class ProdutoBean implements Serializable{
	private static final long serialVersionUID = 7708544055015828412L;
	
	private Map<TipoProduto, List<ProdutoDTO>> map = null;
	
	private SelectItem[] gruposItem = null;
	
	public ProdutoBean() {
		atualizaListagemProdutos();
	}

	private void atualizaListagemProdutos() {
		try {
			List<ProdutoDTO> produtos = FactoryDao.getInstance().getProdutoDao().getProdutos();
			
			map = new LinkedHashMap<TipoProduto, List<ProdutoDTO>>();
			TipoProduto[] tipos = TipoProduto.values();
			for (TipoProduto tipoProduto : tipos) {
				map.put(tipoProduto, new ArrayList<ProdutoDTO>());
				
			}
			
			for (ProdutoDTO produtoDTO : produtos) {
				LoggerApp.debug("produto : " + produtoDTO.toString());
				List<ProdutoDTO> lista = map.get(produtoDTO.getTipo());
				lista.add(produtoDTO);
			}
			
			gruposItem = new SelectItem[tipos.length];
			for (int i = 0; i < tipos.length; i++) {
				SelectItemGroup grupo = new SelectItemGroup(tipos[i].getDescricao());
				gruposItem[i] = grupo;
				List<ProdutoDTO> lista = map.get(tipos[i]);
				SelectItem[] itens = new SelectItem[lista.size()];
				for (int j = 0; j < lista.size(); j++) {
					ProdutoDTO produtoDTO = lista.get(j);
					SelectItem item = new SelectItem(produtoDTO.getIdProduto(), produtoDTO.getDescricao());
					itens[j] = item;
				}
				grupo.setSelectItems(itens);
			}
			
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	public SelectItem[] getProdutos(){
		atualizaListagemProdutos();
		return gruposItem;
	}
		
	public String initCadastro(){
		return "cadProdutos";
	}
}

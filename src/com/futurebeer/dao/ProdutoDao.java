package com.futurebeer.dao;

import java.util.LinkedList;
import java.util.List;

import com.futurebeer.dao.interfaces.IProdutoDao;
import com.futurebeer.dto.ProdutoDTO;
import com.futurebeer.entity.Produto;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.HibernateUtil;

public class ProdutoDao implements IProdutoDao {

	@Override
	public Produto findById(int idProduto) throws BaseException {
		return null;
	}
	
	public List<ProdutoDTO> getProdutos() {
		List<Produto> lista = HibernateUtil.getInstance().getSession().createCriteria(Produto.class).list();
		List<ProdutoDTO> produtos = new LinkedList<ProdutoDTO>();
		for (Produto item : lista) {
			ProdutoDTO produto = new ProdutoDTO();
			produto.setIdProduto(item.getId());
			produto.setDescricao(item.getDescricao());
		
			produtos.add(produto);
		}

		return produtos;
	}	
}

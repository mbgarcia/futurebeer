package com.futurebeer.dao;

import com.futurebeer.dao.interfaces.IProdutoDao;
import com.futurebeer.entity.Produto;
import com.futurebeer.exception.BaseException;

public class ProdutoDao implements IProdutoDao {

	@Override
	public Produto findById(int idProduto) throws BaseException {
		return null;
	}
}

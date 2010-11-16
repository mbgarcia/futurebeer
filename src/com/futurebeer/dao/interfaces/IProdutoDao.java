package com.futurebeer.dao.interfaces;

import com.futurebeer.entity.Produto;
import com.futurebeer.exception.BaseException;

public interface IProdutoDao {
	public Produto findById(int idProduto) throws BaseException;
}

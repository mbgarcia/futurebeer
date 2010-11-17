package com.futurebeer.dao.interfaces;

import java.util.List;

import com.futurebeer.dto.ProdutoDTO;
import com.futurebeer.entity.Produto;
import com.futurebeer.exception.BaseException;

public interface IProdutoDao {
	public Produto findById(int idProduto) throws BaseException;
	
	public List<ProdutoDTO> getProdutos() throws BaseException;
}

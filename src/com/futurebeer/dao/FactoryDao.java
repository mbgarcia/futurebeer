package com.futurebeer.dao;

import com.futurebeer.dao.interfaces.IMesaDao;
import com.futurebeer.dao.interfaces.IMesaOcupacaoDao;
import com.futurebeer.dao.interfaces.IPedidoDao;
import com.futurebeer.dao.interfaces.IProdutoDao;

public class FactoryDao {
	
	private static FactoryDao instance = null;
	
	public static FactoryDao getInstance(){
		if (instance == null){
			instance = new FactoryDao();
		}
		return instance;
	}
	
	private IMesaDao mesaDao = null;
	
	public IMesaDao getMesaDao(){
		if (mesaDao == null){
			mesaDao = new MesaDao();
		}
		return mesaDao;
	}

	private IProdutoDao produtoDao = null;
	
	public IProdutoDao getProdutoDao(){
		if (produtoDao == null){
			produtoDao = new ProdutoDao();
		}
		return produtoDao;
	}

	private IMesaOcupacaoDao mesaOcupacaoDao = null;
	
	public IMesaOcupacaoDao getMesaOcupacaoDao(){
		if (mesaOcupacaoDao == null){
			mesaOcupacaoDao = new MesaOcupacaoDao();
		}
		return mesaOcupacaoDao;
	}

	private IPedidoDao pedidoDao = null;
	
	public IPedidoDao getPedidoDao(){
		if (pedidoDao == null){
			pedidoDao = new PedidoDao();
		}
		return pedidoDao;
	}
}

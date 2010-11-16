package com.futurebeer.dao;

import com.futurebeer.dao.interfaces.IMesaDao;

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
	
}

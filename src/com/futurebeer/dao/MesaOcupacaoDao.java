package com.futurebeer.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.futurebeer.dao.interfaces.IMesaOcupacaoDao;
import com.futurebeer.dto.MesaDTO;
import com.futurebeer.entity.MesaOcupacao;
import com.futurebeer.entity.PersistenceManager;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.LoggerApp;

public class MesaOcupacaoDao implements IMesaOcupacaoDao{

	public List<MesaDTO> getOcupacoes() throws BaseException {
		return null;
	}

	public MesaOcupacao findById(Integer id) throws BaseException {
		LoggerApp.debug("mesaOcupacao - findById: " + id);
		MesaOcupacao ocupacao = null;
		EntityManager em = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			em.getTransaction().begin();
			ocupacao = em.find(MesaOcupacao.class, id);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new BaseException("Erro ao recuperar mesaOcupacao pelo id :" + id, e);
		}finally{
			em.close();
		}

		return ocupacao;		
	}
}

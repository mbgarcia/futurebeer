package com.futurebeer.dao;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;

import com.futurebeer.dao.interfaces.IMesaDao;
import com.futurebeer.dto.MesaDTO;
import com.futurebeer.entity.Mesa;
import com.futurebeer.entity.MesaOcupacao;
import com.futurebeer.entity.PersistenceManager;
import com.futurebeer.exception.BaseException;

public class MesaDao implements IMesaDao{

	public List<MesaDTO> getMesas() throws BaseException{
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager em = null;
		List<MesaDTO> mesas = null;
		
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Session session = (Session)em.getDelegate();
			List<Mesa> lista = session.createCriteria(Mesa.class).list();
			mesas = new LinkedList<MesaDTO>();
			for (Mesa item : lista) {
				MesaDTO mesa = new MesaDTO();
				mesa.setNumero(item.getNumero());
				mesa.setStatus(item.getStatus());
				
				List<MesaOcupacao> ocupacoes = item.getOcupacoes();
				
				for (MesaOcupacao mesaOcupacao : ocupacoes) {
					mesa.setIdOcupacao(mesaOcupacao.getId());
				}
				
				mesas.add(mesa);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new BaseException("Erro ao recuperar mesas.", e);
		}finally{
			em.close();
		}

		return mesas;
	}
	
//	public MesaOcupacao getOcupacaoMesa(int idMesa){
//		
//	}
}

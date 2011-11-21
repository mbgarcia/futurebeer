package com.futurebeer.dao;

import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.beanutils.BeanUtils;

import com.futurebeer.dao.interfaces.IMesaDao;
import com.futurebeer.dto.MesaDTO;
import com.futurebeer.entity.Mesa;
import com.futurebeer.entity.MesaOcupacao;
import com.futurebeer.entity.PersistenceManager;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.LoggerApp;
import com.futurebeer.util.MesaNumeroComparable;
import com.futurebeer.util.MesaUtil;
import com.futurebeer.util.MessagesUtil;
import com.futurebeer.util.StatusMesa;

public class MesaDao implements IMesaDao{
	
	public Mesa findByNumero(String numero) throws BaseException {
		if (numero == null){
			return null;
		}
		LoggerApp.debug("Find mesa pelo numero: " + numero);
		EntityManager em = null;
		Mesa mesa = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			Query query = em.createQuery("from Mesa as m where m.numero = :numero");
			query.setParameter("numero", numero);
			//Como nao pode haver mais de uma mesa com o mesmo numero, deve retornar sempre um unico resultado.
			mesa = (Mesa) query.getSingleResult();
		}catch (NoResultException e){
			return null;
		}catch (Exception e) {
			throw new BaseException(MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_FIND_MESA_NUMERO), e);
		}finally{
			em.close();
		}
		
		return mesa;
	}

	public List<MesaDTO> getMesas() throws BaseException{
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager em = null;
		List<MesaDTO> mesas = null;
		
		try {
			em = emf.createEntityManager();
			List<Mesa> lista = em.createQuery("select mesa from Mesa mesa", Mesa.class).getResultList();
			mesas = new LinkedList<MesaDTO>();
			for (Mesa item : lista) {
				if (item.getAtiva() == 1){
					MesaDTO mesa = new MesaDTO();
					BeanUtils.copyProperties(mesa, item);
					mesa.setStatus(item.getStatus().ordinal());
					
					switch (item.getStatus()){
						case LIVRE:{
							mesa.setCssMesa("mesa_livre");
							mesa.setOcupada(false);
							break;
						}
						case OCUPADA: {
							mesa.setCssMesa("mesa_ocupada");
							mesa.setOcupada(true);
							break;
						}
					}
					
					List<MesaOcupacao> ocupacoes = item.getOcupacoes();
					
					for (MesaOcupacao mesaOcupacao : ocupacoes) {
						if (mesaOcupacao.getFechamento() == null){
							mesa.setIdOcupacao(mesaOcupacao.getId());
						}
					}
					
					mesas.add(mesa);					
				}
			}
		} catch (Exception e) {
			throw new BaseException("Erro ao recuperar mesas.", e);
		}finally{
			em.close();
		}
		
		Collections.sort(mesas, new MesaNumeroComparable());

		return mesas;
	}

	public List<MesaDTO> abrirMesa(MesaDTO mesaDTO) throws BaseException {
		LoggerApp.debug("Abrindo mesa: " + mesaDTO.getId());
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			MesaOcupacao ocupacao = new MesaOcupacao();
			
			Mesa mesa = em.find(Mesa.class, Integer.valueOf(mesaDTO.getId()));
		
			ocupacao.setMesa(mesa);
			ocupacao.setAbertura(Calendar.getInstance().getTime());

			em.persist(ocupacao);
			mesa.setStatus(StatusMesa.OCUPADA);
			em.merge(mesa);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new BaseException("Erro ao abrir mesa.", e);
		}finally{
			em.close();
		}
		
		return this.getMesas();
	}

	public List<MesaDTO> fecharMesa(MesaDTO mesaDTO) throws BaseException {
		LoggerApp.debug("Fechando mesa: " + mesaDTO.getId());
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			MesaOcupacao ocupacao = em.find(MesaOcupacao.class, Integer.valueOf(mesaDTO.getIdOcupacao()));
			
			Mesa mesa = em.find(Mesa.class, Integer.valueOf(mesaDTO.getId()));
		
			ocupacao.setFechamento(Calendar.getInstance().getTime());
			ocupacao.setTotal(MesaUtil.calculaTotalMesa(ocupacao));

			em.merge(ocupacao);
			mesa.setStatus(StatusMesa.LIVRE);
			em.merge(mesa);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new BaseException("Erro ao fechar mesa.", e);
		}finally{
			em.close();
		}
		return this.getMesas();
	}
	
	public void addMesaExtra(MesaDTO mesaDTO) throws BaseException {
		LoggerApp.debug("Adicionando mesa: " + mesaDTO);
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager em = null;
		
		Mesa mesaExtra = null;
		int countExtras = 1;
		List<Mesa> mesas = this.findMesasExtrasByNumeroPrincipal(mesaDTO.getNumero());
		if (mesas != null && mesas.size()> 0){
			//caso seja preciso criar uma nova mesa extra, esta nova tera o tamanho da lista incrementado de um
			countExtras = mesas.size() + 1;
			//verifica se existe uma mesa extra inativa
			//se nao existir, vai criar uma nova
			for (Mesa mesa : mesas) {
				if (mesa.getAtiva() == 0){
					mesaExtra = mesa;
					break;
				}
			}
		}
		
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			if (mesaExtra != null){//reativa a mesa
				mesaExtra.setAtiva(1);
				em.merge(mesaExtra);
			}else{
				mesaExtra = new Mesa();
				mesaExtra.setNumero(mesaDTO.getNumero() + "E" + countExtras);
				mesaExtra.setExtra(mesaDTO.getExtra());
				mesaExtra.setStatus(StatusMesa.LIVRE);
				mesaExtra.setAtiva(1);
				
				em.persist(mesaExtra);
			}

			em.getTransaction().commit();
		} catch (Exception e) {
			throw new BaseException(MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_SALVAR_MESA), e);
		}finally{
			em.close();
		}
		
		LoggerApp.debug("Mesa adicionada: " + mesaExtra);
	}
	
	public void excluirMesa(MesaDTO mesaDTO) throws BaseException {
		LoggerApp.debug("Excluindo mesa: " + mesaDTO.getId());
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			//Testa se a mesa est‡ ocupada
			if (mesaDTO.getIdOcupacao() != null){
				MesaOcupacao ocupacao = em.find(MesaOcupacao.class, Integer.valueOf(mesaDTO.getIdOcupacao()));
				
				//Nao pode excluir uma mesa que est‡ sendo ocupada.
				if (ocupacao != null && ocupacao.getFechamento() == null){
					throw new BaseException(MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_DEL_MESA) + MessagesUtil.getInstance().getWebMessage(MessagesUtil.AVISO_MESA_OCUPADA));
				}
			}
			
			Mesa mesa = em.find(Mesa.class, Integer.valueOf(mesaDTO.getId()));
			mesa.setAtiva(0);
			em.merge(mesa);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new BaseException(MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_DEL_MESA), e);
		}finally{
			em.close();
		}
	}
	
	private List<Mesa> findMesasExtrasByNumeroPrincipal(String numero) throws BaseException {
		if (numero == null){
			return null;
		}
		LoggerApp.debug("Find mesa pelo numero: " + numero);
		EntityManager em = null;
		List<Mesa> mesas = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			Query query = em.createQuery("from Mesa as m where m.numero  like :numero and m.extra = 1 order by m.numero");
			query.setParameter("numero", "%" + numero + "%");
			//Como nao pode haver mais de uma mesa com o mesmo numero, deve retornar sempre um unico resultado.
			mesas = query.getResultList();
		}catch (Exception e) {
			throw new BaseException(MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_FIND_MESA_NUMERO), e);
		}finally{
			em.close();
		}
		
		return mesas;
	}	
}

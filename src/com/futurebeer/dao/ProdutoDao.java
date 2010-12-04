package com.futurebeer.dao;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.futurebeer.dao.interfaces.IProdutoDao;
import com.futurebeer.dto.ProdutoDTO;
import com.futurebeer.entity.PersistenceManager;
import com.futurebeer.entity.Produto;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.LoggerApp;

public class ProdutoDao implements IProdutoDao {

	public Produto findById(Integer idProduto) throws BaseException {
		LoggerApp.debug("produto - findById: " + idProduto);
		EntityManager em = null;
		Produto produto = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			produto = em.find(Produto.class, idProduto);
		} catch (Exception e) {
			throw new BaseException("Erro ao recuperar produto pelo id: " + idProduto, e);
		}finally{
			em.close();
		}

		return produto;
	}
	
	public List<ProdutoDTO> getProdutos() throws BaseException{
		EntityManager em = null;
//		Session session = null;
		List<ProdutoDTO> produtos = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			//session = (Session)em.getDelegate();
			//List<Produto> lista = session.createCriteria(Produto.class).list();
			Query query = em.createQuery("select produto from Produto produto", Produto.class);
			List<Produto> lista = query.getResultList();
			
			produtos = new LinkedList<ProdutoDTO>();
			for (Produto item : lista) {
				ProdutoDTO produto = new ProdutoDTO();
				produto.setIdProduto(item.getId());
				produto.setDescricao(item.getDescricao());
				produto.setTipo(item.getTipo());
				produto.setValor(item.getValor());
			
				produtos.add(produto);
			}
		} catch (Exception e) {
			throw new BaseException("Erro ao listar produtos.", e);
		}finally{
//			session.close();
			em.close();
		}
		
		return produtos;
	}

	public Produto cadastrarProduto(ProdutoDTO produtoDTO) throws BaseException {
		LoggerApp.debug("Adicionando produto: " + produtoDTO);
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager em = null;
		Produto produto = new Produto();
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			produto.setDescricao(produtoDTO.getDescricao());
			produto.setTipo(produtoDTO.getTipo());
			produto.setValor(produtoDTO.getValor());
			
			em.persist(produto);

			em.getTransaction().commit();
		} catch (Exception e) {
			throw new BaseException("Erro ao abrir mesa.", e);
		}finally{
			em.close();
		}
		
		LoggerApp.debug("Produto adicionado: " + produto);

		return produto;
	}	

	public Produto atualizarProduto(ProdutoDTO produtoDTO) throws BaseException {
		LoggerApp.debug("Atualizando produto: " + produtoDTO);
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager em = null;
		Produto produto = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			produto = em.find(Produto.class, produtoDTO.getIdProduto());
			produto.setDescricao(produtoDTO.getDescricao());
			produto.setTipo(produtoDTO.getTipo());
			produto.setValor(produtoDTO.getValor());
			
			em.merge(produto);
			
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new BaseException("Erro ao abrir mesa.", e);
		}finally{
			em.close();
		}
		
		LoggerApp.debug("Produto atualizado: " + produto);
		
		return produto;
	}	
}

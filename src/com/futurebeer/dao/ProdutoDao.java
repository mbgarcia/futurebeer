package com.futurebeer.dao;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.futurebeer.dao.interfaces.IProdutoDao;
import com.futurebeer.dto.ProdutoDTO;
import com.futurebeer.entity.PersistenceManager;
import com.futurebeer.entity.Produto;
import com.futurebeer.exception.BaseException;
import com.futurebeer.util.LoggerApp;
import com.futurebeer.util.MessagesUtil;

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
			throw new BaseException(MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_FIND_PROD_ID), e);
		}finally{
			em.close();
		}

		return produto;
	}

	/**
	 * Considera-se que nao pode haver dois ou mais produtos com o mesmo nome.
	 * 
	 * @param descricao
	 * @return
	 * @throws BaseException
	 */
	public Produto findByDescricao(String descricao) throws BaseException {
		if (descricao == null){
			return null;
		}
		LoggerApp.debug("Find produto pela descicao: " + descricao);
		EntityManager em = null;
		Produto produto = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			Query query = em.createQuery("from Produto as p where p.descricao = lower(:desc)");
			query.setParameter("desc", descricao.toLowerCase());
			//Como nao pode haver mais de um produto com o mesmo nome, deve retornar sempre um unico resultado.
			produto = (Produto) query.getSingleResult();
		}catch (NoResultException e){
			return null;
		}catch (Exception e) {
			throw new BaseException(MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_FIND_PROD_DESC), e);
		}finally{
			em.close();
		}
		
		return produto;
	}
	
	public List<ProdutoDTO> getProdutos() throws BaseException{
		EntityManager em = null;
		List<ProdutoDTO> produtos = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			Query query = em.createQuery("select produto from Produto produto", Produto.class);
			List<Produto> lista = query.getResultList();
			
			produtos = new LinkedList<ProdutoDTO>();
			for (Produto item : lista) {
				if (item.getAtivo() == 1){
					ProdutoDTO produto = new ProdutoDTO();
					produto.setIdProduto(item.getId());
					produto.setDescricao(item.getDescricao());
					produto.setTipo(item.getTipo());
					produto.setValor(item.getValor());
					
					produtos.add(produto);
				}
			}
		} catch (Exception e) {
			throw new BaseException(MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_LIST_PROD), e);
		}finally{
			em.close();
		}
		
		return produtos;
	}

	public Produto cadastrarProduto(ProdutoDTO produtoDTO) throws BaseException {
		LoggerApp.debug("Adicionando produto: " + produtoDTO);
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager em = null;
		
		Produto produto = this.findByDescricao(produtoDTO.getDescricao());
		if (produto != null && produto.getAtivo() == 1){
			throw new BaseException(MessagesUtil.getInstance().getWebMessage(MessagesUtil.AVISO_EXISTE_PRODUTO_COM_DESC));
		}
		
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			if (produto != null && produto.getAtivo() == 0){//reativa o produto
				produto.setAtivo(1);
				produto.setTipo(produtoDTO.getTipo());
				produto.setValor(produtoDTO.getValor());
				em.merge(produto);
			}else{
				produto = new Produto();
				produto.setDescricao(produtoDTO.getDescricao());
				produto.setTipo(produtoDTO.getTipo());
				produto.setValor(produtoDTO.getValor());
				produto.setAtivo(1);
				
				em.persist(produto);
			}

			em.getTransaction().commit();
		} catch (Exception e) {
			throw new BaseException(MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_SALVAR_PROD), e);
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
		Produto produto = this.findByDescricao(produtoDTO.getDescricao());
		if (produto != null){
			//realiza a validacao de descricao somente se for um produto diferente
			if (produto.getId().intValue() != produtoDTO.getIdProduto().intValue()){
				if (produto.getAtivo() == 1){
					throw new BaseException(MessagesUtil.getInstance().getWebMessage(MessagesUtil.AVISO_EXISTE_PRODUTO_COM_DESC));
				}else{
					throw new BaseException(MessagesUtil.getInstance().getWebMessage(MessagesUtil.AVISO_EXISTE_PRODUTO_COM_DESC_INATIVO));
				}
			}
		}

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
			throw new BaseException(MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_SALVAR_PROD), e);
		}finally{
			em.close();
		}
		
		LoggerApp.debug("Produto atualizado: " + produto);
		
		return produto;
	}
	
	public void deleteProduto(Integer idProduto) throws BaseException{
		LoggerApp.debug("Exclusao do produto: "  + idProduto);
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager em = null;
		Produto produto = null;
		try {
			if (idProduto != null && idProduto != 0){
				em = emf.createEntityManager();
				em.getTransaction().begin();
				produto = em.find(Produto.class, idProduto);
				if (produto != null){
					produto.setAtivo(0);
					em.merge(produto);
				}
					
				em.getTransaction().commit();
			}
		} catch (Exception e) {
			throw new BaseException(MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_DEL_PROD), e);
		}finally{
			if (em != null){
				em.close();
			}
		}
	}
}

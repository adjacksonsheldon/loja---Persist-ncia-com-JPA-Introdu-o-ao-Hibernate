package br.com.alura.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Produto;

public class ProdutoDao {

	private EntityManager em;

	public ProdutoDao(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Produto produto) {
		this.em.persist(produto);
	}

	public void atualizar(Produto produto) {
		this.em.merge(produto);
	}

	public void remover(Produto produto) {
		produto = this.em.merge(produto);
		this.em.remove(produto);
	}
	
	public Produto buscarPorId(Long id) {
		return em.find(Produto.class, id);
	}
	
	public List<Produto> buscarTodos(){
		String jqpl = "select p from Produto p ";
		return em.createQuery(jqpl, Produto.class).getResultList();
	}

	public List<Produto> buscarPorNome(String nome){
		String jqpl = "select p from Produto p where p.nome = :nome";
		
		return em.createQuery(jqpl, Produto.class)
				.setParameter("nome", nome)
				.getResultList();
	}

	public List<Produto> buscarPorNomeDaCategoria(String nome){
		String jqpl = "select p from Produto p where p.categoria.nome = :nome";
		
		return em.createQuery(jqpl, Produto.class)
				.setParameter("nome", nome)
				.getResultList();
	}
}
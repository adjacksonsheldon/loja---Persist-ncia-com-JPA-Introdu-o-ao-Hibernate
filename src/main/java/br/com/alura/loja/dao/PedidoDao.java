package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.RelatorioVendasDTO;

public class PedidoDao {

	/*
	 * Não tem injeção de dependência pois o foco do curso/projeto era a utilização do JPA puro.
	 */
	private EntityManager em;

	public PedidoDao(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}

	public void atualizar(Pedido pedido) {
		this.em.merge(pedido);
	}

	public void remover(Pedido pedido) {
		pedido = this.em.merge(pedido);
		this.em.remove(pedido);
	}
	
	public Pedido buscarPorId(Long id) {
		return em.find(Pedido.class, id);
	}
	
	public BigDecimal valorTotalVendido() {
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";		
		return em.createQuery(jpql, BigDecimal.class).getSingleResult();
	}
	
	public List<RelatorioVendasDTO> relatorioVendas() {
		String jpql = "SELECT new br.com.alura.loja.modelo.RelatorioVendasDTO("
							 + "produto.nome, "
							 + "SUM(item.quantidade), "
							 + "MAX(pedido.data)) "
					  + "FROM Pedido pedido "
					  + "JOIN pedido.itens item "
					  + "JOIN item.produto produto " 
					  + "GROUP BY produto.nome "
					  + "ORDER BY item.quantidade DESC";
		
		return em.createQuery(jpql, RelatorioVendasDTO.class).getResultList();
	}
	
	public Pedido buscarPedidoComCliente(Long id) {
		String jpql = "SELECT p FROM Pedido p FETCH JOIN p.cliente WHERE p.id = :id";		
		return this.em.createQuery(jpql, Pedido.class).getSingleResult();
	}
}
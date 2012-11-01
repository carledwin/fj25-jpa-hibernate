package br.com.caelum.financas.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.modelo.ValorPorMesEAno;

public class MovimentacaoDAO {
	private final EntityManager em;
	private final DAO<Movimentacao> dao;

	public MovimentacaoDAO(EntityManager em) {
		this.em = em;
		dao = new DAO<Movimentacao>(em,Movimentacao.class);			
	}

	public void adiciona(Movimentacao t) {
		dao.adiciona(t);
	}
	
	public void altera(Movimentacao t) {
		dao.altera(t);
	}

	public Movimentacao busca(Integer id) {
		return dao.busca(id);
	}

	public List<Movimentacao> lista() {
		return dao.lista();
	}

	public void remove(Movimentacao t) {
		dao.remove(t);
	}
	
	public List<Movimentacao> listaTodasMovimentacoes(Conta conta) {
		String jpql = "select m from Movimentacao m " + 
					  "where m.conta = :conta order by m.valor desc ";
		Query qry = this.em.createQuery(jpql);
		qry.setParameter("conta", conta);
		
		return qry.getResultList();
	}
	
	public List<Movimentacao> listaPorValorETipo(BigDecimal valor, TipoMovimentacao tipo) {
		String jpql = "select m from Movimentacao m " + 
				  		"where m.valor <= :valor and m.tipoMovimentacao = :tipo order by m.valor desc ";
		Query qry = this.em.createQuery(jpql);
		qry.setParameter("valor", valor);
		qry.setParameter("tipo", tipo);
	
		return qry.getResultList();
	}
	
	public BigDecimal calculaTotalMovimentado(Conta conta, TipoMovimentacao tipo) {
		String jpql = "select sum(m.valor) from Movimentacao m where " + 
						"m.conta = :conta and m.tipoMovimentacao = :tipo";
		TypedQuery<BigDecimal> query = this.em.createQuery(jpql, BigDecimal.class);
		query.setParameter("conta", conta);
		query.setParameter("tipo", tipo);
		return query.getSingleResult();
	}
	
	public List<Movimentacao> buscaTodasMovimentacoesDaConta(String titular) {
		String jpql = "select m from Movimentacao m where m.conta.titular like :titular";
		TypedQuery<Movimentacao> query = this.em.createQuery(jpql, Movimentacao.class);
		query.setParameter("titular", "%" + titular + "%");
		return query.getResultList();
	}
	
	public List<ValorPorMesEAno> listaMesesComMovimentacoes(Conta conta, TipoMovimentacao tipo) {
		String jpql = "select " + 
					  "new br.com.caelum.financas.modelo.ValorPorMesEAno(month(m.data), year(m.data), sum(m.valor)) " +
					  "from Movimentacao m " +
					  "where m.conta=:conta and m.tipoMovimentacao=:tipo " + 
					  "group by year(m.data)||month(m.data) " +
					  "order by sum(m.valor) desc";
		Query query = this.em.createQuery(jpql);
		query.setParameter("conta", conta);
		query.setParameter("tipo", tipo);

		return query.getResultList();		
	}
	
	public List<Movimentacao> buscaMovimentacoesBaseadoNasTags(String texto) {
		FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
		QueryParser parser = new QueryParser(Version.LUCENE_29, "tags.nome",
				new BrazilianAnalyzer(Version.LUCENE_29));
		
		try {
			org.apache.lucene.search.Query query = parser.parse(texto);
			FullTextQuery txtQuery = ftem.createFullTextQuery(query, Movimentacao.class);
			return txtQuery.getResultList();
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
}

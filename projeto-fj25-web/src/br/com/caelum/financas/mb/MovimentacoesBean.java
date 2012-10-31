package br.com.caelum.financas.mb;

import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;

import br.com.caelum.financas.dao.ContaDAO;
import br.com.caelum.financas.dao.MovimentacaoDAO;
import br.com.caelum.financas.infra.JPAUtil;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;


@ManagedBean
public class MovimentacoesBean {
	private List<Movimentacao> movimentacoes;
	private Movimentacao movimentacao = new Movimentacao();
	private Integer contaId;
	private String tags;
//	@ManagedProperty(name="em",value="#{requestScope.em}")
//	private EntityManager em;
	
//	public void setEm(EntityManager em) {
//		this.em = em;
//	}
	
	public void grava() {
		EntityManager em = new JPAUtil().getEntityManager();
		MovimentacaoDAO dao = new MovimentacaoDAO(em);
		ContaDAO cdao = new ContaDAO(em);
		Conta conta = cdao.busca(contaId);
		
		em.getTransaction().begin();
		
    	movimentacao.setConta(conta);
		dao.adiciona(movimentacao);
		movimentacoes = dao.lista();
		em.getTransaction().commit();
		em.close();

		limpaFormularioDoJSF();
	}
	

	public void remove() {
		EntityManager em = new JPAUtil().getEntityManager();
		MovimentacaoDAO dao = new MovimentacaoDAO(em);
		
		em.getTransaction().begin();
    	Movimentacao movRemover = dao.busca(this.movimentacao.getId());
		dao.remove(movRemover);
		movimentacoes = dao.lista();
		em.getTransaction().commit();
		em.close();
		
		limpaFormularioDoJSF();
	}

	public List<Movimentacao> getMovimentacoes() {
		if(movimentacoes == null) {
			EntityManager em = new JPAUtil().getEntityManager();
			MovimentacaoDAO dao = new MovimentacaoDAO(em);
			movimentacoes = dao.lista();
			em.close();
		}
		
		return movimentacoes;
	}
	
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	

	public Movimentacao getMovimentacao() {
		if(movimentacao.getData()==null) {
			movimentacao.setData(Calendar.getInstance());
		}
		return movimentacao;
	}

	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}

	public Integer getContaId() {
		return contaId;
	}

	public void setContaId(Integer contaId) {
		this.contaId = contaId;
	}

	/**
	 * Esse método apenas limpa o formulário da forma com que o JSF espera.
	 * Invoque-o no momento em que precisar do formulário vazio.
	 */
	private void limpaFormularioDoJSF() {
		this.movimentacao = new Movimentacao();
		this.tags = null;
	}

	public TipoMovimentacao[] getTiposDeMovimentacao() {
		return TipoMovimentacao.values();
	}
}

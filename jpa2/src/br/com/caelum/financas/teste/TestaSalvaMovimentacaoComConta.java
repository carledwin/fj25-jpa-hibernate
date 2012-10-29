package br.com.caelum.financas.teste;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.EntityManager;

import br.com.caelum.financas.infra.JPAUtil;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;

public class TestaSalvaMovimentacaoComConta {
	
	public static void main(String[] args) {
		EntityManager em = new JPAUtil().getEntityManager();
	
		em.getTransaction().begin();
		Conta conta = new Conta();
		conta.setBanco("Banco Santander");
		conta.setNumero("10100-2");
		conta.setAgencia("2234");
		conta.setTitular("Maria");
		em.persist(conta);
		
		Movimentacao mv = new Movimentacao();
		mv.setConta(conta);
		mv.setData(Calendar.getInstance());
		mv.setDescricao("Movimentacao teste");
		mv.setValor(new BigDecimal("10000"));
		mv.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		
		em.persist(mv);
		em.getTransaction().commit();
		em.close();
	}
}

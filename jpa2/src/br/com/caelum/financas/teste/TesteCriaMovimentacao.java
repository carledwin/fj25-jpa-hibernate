package br.com.caelum.financas.teste;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.EntityManager;

import br.com.caelum.financas.infra.JPAUtil;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;

public class TesteCriaMovimentacao {
	public static void main(String[] args) {
		EntityManager em = new JPAUtil().getEntityManager();

		
		em.getTransaction().begin();
		
		Conta conta = new Conta();
		conta.setTitular("José Roberto");
		conta.setBanco("Banco do Brasil");
		conta.setNumero("123456-6");
		conta.setAgencia("0999");
		em.persist(conta);
		
		Movimentacao mv = new Movimentacao();
		mv.setConta(conta);
		mv.setData(Calendar.getInstance());
		mv.setDescricao("Movimentacao Teste");
		mv.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		mv.setValor(new BigDecimal(10000.0));
		
		em.persist(mv);
		em.getTransaction().commit();
		em.close();
	}
}

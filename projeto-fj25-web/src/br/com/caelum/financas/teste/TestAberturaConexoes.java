package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;

import br.com.caelum.financas.infra.JPAUtil;

public class TestAberturaConexoes {
	public static void main(String[] args) throws InterruptedException {
		for (int j = 0; j < 30; j++) {
			EntityManager em = new JPAUtil().getEntityManager();
			em.getTransaction().begin();
			System.out.println("criado EntityManager numero " + j);
		}
		
		Thread.sleep(30 * 1000);
	
	}
}

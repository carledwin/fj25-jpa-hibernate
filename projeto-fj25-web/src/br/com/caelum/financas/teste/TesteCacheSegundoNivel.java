package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;

import br.com.caelum.financas.infra.JPAUtil;
import br.com.caelum.financas.modelo.Conta;

public class TesteCacheSegundoNivel {
	public static void main(String[] args) {
		EntityManager em1 = new JPAUtil().getEntityManager();
		em1.getTransaction().begin();
		Conta conta1 = em1.find(Conta.class, 1);
		em1.getTransaction().commit();
		em1.close();
		
		EntityManager em2 = new JPAUtil().getEntityManager();
		Conta conta2 = em2.find(Conta.class, 1);
		em2.close();
		
		System.out.println("Titular 1: " + conta1.getTitular());
		System.out.println("Titular 2: " + conta2.getTitular());
	}
}

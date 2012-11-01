package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;

import br.com.caelum.financas.infra.JPAUtil;
import br.com.caelum.financas.modelo.Conta;

public class TesteCachePrimeiroNivel {
	public static void main(String[] args) {
		EntityManager em = new JPAUtil().getEntityManager();
		
		Conta primeiraConta = em.find(Conta.class, 1);
		Conta segundaConta = em.find(Conta.class, 1);
		
		System.out.println("Titular 1: " + primeiraConta.getTitular());
		System.out.println("Titular 2: " + segundaConta.getTitular());
	}
}

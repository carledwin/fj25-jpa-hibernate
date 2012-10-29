package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;

import br.com.caelum.financas.dao.ContaDAO;
import br.com.caelum.financas.infra.JPAUtil;
import br.com.caelum.financas.modelo.Conta;

public class TestePesquisaIdConta {
	public static void main(String[] args) {
		long inicio = System.currentTimeMillis();
		
		EntityManager entityManager = new JPAUtil().getEntityManager();
		ContaDAO dao = new ContaDAO(entityManager);
		
		entityManager.getTransaction().begin();
		Conta encontrado = dao.busca(3);
		entityManager.getTransaction().commit();
		
		long fim = System.currentTimeMillis();
		System.out.println("Executado em: " + (fim - inicio) + " ms");
		System.out.println(encontrado.getTitular());
		entityManager.close();
	}

}


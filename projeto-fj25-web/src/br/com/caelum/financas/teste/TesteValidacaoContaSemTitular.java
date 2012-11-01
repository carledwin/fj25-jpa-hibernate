package br.com.caelum.financas.teste;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import br.com.caelum.financas.infra.ValidatorUtil;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;

public class TesteValidacaoContaSemTitular {
	
	public static void main(String[] args) {
		Validator validator = new ValidatorUtil().getValidator();
		
		Conta conta = new Conta();
		conta.setAgencia("123456");
		//Movimentacao m = new Movimentacao();
		//m.setValor(new BigDecimal("0.001"));
		
		
		Set<ConstraintViolation<Conta>> erros = validator.validate(conta);
		
		for (ConstraintViolation<Conta> err : erros) {
			System.out.println(err.getMessage());
		}

	}
}

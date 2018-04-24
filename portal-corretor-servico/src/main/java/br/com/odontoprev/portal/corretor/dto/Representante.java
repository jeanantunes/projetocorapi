package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class Representante implements Serializable {

	private static final long serialVersionUID = -4619514049007987187L;
	
	private String nome;
	private String cpf;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	

}

package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class ContatoEmpresa implements Serializable {

	private static final long serialVersionUID = 1727478594054336083L;

	private String celular;
	private String email;
	private String nome;
	private String telefone;
	
	private Corretora corretora;
	
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Corretora getCorretora() {
		return corretora;
	}
	public void setCorretora(Corretora corretora) {
		this.corretora = corretora;
	}
	
}

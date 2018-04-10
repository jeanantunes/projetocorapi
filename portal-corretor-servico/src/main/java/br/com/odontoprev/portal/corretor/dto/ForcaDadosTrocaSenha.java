package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class ForcaDadosTrocaSenha implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	private Long cdForcaVenda;
	private String nome;
	private String celular;
	private String email;
	
	public Long getCdForcaVenda() {
		return cdForcaVenda;
	}
	public void setCdForcaVenda(Long cdForcaVenda) {
		this.cdForcaVenda = cdForcaVenda;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
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
	
	@Override
	public String toString() {
		return "ForcaDadosTrocaSenha [cdForcaVenda=" + cdForcaVenda + ", nome=" + nome + ", celular=" + celular
				+ ", email=" + email + "]";
	}
	
	
}

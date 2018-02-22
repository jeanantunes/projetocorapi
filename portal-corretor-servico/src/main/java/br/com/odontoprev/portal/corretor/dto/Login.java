package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class Login implements Serializable {
	
	private static final long serialVersionUID = -5493233987085523214L;
	
	private String senha;

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}

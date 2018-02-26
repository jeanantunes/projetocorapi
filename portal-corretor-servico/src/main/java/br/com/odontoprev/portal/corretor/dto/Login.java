package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class Login implements Serializable {

	private static final long serialVersionUID = -5493233987085523214L;

	private String senha;
	private String usuario;

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}

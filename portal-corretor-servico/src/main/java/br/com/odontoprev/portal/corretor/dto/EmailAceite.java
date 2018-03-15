package br.com.odontoprev.portal.corretor.dto;

import java.util.List;

public class EmailAceite {

	private String nomeCorretor;
	private String nomeCorretora;
	private String nomeEmpresa;
	private String emailEnvio;
	private String token;
	private List<Plano> planos;

	public String getNomeCorretor() {
		return nomeCorretor;
	}

	public void setNomeCorretor(String nomeCorretor) {
		this.nomeCorretor = nomeCorretor;
	}

	public String getNomeCorretora() {
		return nomeCorretora;
	}

	public void setNomeCorretora(String nomeCorretora) {
		this.nomeCorretora = nomeCorretora;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getEmailEnvio() {
		return emailEnvio;
	}

	public void setEmailEnvio(String emailEnvio) {
		this.emailEnvio = emailEnvio;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<Plano> getPlanos() {
		return planos;
	}

	public void setPlanos(List<Plano> planos) {
		this.planos = planos;
	}

	@Override
	public String toString() {
		return "Email [nomeCorretor=" + nomeCorretor + ", nomeCorretora=" + nomeCorretora + ", nomeEmpresa="
				+ nomeEmpresa + ", emailEnvio=" + emailEnvio + ", token=" + token + ", planos=" + planos + "]";
	}
	
}

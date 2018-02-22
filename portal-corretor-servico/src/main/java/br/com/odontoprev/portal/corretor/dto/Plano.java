package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class Plano implements Serializable {

	private static final long serialVersionUID = 3449851941806562255L;

	private long cdPlano;
	private String titulo;
	private String descricao;
	private String sigla;
	private String valor;
	private String tipo;

	private long cdVenda;

	public long getCdPlano() {
		return cdPlano;
	}

	public void setCdPlano(long cdPlano) {
		this.cdPlano = cdPlano;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public long getCdVenda() {
		return cdVenda;
	}
	
	public void setCdVenda(long cdVenda) {
		this.cdVenda = cdVenda;
	}

}

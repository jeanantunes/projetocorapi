package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

//201809181556 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)
public class TipoBloqueio implements Serializable {
	private static final long serialVersionUID = 1L;

	private long cdTipoBloqueio;
	private String descricao;

	public TipoBloqueio() {
	}

	public long getCdTipoBloqueio() {
		return cdTipoBloqueio;
	}

	public void setCdTipoBloqueio(long cdTipoBloqueio) {
		this.cdTipoBloqueio = cdTipoBloqueio;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "TipoBloqueio [cdTipoBloqueio=" + cdTipoBloqueio + ", descricao=" + descricao + "]";
	}
	
}
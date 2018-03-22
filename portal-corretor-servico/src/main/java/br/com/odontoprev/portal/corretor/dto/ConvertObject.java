package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Date;

public class ConvertObject implements Serializable {
	
	private static final long serialVersionUID = 8967930898510022470L;
	
	private long cdJsonRequest;
	private String cdForcaVenda;
	private Date dtCriacao;
	private String json;
	
	public long getCdJsonRequest() {
		return cdJsonRequest;
	}
	public void setCdJsonRequest(long cdJsonRequest) {
		this.cdJsonRequest = cdJsonRequest;
	}
	public String getCdForcaVenda() {
		return cdForcaVenda;
	}
	public void setCdForcaVenda(String cdForcaVenda) {
		this.cdForcaVenda = cdForcaVenda;
	}
	public Date getDtCriacao() {
		return dtCriacao;
	}
	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
}

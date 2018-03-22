package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TBOD_JSON_REQUEST database table.
 * 
 */
@Entity
@Table(name="TBOD_JSON_REQUEST")
@NamedQuery(name="TbodJsonRequest.findAll", query="SELECT t FROM TbodJsonRequest t")
public class TbodJsonRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_JSON_REQUEST")
	private long cdJsonRequest;

	@Column(name="CD_FORCA_VENDA")
	private String cdForcaVenda;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_CRIACAO")
	private Date dtCriacao;

	private String json;

	public TbodJsonRequest() {
	}

	public long getCdJsonRequest() {
		return this.cdJsonRequest;
	}

	public void setCdJsonRequest(long cdJsonRequest) {
		this.cdJsonRequest = cdJsonRequest;
	}

	public String getCdForcaVenda() {
		return this.cdForcaVenda;
	}

	public void setCdForcaVenda(String cdForcaVenda) {
		this.cdForcaVenda = cdForcaVenda;
	}

	public Date getDtCriacao() {
		return this.dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getJson() {
		return this.json;
	}

	public void setJson(String json) {
		this.json = json;
	}

}

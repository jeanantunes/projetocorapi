package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the TBOD_JSON_REQUEST database table.
 * 
 */
@Entity
@Table(name = "TBOD_JSON_REQUEST")
@NamedQuery(name = "TbodJsonRequest.findAll", query = "SELECT t FROM TbodJsonRequest t")
public class TbodJsonRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_JSON_REQUEST", sequenceName = "SEQ_TBOD_JSON_REQUEST", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_JSON_REQUEST")
	@Column(name = "CD_JSON_REQUEST")
	private long cdJsonRequest;

	@Column(name = "CD_FORCA_VENDA")
	private long cdForcaVenda;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_CRIACAO")
	private Date dtCriacao;

	@Column(name = "JSON")
	private String json;

	@Column(name = "MODELO_CELULAR")
	private String modeloCelular;

	@Column(name = "URL")
	private String url;
	
	@Column(name="CD_CORRETORA")
	private long cdCorretora;

	public TbodJsonRequest() {
	}

	public long getCdJsonRequest() {
		return this.cdJsonRequest;
	}

	public void setCdJsonRequest(long cdJsonRequest) {
		this.cdJsonRequest = cdJsonRequest;
	}

	public long getCdForcaVenda() {
		return cdForcaVenda;
	}

	public void setCdForcaVenda(long cdForcaVenda) {
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

	public String getModeloCelular() {
		return modeloCelular;
	}

	public void setModeloCelular(String modeloCelular) {
		this.modeloCelular = modeloCelular;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getCdCorretora() {
		return cdCorretora;
	}

	public void setCdCorretora(long cdCorretora) {
		this.cdCorretora = cdCorretora;
	}
	
}

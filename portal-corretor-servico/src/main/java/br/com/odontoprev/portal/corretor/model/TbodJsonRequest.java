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
	private Long cdJsonRequest; //201810301710 - esert - alterado de long para Long conforme implementacoes atuais

	@Column(name = "CD_FORCA_VENDA")
	private String cdForcaVenda; //201810301710 - esert - alterado de long para String compativel com base //apesar que estava diferente desde criacao tabela em 2018-05-24 11:55:44

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
	private Long cdCorretora;

	//201806121714 - esert - inclusao campo para salvar header
	@Column(name="HEADER")
	private String header;

	//201806121714 - esert - inclusao campo para salvar parameter
	@Column(name="PARAMETER")
	private String parameter;

	public TbodJsonRequest() {
	}

	public Long getCdJsonRequest() {
		return this.cdJsonRequest;
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

	public Long getCdCorretora() {
		return cdCorretora;
	}
	public void setCdCorretora(Long cdCorretora) {
		this.cdCorretora = cdCorretora;
	}

	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}

	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Override
	public String toString() {
		return "TbodJsonRequest [" 
				+ "cdJsonRequest=" + cdJsonRequest 
				+ ", cdForcaVenda=" + cdForcaVenda 
				+ ", dtCriacao=" + dtCriacao 
				+ ", json=" + json 
				+ ", modeloCelular=" + modeloCelular 
				+ ", url=" + url 
				+ ", cdCorretora=" + cdCorretora 
				+ ", header=" + header 
				+ ", parameter=" + parameter 
				+ "]";
	}
}

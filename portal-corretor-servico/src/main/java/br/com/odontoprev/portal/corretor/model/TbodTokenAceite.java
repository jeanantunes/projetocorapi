package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TBOD_TOKEN_ACEITE database table.
 * 
 */
@Entity
@Table(name="TBOD_TOKEN_ACEITE")
@NamedQuery(name="TbodTokenAceite.findAll", query="SELECT t FROM TbodTokenAceite t")
public class TbodTokenAceite implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TbodTokenAceitePK id;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_ACEITE")
	private Date dtAceite;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_ENVIO")
	private Date dtEnvio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_EXPIRACAO")
	private Date dtExpiracao;

	@Column(name="EMAIL_ENVIO")
	private String emailEnvio;

	private String ip;

	//bi-directional many-to-one association to TbodVenda
	@ManyToOne
	@JoinColumn(name="CD_VENDA", insertable=false, updatable=false)
	private TbodVenda tbodVenda;

	public TbodTokenAceite() {
	}

	public TbodTokenAceitePK getId() {
		return this.id;
	}

	public void setId(TbodTokenAceitePK id) {
		this.id = id;
	}

	public Date getDtAceite() {
		return this.dtAceite;
	}

	public void setDtAceite(Date dtAceite) {
		this.dtAceite = dtAceite;
	}

	public Date getDtEnvio() {
		return this.dtEnvio;
	}

	public void setDtEnvio(Date dtEnvio) {
		this.dtEnvio = dtEnvio;
	}

	public Date getDtExpiracao() {
		return this.dtExpiracao;
	}

	public void setDtExpiracao(Date dtExpiracao) {
		this.dtExpiracao = dtExpiracao;
	}

	public String getEmailEnvio() {
		return this.emailEnvio;
	}

	public void setEmailEnvio(String emailEnvio) {
		this.emailEnvio = emailEnvio;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public TbodVenda getTbodVenda() {
		return this.tbodVenda;
	}

	public void setTbodVenda(TbodVenda tbodVenda) {
		this.tbodVenda = tbodVenda;
	}

}

package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//201805221239 - esert - COR-225 - Serviço - LOG Envio e-mail de Boas Vindas PME
//Sugestão de nome para a tabela: TBOD_LOG_EMAIL_BOASVINDASPME 
@Entity
@Table(name = "TBOD_LOG_EMAIL_BOASVINDASPME")
@NamedQuery(name = "TbodLogEmailBoasVindasPME.findAll", query = "SELECT t FROM TbodLogEmailBoasVindasPME t")
public class TbodLogEmailBoasVindasPME implements Serializable {

	private static final long serialVersionUID = -5838590203922555875L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_LOG_EMAIL_BOASVINDASPME", sequenceName = "SEQ_TBOD_LOG_EMAIL_BOASVINDASPME", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_LOG_EMAIL_BOASVINDASPME")
	@Column(name = "CD_LOG_EMAIL_BOASVINDASPME")
	private Long cdLogin;

	//- Data do Envio 
	@Column(name = "DT_ENVIO")
	private Date dtEnvio;

	//- Email Destinatario 
	@Column(name = "EMAIL")
	private String email;

	//- Codigo da Empresa 
	@Column(name = "CD_EMPRESA")
	private Long cdEmpresa;
	
	//- Nome da Empresa
	@Column(name = "RAZAO_SOCIAL")
	private String razaoSocial;

	public TbodLogEmailBoasVindasPME() {
	}

	public Long getCdLogin() {
		return cdLogin;
	}
	public void setCdLogin(Long cdLogin) {
		this.cdLogin = cdLogin;
	}

	public Date getDtEnvio() {
		return dtEnvio;
	}
	public void setDtEnvio(Date dtEnvio) {
		this.dtEnvio = dtEnvio;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCdEmpresa() {
		return cdEmpresa;
	}
	public void setCdEmpresa(Long cdEmpresa) {
		this.cdEmpresa = cdEmpresa;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@Override
	public String toString() {
		return "TbodLogEmailBoasVindasPME [cdLogin=" + cdLogin + ", dtEnvio=" + dtEnvio + ", email=" + email
				+ ", cdEmpresa=" + cdEmpresa + ", razaoSocial=" + razaoSocial + "]";
	}

}
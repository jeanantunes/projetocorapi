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

/*
//2018061112117 - esert - nome reduzido de SEQ_TBOD_LOG_EMAIL_BOASVINDASPME(tam 32) para SEQ_TBOD_LOG_EMAIL_BVPME(tam 24) para nao passar de 30 trinta no nome dos objetos
//evitar Erro "ORA-00972: identifier is too long" (ORA-00972: identificador é muito longo)
//Ao tentar usar um objeto do banco de dados da Oracle (por exemplo, coluna ou tabela) com um nome mais longo que trinta (30) caracteres, o erro a seguir pode ocorrer:
*/

//201805221239 - esert - COR-225 - Serviço - LOG Envio e-mail de Boas Vindas PME
//Sugestão de nome para a tabela: TBOD_LOG_EMAIL_BOASVINDASPME 
@Entity
@Table(name = "TBOD_LOG_EMAIL_BVPME")
@NamedQuery(name = "TbodLogEmailBoasVindasPME.findAll", query = "SELECT t FROM TbodLogEmailBoasVindasPME t")
public class TbodLogEmailBoasVindasPME implements Serializable {

	private static final long serialVersionUID = -5838590203922555875L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_LOG_EMAIL_BVPME", sequenceName = "SEQ_TBOD_LOG_EMAIL_BVPME", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_LOG_EMAIL_BVPME")
	@Column(name = "ID")
	private Long id;

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

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
		return "TbodLogEmailBoasVindasPME [id=" + id + ", dtEnvio=" + dtEnvio + ", email=" + email + ", cdEmpresa="
				+ cdEmpresa + ", razaoSocial=" + razaoSocial + "]";
	}

}
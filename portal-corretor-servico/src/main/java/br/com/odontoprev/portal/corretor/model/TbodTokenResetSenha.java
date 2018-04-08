package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TBOD_TOKEN_RESET_SENHA database table.
 * 
 */
@Entity
@Table(name="TBOD_TOKEN_RESET_SENHA")
@NamedQuery(name="TbodTokenResetSenha.findAll", query="SELECT t FROM TbodTokenResetSenha t")
public class TbodTokenResetSenha implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_TOKEN_RESET_SENHA", sequenceName = "SEQ_TBOD_TOKEN_RESET_SENHA", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_TOKEN_RESET_SENHA")
	@Column(name = "ID")
	private long id;
	
	//@Column(name="CD_CORRETORA")
	//private long cdCorretora;
	
	@Column(name="CPF")
	private String cpf;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_ENVIO_EMAIL")
	private Date dataEnvioEmail;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_RESET_SENHA")
	private Date dataResetSenha;

	@Column(name="EMAIL_DESTINATARIO")
	private String emailDestinatario;

	private String token;

	/*//bi-directional many-to-one association to TbodCorretora
	@ManyToOne
	@JoinColumn(name="CD_CORRETORA")
	private TbodCorretora tbodCorretora;*/

	/*//bi-directional many-to-one association to TbodForcaVenda
	@ManyToOne
	@JoinColumn(name="CD_FORCA_VENDA")
	private TbodForcaVenda tbodForcaVenda;*/

	public TbodTokenResetSenha() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataEnvioEmail() {
		return this.dataEnvioEmail;
	}

	public void setDataEnvioEmail(Date dataEnvioEmail) {
		this.dataEnvioEmail = dataEnvioEmail;
	}

	public Date getDataResetSenha() {
		return this.dataResetSenha;
	}

	public void setDataResetSenha(Date dataResetSenha) {
		this.dataResetSenha = dataResetSenha;
	}

	public String getEmailDestinatario() {
		return this.emailDestinatario;
	}

	public void setEmailDestinatario(String emailDestinatario) {
		this.emailDestinatario = emailDestinatario;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}	

}
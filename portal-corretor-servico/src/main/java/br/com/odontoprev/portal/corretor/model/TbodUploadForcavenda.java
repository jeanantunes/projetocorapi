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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the TBOD_UPLOAD_FORCAVENDA database table.
 * 
 */
@Entity
@Table(name="TBOD_UPLOAD_FORCAVENDA")
@NamedQuery(name="TbodUploadForcavenda.findAll", query="SELECT t FROM TbodUploadForcavenda t")
public class TbodUploadForcavenda implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_UPLOAD_FORCAVENDA", sequenceName = "SEQ_TBOD_UPLOAD_FORCAVENDA",  allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_UPLOAD_FORCAVENDA")
	@Column(name="ID")
	private long id;

	@Column(name="CARGO")
	private String cargo;

	@Column(name="CD_CORRETORA")
	private Long cdCorretora;

	@Column(name="CELULAR")
	private String celular;

	@Column(name="CPF")
	private String cpf;

	@Column(name="CRITICA")
	private String critica;

	@Column(name="CSV_ARQUIVO")
	private String csvArquivo;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_CRIACAO")
	private Date dataCriacao;

	@Column(name="DATA_NASCIMENTO")
	private String dataNascimento;

	@Column(name="DEPARTAMENTO")
	private String departamento;

	@Column(name="EMAIL")
	private String email;

	@Column(name="NOME")
	private String nome;

	@Column(name="STATUS")
	private String status;

	public TbodUploadForcavenda() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Long getCdCorretora() {
		return cdCorretora;
	}

	public void setCdCorretora(Long cdCorretora) {
		this.cdCorretora = cdCorretora;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCritica() {
		return this.critica;
	}

	public void setCritica(String critica) {
		this.critica = critica;
	}

	public String getCsvArquivo() {
		return this.csvArquivo;
	}

	public void setCsvArquivo(String csvArquivo) {
		this.csvArquivo = csvArquivo;
	}

	public Date getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getDepartamento() {
		return this.departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
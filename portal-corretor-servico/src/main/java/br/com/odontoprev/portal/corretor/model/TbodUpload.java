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
 * The persistent class for the TBOD_UPLOAD_CORRETORA database table.
 * 
 */
@Entity
@Table(name="TBOD_UPLOAD_CORRETORA")
@NamedQuery(name="TbodUpload.findAll", query="SELECT t FROM TbodUpload t")
public class TbodUpload implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_TBOD_UPLOAD_CORRETORA", sequenceName = "SEQ_TBOD_UPLOAD_CORRETORA",  allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_UPLOAD_CORRETORA")
	@Column(name="ID")
	private long id;

	@Column(name="CNPJ")
	private String cnpj;
	
	@Column(name="ARQUIVO")
	private String arquivo;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_CRIACAO")
	private Date dataCriacao;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="CRITICA")
	private String critica;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="CPF")
	private String cpf;
	
	@Column(name="DATA_NASCIMENTO")
	private String dataNascimento;
	
	@Column(name="CELULAR")
	private String celular;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="DEPARTAMENTO")
	private String departamento;
	
	@Column(name="CARGO")
	private String cargo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCritica() {
		return critica;
	}

	public void setCritica(String critica) {
		this.critica = critica;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
}

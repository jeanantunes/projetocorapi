package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class Upload implements Serializable {

	private static final long serialVersionUID = -470343704625309491L;

	private String cnpj;
	private String arquivo;	
	private String dataCriacao;
	private String status;
	private String critica;
	private String nome;
	private String cpf;
	private String dataNascimento;
	private String celular;
	private String email;
	private String departamento;
	private String cargo;
	
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
	public String getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(String dataCriacao) {
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

package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class ForcaVenda implements Serializable {

	private static final long serialVersionUID = 3665956677976317178L;

	private String nome;
	private String celular;
	private String email;
	private Corretora corretora;
	private String cpf;
	private boolean ativo;
	private String departamento;
	private String cargo;
	private String dataNascimento;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public Corretora getCorretora() {
		return corretora;
	}

	public void setCorretora(Corretora corretora) {
		this.corretora = corretora;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
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

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public String toString() {
		return "ForcaVenda [nome=" + nome + ", celular=" + celular + ", email=" + email + ", corretora=" + corretora
				+ ", cpf=" + cpf + ", ativo=" + ativo + ", departamento=" + departamento + ", cargo=" + cargo
				+ ", dataNascimento=" + dataNascimento + "]";
	}

}

package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Date;

public class ForcaVenda implements Serializable {

	private static final long serialVersionUID = 3665956677976317178L;

	private String nome;
	private String celular;
	private String email;
	private Corretora corretora;
	private String statusForcaVenda;
	private String cpf;
	private boolean ativo;
	private String departamento;
	private String cargo;
	private Date dataNascimento;

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

	public String getStatusForcaVenda() {
		return statusForcaVenda;
	}

	public void setStatusForcaVenda(String statusForcaVenda) {
		this.statusForcaVenda = statusForcaVenda;
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

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public String toString() {
		return "ForcaVenda [nome=" + nome + ", celular=" + celular + ", email=" + email + ", corretora=" + corretora
				+ ", statusForcaVenda=" + statusForcaVenda + ", cpf=" + cpf + ", ativo=" + ativo + ", departamento="
				+ departamento + ", cargo=" + cargo + ", dataNascimento=" + dataNascimento + "]";
	}

}

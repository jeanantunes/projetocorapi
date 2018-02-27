package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class ForcaVenda implements Serializable {

	private static final long serialVersionUID = 3665956677976317178L;

	private Long cdForcaVenda;
	private String nome;
	private String celular;
	private String email;
	private Corretora corretora;
	private String statusForcaVenda;
	private String cpf;
	private boolean ativo;
	private String departamento;
	private String cargo;
	private String dataNascimento;
	private String nomeEmpresa;
	private String nomeGerente;
	private String responsavel;
	private String rg;
	private String senha;
	private String canalVenda;

	public Long getCdForcaVenda() {
		return cdForcaVenda;
	}

	public void setCdForcaVenda(Long cdForcaVenda) {
		this.cdForcaVenda = cdForcaVenda;
	}

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

	public String getStatusForcaVenda() {
		return statusForcaVenda;
	}

	public void setStatusForcaVenda(String statusForcaVenda) {
		this.statusForcaVenda = statusForcaVenda;
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

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getNomeGerente() {
		return nomeGerente;
	}

	public void setNomeGerente(String nomeGerente) {
		this.nomeGerente = nomeGerente;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCanalVenda() {
		return canalVenda;
	}

	public void setCanalVenda(String canalVenda) {
		this.canalVenda = canalVenda;
	}

	@Override
	public String toString() {
		return "ForcaVenda [cdForcaVenda=" + cdForcaVenda + ", nome=" + nome + ", celular=" + celular + ", email="
				+ email + ", corretora=" + corretora + ", statusForcaVenda=" + statusForcaVenda + ", cpf=" + cpf
				+ ", ativo=" + ativo + ", departamento=" + departamento + ", cargo=" + cargo + ", dataNascimento="
				+ dataNascimento + ", nomeEmpresa=" + nomeEmpresa + ", nomeGerente=" + nomeGerente + ", responsavel="
				+ responsavel + ", rg=" + rg + ", senha=" + senha + "]";
	}

}

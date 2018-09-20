package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Objects;

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
	private String status;
	private Login login;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return "ForcaVenda [cdForcaVenda=" + cdForcaVenda + ", nome=" + nome + ", celular=" + celular + ", email="
				+ email + ", corretora=" + corretora + ", statusForcaVenda=" + statusForcaVenda + ", cpf=" + cpf
				+ ", ativo=" + ativo + ", departamento=" + departamento + ", cargo=" + cargo + ", dataNascimento="
				+ dataNascimento + ", nomeEmpresa=" + nomeEmpresa + ", nomeGerente=" + nomeGerente + ", responsavel="
				+ responsavel + ", rg=" + rg + ", senha=" + senha + "]";
	}

	//201809201611 - esert - apenas move dos metodos pro fim da classe
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ForcaVenda)) return false;
		ForcaVenda that = (ForcaVenda) o;
		return isAtivo() == that.isAtivo() &&
				Objects.equals(getCdForcaVenda(), that.getCdForcaVenda()) &&
				Objects.equals(getNome(), that.getNome()) &&
				Objects.equals(getCelular(), that.getCelular()) &&
				Objects.equals(getEmail(), that.getEmail()) &&
				Objects.equals(getCorretora(), that.getCorretora()) &&
				Objects.equals(getStatusForcaVenda(), that.getStatusForcaVenda()) &&
				Objects.equals(getCpf(), that.getCpf()) &&
				Objects.equals(getDepartamento(), that.getDepartamento()) &&
				Objects.equals(getCargo(), that.getCargo()) &&
				Objects.equals(getDataNascimento(), that.getDataNascimento()) &&
				Objects.equals(getNomeEmpresa(), that.getNomeEmpresa()) &&
				Objects.equals(getNomeGerente(), that.getNomeGerente()) &&
				Objects.equals(getResponsavel(), that.getResponsavel()) &&
				Objects.equals(getRg(), that.getRg()) &&
				Objects.equals(getSenha(), that.getSenha()) &&
				Objects.equals(getCanalVenda(), that.getCanalVenda()) &&
				Objects.equals(getStatus(), that.getStatus()) &&
				Objects.equals(getLogin(), that.getLogin());
	}

	@Override
	public int hashCode() {

		return Objects.hash(getCdForcaVenda(), getNome(), getCelular(), getEmail(), getCorretora(), getStatusForcaVenda(), getCpf(), isAtivo(), getDepartamento(), getCargo(), getDataNascimento(), getNomeEmpresa(), getNomeGerente(), getResponsavel(), getRg(), getSenha(), getCanalVenda(), getStatus(), getLogin());
	}

}

package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class Corretora implements Serializable {

	private static final long serialVersionUID = 2180567984276302023L;

	private long cdCorretora;
	private String cnpj;
	private String razaoSocial;
	private String cnae;
	private String telefone;
	private String celular;
	private String email;
	private String statusCnpj;
	private String simplesNacional;
	private String dataAbertura;
	private Endereco enderecoCorretora;
	private Conta conta;
	private Login login;
	private List<Representante> representantes;

	public long getCdCorretora() {
		return cdCorretora;
	}

	public void setCdCorretora(long cdCorretora) {
		this.cdCorretora = cdCorretora;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnae() {
		return cnae;
	}

	public void setCnae(String cnae) {
		this.cnae = cnae;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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

	public String getStatusCnpj() {
		return statusCnpj;
	}

	public void setStatusCnpj(String statusCnpj) {
		this.statusCnpj = statusCnpj;
	}

	public String getSimplesNacional() {
		return simplesNacional;
	}

	public void setSimplesNacional(String simplesNacional) {
		this.simplesNacional = simplesNacional;
	}

	public String getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(String dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Endereco getEnderecoCorretora() {
		return enderecoCorretora;
	}

	public void setEnderecoCorretora(Endereco enderecoCorretora) {
		this.enderecoCorretora = enderecoCorretora;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public List<Representante> getRepresentantes() {
		return representantes;
	}

	public void setRepresentantes(List<Representante> representantes) {
		this.representantes = representantes;
	}

	@Override
	public String toString() {
		return "Corretora [cdCorretora=" + cdCorretora + ", cnpj=" + cnpj + ", razaoSocial=" + razaoSocial + ", cnae="
				+ cnae + ", telefone=" + telefone + ", celular=" + celular + ", email=" + email + ", statusCnpj="
				+ statusCnpj + ", simplesNacional=" + simplesNacional + ", dataAbertura=" + dataAbertura
				+ ", enderecoCorretora=" + enderecoCorretora + ", conta=" + conta + ", login=" + login
				+ ", representantes=" + representantes + "]";
	}

}

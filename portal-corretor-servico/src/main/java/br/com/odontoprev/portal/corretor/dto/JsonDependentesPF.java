package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class JsonDependentesPF implements Serializable {

	private static final long serialVersionUID = 3103017260669982091L;
		private Long cdVida;
	private Long cdTitular;
	private String celular;
	private String cpf;	
	private String dataNascimento;
	private String email;
	private String nome;
	private String nomeMae;
	private String sexo;
	private String pFpJ;
	private Boolean enderecoTitular;
	
	public Long getCdVida() {
		return cdVida;
	}
	public void setCdVida(Long cdVida) {
		this.cdVida = cdVida;
	}
	public Long getCdTitular() {
		return cdTitular;
	}
	public void setCdTitular(Long cdTitular) {
		this.cdTitular = cdTitular;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNomeMae() {
		return nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getpFpJ() {
		return pFpJ;
	}
	public void setpFpJ(String pFpJ) {
		this.pFpJ = pFpJ;
	}
	public Boolean getEnderecoTitular() {
		return enderecoTitular;
	}
	public void setEnderecoTitular(Boolean enderecoTitular) {
		this.enderecoTitular = enderecoTitular;
	}
	@Override
	public String toString() {
		return "[cdVida=" + cdVida + ", cdTitular=" + cdTitular + ", celular=" + celular + ", cpf="
				+ cpf + ", dataNascimento=" + dataNascimento + ", email=" + email + ", nome=" + nome + ", nomeMae="
				+ nomeMae + ", sexo=" + sexo + ", pFpJ=" + pFpJ + ", enderecoTitular=" + enderecoTitular + "]";
	}
	
}

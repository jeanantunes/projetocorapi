package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class JsonTitularesPME implements Serializable {

	private static final long serialVersionUID = 3103017260669982091L;
	
	private Long cdVida;
	private Long cdTitular;
	private String cnpj;	
	private String celular;
	private String cpf;
	private String dataNascimento;
	private String email;
	private String nome;
	private String nomeMae;
	private String pfPj;
	private String sexo;
	private Long cdPlano;
	private Long cdVenda;
	
	private JsonEnderecoPME endereco;
	
	private List<JsonDependentesPF> dependentes;

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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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

	public String getPfPj() {
		return pfPj;
	}

	public void setPfPj(String pfPj) {
		this.pfPj = pfPj;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Long getCdPlano() {
		return cdPlano;
	}

	public void setCdPlano(Long cdPlano) {
		this.cdPlano = cdPlano;
	}

	public Long getCdVenda() {
		return cdVenda;
	}

	public void setCdVenda(Long cdVenda) {
		this.cdVenda = cdVenda;
	}

	public JsonEnderecoPME getEndereco() {
		return endereco;
	}

	public void setEndereco(JsonEnderecoPME endereco) {
		this.endereco = endereco;
	}

	public List<JsonDependentesPF> getDependentes() {
		return dependentes;
	}

	public void setDependentes(List<JsonDependentesPF> dependentes) {
		this.dependentes = dependentes;
	}

	@Override
	public String toString() {
		return "[cdVida=" + cdVida + ", cdTitular=" + cdTitular + ", cnpj=" + cnpj + ", celular="
				+ celular + ", cpf=" + cpf + ", dataNascimento=" + dataNascimento + ", email=" + email + ", nome="
				+ nome + ", nomeMae=" + nomeMae + ", pfPj=" + pfPj + ", sexo=" + sexo + ", cdPlano=" + cdPlano
				+ ", cdVenda=" + cdVenda + ", endereco=" + endereco + ", dependentes=" + dependentes + "]";
	}

	
	
}

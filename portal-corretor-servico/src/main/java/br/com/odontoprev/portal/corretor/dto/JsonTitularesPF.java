package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class JsonTitularesPF implements Serializable {

	private static final long serialVersionUID = 3103017260669982091L;
	
	private String celular;
	private String cpf;	
	private String dataNascimento;
	private String email;
	private String nome;
	private String nomeMae;
	private String sexo;
	//private String status;
	//private Boolean contatoEmpresa;
	//private Boolean titular;	
		
	private DadosBancariosVenda dadosBancarios;
	
	private Endereco endereco;
	
	List<JsonDependentesPF> dependentes;

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

	public List<JsonDependentesPF> getDependentes() {
		return dependentes;
	}

	public void setDependentes(List<JsonDependentesPF> dependentes) {
		this.dependentes = dependentes;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}	

	public DadosBancariosVenda getDadosBancarios() {
		return dadosBancarios;
	}

	public void setDadosBancarios(DadosBancariosVenda dadosBancarios) {
		this.dadosBancarios = dadosBancarios;
	}

	@Override
	public String toString() {
		return "[celular=" + celular + ", cpf=" + cpf + ", dataNascimento=" + dataNascimento
				+ ", email=" + email + ", nome=" + nome + ", nomeMae=" + nomeMae + ", sexo=" + sexo
				+ ", dadosBancarios=" + dadosBancarios + ", endereco=" + endereco + ", dependentes=" + dependentes
				+ "]";
	}
	
}

package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

//h t t p s : / / s t a c k o v e r f l o w . c o m  /questions /11757487 /how-to-tell-jackson-to-ignore-a-field-during-serialization-if-its-value-is-null
@JsonInclude(Include.NON_NULL) //201807251320 - rmarq/esert - omitir nomes dos campos com valor nulo
public class Beneficiario implements Serializable {

	private static final long serialVersionUID = 3103017260669982091L;

	private Long cdVida; //201807251618 - esert - trocado tipo (long)(primitivo) por (Long)(objeto) para suportar (null) no JSON 
	private Long cdTitular; //201807251618 - esert - trocado tipo (long)(primitivo) por (Long)(objeto) para suportar (null) no JSON
	private String celular;
	private String cpf;
	private String cnpj;
	private String dataNascimento;
	private String email;
	private String nome;
	private String nomeMae;
	private String pfPj;
	private String sexo;
	private Long cdPlano; //201807251618 - esert - trocado tipo (long)(primitivo) por (Long)(objeto) para suportar (null) no JSON
	private Long cdVenda; //201807251618 - esert - trocado tipo (long)(primitivo) por (Long)(objeto) para suportar (null) no JSON
		
	private DadosBancariosVenda dadosBancarios;

	private Endereco endereco;
	
	List<Beneficiario> dependentes;

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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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
	
	public DadosBancariosVenda getDadosBancarios() {
		return dadosBancarios;
	}

	public void setDadosBancarios(DadosBancariosVenda dadosBancarios) {
		this.dadosBancarios = dadosBancarios;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Beneficiario> getDependentes() {
		return dependentes;
	}

	public void setDependentes(List<Beneficiario> dependentes) {
		this.dependentes = dependentes;
	}

	@Override
	public String toString() {
		return "Beneficiario [cdVida=" + cdVida + ", cdTitular=" + cdTitular + ", celular=" + celular + ", cpf=" + cpf
				+ ", cnpj=" + cnpj + ", dataNascimento=" + dataNascimento + ", email=" + email + ", nome=" + nome
				+ ", nomeMae=" + nomeMae + ", pfPj=" + pfPj + ", sexo=" + sexo + ", cdPlano=" + cdPlano + ", cdVenda="
				+ cdVenda + ", dadosBancarios=" + dadosBancarios + ", endereco=" + endereco + ", dependentes="
				+ dependentes + "]";
	}

}

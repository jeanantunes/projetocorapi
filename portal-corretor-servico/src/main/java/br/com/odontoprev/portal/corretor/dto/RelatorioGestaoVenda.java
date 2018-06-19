package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class RelatorioGestaoVenda implements Serializable {
	
	private static final long serialVersionUID = -5451115844138196171L;
	
	private String dataVenda;
	private String nome;
	private String cpf;
	private String dataNascimento;
	private String nomeMae;
	private String celular;
	private String email;
	private String logradouro;
	private String cep;
	private String cidade;
	private String complemento;
	private String bairro;
	private String uf;
	private String numero;
	private String nomePlano;
	private String tipoPlano;
	private String valorMensal;
	private String valorAnual;
	private String forcaNome; //201805301905 - esert/jack - COR-8 - ajuste
	
	public String getDataVenda() {
		return dataVenda;
	}
	public void setDataVenda(String dataVenda) {
		this.dataVenda = dataVenda;
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
	public String getNomeMae() {
		return nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
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
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNomePlano() {
		return nomePlano;
	}
	public void setNomePlano(String nomePlano) {
		this.nomePlano = nomePlano;
	}
	public String getTipoPlano() {
		return tipoPlano;
	}
	public void setTipoPlano(String tipoPlano) {
		this.tipoPlano = tipoPlano;
	}
	public String getValorMensal() {
		return valorMensal;
	}
	public void setValorMensal(String valorMensal) {
		this.valorMensal = valorMensal;
	}
	public String getValorAnual() {
		return valorAnual;
	}
	public void setValorAnual(String valorAnual) {
		this.valorAnual = valorAnual;
	}
	
	public String getForcaNome() {
		return forcaNome;
	}
	public void setForcaNome(String forcaNome) {
		this.forcaNome = forcaNome;
	}
	
	@Override
	public String toString() {
		return "RelatorioGestaoVenda [dataVenda=" + dataVenda + ", nome=" + nome + ", cpf=" + cpf + ", dataNascimento="
				+ dataNascimento + ", nomeMae=" + nomeMae + ", celular=" + celular + ", email=" + email
				+ ", logradouro=" + logradouro + ", cep=" + cep + ", cidade=" + cidade + ", complemento=" + complemento
				+ ", bairro=" + bairro + ", uf=" + uf + ", numero=" + numero + ", nomePlano=" + nomePlano
				+ ", tipoPlano=" + tipoPlano + ", valorMensal=" + valorMensal + ", valorAnual=" + valorAnual
				+ ", forcaNome=" + forcaNome + "]";
	}	
}

package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class JsonEmpresaPME implements Serializable {

	private static final long serialVersionUID = -4383849175380326234L;
	
	private String status;
	private String cnpj;
	private String cnae;	
	private String razaoSocial;
	private String incEstadual;
	private String ramoAtividade;
	private String nomeFantasia;
	private String representanteLegal;
	private boolean contatoEmpresa;
	private String telefone;
	private String celular;
	private String email;
	private String vencimentoFatura;
	private String cpfRepresentante;
	
	private JsonEnderecoPME enderecoEmpresa;
	private List<JsonPlanoPME> planos;	
	private JsonContatoEmpresaPME contactEmpresa;	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getCnae() {
		return cnae;
	}
	public void setCnae(String cnae) {
		this.cnae = cnae;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getIncEstadual() {
		return incEstadual;
	}
	public void setIncEstadual(String incEstadual) {
		this.incEstadual = incEstadual;
	}
	public String getRamoAtividade() {
		return ramoAtividade;
	}
	public void setRamoAtividade(String ramoAtividade) {
		this.ramoAtividade = ramoAtividade;
	}
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	public String getRepresentanteLegal() {
		return representanteLegal;
	}
	public void setRepresentanteLegal(String representanteLegal) {
		this.representanteLegal = representanteLegal;
	}
	public boolean isContatoEmpresa() {
		return contatoEmpresa;
	}
	public void setContatoEmpresa(boolean contatoEmpresa) {
		this.contatoEmpresa = contatoEmpresa;
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
	public String getVencimentoFatura() {
		return vencimentoFatura;
	}
	public void setVencimentoFatura(String vencimentoFatura) {
		this.vencimentoFatura = vencimentoFatura;
	}	
	public JsonEnderecoPME getEnderecoEmpresa() {
		return enderecoEmpresa;
	}
	public void setEnderecoEmpresa(JsonEnderecoPME enderecoEmpresa) {
		this.enderecoEmpresa = enderecoEmpresa;
	}	
	public JsonContatoEmpresaPME getContactEmpresa() {
		return contactEmpresa;
	}
	public void setContactEmpresa(JsonContatoEmpresaPME contactEmpresa) {
		this.contactEmpresa = contactEmpresa;
	}
	public List<JsonPlanoPME> getPlanos() {
		return planos;
	}
	public void setPlanos(List<JsonPlanoPME> planos) {
		this.planos = planos;
	}
	public String getCpfRepresentante() {
		return cpfRepresentante;
	}
	public void setCpfRepresentante(String cpfRepresentante) {
		this.cpfRepresentante = cpfRepresentante;
	}
	@Override
	public String toString() {
		return "[status=" + status + ", cnpj=" + cnpj + ", cnae=" + cnae + ", razaoSocial=" + razaoSocial
				+ ", incEstadual=" + incEstadual + ", ramoAtividade=" + ramoAtividade + ", nomeFantasia=" + nomeFantasia
				+ ", representanteLegal=" + representanteLegal + ", contatoEmpresa=" + contatoEmpresa + ", telefone="
				+ telefone + ", celular=" + celular + ", email=" + email + ", vencimentoFatura=" + vencimentoFatura
				+ ", cpfRepresentante=" + cpfRepresentante + ", enderecoEmpresa=" + enderecoEmpresa + ", planos="
				+ planos + ", contactEmpresa=" + contactEmpresa + "]";
	}
	
		
	
}

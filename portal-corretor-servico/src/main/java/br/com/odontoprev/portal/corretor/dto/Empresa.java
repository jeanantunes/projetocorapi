package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class Empresa implements Serializable {

	private static final long serialVersionUID = -6170025278181580898L;

//	private long cdEmpresa;
	private String cnpj;
	private String razaoSocial;
	private String incEstadual;
	private String ramoAtividade;
	private String nomeFantasia;
	private String representanteLegal;
	private boolean contatoEmpresa;
	private String telefone;
	private String celular;
	private String email;
	private long vencimentoFatura;
	private Endereco enderecoEmpresa;
	private List<Plano> planos;

//	public long getCdEmpresa() {
//		return cdEmpresa;
//	}
//
//	public void setCdEmpresa(long cdEmpresa) {
//		this.cdEmpresa = cdEmpresa;
//	}

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

	public Endereco getEnderecoEmpresa() {
		return enderecoEmpresa;
	}

	public void setEnderecoEmpresa(Endereco enderecoEmpresa) {
		this.enderecoEmpresa = enderecoEmpresa;
	}

	public long getVencimentoFatura() {
		return vencimentoFatura;
	}

	public void setVencimentoFatura(long vencimentoFatura) {
		this.vencimentoFatura = vencimentoFatura;
	}

	public List<Plano> getPlanos() {
		return planos;
	}

	public void setPlanos(List<Plano> planos) {
		this.planos = planos;
	}

	@Override
	public String toString() {
		return "Empresa [cnpj=" + cnpj + ", razaoSocial=" + razaoSocial + ", incEstadual="
				+ incEstadual + ", ramoAtividade=" + ramoAtividade + ", nomeFantasia=" + nomeFantasia
				+ ", representanteLegal=" + representanteLegal + ", contatoEmpresa=" + contatoEmpresa + ", telefone="
				+ telefone + ", celular=" + celular + ", email=" + email + ", enderecoEmpresa=" + enderecoEmpresa
				+ ", vencimentoFatura=" + vencimentoFatura + ", planos=" + planos + "]";
	}

}

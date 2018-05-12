package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Date;

public class CnpjDadosAceite implements Serializable { //2018051102036 - esert - COR-172  

	private static final long serialVersionUID = -4530821493004106650L;

	private String cnpj;
	private Long cdEmpresa;
	private String razaoSocial;
	private String observacao;
	private Long cdVenda;
	private Date dtVenda; //2018051111438 - esert - COR-172
	private String empDcms; //2018051102036 - esert - COR-169
	private TokenAceite tokenAceite;
	
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public Long getCdEmpresa() {
		return cdEmpresa;
	}
	public void setCdEmpresa(Long cdEmpresa) {
		this.cdEmpresa = cdEmpresa;
	}
	public Long getCdVenda() {
		return cdVenda;
	}
	public void setCdVenda(Long cdVenda) {
		this.cdVenda = cdVenda;
	}
	
	public Date getDtVenda() {
		return dtVenda;
	}
	public void setDtVenda(Date dtVenda) {
		this.dtVenda = dtVenda;
	}
	
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getEmpDcms() {
		return empDcms;
	}
	public void setEmpDcms(String empDcms) {
		this.empDcms = empDcms;
	}
	public TokenAceite getTokenAceite() {
		return tokenAceite;
	}
	public void setTokenAceite(TokenAceite tokenAceite) {
		this.tokenAceite = tokenAceite;
	}
	
	@Override
	public String toString() {
		return "CnpjDadosAceite [cnpj=" + cnpj + ", cdEmpresa=" + cdEmpresa + ", razaoSocial=" + razaoSocial
				+ ", observacao=" + observacao + ", cdVenda=" + cdVenda + ", dtVenda=" + dtVenda + ", empDcms="
				+ empDcms + ", tokenAceite=" + tokenAceite + "]";
	}
	
}

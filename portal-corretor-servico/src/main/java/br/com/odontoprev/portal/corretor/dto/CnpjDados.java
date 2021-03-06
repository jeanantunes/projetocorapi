package br.com.odontoprev.portal.corretor.dto;

public class CnpjDados {

	private Long cdEmpresa;
	private String cnpj;
	private String razaoSocial;
	private String observacao;
	private String empDcms; //201805102036 - esert - COR-169
	private String observacaoLoteDcms;

	public Long getCdEmpresa() {
		return cdEmpresa;
	}
	public void setCdEmpresa(Long cdEmpresa) {
		this.cdEmpresa = cdEmpresa;
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

	public String getObservacaoLoteDcms() {
		return observacaoLoteDcms;
	}
	public void setObservacaoLoteDcms(String observacaoLoteDcms) {
		this.observacaoLoteDcms = observacaoLoteDcms;
	}

	@Override
	public String toString() {
		return "CnpjDados [cdEmpresa=" + cdEmpresa + ", cnpj=" + cnpj + ", razaoSocial=" + razaoSocial + ", observacao="
				+ observacao + ", empDcms=" + empDcms + "]";
	}	
	
	
}

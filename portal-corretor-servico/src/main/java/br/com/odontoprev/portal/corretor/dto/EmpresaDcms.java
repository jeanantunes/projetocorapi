package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class EmpresaDcms implements Serializable {

	private static final long serialVersionUID = -7393237693663557316L;

	private Long cdEmpresa;
	private String cnpj;
	private String empDcms;
	private String email; //201805221106 - esert - COR-160 - refactor - inc campo especifico para email

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

	public String getEmpDcms() {
		return empDcms;
	}

	public void setEmpDcms(String empDcms) {
		this.empDcms = empDcms;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "EmpresaDcms [cdEmpresa=" + cdEmpresa + ", cnpj=" + cnpj + ", empDcms=" + empDcms + ", email=" + email
				+ "]";
	}

}

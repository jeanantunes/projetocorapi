package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class EmpresaEmailAceite implements Serializable { //201805111538 - esert - COR-171 

	private static final long serialVersionUID = -1388958358380373819L;

	private Long cdEmpresa;
	private String email;

	public Long getCdEmpresa() {
		return cdEmpresa;
	}
	public void setCdEmpresa(Long cdEmpresa) {
		this.cdEmpresa = cdEmpresa;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "EmpresaEmail [cdEmpresa=" + cdEmpresa + ", email=" + email + "]";
	}
	
}

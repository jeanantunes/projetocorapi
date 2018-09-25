package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class EmpresaEmailAceite implements Serializable { //201805111538 - esert - COR-171 

	private static final long serialVersionUID = -1388958358380373819L;

	private Long cdEmpresa;
	private Long cdVenda;
	private String email;

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
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "EmpresaEmailAceite [" 
				+ "cdEmpresa=" + cdEmpresa 
				+ ", cdVenda=" + cdVenda 
				+ ", email=" + email 
				+ "]";
	}
	
	//20189251948 - esert - COR-820 : Serviço - Criar POST /empresa-emailaceite
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cdEmpresa == null) ? 0 : cdEmpresa.hashCode());
		result = prime * result + ((cdVenda == null) ? 0 : cdVenda.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}
	
	//20189251948 - esert - COR-820 : Serviço - Criar POST /empresa-emailaceite
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmpresaEmailAceite other = (EmpresaEmailAceite) obj;
		if (cdEmpresa == null) {
			if (other.cdEmpresa != null)
				return false;
		} else if (!cdEmpresa.equals(other.cdEmpresa))
			return false;
		if (cdVenda == null) {
			if (other.cdVenda != null)
				return false;
		} else if (!cdVenda.equals(other.cdVenda))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}

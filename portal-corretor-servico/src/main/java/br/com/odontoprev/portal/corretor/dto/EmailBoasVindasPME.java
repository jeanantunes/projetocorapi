package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class EmailBoasVindasPME implements Serializable {

	private static final long serialVersionUID = -7441888142532474016L;
	
	private Long cdEmpresa;

	public Long getCdEmpresa() {
		return cdEmpresa;
	}

	public void setCdEmpresa(Long cdEmpresa) {
		this.cdEmpresa = cdEmpresa;
	}

	@Override
	public String toString() {
		return "EmailBoasVindasPME [cdEmpresa=" + cdEmpresa + "]";
	}
	
}

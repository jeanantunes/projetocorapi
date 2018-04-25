package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class JsonPlanoPME implements Serializable {

	private static final long serialVersionUID = 1734710747845221504L;
	
	private Long cdPlano;

	public Long getCdPlano() {
		return cdPlano;
	}

	public void setCdPlano(Long cdPlano) {
		this.cdPlano = cdPlano;
	}

	@Override
	public String toString() {
		return "[cdPlano=" + cdPlano + "]";
	}

}

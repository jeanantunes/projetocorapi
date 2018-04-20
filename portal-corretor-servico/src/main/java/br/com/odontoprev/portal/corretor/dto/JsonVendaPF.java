package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class JsonVendaPF implements Serializable {

	private static final long serialVersionUID = -3800643807807974044L;
	
	private Long cdPlano;
	private Long cdForcaVenda;

	private List<JsonTitularesPF> titulares;
	
	private ResponsavelContratual responsavelContratual;

	public Long getCdPlano() {
		return cdPlano;
	}

	public void setCdPlano(Long cdPlano) {
		this.cdPlano = cdPlano;
	}

	public Long getCdForcaVenda() {
		return cdForcaVenda;
	}

	public void setCdForcaVenda(Long cdForcaVenda) {
		this.cdForcaVenda = cdForcaVenda;
	}

	public List<JsonTitularesPF> getTitulares() {
		return titulares;
	}

	public void setTitulares(List<JsonTitularesPF> titulares) {
		this.titulares = titulares;
	}	
	
	public ResponsavelContratual getResponsavelContratual() {
		return responsavelContratual;
	}

	public void setResponsavelContratual(ResponsavelContratual responsavelContratual) {
		this.responsavelContratual = responsavelContratual;
	}

	@Override
	public String toString() {
		return "[cdPlano=" + cdPlano + ", cdForcaVenda=" + cdForcaVenda + ", titulares=" + titulares
				+ ", responsavelContratual=" + responsavelContratual + "]";
	}
	
	
}

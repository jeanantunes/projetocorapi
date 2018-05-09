package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class JsonVendaPME implements Serializable {

	private static final long serialVersionUID = 8632258575883879547L;
	
	private Long cdForcaVenda;
	
	private JsonEmpresaPME empresas;
	
	private List<JsonTitularesPME> titulares;

	public Long getCdForcaVenda() {
		return cdForcaVenda;
	}

	public void setCdForcaVenda(Long cdForcaVenda) {
		this.cdForcaVenda = cdForcaVenda;
	}

	public JsonEmpresaPME getEmpresas() {
		return empresas;
	}

	public void setEmpresas(JsonEmpresaPME empresas) {
		this.empresas = empresas;
	}

	public List<JsonTitularesPME> getTitulares() {
		return titulares;
	}

	public void setTitulares(List<JsonTitularesPME> titulares) {
		this.titulares = titulares;
	}

	@Override
	public String toString() {
		return "[cdForcaVenda=" + cdForcaVenda + ", empresas=" + empresas + ", titulares=" + titulares
				+ "]";
	}

	
}

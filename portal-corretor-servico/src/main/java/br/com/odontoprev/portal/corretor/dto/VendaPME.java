package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class VendaPME implements Serializable {

	private static final long serialVersionUID = 3281440316438484993L;

	private List<Empresa> empresas;

	private List<Beneficiario> titulares;

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public List<Beneficiario> getTitulares() {
		return titulares;
	}

	public void setTitulares(List<Beneficiario> titulares) {
		this.titulares = titulares;
	}

	@Override
	public String toString() {
		return "VendaPME ["
		+"empresas.size()=" + empresas.size() 
		+ ", titulares.size()=" + titulares.size() 
		+ "]";
	}	
}

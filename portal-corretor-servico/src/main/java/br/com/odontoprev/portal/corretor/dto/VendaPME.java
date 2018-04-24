package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class VendaPME implements Serializable {

	private static final long serialVersionUID = 3281440316438484993L;

	private Long cdForcaVenda;

	private List<Empresa> empresas;

	private List<Beneficiario> titulares;

	public Long getCdForcaVenda() {
		return cdForcaVenda;
	}

	public void setCdForcaVenda(Long cdForcaVenda) {
		this.cdForcaVenda = cdForcaVenda;
	}

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
		return "VendaPME [" + "empresas.size()=" + (empresas != null ? empresas.size() : "null") + ", titulares.size()="
				+ (titulares != null ? titulares.size() : "null") + "]";
	}
}

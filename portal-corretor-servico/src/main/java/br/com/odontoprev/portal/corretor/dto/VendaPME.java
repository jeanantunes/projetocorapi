package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class VendaPME implements Serializable {

	private static final long serialVersionUID = 3281440316438484993L;

	private Long cdForcaVenda;

	private String plataforma; //201807201122 - esert/yalm - COR-431

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

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	@Override
	public String toString() {
		return "VendaPME ["
		+ "cdForcaVenda=" + cdForcaVenda //201805241355 - esert/yalm
		+ "plataforma=" + plataforma //201807201427 - esert/yalm - COR-431
		+ ", empresas.size()=" + (empresas != null ? empresas.size() : "null") 
		+ ", titulares.size()="	+ (titulares != null ? titulares.size() : "null") 
		+ "]";
	}

	//201808291915 - esert - COR-632 tdd POST/vendapme
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cdForcaVenda == null) ? 0 : cdForcaVenda.hashCode());
		result = prime * result + ((empresas == null) ? 0 : empresas.hashCode());
		result = prime * result + ((plataforma == null) ? 0 : plataforma.hashCode());
		result = prime * result + ((titulares == null) ? 0 : titulares.hashCode());
		return result;
	}

	//201808291915 - esert - COR-632 tdd POST/vendapme
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VendaPME other = (VendaPME) obj;
		
		if(other.toString().equals(this.toString()))
			return true;
		
//		if (cdForcaVenda == null) {
//			if (other.cdForcaVenda != null)
//				return false;
//		} else if (!cdForcaVenda.equals(other.cdForcaVenda))
//			return false;
//		if (empresas == null) {
//			if (other.empresas != null)
//				return false;
//		} else if (!empresas.equals(other.empresas))
//			return false;
//		if (plataforma == null) {
//			if (other.plataforma != null)
//				return false;
//		} else if (!plataforma.equals(other.plataforma))
//			return false;
//		if (titulares == null) {
//			if (other.titulares != null)
//				return false;
//		} else if (!titulares.equals(other.titulares))
//			return false;
		
		return true;
	}
	
	
}

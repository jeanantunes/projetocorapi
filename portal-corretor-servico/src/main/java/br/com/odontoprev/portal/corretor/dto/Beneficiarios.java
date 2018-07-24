package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

//201807241734 - esert - COR-398
public class Beneficiarios implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5718299865615169423L;
	
	Long tamPagina;
	Long numPagina;
	Long qtdPaginas;
	Long qtdRegistros;
	
	List<Beneficiario> titulares;

	public List<Beneficiario> getTitulares() {
		return titulares;
	}

	public void setTitulares(List<Beneficiario> titulares) {
		this.titulares = titulares;
	}

	public Long getTamPagina() {
		return tamPagina;
	}

	public void setTamPagina(Long tamPagina) {
		this.tamPagina = tamPagina;
	}

	public Long getNumPagina() {
		return numPagina;
	}

	public void setNumPagina(Long numPagina) {
		this.numPagina = numPagina;
	}

	public Long getQtdPaginas() {
		return qtdPaginas;
	}

	public void setQtdPaginas(Long qtdPaginas) {
		this.qtdPaginas = qtdPaginas;
	}

	public Long getQtdRegistros() {
		return qtdRegistros;
	}

	public void setQtdRegistros(Long qtdRegistros) {
		this.qtdRegistros = qtdRegistros;
	}

	@Override
	public String toString() {
		return "Beneficiarios [" 
				+ "tamPagina=" + tamPagina 
				+ ", numPagina=" + numPagina 
				+ ", qtdPaginas=" + qtdPaginas
				+ ", qtdRegistros=" + qtdRegistros 
				+ ", titulares=" + (titulares!=null?titulares.size():"NuLL") 
				+ "]";
	}

}

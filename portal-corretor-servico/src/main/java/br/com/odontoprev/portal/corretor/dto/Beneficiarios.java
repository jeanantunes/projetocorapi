package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

//201807241734 - esert - COR-398
public class Beneficiarios implements Serializable {
	
	private static final long serialVersionUID = -5718299865615169423L;
	
	Long codEmpresa;
	Long tamPagina;
	Long numPagina;
	Long qtdPaginas;
	Long qtdRegistros;
	
	List<BeneficiarioPaginacao> titulares;

	public Long getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(Long codEmpresa) {
		this.codEmpresa = codEmpresa;
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

	public List<BeneficiarioPaginacao> getTitulares() {
		return titulares;
	}

	public void setTitulares(List<BeneficiarioPaginacao> titulares) {
		this.titulares = titulares;
	}

	@Override
	public String toString() {
		return "Beneficiarios [" 
				+ "codEmpresa=" + codEmpresa 
				+ ", tamPagina=" + tamPagina 
				+ ", numPagina=" + numPagina 
				+ ", qtdPaginas=" + qtdPaginas
				+ ", qtdRegistros=" + qtdRegistros 
				+ ", titulares=" + (titulares!=null?titulares.size():"NuLL") 
				+ "]";
	}

}

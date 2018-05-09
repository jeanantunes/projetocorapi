package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class TxtImportacao implements Serializable {

	private static final long serialVersionUID = 7593722617403593195L;

	private String nrAtendimento;
	
	private String dsErroRegistro;

	public TxtImportacao() {
	}

	public String getNrAtendimento() {
		return nrAtendimento;
	}

	public void setNrAtendimento(String nrAtendimento) {
		this.nrAtendimento = nrAtendimento;
	}

	public String getDsErroRegistro() {
		return dsErroRegistro;
	}

	public void setDsErroRegistro(String dsErroRegistro) {
		this.dsErroRegistro = dsErroRegistro;
	}
	
}
package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Date;

public class ContratoCorretoraDataAceite implements Serializable  {

	private long cdCorretora;
	private String dtAceiteContrato;

	public long getCdCorretora() {
		return cdCorretora;
	}

	public void setCdCorretora(long cdCorretora) {
		this.cdCorretora = cdCorretora;
	}

	public String getDtAceiteContrato() {
		return dtAceiteContrato;
	}

	public void setDtAceiteContrato(String dtAceiteContrato) {
		this.dtAceiteContrato = dtAceiteContrato;
	}

}

package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class DashboardResponse implements Serializable {

	private static final long serialVersionUID = 5868711575780143126L;
	
	private long countForcaVendaAprovacao;

	public long getCountForcaVendaAprovacao() {
		return countForcaVendaAprovacao;
	}

	public void setCountForcaVendaAprovacao(long countForcaVendaAprovacao) {
		this.countForcaVendaAprovacao = countForcaVendaAprovacao;
	}

	@Override
	public String toString() {
		return "DashboardResponse [countForcaVendaAprovacao=" + countForcaVendaAprovacao + "]";
	}
	
}

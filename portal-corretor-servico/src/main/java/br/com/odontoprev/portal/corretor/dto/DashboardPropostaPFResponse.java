package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class DashboardPropostaPFResponse implements Serializable {

	private static final long serialVersionUID = 4939511333292839071L;

	private List<DashboardPropostaPF> dashboardPropostasPF;

	public List<DashboardPropostaPF> getDashboardPropostasPF() {
		return dashboardPropostasPF;
	}

	public void setDashboardPropostasPF(List<DashboardPropostaPF> dashboardPropostasPF) {
		this.dashboardPropostasPF = dashboardPropostasPF;
	}

}

package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class DashboardPropostaPMEResponse implements Serializable {

	private static final long serialVersionUID = 5125576325689105242L;

	private List<DashboardPropostaPME> dashboardPropostasPME;
//	private List<DashboardPropostaPF> dashboardPropostasPF;

	public List<DashboardPropostaPME> getDashboardPropostasPME() {
		return dashboardPropostasPME;
	}

	public void setDashboardPropostasPME(List<DashboardPropostaPME> dashboardPropostasPME) {
		this.dashboardPropostasPME = dashboardPropostasPME;
	}

//	public List<DashboardPropostaPF> getDashboardPropostasPF() {
//		return dashboardPropostasPF;
//	}
//
//	public void setDashboardPropostasPF(List<DashboardPropostaPF> dashboardPropostasPF) {
//		this.dashboardPropostasPF = dashboardPropostasPF;
//	}

}

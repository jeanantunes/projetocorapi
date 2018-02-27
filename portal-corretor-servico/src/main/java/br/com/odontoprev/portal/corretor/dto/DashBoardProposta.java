package br.com.odontoprev.portal.corretor.dto;

import java.util.Date;

public class DashBoardProposta {

	private Date dtInicio; 
	private Date dtFim;
	private long cdCorretora;
	private long cdForcaVenda;
	
	public Date getDtInicio() {
		return dtInicio;
	}
	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}
	public Date getDtFim() {
		return dtFim;
	}
	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}
	public long getCdCorretora() {
		return cdCorretora;
	}
	public void setCdCorretora(long cdCorretora) {
		this.cdCorretora = cdCorretora;
	}
	public long getCdForcaVenda() {
		return cdForcaVenda;
	}
	public void setCdForcaVenda(long cdForcaVenda) {
		this.cdForcaVenda = cdForcaVenda;
	}
}

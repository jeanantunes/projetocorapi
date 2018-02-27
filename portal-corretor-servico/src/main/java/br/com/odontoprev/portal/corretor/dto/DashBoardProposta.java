package br.com.odontoprev.portal.corretor.dto;

public class DashBoardProposta {

	private String dtInicio; 
	private String dtFim;
	private long cdCorretora;
	private long cdForcaVenda;
	
	public String getDtInicio() {
		return dtInicio;
	}
	public void setDtInicio(String dtInicio) {
		this.dtInicio = dtInicio;
	}
	public String getDtFim() {
		return dtFim;
	}
	public void setDtFim(String dtFim) {
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

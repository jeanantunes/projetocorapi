package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

//201806121142 - esert - relatorio corretora total vidas pf
public class CorretoraTotalVidasPF implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5686830490991237941L;
	
	private String dtVendaInicio;
	private String dtVendaFim;
	private String cnpjCorretora;
	
	public String getDtVendaInicio() {
		return dtVendaInicio;
	}
	public void setDtVendaInicio(String dtVendaInicio) {
		this.dtVendaInicio = dtVendaInicio;
	}
	
	public String getDtVendaFim() {
		return dtVendaFim;
	}
	public void setDtVendaFim(String dtVendaFim) {
		this.dtVendaFim = dtVendaFim;
	}
	
	public String getCnpjCorretora() {
		return cnpjCorretora;
	}
	public void setCnpjCorretora(String cnpjCorretora) {
		this.cnpjCorretora = cnpjCorretora;
	}
	
	@Override
	public String toString() {
		return "CorretoraTotalVidasPF [" 
				+ "dtVendaInicio=" + dtVendaInicio 
				+ ", dtVendaFim=" + dtVendaFim 
				+ ", cnpjCorretora="	+ cnpjCorretora 
				+ "]";
	}	
}

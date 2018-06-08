package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Date;

//201806081711 - esert - relatorio corretora total vidas pme
public class CorretoraTotalVidasPME implements Serializable {
	
	private static final long serialVersionUID = 7629097794815722943L;
	
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
		return "CorretoraTotalVidasPME [" 
				+ "dtVendaInicio=" + dtVendaInicio 
				+ ", dtVendaFim=" + dtVendaFim 
				+ ", cnpjCorretora="	+ cnpjCorretora 
				+ "]";
	}	
}

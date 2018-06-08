package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Date;

//201806081711 - esert - relatorio corretora total vidas pme
public class CorretoraTotalVidasPME implements Serializable {
	
	private static final long serialVersionUID = 7629097794815722943L;
	
	private Date dtVendaInicio;
	private Date dtVendaFim;
	private String cnpjCorretora;
	
	public Date getDtVendaInicio() {
		return dtVendaInicio;
	}
	public void setDtVendaInicio(Date dtVendaInicio) {
		this.dtVendaInicio = dtVendaInicio;
	}
	
	
	public Date getDtVendaFim() {
		return dtVendaFim;
	}
	public void setDtVendaFim(Date dtVendaFim) {
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

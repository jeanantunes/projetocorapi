package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class Venda2 extends Venda implements Serializable {

	private static final long serialVersionUID = 2889420439582038249L;
		
	private String propostaDcms;
	private DadosBancariosVenda dadosBancariosVenda;
	
	public String getPropostaDcms() {
		return propostaDcms;
	}
	public void setPropostaDcms(String propostaDcms) {
		this.propostaDcms = propostaDcms;
	}
	
	public DadosBancariosVenda getDadosBancariosVenda() {
		return dadosBancariosVenda;
	}
	public void setDadosBancariosVenda(DadosBancariosVenda dadosBancariosVenda) {
		this.dadosBancariosVenda = dadosBancariosVenda;
	}
	
	@Override
	public String toString() {
		return "Venda2 [" 
				+ "Venda=" + super.toString() 
				+ ", propostaDcms=" + propostaDcms 
				+ ", dadosBancariosVenda=" + dadosBancariosVenda 
				+ "]";
	}
	
	
}

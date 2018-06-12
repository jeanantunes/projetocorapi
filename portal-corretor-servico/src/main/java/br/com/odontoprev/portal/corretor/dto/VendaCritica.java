package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class VendaCritica extends Venda implements Serializable {

	private static final long serialVersionUID = 2889420439582038249L;
	
	private Plano plano;
	private List<TxtImportacao> criticas;
	private String propostaDcms;
	private DadosBancariosVenda dadosBancariosVenda;

	public Plano getPlano() {
		return plano;
	}

	public void setPlano(Plano plano) {
		this.plano = plano;
	}

	public List<TxtImportacao> getCriticas() {
		return criticas;
	}

	public void setCriticas(List<TxtImportacao> criticas) {
		this.criticas = criticas;
	}
	
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

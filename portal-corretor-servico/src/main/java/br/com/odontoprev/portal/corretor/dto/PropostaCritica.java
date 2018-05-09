package br.com.odontoprev.portal.corretor.dto;

import java.util.List;

//201805081510 - esert
public class PropostaCritica {

	private Venda venda;
	private List<Beneficiario> vidas;
	private Plano plano;
	private List<TxtImportacao> criticas;
	
	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Beneficiario> getVidas() {
		return vidas;
	}

	public void setVidas(List<Beneficiario> vidas) {
		this.vidas = vidas;
	}

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

	@Override
	public String toString() {
		return "PropostaCriticaResponse [" 
				+ "venda=" + venda 
				+ ", vidas.size=" + vidas.size() 
				+ ", plano=" + plano
				+ ", criticas.size="	+ criticas.size() 
				+ "]";
	}
}

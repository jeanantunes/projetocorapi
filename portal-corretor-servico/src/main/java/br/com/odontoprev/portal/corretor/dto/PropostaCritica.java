package br.com.odontoprev.portal.corretor.dto;

//201805081510 - esert
public class PropostaCritica {

	private VendaCritica venda;
	
	public VendaCritica getVenda() {
		return venda;
	}

	public void setVenda(VendaCritica venda) {
		this.venda = venda;
	}

	@Override
	public String toString() {
		return "PropostaCriticaResponse [" 
				+ "venda=" + venda.toString() 
				+ "]";
	}
}

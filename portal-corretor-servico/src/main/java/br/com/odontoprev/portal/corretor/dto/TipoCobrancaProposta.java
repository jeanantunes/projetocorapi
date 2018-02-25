package br.com.odontoprev.portal.corretor.dto;

public class TipoCobrancaProposta {

	private int codigoTipoCobranca; //Informar o que vem do serviço da empresa

	private String siglaTipoCobranca; //BO = Boleto / DA = Debito automatico

	public int getCodigoTipoCobranca() {
		return codigoTipoCobranca;
	}

	public void setCodigoTipoCobranca(int codigoTipoCobranca) {
		this.codigoTipoCobranca = codigoTipoCobranca;
	}

	public String getSiglaTipoCobranca() {
		return siglaTipoCobranca;
	}

	public void setSiglaTipoCobranca(String siglaTipoCobranca) {
		this.siglaTipoCobranca = siglaTipoCobranca;
	}

	@Override
	public String toString() {
		return "TipoCobrancaProposta [" 
				+ "codigoTipoCobranca=" + codigoTipoCobranca 
				+ ", siglaTipoCobranca=" + siglaTipoCobranca 
				+ "]";
	}

}

package br.com.odontoprev.portal.corretor.dto;

public class PropostaDCMSResponse {

	private String numeroProposta;
	private String mensagemErro;

	public String getNumeroProposta() {
		return numeroProposta;
	}

	public void setNumeroProposta(String codigoProposta) {
		this.numeroProposta = codigoProposta;
	}

	public String getMensagemErro() {
		return mensagemErro;
	}

	public void setMensagemErro(String mensagemErro) {
		this.mensagemErro = mensagemErro;
	}

	@Override
	public String toString() {
		return "PropostaDCMSResponse [numeroProposta=" + numeroProposta + ", mensagemErro=" + mensagemErro + "]";
	}
	
}

package br.com.odontoprev.portal.corretor.dto;

public class TipoCobrancaProposta {

	private Long codigo; //Informar o que vem do serviço da empresa

	private String sigla; //BO = Boleto / DA = Debito automatico

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@Override
	public String toString() {
		return "TipoCobrancaProposta [" 
				+ "codigo=" + codigo 
				+ ", sigla=" + sigla 
				+ "]";
	}

}

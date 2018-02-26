package br.com.odontoprev.portal.corretor.dto;

public class DadosBancariosProposta {

	private String codigoBanco;

	private String agencia;

	private String agenciaDV;

	private String tipoConta;

	private String conta;

	private String contaDV;

	public String getCodigoBanco() {
		return codigoBanco;
	}

	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getAgenciaDV() {
		return agenciaDV;
	}

	public void setAgenciaDV(String agenciaDV) {
		this.agenciaDV = agenciaDV;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getContaDV() {
		return contaDV;
	}

	public void setContaDV(String contaDV) {
		this.contaDV = contaDV;
	}

	@Override
	public String toString() {
		return "DadosBancarioProposta [" 
		+ "codigoBanco=" + codigoBanco 
		+ ", agencia=" + agencia 
		+ ", agenciaDV=" + agenciaDV
		+ ", tipoConta=" + tipoConta 
		+ ", conta=" + conta 
		+ ", contaDV=" + contaDV 
		+ "]";
	}
	
}

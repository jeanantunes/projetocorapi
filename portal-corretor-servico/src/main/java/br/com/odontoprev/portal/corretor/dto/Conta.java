package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class Conta implements Serializable {

	private static final long serialVersionUID = -7882196047868123154L;

	private Long cdBancoConta;
	private String codigoAgencia;
	private String codigoBanco;
	private String numeroConta;

	public Long getCdBancoConta() {
		return cdBancoConta;
	}

	public void setCdBancoConta(Long cdBancoConta) {
		this.cdBancoConta = cdBancoConta;
	}

	public String getCodigoAgencia() {
		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}

	public String getCodigoBanco() {
		return codigoBanco;
	}

	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	@Override
	public String toString() {
		return "Conta [" 
				+ "cdBancoConta=" + cdBancoConta 
				+ ", codigoAgencia=" + codigoAgencia 
				+ ", codigoBanco=" + codigoBanco 
				+ ", numeroConta=" + numeroConta 
				+ "]";
	}

}

package br.com.odontoprev.portal.corretor.dto;

public class DadosBancariosVenda {

	private String codigoBanco;

	private String agencia;

	//private String agenciaDV; //custso separar o DV no app vide Diego em 2018.02.28

	private String tipoConta;

	private String conta;

	//private String contaDV; //custso separar o DV no app vide Diego em 2018.02.28

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

	@Override
	public String toString() {
		return "DadosBancarioProposta [" 
		+ "codigoBanco=" + codigoBanco 
		+ ", agencia=" + agencia 
		+ ", tipoConta=" + tipoConta 
		+ ", conta=" + conta 
		+ "]";
	}
	
}

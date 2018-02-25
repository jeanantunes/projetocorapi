package br.com.odontoprev.portal.corretor.dto;

import java.util.List;

public class Proposta {

	private CorretoraProposta corretoraProposta;
	
	private String codigoEmpresaDCMS;
	
	private int CodigoCanalVendas;
	
	private int codigoLogin; //Codigo identificador do usuario
	
	private TipoCobrancaProposta tipoCobranca;
		
	private DadosBancarioProposta dadosBancario;

	private List<BeneficiarioProposta> beneficiarioProposta;

	public CorretoraProposta getCorretoraProposta() {
		return corretoraProposta;
	}

	public void setCorretoraProposta(CorretoraProposta corretoraProposta) {
		this.corretoraProposta = corretoraProposta;
	}

	public String getCodigoEmpresaDCMS() {
		return codigoEmpresaDCMS;
	}

	public void setCodigoEmpresaDCMS(String codigoEmpresaDCMS) {
		this.codigoEmpresaDCMS = codigoEmpresaDCMS;
	}

	public int getCodigoCanalVendas() {
		return CodigoCanalVendas;
	}

	public void setCodigoCanalVendas(int codigoCanalVendas) {
		CodigoCanalVendas = codigoCanalVendas;
	}

	public int getCodigoLogin() {
		return codigoLogin;
	}

	public void setCodigoLogin(int codigoLogin) {
		this.codigoLogin = codigoLogin;
	}

	public TipoCobrancaProposta getTipoCobranca() {
		return tipoCobranca;
	}

	public void setTipoCobranca(TipoCobrancaProposta tipoCobranca) {
		this.tipoCobranca = tipoCobranca;
	}

	public DadosBancarioProposta getDadosBancario() {
		return dadosBancario;
	}

	public void setDadosBancario(DadosBancarioProposta dadosBancario) {
		this.dadosBancario = dadosBancario;
	}

	public List<BeneficiarioProposta> getBeneficiarioProposta() {
		return beneficiarioProposta;
	}

	public void setBeneficiarioProposta(List<BeneficiarioProposta> beneficiarioProposta) {
		this.beneficiarioProposta = beneficiarioProposta;
	}

	@Override
	public String toString() {
		return "Proposta [" 
				+ "corretoraProposta=" + corretoraProposta 
				+ ", codigoEmpresaDCMS=" + codigoEmpresaDCMS
				+ ", CodigoCanalVendas=" + CodigoCanalVendas 
				+ ", codigoLogin=" + codigoLogin 
				+ ", tipoCobranca=" + tipoCobranca 
				+ ", dadosBancario=" + dadosBancario 
				+ ", beneficiarioProposta=" + beneficiarioProposta
				+ "]";
	}	
}

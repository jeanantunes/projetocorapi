package br.com.odontoprev.portal.corretor.dto;

import java.util.List;

public class Proposta {

	private CorretoraProposta corretora;
	
	private String codigoEmpresaDCMS;
	
	private Long codigoCanalVendas;
	
	private Long codigoUsuario; //Codigo identificador do usuario
	
	private TipoCobrancaProposta tipoCobranca;
		
	private DadosBancariosProposta dadosBancarios;

	private List<BeneficiarioProposta> beneficiarios;

	public CorretoraProposta getCorretora() {
		return corretora;
	}

	public void setCorretora(CorretoraProposta corretora) {
		this.corretora = corretora;
	}

	public String getCodigoEmpresaDCMS() {
		return codigoEmpresaDCMS;
	}

	public void setCodigoEmpresaDCMS(String codigoEmpresaDCMS) {
		this.codigoEmpresaDCMS = codigoEmpresaDCMS;
	}

	public Long getCodigoCanalVendas() {
		return codigoCanalVendas;
	}

	public void setCodigoCanalVendas(Long codigoCanalVendas) {
		this.codigoCanalVendas = codigoCanalVendas;
	}

	public Long getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(Long codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public TipoCobrancaProposta getTipoCobranca() {
		return tipoCobranca;
	}

	public void setTipoCobranca(TipoCobrancaProposta tipoCobranca) {
		this.tipoCobranca = tipoCobranca;
	}

	public DadosBancariosProposta getDadosBancarios() {
		return dadosBancarios;
	}

	public void setDadosBancarios(DadosBancariosProposta dadosBancarios) {
		this.dadosBancarios = dadosBancarios;
	}

	public List<BeneficiarioProposta> getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(List<BeneficiarioProposta> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

	@Override
	public String toString() {
		return "Proposta [" 
				+ "corretora=" + corretora 
				+ ", codigoEmpresaDCMS=" + codigoEmpresaDCMS
				+ ", codigoCanalVendas=" + codigoCanalVendas 
				+ ", codigoUsuario=" + codigoUsuario 
				+ ", tipoCobranca=" + tipoCobranca 
				+ ", dadosBancarios=" + dadosBancarios 
				+ ", beneficiarios=" + beneficiarios
				+ "]";
	}	
}

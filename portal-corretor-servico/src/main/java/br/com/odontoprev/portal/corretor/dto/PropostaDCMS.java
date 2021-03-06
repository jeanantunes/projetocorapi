package br.com.odontoprev.portal.corretor.dto;

import java.util.List;

public class PropostaDCMS {

	private CorretoraPropostaDCMS corretorMaster;

	private CorretoraPropostaDCMS angariador;
	
	private String codigoEmpresaDCMS;
	
	private String codigoCanalVendas;
	
	private Long codigoUsuario; //Codigo identificador do usuario
	
	private TipoCobrancaPropostaDCMS tipoCobranca;
		
	private DadosBancariosPropostaDCMS dadosBancarios;

	private List<BeneficiarioPropostaDCMS> beneficiarios;
	
	private ResponsavelContratual responsavelContratual;

	public CorretoraPropostaDCMS getCorretorMaster() {
		return corretorMaster;
	}

	public void setCorretorMaster(CorretoraPropostaDCMS corretorMaster) {
		this.corretorMaster = corretorMaster;
	}

	public CorretoraPropostaDCMS getAngariador() {
		return angariador;
	}

	public void setAngariador(CorretoraPropostaDCMS angariador) {
		this.angariador = angariador;
	}

	public String getCodigoEmpresaDCMS() {
		return codigoEmpresaDCMS;
	}

	public void setCodigoEmpresaDCMS(String codigoEmpresaDCMS) {
		this.codigoEmpresaDCMS = codigoEmpresaDCMS;
	}

	public String getCodigoCanalVendas() {
		return codigoCanalVendas;
	}

	public void setCodigoCanalVendas(String codigoCanalVendas) {
		this.codigoCanalVendas = codigoCanalVendas;
	}

	public Long getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(Long codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public TipoCobrancaPropostaDCMS getTipoCobranca() {
		return tipoCobranca;
	}

	public void setTipoCobranca(TipoCobrancaPropostaDCMS tipoCobranca) {
		this.tipoCobranca = tipoCobranca;
	}

	public DadosBancariosPropostaDCMS getDadosBancarios() {
		return dadosBancarios;
	}

	public void setDadosBancarios(DadosBancariosPropostaDCMS dadosBancarios) {
		this.dadosBancarios = dadosBancarios;
	}

	public List<BeneficiarioPropostaDCMS> getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(List<BeneficiarioPropostaDCMS> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}
	
	public ResponsavelContratual getResponsavelContratual() {
		return responsavelContratual;
	}

	public void setResponsavelContratual(ResponsavelContratual responsavelContratual) {
		this.responsavelContratual = responsavelContratual;
	}

	@Override
	public String toString() {
		return "PropostaDCMS{" +
				"corretoraMaster=" + corretorMaster +
				", angariador=" + angariador +
				", codigoEmpresaDCMS='" + codigoEmpresaDCMS + '\'' +
				", codigoCanalVendas='" + codigoCanalVendas + '\'' +
				", codigoUsuario=" + codigoUsuario +
				", tipoCobranca=" + tipoCobranca +
				", dadosBancarios=" + dadosBancarios +
				", beneficiarios=" + beneficiarios +
				", responsavelContratual=" + responsavelContratual +
				'}';
	}
}

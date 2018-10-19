package br.com.odontoprev.portal.corretor.dto;

import java.util.List;

public class EmailAceite {

	private String nomeCorretor;
	private String nomeCorretora;
	private String nomeEmpresa;
	private String emailEnvio;
	private String token;
	private List<Plano> planos;
	private Long codigoEmpresa; //201808231840 - esert - COR-617 Serviço Geração PDF Contratação PME
	//private String arquivoBase64; //201808231852 - esert - COR-617 Serviço Geração PDF Contratação PME
	private ArquivoContratacao arquivoContratacao; //201808240102 - esert - COR-617 Serviço Geração PDF Contratação PME
	private String dataVigencia;
	private String dataMovimentacao;

	public String getNomeCorretor() {
		return nomeCorretor;
	}

	public void setNomeCorretor(String nomeCorretor) {
		this.nomeCorretor = nomeCorretor;
	}

	public String getNomeCorretora() {
		return nomeCorretora;
	}

	public void setNomeCorretora(String nomeCorretora) {
		this.nomeCorretora = nomeCorretora;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getEmailEnvio() {
		return emailEnvio;
	}

	public void setEmailEnvio(String emailEnvio) {
		this.emailEnvio = emailEnvio;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<Plano> getPlanos() {
		return planos;
	}

	public void setPlanos(List<Plano> planos) {
		this.planos = planos;
	}

	public Long getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(Long codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

//	public String getArquivoBase64() {
//		return arquivoBase64;
//	}
//	public void setArquivoBase64(String arquivoBase64) {
//		this.arquivoBase64 = arquivoBase64;
//	}


	public ArquivoContratacao getArquivoContratacao() {
		return arquivoContratacao;
	}
	public void setArquivoContratacao(ArquivoContratacao arquivoContratacao) {
		this.arquivoContratacao = arquivoContratacao;
	}

	public String getDataVigencia() {
		return dataVigencia;
	}

	public void setDataVigencia(String dataVigencia) {
		this.dataVigencia = dataVigencia;
	}

	public String getDataMovimentacao() {
		return dataMovimentacao;
	}

	public void setDataMovimentacao(String dataMovimentacao) {
		this.dataMovimentacao = dataMovimentacao;
	}

	@Override
	public String toString() {
		return "EmailAceite{" +
				"nomeCorretor='" + nomeCorretor + '\'' +
				", nomeCorretora='" + nomeCorretora + '\'' +
				", nomeEmpresa='" + nomeEmpresa + '\'' +
				", emailEnvio='" + emailEnvio + '\'' +
				", token='" + token + '\'' +
				", planos=" + planos +
				", codigoEmpresa=" + codigoEmpresa +
				//", arquivoContratacao=" + arquivoContratacao +
				", dataVigencia='" + dataVigencia + '\'' +
				", dataMovimentacao='" + dataMovimentacao + '\'' +
				'}';
	}

}

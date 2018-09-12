package br.com.odontoprev.portal.corretor.dto;

//201809121720 - esert - COR-714 - Serviço - Novo serviço gerar enviar contrato corretora
public class EmailContratoCorretora {

	private String nomeCorretor;
	private String nomeCorretora;
	private String nomeEmpresa;
	private String emailEnvio;
	private String token;
	private Long cdCorretora;
	private ContratoCorretora contratoCorretora; //201808240102 - esert - COR-617 Serviço Geração PDF Contratação PME

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

	public Long getCdCorretora() {
		return cdCorretora;
	}

	public void setCdCorretora(Long cdCorretora) {
		this.cdCorretora = cdCorretora;
	}

	public ContratoCorretora getContratoCorretora() {
		return contratoCorretora;
	}

	public void setContratoCorretora(ContratoCorretora contratoCorretora) {
		this.contratoCorretora = contratoCorretora;
	}

	@Override
	public String toString() {
		return "EmailContratoCorretora [" 
				+ "nomeCorretor=" + nomeCorretor 
				+ ", nomeCorretora=" + nomeCorretora 
				+ ", nomeEmpresa=" + nomeEmpresa 
				+ ", emailEnvio=" + emailEnvio 
				+ ", token=" + token 
				+ ", cdCorretora=" + cdCorretora 
				+ ", contratoCorretora=" + contratoCorretora 
				+ "]";
	}

	
}

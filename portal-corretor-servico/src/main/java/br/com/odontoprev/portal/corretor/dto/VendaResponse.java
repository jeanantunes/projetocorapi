package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class VendaResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8287639387532985307L;
	
	private long id;
	private String mensagem;
	private Long cdVenda; 
	private String numeroProposta; 
	private String dtVenda;
	private String mensagemErro;
	private Long cdEmpresa;
	private boolean temBloqueio;

	@Override
	public String toString() {
		return "VendaResponse{" +
				"id=" + id +
				", mensagem='" + mensagem + '\'' +
				", cdVenda=" + cdVenda +
				", numeroProposta='" + numeroProposta + '\'' +
				", dtVenda='" + dtVenda + '\'' +
				", mensagemErro='" + mensagemErro + '\'' +
				", cdEmpresa=" + cdEmpresa +
				", temBloqueio=" + temBloqueio +
				", codigoTipoBloqueio=" + codigoTipoBloqueio +
				", descricaoTipoBloqueio='" + descricaoTipoBloqueio + '\'' +
				'}';
	}

	private Long codigoTipoBloqueio;
	private String descricaoTipoBloqueio;

	public VendaResponse(boolean temBloqueio, Long codigoTipoBloqueio, String descricaoTipoBloqueio, String mensagem) {

		this.temBloqueio = temBloqueio;
		this.codigoTipoBloqueio = codigoTipoBloqueio;
		this.descricaoTipoBloqueio = descricaoTipoBloqueio;
		this.mensagem = mensagem;
	}

	public VendaResponse(long id) {
		this.id = id;
	}

	public VendaResponse(long id, String mensagem) {
		this.id = id;
		this.mensagem = mensagem;
	}
	
	//201808241648 - esert - COR-619 serv disp pdf pme - passar cdEmpresa para App/Web a fim de facilitar GET do PDF por cdEmpresa
	public VendaResponse(
			long id, 
			String mensagem,
			Long cdVenda, 
			String numeroProposta, 
			String dtVenda,
			String mensagemErro,
			Long cdEmpresa
			) {
		this.id = id;
		this.mensagem = mensagem;
		this.cdVenda = cdVenda; 
		this.numeroProposta = numeroProposta; 
		this.dtVenda = dtVenda;
		this.mensagemErro = mensagemErro;
		this.cdEmpresa = cdEmpresa;
	}

	public long getId() {
		return id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getCdVenda() {
		return cdVenda;
	}

	public String getNumeroProposta() {
		return numeroProposta;
	}

	public String getDtVenda() {
		return dtVenda;
	}

	public String getMensagemErro() {
		return mensagemErro;
	}

	public Long getCdEmpresa() {
		return cdEmpresa;
	}

	public boolean getTemBloqueio() {
		return temBloqueio;
	}

	public void setTemBloqueio(boolean temBloqueio) {
		this.temBloqueio = temBloqueio;
	}

	public Long getCodigoTipoBloqueio() {
		return codigoTipoBloqueio;
	}

	public void setCodigoTipoBloqueio(Long codigoTipoBloqueio) {
		this.codigoTipoBloqueio = codigoTipoBloqueio;
	}

	public String getDescricaoTipoBloqueio() {
		return descricaoTipoBloqueio;
	}

	public void setDescricaoTipoBloqueio(String descricaoTipoBloqueio) {
		this.descricaoTipoBloqueio = descricaoTipoBloqueio;
	}

}

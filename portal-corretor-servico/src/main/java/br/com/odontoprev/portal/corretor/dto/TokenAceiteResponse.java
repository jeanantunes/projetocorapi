package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class TokenAceiteResponse implements Serializable {
	
	private static final long serialVersionUID = -2732447739041470919L;
	
	private long id;
	private String mensagem;
	private String cdToken; //201808082010 - esert
	private Long cdVenda; //201808082010 - esert
	
	public TokenAceiteResponse(long id) {		
		this.id = id;
	}
	
	public TokenAceiteResponse(long id, String mensagem) {		
		this.id = id;
		this.mensagem = mensagem;
	}

	public long getId() {
		return id;
	}
	
	public String getMensagem() {
		return mensagem;
	}

	public String getCdToken() {
		return cdToken;
	}

	public void setCdToken(String token) {
		this.cdToken = token;
	}

	public Long getCdVenda() {
		return cdVenda;
	}

	public void setCdVenda(Long cdVenda) {
		this.cdVenda = cdVenda;
	}

	@Override
	public String toString() {  //201808082010 - esert
		return "TokenAceiteResponse [" 
				+ "id=" + id 
				+ ", mensagem=" + mensagem 
				+ ", cdToken=" + cdToken 
				+ ", cdVenda=" + cdVenda
				+ "]";
	}
	
	
}

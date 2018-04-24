package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class TokenAceiteResponse implements Serializable {
	
	private static final long serialVersionUID = -2732447739041470919L;
	
	private long id;
	private String mensagem;
	
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
}

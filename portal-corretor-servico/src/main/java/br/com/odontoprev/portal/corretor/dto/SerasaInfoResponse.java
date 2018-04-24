package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class SerasaInfoResponse implements Serializable {

	private static final long serialVersionUID = 7640288995585449279L;
	
	private long id;
	private String mensagem;	
	
	public SerasaInfoResponse() {		
	}

	public SerasaInfoResponse(long id) {		
		this.id = id;
	}

	public SerasaInfoResponse(long id, String mensagem) {		
		this.id = id;
		this.mensagem = mensagem;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}

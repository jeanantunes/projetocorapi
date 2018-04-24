package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class UploadResponse implements Serializable{
	
	private static final long serialVersionUID = -7733081500381874775L;
	
	private long id;
	private String mensagem;	
	
	public UploadResponse() {
		
	}
	public UploadResponse(long id) {		
		this.id = id;
	}
	public UploadResponse(long id, String mensagem) {	
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

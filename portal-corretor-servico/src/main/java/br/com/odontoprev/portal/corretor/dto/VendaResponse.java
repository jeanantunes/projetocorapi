package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class VendaResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8287639387532985307L;
	
	private long id;
	private String mensagem;

	public VendaResponse(long id) {
		this.id = id;
	}

	public VendaResponse(long id, String mensagem) {
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

package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class ForcaVendaResponse implements Serializable {

	private static final long serialVersionUID = 2116137054341623815L;

	private long id;
	private String mensagem;

	public ForcaVendaResponse(long id) {
		this.id = id;
	}

	public ForcaVendaResponse(long id, String mensagem) {
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

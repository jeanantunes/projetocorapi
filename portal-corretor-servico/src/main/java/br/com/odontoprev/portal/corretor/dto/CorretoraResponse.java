package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class CorretoraResponse implements Serializable {

	private static final long serialVersionUID = 2127819694346550728L;

	private long id;
	private String mensagem;

	public CorretoraResponse(long id, String mensagem) {
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

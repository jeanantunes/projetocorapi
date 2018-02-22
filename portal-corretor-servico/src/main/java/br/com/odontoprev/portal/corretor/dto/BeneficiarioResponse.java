package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class BeneficiarioResponse implements Serializable {

	private static final long serialVersionUID = -3950815742146802316L;

	private long id;
	private String mensagem;

	public BeneficiarioResponse(long id) {
		this.id = id;
	}

	public BeneficiarioResponse(long id, String mensagem) {
		this.id = id;
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public long getId() {
		return id;
	}

}

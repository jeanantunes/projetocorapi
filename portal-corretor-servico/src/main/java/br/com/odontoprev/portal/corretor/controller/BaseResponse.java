package br.com.odontoprev.portal.corretor.controller;

import java.io.Serializable;

public class BaseResponse  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mensagem;
	
	

	public BaseResponse(String mensagem) {
		super();
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
}

package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Date;

import br.com.odontoprev.portal.corretor.model.TbodDeviceToken;

public class DeviceToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String token;
	private Date dataInclusao;
	private String modelo;
	private String sistemaOperacional;

	public DeviceToken(TbodDeviceToken token) {
		this.token = token.getToken();
		this.dataInclusao = token.getDataInclusao();
		this.modelo = token.getModelo();
		this.sistemaOperacional = token.getSistemaOperacional();
	}
	
	

	public DeviceToken() {
		super();
	}



	public String getToken() {		
		return token;
	}

	public Date getDataInclusao() {		
		return dataInclusao;
	}

	public String getModelo() {		
		return modelo;
	}

	public String getSistemaOperacional() {		
		return sistemaOperacional;
	}

}

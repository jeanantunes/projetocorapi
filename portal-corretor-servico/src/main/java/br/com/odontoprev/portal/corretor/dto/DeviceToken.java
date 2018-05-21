package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Date;

import br.com.odontoprev.portal.corretor.model.TbodDeviceToken;

public class DeviceToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long codigo;
	private String token;
	private Date dataInclusao;
	private String modelo;
	private String sistemaOperacional;
	private Date dataAtualizacao;

	public DeviceToken(TbodDeviceToken token) {
		this.codigo = token.getCodigo();
		this.token = token.getToken();
		this.dataInclusao = token.getDataInclusao();
		this.dataAtualizacao = token.getDataAtualizacao();
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



	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}



	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}



	public void setToken(String token) {
		this.token = token;
	}



	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}



	public void setModelo(String modelo) {
		this.modelo = modelo;
	}



	public void setSistemaOperacional(String sistemaOperacional) {
		this.sistemaOperacional = sistemaOperacional;
	}



	public Long getCodigo() {
		return codigo;
	}



	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	

}

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



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((dataAtualizacao == null) ? 0 : dataAtualizacao.hashCode());
		result = prime * result + ((dataInclusao == null) ? 0 : dataInclusao.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		result = prime * result + ((sistemaOperacional == null) ? 0 : sistemaOperacional.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceToken other = (DeviceToken) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (dataAtualizacao == null) {
			if (other.dataAtualizacao != null)
				return false;
		} else if (!dataAtualizacao.equals(other.dataAtualizacao))
			return false;
		if (dataInclusao == null) {
			if (other.dataInclusao != null)
				return false;
		} else if (!dataInclusao.equals(other.dataInclusao))
			return false;
		if (modelo == null) {
			if (other.modelo != null)
				return false;
		} else if (!modelo.equals(other.modelo))
			return false;
		if (sistemaOperacional == null) {
			if (other.sistemaOperacional != null)
				return false;
		} else if (!sistemaOperacional.equals(other.sistemaOperacional))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "DeviceToken [codigo=" + codigo + ", token=" + token + ", dataInclusao=" + dataInclusao + ", modelo="
				+ modelo + ", sistemaOperacional=" + sistemaOperacional + ", dataAtualizacao=" + dataAtualizacao + "]";
	}
	

	
}

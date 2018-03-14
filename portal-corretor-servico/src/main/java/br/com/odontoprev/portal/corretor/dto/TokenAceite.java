package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class TokenAceite implements Serializable {
		
	private static final long serialVersionUID = -1407492746420389077L;
	
	private Long cdTokenAceite;
	private Long cdVenda;
	private String ip;
	private String dataAceite;
	private String email;
	private String dataEnvio;
	private String dataExpiracao;
	
	public Long getCdTokenAceite() {
		return cdTokenAceite;
	}
	public void setCdTokenAceite(Long cdTokenAceite) {
		this.cdTokenAceite = cdTokenAceite;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDataAceite() {
		return dataAceite;
	}
	public void setDataAceite(String dataAceite) {
		this.dataAceite = dataAceite;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDataEnvio() {
		return dataEnvio;
	}
	public void setDataEnvio(String dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	public String getDataExpiracao() {
		return dataExpiracao;
	}
	public void setDataExpiracao(String dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}
	public Long getCdVenda() {
		return cdVenda;
	}
	public void setCdVenda(Long cdVenda) {
		this.cdVenda = cdVenda;
	}
}

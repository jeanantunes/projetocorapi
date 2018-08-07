package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class TokenAceite implements Serializable {
		
	private static final long serialVersionUID = -1407492746420389077L;

	private long id;
	private String mensagem;
	private Long cdTokenAceite;
	private Long cdVenda;
	private String ip;
	private String dataAceite;
	private String email;
	private String dataEnvio;
	private String dataExpiracao;
	private String token;

	public TokenAceite() {
	}

	public TokenAceite(long id, String mensagem) {
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public String toString() { //2018051102036 - esert - COR-172
		return "TokenAceite [cdTokenAceite=" + cdTokenAceite + ", cdVenda=" + cdVenda + ", ip=" + ip + ", dataAceite="
				+ dataAceite + ", email=" + email + ", dataEnvio=" + dataEnvio + ", dataExpiracao=" + dataExpiracao
				+ ", token=" + token + "]";
	}
}

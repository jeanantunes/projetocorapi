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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cdTokenAceite == null) ? 0 : cdTokenAceite.hashCode());
		result = prime * result + ((cdVenda == null) ? 0 : cdVenda.hashCode());
		result = prime * result + ((dataAceite == null) ? 0 : dataAceite.hashCode());
		result = prime * result + ((dataEnvio == null) ? 0 : dataEnvio.hashCode());
		result = prime * result + ((dataExpiracao == null) ? 0 : dataExpiracao.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((mensagem == null) ? 0 : mensagem.hashCode());
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
		TokenAceite other = (TokenAceite) obj;
		
		if(!this.toString().equals(other.toString()))
			return false;
		
//		if (cdTokenAceite == null) {
//			if (other.cdTokenAceite != null)
//				return false;
//		} else if (!cdTokenAceite.equals(other.cdTokenAceite))
//			return false;
//		if (cdVenda == null) {
//			if (other.cdVenda != null)
//				return false;
//		} else if (!cdVenda.equals(other.cdVenda))
//			return false;
//		if (dataAceite == null) {
//			if (other.dataAceite != null)
//				return false;
//		} else if (!dataAceite.equals(other.dataAceite))
//			return false;
//		if (dataEnvio == null) {
//			if (other.dataEnvio != null)
//				return false;
//		} else if (!dataEnvio.equals(other.dataEnvio))
//			return false;
//		if (dataExpiracao == null) {
//			if (other.dataExpiracao != null)
//				return false;
//		} else if (!dataExpiracao.equals(other.dataExpiracao))
//			return false;
//		if (email == null) {
//			if (other.email != null)
//				return false;
//		} else if (!email.equals(other.email))
//			return false;
//		if (id != other.id)
//			return false;
//		if (ip == null) {
//			if (other.ip != null)
//				return false;
//		} else if (!ip.equals(other.ip))
//			return false;
//		if (mensagem == null) {
//			if (other.mensagem != null)
//				return false;
//		} else if (!mensagem.equals(other.mensagem))
//			return false;
//		if (token == null) {
//			if (other.token != null)
//				return false;
//		} else if (!token.equals(other.token))
//			return false;
		
		return true;
	}
	
	
}


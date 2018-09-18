package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class Login implements Serializable {

	private static final long serialVersionUID = -5493233987085523214L;

	private Long cdLogin;
	private Long cdTipoLogin;
	private String fotoPerfilB64;
	private String senha;
	private String usuario;
	private boolean temBloqueio; //201809181556 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)
	private String codigoTipoBloqueio; //201809181556 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)
	private String descricaoTipoBloqueio; //201809181556 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Long getCdLogin() {
		return cdLogin;
	}

	public void setCdLogin(Long cdLogin) {
		this.cdLogin = cdLogin;
	}

	public Long getCdTipoLogin() {
		return cdTipoLogin;
	}

	public void setCdTipoLogin(Long cdTipoLogin) {
		this.cdTipoLogin = cdTipoLogin;
	}

	public String getFotoPerfilB64() {
		return fotoPerfilB64;
	}

	public void setFotoPerfilB64(String fotoPerfilB64) {
		this.fotoPerfilB64 = fotoPerfilB64;
	}
	
	public boolean isTemBloqueio() {
		return temBloqueio;
	}

	public void setTemBloqueio(boolean temBloqueio) {
		this.temBloqueio = temBloqueio;
	}

	public String getCodigoTipoBloqueio() {
		return codigoTipoBloqueio;
	}

	public void setCodigoTipoBloqueio(String codigoTipoBloqueio) {
		this.codigoTipoBloqueio = codigoTipoBloqueio;
	}

	public String getDescricaoTipoBloqueio() {
		return descricaoTipoBloqueio;
	}

	public void setDescricaoTipoBloqueio(String descricaoTipoBloqueio) {
		this.descricaoTipoBloqueio = descricaoTipoBloqueio;
	}

	@Override
	public String toString() {
		return "Login [" 
				+ "cdLogin=" + cdLogin 
				+ ", cdTipoLogin=" + cdTipoLogin 
				+ ", fotoPerfilB64=" + fotoPerfilB64
				+ ", senha=" + senha 
				+ ", usuario=" + usuario 
				+ ", temBloqueio=" + temBloqueio 
				+ ", codigoTipoBloqueio=" + codigoTipoBloqueio 
				+ ", descricaoTipoBloqueio=" + descricaoTipoBloqueio 
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cdLogin == null) ? 0 : cdLogin.hashCode());
		result = prime * result + ((cdTipoLogin == null) ? 0 : cdTipoLogin.hashCode());
		result = prime * result + ((codigoTipoBloqueio == null) ? 0 : codigoTipoBloqueio.hashCode());
		result = prime * result + ((descricaoTipoBloqueio == null) ? 0 : descricaoTipoBloqueio.hashCode());
		result = prime * result + ((fotoPerfilB64 == null) ? 0 : fotoPerfilB64.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + (temBloqueio ? 1231 : 1237);
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Login other = (Login) obj;
		if (cdLogin == null) {
			if (other.cdLogin != null)
				return false;
		} else if (!cdLogin.equals(other.cdLogin))
			return false;
		if (cdTipoLogin == null) {
			if (other.cdTipoLogin != null)
				return false;
		} else if (!cdTipoLogin.equals(other.cdTipoLogin))
			return false;
		if (codigoTipoBloqueio == null) {
			if (other.codigoTipoBloqueio != null)
				return false;
		} else if (!codigoTipoBloqueio.equals(other.codigoTipoBloqueio))
			return false;
		if (descricaoTipoBloqueio == null) {
			if (other.descricaoTipoBloqueio != null)
				return false;
		} else if (!descricaoTipoBloqueio.equals(other.descricaoTipoBloqueio))
			return false;
		if (fotoPerfilB64 == null) {
			if (other.fotoPerfilB64 != null)
				return false;
		} else if (!fotoPerfilB64.equals(other.fotoPerfilB64))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (temBloqueio != other.temBloqueio)
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

}

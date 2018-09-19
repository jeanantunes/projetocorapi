package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Objects;

public class Login implements Serializable {

    private static final long serialVersionUID = -5493233987085523214L;

    private Long cdLogin;
    private Long cdTipoLogin;
    private String fotoPerfilB64;
    private String senha;
    private String usuario;
    private boolean temBloqueio; //201809181556 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)
    private Long codigoTipoBloqueio; //201809181556 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)
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

    public boolean getTemBloqueio() {
        return temBloqueio;
    }

    public void setTemBloqueio(boolean temBloqueio) {
        this.temBloqueio = temBloqueio;
    }

    public Long getCodigoTipoBloqueio() {
        return codigoTipoBloqueio;
    }

    public void setCodigoTipoBloqueio(Long codigoTipoBloqueio) {
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
        return "Login{" +
                "cdLogin=" + cdLogin +
                ", cdTipoLogin=" + cdTipoLogin +
                ", fotoPerfilB64='" + fotoPerfilB64 + '\'' +
                ", senha='" + senha + '\'' +
                ", usuario='" + usuario + '\'' +
                ", temBloqueio=" + temBloqueio +
                ", codigoTipoBloqueio=" + codigoTipoBloqueio +
                ", descricaoTipoBloqueio='" + descricaoTipoBloqueio + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return temBloqueio == login.temBloqueio &&
                Objects.equals(cdLogin, login.cdLogin) &&
                Objects.equals(cdTipoLogin, login.cdTipoLogin) &&
                Objects.equals(fotoPerfilB64, login.fotoPerfilB64) &&
                Objects.equals(senha, login.senha) &&
                Objects.equals(usuario, login.usuario) &&
                Objects.equals(codigoTipoBloqueio, login.codigoTipoBloqueio) &&
                Objects.equals(descricaoTipoBloqueio, login.descricaoTipoBloqueio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cdLogin, cdTipoLogin, fotoPerfilB64, senha, usuario, temBloqueio, codigoTipoBloqueio, descricaoTipoBloqueio);
    }
}

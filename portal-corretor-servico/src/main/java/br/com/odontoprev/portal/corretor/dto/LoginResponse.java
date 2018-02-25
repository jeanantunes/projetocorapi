package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    private static final long serialVersionUID = -5493233987085523214L;

    private String usuario;

    private Boolean sucesso;
    private Long cdLogin;


    public LoginResponse(String usuario, Boolean sucesso, Long cdLogin) {
        this.usuario = usuario;
        this.sucesso = sucesso;
        this.cdLogin = cdLogin;
    }

    public Long getCdLogin() {
        return cdLogin;
    }

    public void setCdLogin(Long cdLogin) {
        this.cdLogin = cdLogin;
    }

    public Boolean getSucesso() {
        return sucesso;
    }

    public void setSucesso(Boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}

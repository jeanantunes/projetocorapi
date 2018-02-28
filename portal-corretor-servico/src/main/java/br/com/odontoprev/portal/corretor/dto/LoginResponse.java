package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    private static final long serialVersionUID = -5493233987085523214L;

    private long codigoUsuario;
    private String nomeUsuario;
    private String documento;
    private long codigoCorretora;
    private String nomeCorretora;
    private String perfil;

    public LoginResponse() {

    }

    public long getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(long codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public long getCodigoCorretora() {
        return codigoCorretora;
    }

    public String getNomeCorretora() {
        return nomeCorretora;
    }

    public void setNomeCorretora(String nomeCorretora) {
        this.nomeCorretora = nomeCorretora;
    }

    public void setCodigoCorretora(long codigoCorretora) {
        this.codigoCorretora = codigoCorretora;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public LoginResponse(long codigoUsuario, String nomeUsuario, String documento, long codigoCorretora, String nomeCorretora, String perfil) {
        this.codigoUsuario = codigoUsuario;
        this.nomeUsuario = nomeUsuario;
        this.documento = documento;
        this.codigoCorretora = codigoCorretora;
        this.nomeCorretora = nomeCorretora;
        this.perfil = perfil;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}

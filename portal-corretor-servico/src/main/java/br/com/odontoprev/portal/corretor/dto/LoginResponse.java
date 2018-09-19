package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    private static final long serialVersionUID = -5493233987085523214L;

    private long codigoDcss;
    private long codigoUsuario;
    private String nomeUsuario;
    private String documento;
    private long codigoCorretora;
    private String nomeCorretora;
    private String perfil;
    private String dtAceiteContrato;
    private boolean temBloqueio;
    private Long codigoTipoBloqueio;
    private String descricaoTipoBloqueio;

    public LoginResponse() {

    }

    public LoginResponse(long codigoDcss, long codigoUsuario, String nomeUsuario, String documento,
                         long codigoCorretora, String nomeCorretora, String perfil,
                         boolean temBloqueio, long codigoTipoBloqueio, String descricaoTipoBloqueio) {
        this.codigoDcss = codigoDcss;
        this.codigoUsuario = codigoUsuario;
        this.nomeUsuario = nomeUsuario;
        this.documento = documento;
        this.codigoCorretora = codigoCorretora;
        this.nomeCorretora = nomeCorretora;
        this.perfil = perfil;
        this.temBloqueio = temBloqueio;
        this.codigoTipoBloqueio = codigoTipoBloqueio;
        this.descricaoTipoBloqueio = descricaoTipoBloqueio;
    }

    public LoginResponse(long codigoUsuario, String nomeUsuario, String documento,
                         long codigoCorretora, String nomeCorretora, String perfil, String dtAceiteContrato,
                         boolean temBloqueio, long codigoTipoBloqueio, String descricaoTipoBloqueio) {
        this.codigoUsuario = codigoUsuario;
        this.nomeUsuario = nomeUsuario;
        this.documento = documento;
        this.codigoCorretora = codigoCorretora;
        this.nomeCorretora = nomeCorretora;
        this.perfil = perfil;
        this.dtAceiteContrato = dtAceiteContrato;
        this.temBloqueio = temBloqueio;
        this.codigoTipoBloqueio = codigoTipoBloqueio;
        this.descricaoTipoBloqueio = descricaoTipoBloqueio;
    }

    public long getCodigoDcss() {
        return codigoDcss;
    }

    public void setCodigoDcss(long codigoDcss) {
        this.codigoDcss = codigoDcss;
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

    public String getDtAceiteContrato() {
        return dtAceiteContrato;
    }

    public void setDtAceiteContrato(String dtAceiteContrato) {
        this.dtAceiteContrato = dtAceiteContrato;
    }

    public boolean isTemBloqueio() {
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
        return "LoginResponse{" +
                "codigoDcss=" + codigoDcss +
                ", codigoUsuario=" + codigoUsuario +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", documento='" + documento + '\'' +
                ", codigoCorretora=" + codigoCorretora +
                ", nomeCorretora='" + nomeCorretora + '\'' +
                ", perfil='" + perfil + '\'' +
                ", dtAceiteContrato='" + dtAceiteContrato + '\'' +
                ", temBloqueio=" + temBloqueio +
                ", codigoTipoBloqueio=" + codigoTipoBloqueio +
                ", descricaoTipoBloqueio='" + descricaoTipoBloqueio + '\'' +
                '}';
    }

}

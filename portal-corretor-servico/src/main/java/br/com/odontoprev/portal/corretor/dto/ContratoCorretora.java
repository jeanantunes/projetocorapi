package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Date;

public class ContratoCorretora implements Serializable {

    private static final long serialVersionUID = 2180567984276302023L;

    private Long cdContratoCorretora;
    private Long cdCorretora;
    private Long cdContratoModelo;
    private String dtAceiteContrato;
    private String cdSusep;

    public Long getCdContratoCorretora() {
        return cdContratoCorretora;
    }

    public void setCdContratoCorretora(Long cdContratoCorretora) {
        this.cdContratoCorretora = cdContratoCorretora;
    }

    public Long getCdCorretora() {
        return cdCorretora;
    }

    public void setCdCorretora(Long cdCorretora) {
        this.cdCorretora = cdCorretora;
    }

    public Long getCdContratoModelo() {
        return cdContratoModelo;
    }

    public void setCdContratoModelo(Long cdContratoModelo) {
        this.cdContratoModelo = cdContratoModelo;
    }

    public String getDtAceiteContrato() {
        return dtAceiteContrato;
    }

    public void setDtAceiteContrato(String dtAceiteContrato) {
        this.dtAceiteContrato = dtAceiteContrato;
    }

    public String getCdSusep() {
        return cdSusep;
    }

    public void setCdSusep(String cdSusep) {
        this.cdSusep = cdSusep;
    }

    @Override
    public String toString() {
        return "ContratoCorretora{" +
                "cdContratoCorretora=" + cdContratoCorretora +
                ", cdCorretora=" + cdCorretora +
                ", cdContratoModelo=" + cdContratoModelo +
                ", dtAceiteContrato='" + dtAceiteContrato + '\'' +
                ", cdSusep='" + cdSusep + '\'' +
                '}';
    }
}

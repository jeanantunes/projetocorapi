package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Objects;

public class PlanoInfo implements Serializable {

    Long codigoPlanoInfo;
    String nomePlanoInfo;
    String descricao;
    Long codigoArquivoGeral;
    Long codigoArquivoCarencia;
    Long cdArquivoIcone;
    String tipoSegmento;
    String ativo;
    Long codigoArquivoIcone;
    Arquivo arquivoIcone;


    public Long getCodigoPlanoInfo() {
        return codigoPlanoInfo;
    }

    public void setCodigoPlanoInfo(Long codigoPlanoInfo) {
        this.codigoPlanoInfo = codigoPlanoInfo;
    }

    public String getNomePlanoInfo() {
        return nomePlanoInfo;
    }

    public void setNomePlanoInfo(String nomePlanoInfo) {
        this.nomePlanoInfo = nomePlanoInfo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getCodigoArquivoGeral() {
        return codigoArquivoGeral;
    }

    public void setCodigoArquivoGeral(Long codigoArquivoGeral) {
        this.codigoArquivoGeral = codigoArquivoGeral;
    }

    public Long getCodigoArquivoCarencia() {
        return codigoArquivoCarencia;
    }

    public void setCodigoArquivoCarencia(Long codigoArquivoCarencia) {
        this.codigoArquivoCarencia = codigoArquivoCarencia;
    }

    public Long getCdArquivoIcone() {
        return cdArquivoIcone;
    }

    public void setCdArquivoIcone(Long cdArquivoIcone) {
        this.cdArquivoIcone = cdArquivoIcone;
    }

    public String getTipoSegmento() {
        return tipoSegmento;
    }

    public void setTipoSegmento(String tipoSegmento) {
        this.tipoSegmento = tipoSegmento;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public Long getCodigoArquivoIcone() {
        return codigoArquivoIcone;
    }

    public void setCodigoArquivoIcone(Long codigoArquivoIcone) {
        this.codigoArquivoIcone = codigoArquivoIcone;
    }

    public Arquivo getArquivoIcone() {
        return arquivoIcone;
    }

    public void setArquivoIcone(Arquivo arquivoIcone) {
        this.arquivoIcone = arquivoIcone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanoInfo planoInfo = (PlanoInfo) o;
        return Objects.equals(codigoPlanoInfo, planoInfo.codigoPlanoInfo) &&
                Objects.equals(nomePlanoInfo, planoInfo.nomePlanoInfo) &&
                Objects.equals(descricao, planoInfo.descricao) &&
                Objects.equals(codigoArquivoGeral, planoInfo.codigoArquivoGeral) &&
                Objects.equals(codigoArquivoCarencia, planoInfo.codigoArquivoCarencia) &&
                Objects.equals(cdArquivoIcone, planoInfo.cdArquivoIcone) &&
                Objects.equals(tipoSegmento, planoInfo.tipoSegmento) &&
                Objects.equals(ativo, planoInfo.ativo) &&
                Objects.equals(codigoArquivoIcone, planoInfo.codigoArquivoIcone) &&
                Objects.equals(arquivoIcone, planoInfo.arquivoIcone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoPlanoInfo, nomePlanoInfo, descricao, codigoArquivoGeral, codigoArquivoCarencia, cdArquivoIcone, tipoSegmento, ativo, codigoArquivoIcone, arquivoIcone);
    }

    @Override
    public String toString() {
        return "PlanoInfo{" +
                "codigoPlanoInfo=" + codigoPlanoInfo +
                ", nomePlanoInfo='" + nomePlanoInfo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", codigoArquivoGeral=" + codigoArquivoGeral +
                ", codigoArquivoCarencia=" + codigoArquivoCarencia +
                ", cdArquivoIcone=" + cdArquivoIcone +
                ", tipoSegmento='" + tipoSegmento + '\'' +
                ", ativo='" + ativo + '\'' +
                ", codigoArquivoIcone=" + codigoArquivoIcone +
                ", arquivoIcone=" + arquivoIcone +
                '}';
    }
}

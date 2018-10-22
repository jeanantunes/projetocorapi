package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Objects;

public class Arquivo implements Serializable {

    Long cdArquivo;
    String  nomeArquivo;
    String tipoConteudo;
    Long tamanho;
    String dataCriacao;
    String arquivoBase64;
    String caminhoArquivo;

    public Long getCdArquivo() {
        return cdArquivo;
    }

    public void setCdArquivo(Long cdArquivo) {
        this.cdArquivo = cdArquivo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getTipoConteudo() {
        return tipoConteudo;
    }

    public void setTipoConteudo(String tipoConteudo) {
        this.tipoConteudo = tipoConteudo;
    }

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getArquivoBase64() {
        return arquivoBase64;
    }

    public void setArquivoBase64(String arquivoBase64) {
        this.arquivoBase64 = arquivoBase64;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arquivo arquivo = (Arquivo) o;
        return Objects.equals(cdArquivo, arquivo.cdArquivo) &&
                Objects.equals(nomeArquivo, arquivo.nomeArquivo) &&
                Objects.equals(tipoConteudo, arquivo.tipoConteudo) &&
                Objects.equals(tamanho, arquivo.tamanho) &&
                Objects.equals(dataCriacao, arquivo.dataCriacao) &&
                Objects.equals(arquivoBase64, arquivo.arquivoBase64) &&
                Objects.equals(caminhoArquivo, arquivo.caminhoArquivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cdArquivo, nomeArquivo, tipoConteudo, tamanho, dataCriacao, arquivoBase64, caminhoArquivo);
    }

    @Override
    public String toString() {
        return "Arquivo{" +
                "cdArquivo=" + cdArquivo +
                ", nomeArquivo='" + nomeArquivo + '\'' +
                ", tipoConteudo='" + tipoConteudo + '\'' +
                ", tamanho=" + tamanho +
                ", dataCriacao='" + dataCriacao + '\'' +
                ", arquivoBase64='" + arquivoBase64 + '\'' +
                ", caminhoArquivo='" + caminhoArquivo + '\'' +
                '}';
    }
}

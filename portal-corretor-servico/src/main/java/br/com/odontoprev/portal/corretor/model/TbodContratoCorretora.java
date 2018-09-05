package br.com.odontoprev.portal.corretor.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

//201808231711 - esert - COR-617 - nova tabela TBOD_ARQUIVO_CONTRATACAO
@Entity
@Table(name = "TBOD_CONTRATO_CORRETORA")
public class TbodContratoCorretora implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CD_CORRETORA")
    private Long cdCorretora;

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_ACEITE_CONTRATO")
    private Date dtAceiteContrato;

    @Column(name = "TAM_ARQUIVO")
    private Long tamArquivo;

    @Column(name = "TIPO_CONTEUDO")
    private String tipoConteudo;

    @Column(name = "ARQUIVO")
    private byte[] arquivo;

    public Long getCdCorretora() {
        return cdCorretora;
    }

    public void setCdCorretora(Long cdCorretora) {
        this.cdCorretora = cdCorretora;
    }

    public Date getDtAceiteContrato() {
        return dtAceiteContrato;
    }

    public void setDtAceiteContrato(Date dtAceiteContrato) {
        this.dtAceiteContrato = dtAceiteContrato;
    }

    public Long getTamArquivo() {
        return tamArquivo;
    }

    public void setTamArquivo(Long tamArquivo) {
        this.tamArquivo = tamArquivo;
    }

    public String getTipoConteudo() {
        return tipoConteudo;
    }

    public void setTipoConteudo(String tipoConteudo) {
        this.tipoConteudo = tipoConteudo;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    @Override
    public String toString() {
        return "TbodContratoCorretora{" +
                "cdCorretora=" + cdCorretora +
                ", dtAceiteContrato=" + dtAceiteContrato +
                ", tamArquivo=" + tamArquivo +
                ", tipoConteudo='" + tipoConteudo + '\'' +
                ", arquivo=" + Arrays.toString(arquivo) +
                '}';
    }
}
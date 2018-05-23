package br.com.odontoprev.portal.corretor.model;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the TBOD_MENSAGEM_PADRAO database table.
 *
 */
//TODO CLASSE CRIADA SEM A EXISTENCIA DA TABELA, FAVOR REAVALIA-LA APÓS A CRIACAO DA MESMA
@Entity
@Table(name="TBOD_MENSAGEM_PADRAO")
@NamedQuery(name="TbodMensagemPadrao.findAll", query="SELECT m FROM TbodMensagemPadrao m")
public class TbodMensagemPadrao {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CD_MENSAGEM")
    private Long cdMensagem;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name="MENSAGEM")
    private String mensagem;

    //bi-directional many-to-one association to TbodForcaVenda
    @OneToMany(mappedBy="TbodMensagemPadrao")
    private List<TbodMensagemPadrao> TbodMensagemPadrao;

    public TbodMensagemPadrao() {
    }

    public Long getCdMensagem() {
        return cdMensagem;
    }

    public void setCdMensagem(Long cdMensagem) {
        this.cdMensagem = cdMensagem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<br.com.odontoprev.portal.corretor.model.TbodMensagemPadrao> getTbodMensagemPadrao() {
        return TbodMensagemPadrao;
    }

    public void setTbodMensagemPadrao(List<br.com.odontoprev.portal.corretor.model.TbodMensagemPadrao> tbodMensagemPadrao) {
        TbodMensagemPadrao = tbodMensagemPadrao;
    }
}
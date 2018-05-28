package br.com.odontoprev.portal.corretor.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the TBOD_MENSAGEM_PADRAO database table.
 *
 */
//TODO CLASSE CRIADA SEM A EXISTENCIA DA TABELA, FAVOR REAVALIA-LA APÓS A CRIACAO DA MESMA
@Entity
@Table(name="TBOD_MENSAGEM_PADRAO")
@NamedQuery(name="TbodNotificationTemplate.findAll", query="SELECT m FROM TbodNotificationTemplate m")
public class TbodNotificationTemplate {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CD_MENSAGEM")
    private Long cdMensagem;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name="MENSAGEM")
    private String mensagem;

    @Column(name="DATA_INCLUSAO")
    private Date dataInclusao;

    @Column(name="DATA_ATUALIZACAO")
    private Date dataAtualizacao;

    //bi-directional many-to-one association to TbodForcaVenda
    @OneToMany(mappedBy="TbodNotificationTemplate")
    private List<TbodNotificationTemplate> TbodNotificationTemplate;

    public TbodNotificationTemplate() {
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public List<TbodNotificationTemplate> getTbodNotificationTemplate() {
        return TbodNotificationTemplate;
    }

    public void setTbodNotificationTemplate(List<TbodNotificationTemplate> TbodNotificationTemplate) {
        TbodNotificationTemplate = TbodNotificationTemplate;
    }
}
package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name="TBOD_SISTEMA_PUSH")
@Entity
public class TbodSistemaPush implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="CD_SISTEMA")
    private Integer codigoSistema;

    @Column(name="NM_SISTEMA")
    private String sistema;

    @Column(name="DS_DESCRICAO")
    private String descricao;

    @Column(name="NM_PROJETO_FIREBASE")
    private String projetoFirebase;

    @Column(name="TXT_PRIVATE_KEY")
    private String textoPrivateKey;

    @Column(name="DT_INCLUSAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInclusao;

    @Column(name="DT_ALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;

    public Integer getCodigoSistema() {
        return codigoSistema;
    }

    public void setCodigoSistema(Integer codigoSistema) {
        this.codigoSistema = codigoSistema;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTextoPrivateKey() {
        return textoPrivateKey;
    }

    public void setTextoPrivateKey(String textoPrivateKey) {
        this.textoPrivateKey = textoPrivateKey;
    }

    public Date getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public String getProjetoFirebase() {
        return projetoFirebase;
    }

    public void setProjetoFirebase(String projetoFirebase) {
        this.projetoFirebase = projetoFirebase;
    }

    @Override
    public String toString() {
        return "{\"codigoSistema\":\"" + codigoSistema + "\", sistema\":\"" + sistema + "\", descricao\":\"" + descricao
                + "\", projetoFirebase\":\"" + projetoFirebase + "\", textoPrivateKey\":\"" + textoPrivateKey
                + "\", dataInclusao\":\"" + dataInclusao + "\", dataAlteracao\":\"" + dataAlteracao + "}";
    }


}

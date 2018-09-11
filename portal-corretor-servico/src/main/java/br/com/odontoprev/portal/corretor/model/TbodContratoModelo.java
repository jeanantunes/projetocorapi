package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//201809101646 - esert - COR-709 - Serviço - Novo serviço GET/contratocorretora/cdCor/tipo/cdTipo
//201809101646 - esert - COR-751 - DB - nova tabela TBOD_CONTRATO_MODELO
//201809111157 - esert - COR-760 - Serviço - cria POST/contratomodelo
@Entity
@Table(name = "TBOD_CONTRATO_MODELO")
public class TbodContratoModelo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CD_CONTRATO_MODELO")
    private Long cdContratoModelo;

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_CRIACAO")
    private Date dtCriacao;

    @Column(name = "NOME_ARQUIVO")
    private String nomeArquivo;
    
    @Column(name = "TAMANHO_ARQUIVO")
    private Long tamanhoArquivo;
    
    @Column(name = "TIPO_CONTEUDO")
    private String tipoConteudo;
    
    @Column(name = "ARQUIVO")
    private byte[] arquivo;

	public Long getCdContratoModelo() {
		return cdContratoModelo;
	}

	public void setCdContratoModelo(Long cdContratoModelo) {
		this.cdContratoModelo = cdContratoModelo;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public Long getTamanhoArquivo() {
		return tamanhoArquivo;
	}

	public void setTamanhoArquivo(Long tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
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
		return "TbodContratoModelo [" 
				+ "cdContratoModelo=" + cdContratoModelo 
				+ ", dtCriacao=" + dtCriacao
				+ ", nomeArquivo=" + nomeArquivo
				+ ", tamanhoArquivo=" + tamanhoArquivo
				+ ", tipoConteudo=" + tipoConteudo
				+ ", arquivo=" + (arquivo != null ? String.valueOf(Arrays.toString(arquivo).length()) : "NuLL") + "]";
	}

}
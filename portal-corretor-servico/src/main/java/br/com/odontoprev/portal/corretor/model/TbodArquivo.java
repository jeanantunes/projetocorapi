package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//201810221513 - esert - COR-721:API - Novo POST/ARQUIVO Fazer Carga
@Entity
@Table(name = "TBOD_ARQUIVO")
public class TbodArquivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_ARQUIVO", sequenceName = "SEQ_TBOD_ARQUIVO", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_ARQUIVO")
	@Column(name = "CD_ARQUIVO") //"CD_ARQUIVO" NUMBER(10) NOT NULL, -- PK
	private Long codigoArquivo;

	@Column(name = "NOME_ARQUIVO") //"NOME_ARQUIVO" VARCHAR(255) NOT NULL,
	private String nomeArquivo;

	//h t t p s : // stackoverflow .com /questions /35189340 /jpa-date-not-show-hour-minute-second
	@Temporal(TemporalType.TIMESTAMP) //201810231732 - esert - de DATE para TIMESTAMP 
	@Column(name = "DATA_CRIACAO") //"DATA_CRIACAO" DATE NOT NULL,
	private Date dataCriacao;
	
	@Column(name = "TIPO_CONTEUDO") //"TIPO_CONTEUDO" VARCHAR(255) NOT NULL,
	private String tipoConteudo;

	@Column(name = "TAMANHO") //"TAMANHO" NUMBER (10) NOT NULL,
	private Long tamanho;
	
	@Column(name = "ARQUIVO") //"ARQUIVO" BLOB NOT NULL
	private byte[] arquivo;

	public TbodArquivo() {
	}
	
	public Long getCodigoArquivo() {
		return codigoArquivo;
	}

	public void setCodigoArquivo(Long codigoArquivo) {
		this.codigoArquivo = codigoArquivo;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public Long getTamanho() {
		return tamanho;
	}

	public void setTamanhoArquivo(Long tamanhoArquivo) {
		this.tamanho = tamanhoArquivo;
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
		return "TbodMaterialDivulgacao [" 
				+ "codigoArquivo=" + codigoArquivo
				+ ", dataCriacao=" + dataCriacao 
				+ ", nomeArquivo=" + nomeArquivo
				+ ", tamanho=" + tamanho
				+ ", tipoConteudo=" + tipoConteudo 
				+ ", arquivo=" + arquivo!=null?String.valueOf(arquivo.length):"NuLL" //201808231709
				+ "]";
	}

}
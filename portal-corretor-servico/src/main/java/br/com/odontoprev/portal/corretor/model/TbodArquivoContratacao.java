package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//201808231711 - esert - COR-617 - nova tabela TBOD_ARQUIVO_CONTRATACAO
@Entity
@Table(name = "TBOD_ARQUIVO_CONTRATACAO")
public class TbodArquivoContratacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CD_EMPRESA") //"CD_EMPRESA" NUMBER(10,0) DEFAULT NULL NOT NULL ENABLE, 
	private Long codigoEmpresa;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_CRIACAO") //"DATA_CRIACAO" DATE NOT NULL ENABLE, 
	private Date dataCriacao;

	@Column(name = "NOME_ARQUIVO") //"NOME_ARQUIVO" NVARCHAR2(255) NOT NULL ENABLE,
	private String nomeArquivo;

	@Column(name = "TAMANHO_ARQUIVO") //"TAMANHO_ARQUIVO" NUMBER(10) NOT NULL ENABLE,
	private Long tamanhoArquivo;
	
	@Column(name = "TIPO_CONTEUDO") //"TIPO_CONTEUDO" NVARCHAR2(20) NOT NULL ENABLE, 
	private String tipoConteudo;
	
	@Column(name = "ARQUIVO") //"ARQUIVO" BLOB NOT NULL ENABLE, 
	private byte[] arquivo;

	public TbodArquivoContratacao() {
	}
	
	public Long getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(Long codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
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
		return "TbodMaterialDivulgacao [" 
				+ "codigoEmpresa=" + codigoEmpresa 
				+ ", dataCriacao=" + dataCriacao 
				+ ", nomeArquivo=" + nomeArquivo
				+ ", tamanhoArquivo=" + tamanhoArquivo
				+ ", tipoConteudo=" + tipoConteudo 
				+ ", arquivo=" + arquivo!=null?String.valueOf(arquivo.length):"NuLL" //201808231709
				+ "]";
	}

}
package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*201810221532 - esert - COR-932:API - Novo GET /planoinfo*/
@Entity
@Table(name = "TBOD_PLANO_INFO")
public class TbodPlanoInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CD_PLANO_INFO") //"CD_PLANO_INFO" NUMBER(10) NOT NULL, -- PK será referenciada pela TBOD_PLANO.
	@SequenceGenerator(name = "SEQ_TBOD_PLANO_INFO", sequenceName = "SEQ_TBOD_PLANO_INFO", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_PLANO_INFO")
	private Long cdPlanoInfo;

	@Column(name = "NOME_PLANO_INFO") //"NOME_PLANO_INFO" VARCHAR(255) NOT NULL,
	private String nomePlanoInfo;

	@Column(name = "DESCRICAO") //"DESCRICAO" VARCHAR(2000) NOT NULL, -- \n para quebra de linha
	private String descricao;

	// bi-directional many-to-one association to TbodTipoPlano
	@ManyToOne
	@JoinColumn(name = "CD_ARQUIVO_GERAL") //"CD_ARQUIVO_GERAL" NUMBER(10) NULL, -- FK TBOD_ARQUIVO.CD_ARQUIVO
	private TbodArquivo tbodArquivoGeral;
	
	// bi-directional many-to-one association to TbodTipoPlano
	@ManyToOne
	@JoinColumn(name = "CD_ARQUIVO_CARENCIA") //"CD_ARQUIVO_CARENCIA" NUMBER(10) NULL, -- FK TBOD_ARQUIVO.CD_ARQUIVO
	private TbodArquivo tbodArquivoCarencia;
	
	// bi-directional many-to-one association to TbodTipoPlano
	@ManyToOne
	@JoinColumn(name = "CD_ARQUIVO_ICONE") //"CD_ARQUIVO_ICONE" NUMBER(10) NULL, -- FK TBOD_ARQUIVO.CD_ARQUIVO
	private TbodArquivo tbodArquivoIcone;

	@Column(name = "TIPO_SEGMENTO") //"TIPO_SEGMENTO" VARCHAR(3) NOT NULL,
	private String tipoSegmento;
	
	@Column(name = "ATIVO") //"ATIVO" VARCHAR(1) NOT NULL
	private String ativo;

	public TbodPlanoInfo() {
	}

	public Long getCdPlanoInfo() {
		return cdPlanoInfo;
	}

	public void setCdPlanoInfo(long cdPlanoInfo) {
		this.cdPlanoInfo = cdPlanoInfo;
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

	public TbodArquivo getTbodArquivoGeral() {
		return tbodArquivoGeral;
	}

	public void setTbodArquivoGeral(TbodArquivo tbodArquivoGeral) {
		this.tbodArquivoGeral = tbodArquivoGeral;
	}

	public TbodArquivo getTbodArquivoCarencia() {
		return tbodArquivoCarencia;
	}

	public void setTbodArquivoCarencia(TbodArquivo tbodArquivoCarencia) {
		this.tbodArquivoCarencia = tbodArquivoCarencia;
	}

	public TbodArquivo getTbodArquivoIcone() {
		return tbodArquivoIcone;
	}

	public void setTbodArquivoIcone(TbodArquivo tbodArquivoIcone) {
		this.tbodArquivoIcone = tbodArquivoIcone;
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

	@Override
	public String toString() {
		return "TbodPlanoInfo [" 
				+ "cdPlanoInfo=" + cdPlanoInfo 
				+ ", nomePlanoInfo=" + nomePlanoInfo 
				+ ", descricao=" + descricao 
				+ ", tbodArquivoGeral=" + tbodArquivoGeral 
				+ ", tbodArquivoCarencia=" + tbodArquivoCarencia
				+ ", tbodArquivoIcone=" + tbodArquivoIcone 
				+ ", tipoSegmento=" + tipoSegmento 
				+ ", ativo=" + ativo
				+ "]";
	}
}
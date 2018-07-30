package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the TBOD_PLANO database table.
 * 
 */
@Entity
@Table(name = "TBOD_MAT_DIVULGA")
@NamedQuery(
		name = "TbodMaterialDivulgacao.findAllAtivo", 
		query = "SELECT t FROM TbodMaterialDivulgacao t where t.ativo='S' order by codigoCategoria, codigoSubCategoria, codigoMaterialDivulgacao"
		)
public class TbodMaterialDivulgacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_MAT_DIVULGA", sequenceName = "SEQ_TBOD_MAT_DIVULGA", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_MAT_DIVULGA")
	@Column(name = "CD_MAT_DIVULGA")
	private Long codigoMaterialDivulgacao;
	
	@Column(name = "CD_CATEGORIA")
	private Long codigoCategoria;
	
	@Column(name = "CD_SUB_CATEGORIA")
	private Long codigoSubCategoria;

	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	@Column(name = "TIPO_CONTEUDO")
	private String tipoConteudo;
	
	@Column(name = "THUMBNAIL")
	private byte[] thumbnail;
	
	@Column(name = "ARQUIVO")
	private byte[] arquivo;
	
	@Column(name = "ATIVO")
	private String ativo; //S|N //201803031736 String seguindo padrao outras tabelas Corretora e ForcaVenda (esertorio@vector)(moliveira@odpv)

	public TbodMaterialDivulgacao() {
	}

	public Long getCodigoMaterialDivulgacao() {
		return codigoMaterialDivulgacao;
	}

	public void setCodigoMaterialDivulgacao(Long codigoMaterialDivulgacao) {
		this.codigoMaterialDivulgacao = codigoMaterialDivulgacao;
	}

	public Long getCodigoCategoria() {
		return codigoCategoria;
	}

	public void setCodigoCategoria(Long codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}

	public Long getCodigoSubCategoria() {
		return codigoSubCategoria;
	}

	public void setCodigoSubCategoria(Long codigoSubCategoria) {
		this.codigoSubCategoria = codigoSubCategoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoConteudo() {
		return tipoConteudo;
	}

	public void setTipoConteudo(String tipoConteudo) {
		this.tipoConteudo = tipoConteudo;
	}

	public byte[] getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		return "TbodMaterialDivulgacao [" 
				+ "codigoMaterialDivulgacao=" + codigoMaterialDivulgacao 
				+ ", codigoCategoria=" + codigoCategoria 
				+ ", codigoSubCategoria=" + codigoSubCategoria 
				+ ", nome=" + nome 
				+ ", descricao=" + descricao 
				+ ", tipoConteudo=" + tipoConteudo 
				+ ", thumbnail=" + thumbnail!=null?String.valueOf(thumbnail.length):"NuLL" //201807121806
				+ ", arquivo=" + arquivo!=null?String.valueOf(arquivo.length):"NuLL" //201807121806
				+ ", ativo=" + ativo 
				+ "]";
	}

}
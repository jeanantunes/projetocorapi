package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class MaterialDivulgacao implements Serializable{

	private static final long serialVersionUID = 5015692697620981889L;
	
	private Long codigoMaterialDivulgacao;
	private Long codigoCategoria;
	private Long codigoSubCategoria;
	private String nome;
	private String descricao; //desc do material de divulgacao
	private String tipoConteudo; //'img/jpg'
	private String thumbnail; //String base64
	private String arquivo; //String base64
	private String ativo; //S|N
	
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
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getArquivo() {
		return arquivo;
	}
	public void setArquivo(String arquivo) {
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
		return "MaterialDivulgacao [" 
				+ "  codigoMaterialDivulgacao=" + codigoMaterialDivulgacao 
				+ ", codigoCategoria=" + codigoCategoria 
				+ ", codigoSubCategoria=" + codigoSubCategoria 
				+ ", nome=" + nome 
				+ ", descricao=" + descricao 
				+ ", tipoConteudo=" + tipoConteudo 
				+ ", thumbnail=" + thumbnail 
				+ ", arquivo=" + arquivo
				+ ", ativo=" + ativo 
				+ "]";
	}
		
}

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
	private Integer tamanhoThumbnail;
	private String arquivo; //String base64
	private Integer tamanhoArquivo;
	private String ativo; //S|N
	private String caminhoCarga; //"c://arquivos_carregados//imagem//"
	
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
	
	public Integer getTamanhoThumbnail() {
		return tamanhoThumbnail;
	}
	public void setTamanhoThumbnail(Integer tamanhoThumbnail) {
		this.tamanhoThumbnail = tamanhoThumbnail;
	}
	
	public String getArquivo() {
		return arquivo;
	}
	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}
	
	public Integer getTamanhoArquivo() {
		return tamanhoArquivo;
	}
	public void setTamanhoArquivo(Integer tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
	}
	
	public String getAtivo() {
		return ativo;
	}
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}
	
	public String getCaminhoCarga() {
		return caminhoCarga;
	}
	public void setCaminhoCarga(String caminhoCarga) {
		this.caminhoCarga = caminhoCarga;
	}
	@Override
	public String toString() {
		String stringThumbnail = thumbnail != null ? String.valueOf(thumbnail.length()) : "-1";
		String stringArquivo = arquivo != null ? String.valueOf(arquivo.length()) : "-1";
		return "MaterialDivulgacao [" 
				+ "  codigoMaterialDivulgacao=" + codigoMaterialDivulgacao 
				+ ", codigoCategoria=" + codigoCategoria 
				+ ", codigoSubCategoria=" + codigoSubCategoria 
				+ ", nome=" + nome 
				+ ", descricao=" + descricao 
				+ ", tipoConteudo=" + tipoConteudo 
				+ ", thumbnail=" + stringThumbnail
				+ ", tamanhoThumbnail=" + tamanhoThumbnail 
				+ ", arquivo=" + stringArquivo
				+ ", tamanhoArquivo=" + tamanhoArquivo
				+ ", ativo=" + ativo 
				+ ", caminhoCarga=" + caminhoCarga 
				+ "]";
	}
		
}

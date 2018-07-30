package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class MaterialDivulgacaoCategoria implements Serializable{

	private static final long serialVersionUID = 1463893192959157795L;
	
	private Long codigoCategoria;
	private String nome;
	private String descricao;
	private List<MaterialDivulgacaoSubCategoria> subCategoriasMaterialDivulgacao;
	
	public Long getCodigoCategoria() {
		return codigoCategoria;
	}
	public void setCodigoCategoria(Long codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
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
	
	public List<MaterialDivulgacaoSubCategoria> getSubCategoriasMaterialDivulgacao() {
		return subCategoriasMaterialDivulgacao;
	}
	public void setSubCategoriasMaterialDivulgacao(List<MaterialDivulgacaoSubCategoria> subCategoriasMaterialDivulgacao) {
		this.subCategoriasMaterialDivulgacao = subCategoriasMaterialDivulgacao;
	}
	
	@Override
	public String toString() {
		return "CategoriaMaterialDivulgacao [" 
				+ "codigoCategoria=" + codigoCategoria 
				+ ", nome=" + nome
				+ ", descricao=" + descricao
				+ ", subCategoriasMaterialDivulgacao=" + subCategoriasMaterialDivulgacao==null ? "NuLL" : subCategoriasMaterialDivulgacao.size()  
				+ "]";
	}

	
}

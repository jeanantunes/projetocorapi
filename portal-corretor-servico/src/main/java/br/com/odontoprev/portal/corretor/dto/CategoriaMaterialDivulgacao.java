package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class CategoriaMaterialDivulgacao implements Serializable{

	private static final long serialVersionUID = 1463893192959157795L;
	
	private int codigoCategoria;
	private String nome;
	private List<SubCategoriaMaterialDivulgacao> subCategoriasMaterialDivulgacao;
	
	public int getCodigoCategoria() {
		return codigoCategoria;
	}
	public void setCodigoCategoria(int codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<SubCategoriaMaterialDivulgacao> getSubCategoriasMaterialDivulgacao() {
		return subCategoriasMaterialDivulgacao;
	}
	public void setSubCategoriasMaterialDivulgacao(List<SubCategoriaMaterialDivulgacao> subCategoriasMaterialDivulgacao) {
		this.subCategoriasMaterialDivulgacao = subCategoriasMaterialDivulgacao;
	}
	
	@Override
	public String toString() {
		return "CategoriaMaterialDivulgacao [" 
				+ "codigoCategoria=" + codigoCategoria 
				+ ", nome=" + nome
				+ ", subCategoriasMaterialDivulgacao=" + subCategoriasMaterialDivulgacao==null ? "NuLL" : subCategoriasMaterialDivulgacao.size()  
				+ "]";
	}

	
}

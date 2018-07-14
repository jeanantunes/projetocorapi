package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class MaterialDivulgacaoSubCategoria implements Serializable{

	private static final long serialVersionUID = 161660561856110485L;

	private Long codigoSubCategoria;
	private String nome;
	private String descricao;
	private List<MaterialDivulgacao> materiaisDivulgacao;
	
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
	
	public List<MaterialDivulgacao> getMateriaisDivulgacao() {
		return materiaisDivulgacao;
	}
	public void setMateriaisDivulgacao(List<MaterialDivulgacao> materiaisDivulgacao) {
		this.materiaisDivulgacao = materiaisDivulgacao;
	}
	
	@Override
	public String toString() {
		return "SubCategoriaMaterialDivulgacao [" 
				+ "codigoSubCategoria=" + codigoSubCategoria 
				+ ", nome=" + nome
				+ ", materiaisDivulgacao=" + materiaisDivulgacao==null ? "NuLL" : materiaisDivulgacao.size()  
				+ "]";
	}
}

package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class SubCategoriaMaterialDivulgacao implements Serializable{

	private static final long serialVersionUID = 161660561856110485L;

	private int codigoSubCategoria;
	private String nome;
	private List<MaterialDivulgacao> materiaisDivulgacao;
	
	public int getCodigoSubCategoria() {
		return codigoSubCategoria;
	}
	public void setCodigoSubCategoria(int codigoSubCategoria) {
		this.codigoSubCategoria = codigoSubCategoria;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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

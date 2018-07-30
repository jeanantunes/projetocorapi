package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class MateriaisDivulgacao implements Serializable{

	private static final long serialVersionUID = 7497003791805071250L;
	
	private List<MaterialDivulgacaoCategoria> categoriasMaterialDivulgacao;

	public List<MaterialDivulgacaoCategoria> getCategoriasMaterialDivulgacao() {
		return categoriasMaterialDivulgacao;
	}
	public void setCategoriasMaterialDivulgacao(List<MaterialDivulgacaoCategoria> categoriasMaterialDivulgacao) {
		this.categoriasMaterialDivulgacao = categoriasMaterialDivulgacao;
	}

	@Override
	public String toString() {
		return "MateriaisDivulgacao [" 
			+ "categoriasMaterialDivulgacao=" + categoriasMaterialDivulgacao==null ? "NuLL" : categoriasMaterialDivulgacao.size()  
			+ "]";
	}	
}

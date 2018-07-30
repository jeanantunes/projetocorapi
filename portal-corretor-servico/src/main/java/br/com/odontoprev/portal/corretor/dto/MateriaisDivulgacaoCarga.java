package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class MateriaisDivulgacaoCarga implements Serializable{

	private static final long serialVersionUID = 7497003791805071250L;
	
	private List<MaterialDivulgacao> materiaisDivulgacao;

	public List<MaterialDivulgacao> getMateriaisDivulgacao() {
		return materiaisDivulgacao;
	}

	public void setMateriaisDivulgacao(List<MaterialDivulgacao> materiaisDivulgacao) {
		this.materiaisDivulgacao = materiaisDivulgacao;
	}

	@Override
	public String toString() {
		return "MateriaisDivulgacaoCarga [materiaisDivulgacao=" + materiaisDivulgacao==null?"NuLL":String.valueOf(materiaisDivulgacao.size()) + "]";
	}
}

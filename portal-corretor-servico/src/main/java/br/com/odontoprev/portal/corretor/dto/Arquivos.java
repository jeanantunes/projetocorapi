package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class Arquivos implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7781101731965347279L;

	List<Arquivo> arquivos;

	public List<Arquivo> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<Arquivo> arquivos) {
		this.arquivos = arquivos;
	}

	@Override
	public String toString() {
		return "Arquivos [arquivos=" + arquivos!=null ? String.valueOf(arquivos.size()) : "NuLL" + "]";
	}	
}

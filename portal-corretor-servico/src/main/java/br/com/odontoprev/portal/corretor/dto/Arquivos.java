package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

//201810241700 - esert - COR-721:API POST/arquivo/carregar - alterado para suportar List<Arquivo>
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arquivos == null) ? 0 : arquivos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arquivos other = (Arquivos) obj;
		if (arquivos == null) {
			if (other.arquivos != null)
				return false;
		} else if (!arquivos.equals(other.arquivos))
			return false;
		return true;
	}
}

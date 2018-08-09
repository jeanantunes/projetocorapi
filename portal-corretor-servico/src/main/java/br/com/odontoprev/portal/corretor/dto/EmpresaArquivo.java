package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class EmpresaArquivo implements Serializable {

    private static final long serialVersionUID = 3103017260662982091L; //TODO: Alterar serialVersionUID

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof EmpresaArquivo)) return false;
		EmpresaArquivo that = (EmpresaArquivo) o;
		return Objects.equals(getListCdEmpresa(), that.getListCdEmpresa());
	}

	@Override
	public int hashCode() {

		return Objects.hash(getListCdEmpresa());
	}

	private Long cdEmpresaTest;
    
    private List<Long> listCdEmpresa;

    public List<Long> getListCdEmpresa() {
        return listCdEmpresa;
    }

    public void setListCdEmpresa(List<Long> cdEmpresa) {
        this.listCdEmpresa = cdEmpresa;
    }

    @Override
	public String toString() {
		return "EmpresaArquivo [" 
			+ "cdEmpresaTest=" + cdEmpresaTest 
			+ ", listCdEmpresa=" + (listCdEmpresa!=null?listCdEmpresa.size():"NuLL") 
			+ "]";
	}

	public Long getCdEmpresaTest() {
		return cdEmpresaTest;
	}

	public void setCdEmpresaTest(Long cdEmpresaTest) {
		this.cdEmpresaTest = cdEmpresaTest;
	}

}

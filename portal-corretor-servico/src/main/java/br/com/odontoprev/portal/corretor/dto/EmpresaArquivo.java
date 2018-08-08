package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class EmpresaArquivo implements Serializable {

    private static final long serialVersionUID = 3103017260662982091L; //TODO: Alterar serialVersionUID

    private List<Long> cdEmpresa;

    public List<Long> getCdEmpresa() {
        return cdEmpresa;
    }

    public void setCdEmpresa(List<Long> cdEmpresa) {
        this.cdEmpresa = cdEmpresa;
    }

    @Override
    public String toString() {
        return "EmpresaArquivo [cdEmpresa=" + cdEmpresa + "]";
    }

}

package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class EmpresaArquivoResponse implements Serializable {

    private static final long serialVersionUID = 1L; //TODO: Alterar serialVersionUID


    List<EmpresaArquivoResponseItem> empresas;

    public List<EmpresaArquivoResponseItem> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<EmpresaArquivoResponseItem> empresas) {
        this.empresas = empresas;
    }

    @Override
    public String toString() {
        return "EmpresaArquivoResponse{" +
                "responseItems=" + empresas +
                '}';
    }

}

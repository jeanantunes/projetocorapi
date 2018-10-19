package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class EmpresaArquivoResponse implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmpresaArquivoResponse)) return false;
        EmpresaArquivoResponse that = (EmpresaArquivoResponse) o;
        return Objects.equals(getEmpresas(), that.getEmpresas());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getEmpresas());
    }

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

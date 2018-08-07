package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;

public class EmpresaArquivoResponseItem implements Serializable {

    private static final long serialVersionUID = 3103017260662982091L; //TODO: Alterar serialVersionUID

    public EmpresaArquivoResponseItem(Long cdEmpresa, String mensagem){
        this.cdEmpresa = cdEmpresa;
        this.mensagem = mensagem;
    }

    private Long cdEmpresa;

    public Long getCdEmpresa() {
        return cdEmpresa;
    }

    public void setCdEmpresa(Long cdEmpresa) {
        this.cdEmpresa = cdEmpresa;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    private String mensagem;



    @Override
    public String toString() {
        return "EmpresaArquivoResponseItem{" +
                "cdEmpresa=" + cdEmpresa +
                ", mensagem='" + mensagem + '\'' +
                '}';
    }
}

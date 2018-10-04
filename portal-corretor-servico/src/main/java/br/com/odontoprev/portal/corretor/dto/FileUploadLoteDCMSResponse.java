package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

//201810041803 - esert - COR-861:Serviço - Receber / Retornar Planilha
public class FileUploadLoteDCMSResponse implements Serializable {

    private static final long serialVersionUID = 488991477748799482L;

    private long id;
    private String mensagem;

    public FileUploadLoteDCMSResponse() {

    }

    public FileUploadLoteDCMSResponse(long id) {
        this.id = id;
    }

    public FileUploadLoteDCMSResponse(long id, String mensagem) {
        this.id = id;
        this.mensagem = mensagem;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}

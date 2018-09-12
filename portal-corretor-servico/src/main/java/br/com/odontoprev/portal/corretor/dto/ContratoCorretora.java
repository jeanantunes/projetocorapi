package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class ContratoCorretora implements Serializable {

    private static final long serialVersionUID = 2180567984276302023L;

    private Long cdContratoCorretora;
    private Long cdCorretora;
    private Long cdContratoModelo;
    private String dtAceiteContrato;
    private String cdSusep;
    private String nomeArquivo; //201809121631 - esert - COR-714 - Serviço - Novo serviço gerar enviar contrato corretora
    private String caminhoCarga; //201809121631 - esert - COR-714 - Serviço - Novo serviço gerar enviar contrato corretora    
    private String tipoConteudo; //201809121631 - esert - COR-714 - Serviço - Novo serviço gerar enviar contrato corretora    
    private Long tamanhoArquivo; //201809121631 - esert - COR-714 - Serviço - Novo serviço gerar enviar contrato corretora
    
    public Long getCdContratoCorretora() {
        return cdContratoCorretora;
    }

    public void setCdContratoCorretora(Long cdContratoCorretora) {
        this.cdContratoCorretora = cdContratoCorretora;
    }

    public Long getCdCorretora() {
        return cdCorretora;
    }

    public void setCdCorretora(Long cdCorretora) {
        this.cdCorretora = cdCorretora;
    }

    public Long getCdContratoModelo() {
        return cdContratoModelo;
    }

    public void setCdContratoModelo(Long cdContratoModelo) {
        this.cdContratoModelo = cdContratoModelo;
    }

    public String getDtAceiteContrato() {
        return dtAceiteContrato;
    }

    public void setDtAceiteContrato(String dtAceiteContrato) {
        this.dtAceiteContrato = dtAceiteContrato;
    }

    public String getCdSusep() {
        return cdSusep;
    }

    public void setCdSusep(String cdSusep) {
        this.cdSusep = cdSusep;
    }

    public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getCaminhoCarga() {
		return caminhoCarga;
	}

	public void setCaminhoCarga(String caminhoCarga) {
		this.caminhoCarga = caminhoCarga;
	}

	public String getTipoConteudo() {
		return tipoConteudo;
	}

	public void setTipoConteudo(String tipoConteudo) {
		this.tipoConteudo = tipoConteudo;
	}

	public Long getTamanhoArquivo() {
		return tamanhoArquivo;
	}

	public void setTamanhoArquivo(Long tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
	}

	@Override
	public String toString() {
		return "ContratoCorretora [" 
				+ "cdContratoCorretora=" + cdContratoCorretora 
				+ ", cdCorretora=" + cdCorretora
				+ ", cdContratoModelo=" + cdContratoModelo 
				+ ", dtAceiteContrato=" + dtAceiteContrato 
				+ ", cdSusep=" + cdSusep 
				+ ", nomeArquivo=" + nomeArquivo //201809121631 - esert - COR-714 - Serviço - Novo serviço gerar enviar contrato corretora 
				+ ", caminhoCarga=" + caminhoCarga //201809121631 - esert - COR-714 - Serviço - Novo serviço gerar enviar contrato corretora
				+ ", tipoConteudo=" + tipoConteudo //201809121631 - esert - COR-714 - Serviço - Novo serviço gerar enviar contrato corretora
				+ ", tamanhoArquivo=" + tamanhoArquivo //201809121631 - esert - COR-714 - Serviço - Novo serviço gerar enviar contrato corretora
				+ "]";
	}
	
}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caminhoCarga == null) ? 0 : caminhoCarga.hashCode());
		result = prime * result + ((cdContratoCorretora == null) ? 0 : cdContratoCorretora.hashCode());
		result = prime * result + ((cdContratoModelo == null) ? 0 : cdContratoModelo.hashCode());
		result = prime * result + ((cdCorretora == null) ? 0 : cdCorretora.hashCode());
		result = prime * result + ((cdSusep == null) ? 0 : cdSusep.hashCode());
		result = prime * result + ((dtAceiteContrato == null) ? 0 : dtAceiteContrato.hashCode());
		result = prime * result + ((nomeArquivo == null) ? 0 : nomeArquivo.hashCode());
		result = prime * result + ((tamanhoArquivo == null) ? 0 : tamanhoArquivo.hashCode());
		result = prime * result + ((tipoConteudo == null) ? 0 : tipoConteudo.hashCode());
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
		ContratoCorretora other = (ContratoCorretora) obj;
		if (caminhoCarga == null) {
			if (other.caminhoCarga != null)
				return false;
		} else if (!caminhoCarga.equals(other.caminhoCarga))
			return false;
		if (cdContratoCorretora == null) {
			if (other.cdContratoCorretora != null)
				return false;
		} else if (!cdContratoCorretora.equals(other.cdContratoCorretora))
			return false;
		if (cdContratoModelo == null) {
			if (other.cdContratoModelo != null)
				return false;
		} else if (!cdContratoModelo.equals(other.cdContratoModelo))
			return false;
		if (cdCorretora == null) {
			if (other.cdCorretora != null)
				return false;
		} else if (!cdCorretora.equals(other.cdCorretora))
			return false;
		if (cdSusep == null) {
			if (other.cdSusep != null)
				return false;
		} else if (!cdSusep.equals(other.cdSusep))
			return false;
		if (dtAceiteContrato == null) {
			if (other.dtAceiteContrato != null)
				return false;
		} else if (!dtAceiteContrato.equals(other.dtAceiteContrato))
			return false;
		if (nomeArquivo == null) {
			if (other.nomeArquivo != null)
				return false;
		} else if (!nomeArquivo.equals(other.nomeArquivo))
			return false;
		if (tamanhoArquivo == null) {
			if (other.tamanhoArquivo != null)
				return false;
		} else if (!tamanhoArquivo.equals(other.tamanhoArquivo))
			return false;
		if (tipoConteudo == null) {
			if (other.tipoConteudo != null)
				return false;
		} else if (!tipoConteudo.equals(other.tipoConteudo))
			return false;
		return true;
	}
	
}

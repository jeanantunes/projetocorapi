package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Date;

//201809101646 - esert - COR-709 - Serviço - Novo serviço GET/contratocorretora/cdCor/tipo/cdTipo
//201809101646 - esert - COR-751 - DB - nova tabela TBOD_CONTRATO_MODELO
//201809101857 - esert - COR-759 - Serviço - cria GET/contratomodelo/id
public class ContratoModelo implements Serializable {

	private static final long serialVersionUID = 6780270547885706554L;
	
	private Long cdContratoModelo;
    private Date dtCriacao;
    private String arquivoModelo;

	public Long getCdContratoModelo() {
		return cdContratoModelo;
	}

	public void setCdContratoModelo(Long cdContratoModelo) {
		this.cdContratoModelo = cdContratoModelo;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getArquivoModelo() {
		return arquivoModelo;
	}

	public void setArquivoModelo(String arquivoModelo) {
		this.arquivoModelo = arquivoModelo;
	}

	@Override
	public String toString() {
		return "TbodContratoModelo [" 
				+ "cdContratoModelo=" + cdContratoModelo 
				+ ", dtCriacao=" + dtCriacao
				+ ", arquivoModelo=" + (arquivoModelo != null ? String.valueOf(arquivoModelo.length()) : "NuLL") + "]";
	}

}
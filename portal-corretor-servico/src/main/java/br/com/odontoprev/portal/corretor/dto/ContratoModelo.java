package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

//201809101646 - esert - COR-709 - Serviço - Novo serviço GET/contratocorretora/cdCor/tipo/cdTipo
//201809101646 - esert - COR-751 - DB - nova tabela TBOD_CONTRATO_MODELO
//201809101857 - esert - COR-759 - Serviço - cria GET/contratomodelo/id

public class ContratoModelo implements Serializable {
	private static final long serialVersionUID = 6780270547885706554L;
	
	private Long cdContratoModelo;
    private String dtCriacao;
    private String nomeArquivo;
    private Long tamanhoArquivo;
    private String tipoConteudo;
    private String arquivoBase64;
    private String caminhoCarga;
    private String arquivoString;

	public Long getCdContratoModelo() {
		return cdContratoModelo;
	}

	public void setCdContratoModelo(Long cdContratoModelo) {
		this.cdContratoModelo = cdContratoModelo;
	}

	public String getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(String dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public Long getTamanhoArquivo() {
		return tamanhoArquivo;
	}

	public void setTamanhoArquivo(Long tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
	}

	public String getTipoConteudo() {
		return tipoConteudo;
	}

	public void setTipoConteudo(String tipoConteudo) {
		this.tipoConteudo = tipoConteudo;
	}

	public String getArquivoBase64() {
		return arquivoBase64;
	}

	public void setArquivoBase64(String arquivo) {
		this.arquivoBase64 = arquivo;
	}

	public String getArquivoString() {
		return arquivoString;
	}

	public void setArquivoString(String arquivoString) {
		this.arquivoString = arquivoString;
	}

	public String getCaminhoCarga() {
		return caminhoCarga;
	}

	public void setCaminhoCarga(String caminhoCarga) {
		this.caminhoCarga = caminhoCarga;
	}

	@Override
	public String toString() {
		return "ContratoModelo [" 
				+ "cdContratoModelo=" + cdContratoModelo 
				+ ", dtCriacao=" + dtCriacao
				+ ", nomeArquivo=" + nomeArquivo
				+ ", tamanhoArquivo=" + tamanhoArquivo
				+ ", tipoConteudo=" + tipoConteudo
				+ ", arquivoBase64=" + (arquivoBase64 != null ? String.valueOf(arquivoBase64.length()) : "NuLL") 
				+ ", arquivoString=" + (arquivoString != null ? String.valueOf(arquivoString.length()) : "NuLL") 
				+ ", caminhoCarga=" + caminhoCarga 
				+ "]";
	}
	
}
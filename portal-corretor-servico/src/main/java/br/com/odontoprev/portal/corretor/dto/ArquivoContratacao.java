package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Date;

//201808231717 - esert - COR-617 - nova DTO para nova tabela TBOD_ARQUIVO_CONTRATACAO
public class ArquivoContratacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7605496331918144311L;

	//@Id
	//@Column(name = "CD_EMPRESA") //"CD_EMPRESA" NUMBER(10,0) DEFAULT NULL NOT NULL ENABLE, 
	private Long codigoEmpresa;

	//@Temporal(TemporalType.DATE)
	//@Column(name = "DATA_CRIACAO") //"DATA_CRIACAO" DATE NOT NULL ENABLE, 
	private String dataCriacao;

	//@Column(name = "NOME_ARQUIVO") //"NOME_ARQUIVO" NVARCHAR2(255) NOT NULL ENABLE,
	private String nomeArquivo;

	//@Column(name = "TAMANHO_ARQUIVO") //"TAMANHO_ARQUIVO" NUMBER(10) NOT NULL ENABLE,
	private Long tamanhoArquivo;
	
	//@Column(name = "TIPO_CONTEUDO") //"TIPO_CONTEUDO" NVARCHAR2(20) NOT NULL ENABLE, 
	private String tipoConteudo;
	
	//@Column(name = "ARQUIVO") //"ARQUIVO" BLOB NOT NULL ENABLE, 
	private String arquivoBase64;

	private String caminhoCarga;
	
	public ArquivoContratacao() {
	}
	
	public Long getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(Long codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

	public String getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
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

	public void setArquivoBase64(String arquivoBase64) {
		this.arquivoBase64 = arquivoBase64;
	}

	public String getCaminhoCarga() {
		return caminhoCarga;
	}

	public void setCaminhoCarga(String caminhoCarga) {
		this.caminhoCarga = caminhoCarga;
	}

	@Override
	public String toString() {
		String stringArquivo = arquivoBase64 != null ? String.valueOf(arquivoBase64.length()) : "NuLL";
		return "TbodMaterialDivulgacao [" 
				+ "codigoEmpresa=" + codigoEmpresa 
				+ ", dataCriacao=" + dataCriacao 
				+ ", nomeArquivo=" + nomeArquivo
				+ ", tamanhoArquivo=" + tamanhoArquivo
				+ ", tipoConteudo=" + tipoConteudo 
				+ ", arquivo=" + stringArquivo
				+ "]";
	}

}
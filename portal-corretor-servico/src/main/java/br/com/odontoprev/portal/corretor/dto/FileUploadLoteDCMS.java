package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

//201810051557 - yalm/esert - COR-865:Definição de Contrato
public class FileUploadLoteDCMS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7413451254197849416L;
	
	private String tipoConteudo; //"tipoConteudo":"application/vnd.ms-excel",
	private String arquivoBase64; //"arquivoBase64":"qwertyuiopasdfghjklzxcvbnm"
	private String nomeArquivo; //"nomeArquivo":"loteDcms20181005.xls",
	private Long tamanho; //"tamanho":12345
	
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
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public Long getTamanho() {
		return tamanho;
	}
	public void setTamanho(Long tamanho) {
		this.tamanho = tamanho;
	}
	
	@Override
	public String toString() {
		return "FileUploadLoteDCMS [tipoConteudo=" + tipoConteudo + ", arquivoBase64=" + arquivoBase64
				+ ", nomeArquivo=" + nomeArquivo + ", tamanho=" + tamanho + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arquivoBase64 == null) ? 0 : arquivoBase64.hashCode());
		result = prime * result + ((nomeArquivo == null) ? 0 : nomeArquivo.hashCode());
		result = prime * result + ((tamanho == null) ? 0 : tamanho.hashCode());
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
		FileUploadLoteDCMS other = (FileUploadLoteDCMS) obj;
		if (arquivoBase64 == null) {
			if (other.arquivoBase64 != null)
				return false;
		} else if (!arquivoBase64.equals(other.arquivoBase64))
			return false;
		if (nomeArquivo == null) {
			if (other.nomeArquivo != null)
				return false;
		} else if (!nomeArquivo.equals(other.nomeArquivo))
			return false;
		if (tamanho == null) {
			if (other.tamanho != null)
				return false;
		} else if (!tamanho.equals(other.tamanho))
			return false;
		if (tipoConteudo == null) {
			if (other.tipoConteudo != null)
				return false;
		} else if (!tipoConteudo.equals(other.tipoConteudo))
			return false;
		return true;
	}

}

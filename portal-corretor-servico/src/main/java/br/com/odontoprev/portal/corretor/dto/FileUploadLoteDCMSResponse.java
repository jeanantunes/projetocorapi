package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

//201810051557 - yalm/esert - COR-865:Definição de Contrato
public class FileUploadLoteDCMSResponse implements Serializable {
	
	private static final long serialVersionUID = -2818456229298410509L;

	private String tipoConteudo; //"tipoConteudo":"application/vnd.ms-excel",
	private String nomeArquivo; //"nomeArquivo":"loteDcms20181005.xls",
	private Long tamanho; //"tamanho":12345
	private Long registrosProcessados; //201810091923 - esert
	private String arquivoBase64; //"arquivoBase64":"qwertyuiopasdfghjklzxcvbnm"
	public String getTipoConteudo() {
		return tipoConteudo;
	}
	public void setTipoConteudo(String tipoConteudo) {
		this.tipoConteudo = tipoConteudo;
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
	public Long getRegistrosProcessados() {
		return registrosProcessados;
	}
	public void setRegistrosProcessados(Long registrosProcessados) {
		this.registrosProcessados = registrosProcessados;
	}
	public String getArquivoBase64() {
		return arquivoBase64;
	}
	public void setArquivoBase64(String arquivoBase64) {
		this.arquivoBase64 = arquivoBase64;
	}
	
	@Override
	public String toString() {
		return "FileUploadLoteDCMSResponse [" 
				+ "tipoConteudo=" + tipoConteudo 
				+ ", nomeArquivo=" + nomeArquivo
				+ ", tamanho=" + tamanho 
				+ ", registrosProcessados=" + registrosProcessados 
				+ ", arquivoBase64=" + arquivoBase64 
				+ "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arquivoBase64 == null) ? 0 : arquivoBase64.hashCode());
		result = prime * result + ((nomeArquivo == null) ? 0 : nomeArquivo.hashCode());
		result = prime * result + ((registrosProcessados == null) ? 0 : registrosProcessados.hashCode());
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
		FileUploadLoteDCMSResponse other = (FileUploadLoteDCMSResponse) obj;
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
		if (registrosProcessados == null) {
			if (other.registrosProcessados != null)
				return false;
		} else if (!registrosProcessados.equals(other.registrosProcessados))
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

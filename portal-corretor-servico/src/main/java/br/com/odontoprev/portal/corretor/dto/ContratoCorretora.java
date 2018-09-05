package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ContratoCorretora implements Serializable {

	private static final long serialVersionUID = 2180567984276302023L;

	private Long cdCorretora;
	private Date dtAceiteContrato;
	private Long tamArquivo;
	private String tipoConteudo;
	private byte[] arquivo;

	public Long getCdCorretora() {
		return cdCorretora;
	}

	public void setCdCorretora(Long cdCorretora) {
		this.cdCorretora = cdCorretora;
	}

	public Date getDtAceiteContrato() {
		return dtAceiteContrato;
	}

	public void setDtAceiteContrato(Date dtAceiteContrato) {
		this.dtAceiteContrato = dtAceiteContrato;
	}

	public Long getTamArquivo() {
		return tamArquivo;
	}

	public void setTamArquivo(Long tamArquivo) {
		this.tamArquivo = tamArquivo;
	}

	public String getTipoConteudo() {
		return tipoConteudo;
	}

	public void setTipoConteudo(String tipoConteudo) {
		this.tipoConteudo = tipoConteudo;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	@Override
	public String toString() {
		return "ContratoCorretora{" +
				"cdCorretora=" + cdCorretora +
				", dtAceiteContrato=" + dtAceiteContrato +
				", tamArquivo=" + tamArquivo +
				", tipoConteudo='" + tipoConteudo + '\'' +
				", arquivo=" + Arrays.toString(arquivo) +
				'}';
	}
}

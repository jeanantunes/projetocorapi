package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

//201809101646 - esert - COR-709 - Serviço - Novo serviço GET /CONTRATO CORRETORA/{IDCORRETORA}/TIPO/{IDTIPO}
public class ContratoCorretoraPreenchido implements Serializable  {

	private static final long serialVersionUID = -9008526281377540899L;
	
	private Long cdCorretora;
	private Long cdContratoModelo;
	private String contratoPreenchido;
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
	public String getContratoPreenchido() {
		return contratoPreenchido;
	}
	public void setContratoPreenchido(String contratoPreenchido) {
		this.contratoPreenchido = contratoPreenchido;
	}
	
	@Override
	public String toString() {
		return "ContratoCorretoraPreenchido [cdCorretora=" + cdCorretora + ", cdContratoModelo=" + cdContratoModelo
				+ ", contratoPreenchido=" + contratoPreenchido + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cdContratoModelo == null) ? 0 : cdContratoModelo.hashCode());
		result = prime * result + ((cdCorretora == null) ? 0 : cdCorretora.hashCode());
		result = prime * result + ((contratoPreenchido == null) ? 0 : contratoPreenchido.hashCode());
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
		ContratoCorretoraPreenchido other = (ContratoCorretoraPreenchido) obj;
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
		if (contratoPreenchido == null) {
			if (other.contratoPreenchido != null)
				return false;
		} else if (!contratoPreenchido.equals(other.contratoPreenchido))
			return false;
		return true;
	}

}

package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Objects;

public class ContratoCorretoraDataAceite implements Serializable  {

	private static final long serialVersionUID = -945026165974015319L;
	
	private Long cdCorretora;
	private String dtAceiteContrato;

	public long getCdCorretora() {
		return cdCorretora;
	}

	public void setCdCorretora(Long cdCorretora) {
		this.cdCorretora = cdCorretora;
	}

	public String getDtAceiteContrato() {
		return dtAceiteContrato;
	}

	public void setDtAceiteContrato(String dtAceiteContrato) {
		this.dtAceiteContrato = dtAceiteContrato;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ContratoCorretoraDataAceite)) return false;
		ContratoCorretoraDataAceite that = (ContratoCorretoraDataAceite) o;
		return getCdCorretora() == that.getCdCorretora() &&
				Objects.equals(getDtAceiteContrato(), that.getDtAceiteContrato());
	}

	@Override
	public int hashCode() {

		return Objects.hash(getCdCorretora(), getDtAceiteContrato());
	}
}

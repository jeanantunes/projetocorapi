package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Date;

public class DashBoardProposta implements Serializable {

	private static final long serialVersionUID = 2633836795921923201L;
	
	private Date dtInicio;
	private Date dtFim;
	private long cdCorretora;
	private long cdForcaVenda;
	private String cpf;
	private String cnpj;
	private Date dtVenda;

	public Date getDtVenda() {
		return dtVenda;
	}

	public void setDtVenda(Date dtVenda) {
		this.dtVenda = dtVenda;
	}

	public Date getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	public Date getDtFim() {
		return dtFim;
	}

	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}

	public long getCdCorretora() {
		return cdCorretora;
	}

	public void setCdCorretora(long cdCorretora) {
		this.cdCorretora = cdCorretora;
	}

	public long getCdForcaVenda() {
		return cdForcaVenda;
	}

	public void setCdForcaVenda(long cdForcaVenda) {
		this.cdForcaVenda = cdForcaVenda;
	}


	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return "DashBoardProposta [dtInicio=" + dtInicio + ", dtFim=" + dtFim + ", cdCorretora=" + cdCorretora
				+ ", cdForcaVenda=" + cdForcaVenda + "]";
	}

}

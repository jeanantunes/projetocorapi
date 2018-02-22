package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Date;

public class Venda implements Serializable {

	private static final long serialVersionUID = 3281440316438484993L;

	private long cdEmpresa;
	private long cdPlano;
	private long cdForcaVenda;
	private Date dataVenda;
	private String statusVenda;
	private long faturaVencimento;

	public long getCdEmpresa() {
		return cdEmpresa;
	}

	public void setCdEmpresa(long cdEmpresa) {
		this.cdEmpresa = cdEmpresa;
	}

	public long getCdPlano() {
		return cdPlano;
	}

	public void setCdPlano(long cdPlano) {
		this.cdPlano = cdPlano;
	}

	public long getCdForcaVenda() {
		return cdForcaVenda;
	}
	
	public void setCdForcaVenda(long cdForcaVenda) {
		this.cdForcaVenda = cdForcaVenda;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public String getStatusVenda() {
		return statusVenda;
	}

	public void setStatusVenda(String statusVenda) {
		this.statusVenda = statusVenda;
	}

	public long getFaturaVencimento() {
		return faturaVencimento;
	}

	public void setFaturaVencimento(long faturaVencimento) {
		this.faturaVencimento = faturaVencimento;
	}

	@Override
	public String toString() {
		return "Venda [cdEmpresa=" + cdEmpresa + ", cdPlano=" + cdPlano + ", cdForcaVenda=" + cdForcaVenda
				+ ", dataVenda=" + dataVenda + ", statusVenda=" + statusVenda + ", faturaVencimento=" + faturaVencimento
				+ "]";
	}

}

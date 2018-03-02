package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Venda implements Serializable {

	private static final long serialVersionUID = 3281440316438484993L;

	private Long cdVenda;
	private Long cdEmpresa;
	
	//private List<Plano> planos;
	private Long cdPlano;
	
	private Long cdForcaVenda;
	private Date dataVenda;
	private Long cdStatusVenda;
	private Long faturaVencimento;
	
	private String tipoPagamento;

	private List<Beneficiario> titulares;

	public Long getCdVenda() {
		return cdVenda;
	}

	public void setCdVenda(long cdVenda) {
		this.cdVenda = cdVenda;
	}

	public Long getCdEmpresa() {
		return cdEmpresa;
	}

	public void setCdEmpresa(long cdEmpresa) {
		this.cdEmpresa = cdEmpresa;
	}

	public Long getCdForcaVenda() {
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

	public Long getCdStatusVenda() {
		return cdStatusVenda;
	}

	public void setCdStatusVenda(Long cdStatusVenda) {
		this.cdStatusVenda = cdStatusVenda;
	}

	public Long getFaturaVencimento() {
		return faturaVencimento;
	}

	public void setFaturaVencimento(long faturaVencimento) {
		this.faturaVencimento = faturaVencimento;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public List<Beneficiario> getTitulares() {
		return titulares;
	}

	public void setTitulares(List<Beneficiario> titulares) {
		this.titulares = titulares;
	}

	public Long getCdPlano() {
		return cdPlano;
	}

	public void setCdPlano(Long cdPlano) {
		this.cdPlano = cdPlano;
	}
	
	@Override
	public String toString() {
		return "Venda [cdVenda=" + cdVenda + ", cdEmpresa=" + cdEmpresa + ", cdPlano=" + cdPlano + ", cdForcaVenda="
				+ cdForcaVenda + ", dataVenda=" + dataVenda + ", cdStatusVenda=" + cdStatusVenda + ", faturaVencimento="
				+ faturaVencimento + ", tipoPagamento=" + tipoPagamento + ", titulares=" + titulares + "]";
	}
}

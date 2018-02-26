package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Venda implements Serializable {

	private static final long serialVersionUID = 3281440316438484993L;

	private Long cdVenda;
	private Long cdEmpresa;
	private Long cdPlano;
	private Long cdForcaVenda;
	private Date dataVenda;
	private Long cdStatusVenda;
	private Long faturaVencimento;
	private String tipoConta;
	private Long banco;
	private String agencia;
	private String agenciaDv;
	private String conta;
	private String contaDv;
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

	public Long getCdPlano() {
		return cdPlano;
	}

	public void setCdPlano(long cdPlano) {
		this.cdPlano = cdPlano;
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

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public Long getBanco() {
		return banco;
	}

	public void setBanco(Long banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getAgenciaDv() {
		return agenciaDv;
	}

	public void setAgenciaDv(String agenciaDv) {
		this.agenciaDv = agenciaDv;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getContaDv() {
		return contaDv;
	}

	public void setContaDv(String contaDv) {
		this.contaDv = contaDv;
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

}

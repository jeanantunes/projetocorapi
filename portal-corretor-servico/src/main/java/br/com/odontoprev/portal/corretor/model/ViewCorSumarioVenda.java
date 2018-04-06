package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="vwod_cor_sumario_venda")
@Entity
public class ViewCorSumarioVenda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Column(name="CD_EMPRESA") 
	private Long codigoEmpresa;
	
	@Column(name="EMP_DCMS")
	private String empresaDCMS;
	   
	
	@Column(name="TOT_VIDAS")
	private Long totalVidas;
	             
	@Column(name="VALOR_TOTAL")
	private BigDecimal valorTotal;
	
	@Id 
	@Column(name="CD_FORCA_VENDA")
	private Long codigoForcaVenda;
	
	
	@Column(name="NOME_FORCA")
	private String nomeForca;
	
	@Column(name="CPF")
	private String cpf;
	
	@Id            
	@Column(name="CD_CORRETORA")
	private Long codigoCorretora;
	 
	
	@Column(name="NOME_CORRETORA")
	private String nomeCorretora;
	
	
	@Column(name="CNPJ")
	private String cnpj;
	           
	@Id
	@Column(name="TIPO_VENDA")
	private String tipoVenda;
	
	@Id
	@Column(name="DT_VENDA")
	private String dtVenda;

	public String getDtVenda() {
		return dtVenda;
	}


	public void setDtVenda(String dtVenda) {
		this.dtVenda = dtVenda;
	}


	public Long getCodigoEmpresa() {
		return codigoEmpresa;
	}


	public void setCodigoEmpresa(Long codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}


	public String getEmpresaDCMS() {
		return empresaDCMS;
	}


	public void setEmpresaDCMS(String empresaDCMS) {
		this.empresaDCMS = empresaDCMS;
	}


	public Long getTotalVidas() {
		return totalVidas;
	}


	public void setTotalVidas(Long totalVidas) {
		this.totalVidas = totalVidas;
	}


	public BigDecimal getValorTotal() {
		return valorTotal;
	}


	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}


	public Long getCodigoForcaVenda() {
		return codigoForcaVenda;
	}


	public void setCodigoForcaVenda(Long codigoForcaVenda) {
		this.codigoForcaVenda = codigoForcaVenda;
	}


	public String getNomeForca() {
		return nomeForca;
	}


	public void setNomeForca(String nomeForca) {
		this.nomeForca = nomeForca;
	}


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public Long getCodigoCorretora() {
		return codigoCorretora;
	}


	public void setCodigoCorretora(Long codigoCorretora) {
		this.codigoCorretora = codigoCorretora;
	}


	public String getNomeCorretora() {
		return nomeCorretora;
	}


	public void setNomeCorretora(String nomeCorretora) {
		this.nomeCorretora = nomeCorretora;
	}


	public String getCnpj() {
		return cnpj;
	}


	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}


	public String getTipoVenda() {
		return tipoVenda;
	}


	public void setTipoVenda(String tipoVenda) {
		this.tipoVenda = tipoVenda;
	}
	
	

}

package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class BeneficiarioPaginacao extends Beneficiario implements Serializable {

	private static final long serialVersionUID = 3103017260669982091L;

	private Long cdPlano;
	private String descPlano;
	private Long qtdeDep;
	
	public Long getCdPlano() {
		return cdPlano;
	}
	public void setCdPlano(Long cdPlano) {
		this.cdPlano = cdPlano;
	}
	public String getDescPlano() {
		return descPlano;
	}
	public void setDescPlano(String descPlano) {
		this.descPlano = descPlano;
	}
	public Long getQtdeDep() {
		return qtdeDep;
	}
	public void setQtdeDep(Long qtdeDep) {
		this.qtdeDep = qtdeDep;
	}
	
	@Override
	public String toString() {
		return "BeneficiarioPaginacao [" 
				+ "cdPlano=" + cdPlano 
				+ ", descPlano=" + descPlano 
				+ ", qtdeDep=" + qtdeDep 
				+ "]";
	}
}

package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class BeneficiarioPaginacao extends Beneficiario implements Serializable {

	private static final long serialVersionUID = 3103017260669982091L;

	private Long cdPlano;
	private String descPlano;
	private Long qtdDependentes;
	
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

	public Long getQtdDependentes() {
		//return qtdDependentes;
		//return (this.getDependentes()!=null?this.getDependentes().size():-1L); //201807271609 - esert - COR-475
		return (this.getDependentes()!=null?this.getDependentes().size():0L); //201807271732 - esert - COR-475
	}
//	public void setQtdDependentes(Long qtdDependentes) {
//		this.qtdDependentes = qtdDependentes;
//	}
	
	@Override
	public String toString() {
		return "BeneficiarioPaginacao [" 
				+ "cdPlano=" + cdPlano 
				+ ", descPlano=" + descPlano 
				+ ", qtdDependentes=" + qtdDependentes 
				+ "]";
	}
}

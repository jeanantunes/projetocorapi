package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class PlanoDCMS implements Serializable {

	private static final long serialVersionUID = 3449851941806562255L;

	private long codigoPlano;
	private String tipoNegociacao;
	private float valorPlano;
	
	public long getCodigoPlano() {
		return codigoPlano;
	}
	public void setCodigoPlano(long codigoPlano) {
		this.codigoPlano = codigoPlano;
	}
	public String getTipoNegociacao() {
		return tipoNegociacao;
	}
	public void setTipoNegociacao(String tipoNegociacao) {
		this.tipoNegociacao = tipoNegociacao;
	}
	public float getValorPlano() {
		return valorPlano;
	}
	public void setValorPlano(float valorPlano) {
		this.valorPlano = valorPlano;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "PlanoDCMS [codigoPlano=" + codigoPlano + ", tipoNegociacao=" + tipoNegociacao + ", valorPlano="
				+ valorPlano + "]";
	}
}

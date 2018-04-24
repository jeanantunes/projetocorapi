package br.com.odontoprev.portal.corretor.dto;

import java.math.BigDecimal;

public class PropostaPF {

	private BigDecimal valor;
	private Long quantidade;
	private Long quantidadeVidas;
	
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public long getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(long quantidade) {
		this.quantidade = quantidade;
	}
	public Long getQuantidadeVidas() {
		return quantidadeVidas;
	}
	public void setQuantidadeVidas(Long quantidadeVidas) {
		this.quantidadeVidas = quantidadeVidas;
	}
	
}

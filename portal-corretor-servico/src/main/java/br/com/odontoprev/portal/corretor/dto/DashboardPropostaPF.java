package br.com.odontoprev.portal.corretor.dto;

public class DashboardPropostaPF {

	private Long cdVenda;
	private String cpf;
	private String propostaDcms;
	private String nome;
	private String statusVenda;

	public Long getCdVenda() {
		return cdVenda;
	}

	public void setCdVenda(Long cdVenda) {
		this.cdVenda = cdVenda;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPropostaDcms() {
		return propostaDcms;
	}

	public void setPropostaDcms(String propostaDcms) {
		this.propostaDcms = propostaDcms;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getStatusVenda() {
		return statusVenda;
	}

	public void setStatusVenda(String statusVenda) {
		this.statusVenda = statusVenda;
	}

}

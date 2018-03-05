package br.com.odontoprev.portal.corretor.dto;

public class DashboardPropostaPF {

	private Long cdVenda;
	private String cpf;
	private String propostaDcms;
	private String nome;
	private String statusVenda;
	private String atendimento;
	private String empresa;
	private String codOdonto;
	private String nrImportacao;
	private String forca;
	private String corretora;
	private String dsErroRegistro;
	private String criticas;
	private String cnpj;
	private Long cdEmpresa;

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	private double valor;
	
	public Long getCdEmpresa() {
		return cdEmpresa;
	}

	public void setCdEmpresa(Long cdEmpresa) {
		this.cdEmpresa = cdEmpresa;
	}

	
	public String getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(String atendimento) {
		this.atendimento = atendimento;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCodOdonto() {
		return codOdonto;
	}

	public void setCodOdonto(String codOdonto) {
		this.codOdonto = codOdonto;
	}

	public String getNrImportacao() {
		return nrImportacao;
	}

	public void setNrImportacao(String nrImportacao) {
		this.nrImportacao = nrImportacao;
	}

	public String getForca() {
		return forca;
	}

	public void setForca(String forca) {
		this.forca = forca;
	}

	public String getCorretora() {
		return corretora;
	}

	public void setCorretora(String corretora) {
		this.corretora = corretora;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

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


	public String getDsErroRegistro() {
		return dsErroRegistro;
	}

	public void setDsErroRegistro(String dsErroRegistro) {
		this.dsErroRegistro = dsErroRegistro;
	}

	public String getCriticas() {
		return criticas;
	}

	public void setCriticas(String criticas) {
		this.criticas = criticas;
	}

	@Override
	public String toString() {
		return "DashboardPropostaPF [cdVenda=" + cdVenda + ", cpf=" + cpf + ", propostaDcms=" + propostaDcms + ", nome="
				+ nome + ", statusVenda=" + statusVenda + ", atendimento=" + atendimento + ", empresa=" + empresa
				+ ", codOdonto=" + codOdonto + ", nrImportacao=" + nrImportacao + ", forca=" + forca + ", corretora="
				+ corretora + ", dsErroRegistro=" + dsErroRegistro + ", criticas=" + criticas + ", cnpj=" + cnpj
				+ ", cdEmpresa=" + cdEmpresa + "]";
	}
}

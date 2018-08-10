package br.com.odontoprev.portal.corretor.dto;

public class DashboardPropostaPME {

	private Long cdEmpresa;
	private String nome;
	private String nomeFantasia;  // [COR-488] yalm-201807271234
	private String statusVenda;
	private Long cdStatusVenda; // yalm - 201808012050 - COR-508
	private String dataVenda;
	private String cnpj;
	private String corretora;
	private String cpf;
	private String empDcms;
	private String forca;
	private String criticas;

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	private Double valor;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getStatusVenda() {
		return statusVenda;
	}

	public void setStatusVenda(String statusVenda) {
		this.statusVenda = statusVenda;
	}

	public Long getCdStatusVenda() {
		return cdStatusVenda;
	}

	public void setCdStatusVenda(Long cdStatusVenda) {
		this.cdStatusVenda = cdStatusVenda;
	}

	public String getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(String dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Long getCdEmpresa() {
		return cdEmpresa;
	}

	public void setCdEmpresa(Long cdEmpresa) {
		this.cdEmpresa = cdEmpresa;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCorretora() {
		return corretora;
	}

	public void setCorretora(String corretora) {
		this.corretora = corretora;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmpDcms() {
		return empDcms;
	}

	public void setEmpDcms(String empDcms) {
		this.empDcms = empDcms;
	}

	public String getForca() {
		return forca;
	}

	public void setForca(String forca) {
		this.forca = forca;
	}

	public String getCriticas() {
		return criticas;
	}

	public void setCriticas(String criticas) {
		this.criticas = criticas;
	}

	// yalm - 201808012050 - COR-508
	@Override
	public String toString() {
		return "DashboardPropostaPME [cdEmpresa=" + cdEmpresa + ", nome=" + nome + ", nomeFantasia=" + nomeFantasia
				+ ", statusVenda=" + statusVenda + ", cdStatusVenda=" + cdStatusVenda + ", dataVenda=" + dataVenda
				+ ", cnpj=" + cnpj + ", corretora=" + corretora + ", cpf=" + cpf + ", empDcms=" + empDcms + ", forca="
				+ forca + ", criticas=" + criticas + ", valor=" + valor + "]";
	}
	
	
}

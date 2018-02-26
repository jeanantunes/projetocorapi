package br.com.odontoprev.portal.corretor.dto;

public class CorretoraProposta {
	
	private Long codigo; //Codigo identificador da empresa
	
	private String cnpj; //CNPJ
	
	private String nome; //Razão Social

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "CorretoraProposta [" 
				+ "codigo=" + codigo 
				+ ", cnpj=" + cnpj 
				+ ", nome=" + nome 
				+ "]";
	}
}

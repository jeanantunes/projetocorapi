package br.com.odontoprev.portal.corretor.dto;

public class CorretoraProposta {
	
	private int codigo; //Codigo identificador da empresa
	
	private String cNPJ; //CNPJ
	
	private String nome; //Razão Social

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getcNPJ() {
		return cNPJ;
	}

	public void setcNPJ(String cNPJ) {
		this.cNPJ = cNPJ;
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
				+ ", cNPJ=" + cNPJ 
				+ ", nome=" + nome 
				+ "]";
	}
}

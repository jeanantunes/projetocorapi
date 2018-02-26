package br.com.odontoprev.portal.corretor.dto;

public class EnderecoProposta {
	
	private String cep;
	
	private String logradouro;

	private String numero;
	
	private String complemento;

	private String bairro;
	
	private String cidade;

	private String estado;

	public long getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "EnderecoProposta [" 
		+ "cep=" + cep 
		+ ", logradouro=" + logradouro 
		+ ", numero=" + numero 
		+ ", complemento=" + complemento 
		+ ", bairro=" + bairro 
		+ ", cidade=" + cidade 
		+ ", estado=" + estado 
		+ "]";
	}

}

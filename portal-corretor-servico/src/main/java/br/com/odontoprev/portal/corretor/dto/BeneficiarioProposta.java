package br.com.odontoprev.portal.corretor.dto;

import java.util.Date;

public class BeneficiarioProposta {
	
	private String nome; //Nome do dependente

	private String cpf; //CPF do dependente

	private String sexo; //char(1) (M)asculino / (F)eminino Sexo/Genero do dependente

	private Date dataNascimento; //Data de nascimento do dependente

	private String nomeMae; //Nome mãe do dependente

	private String celular; //Celular do dependente

	private String email; //E-mail do dependente

	private String codigoPlano; //Codigo do plano

	private Boolean eTitular; //Flag indicativa se dependente é titular
	
	private EnderecoProposta enderecoProposta;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCodigoPlano() {
		return codigoPlano;
	}

	public void setCodigoPlano(String codigoPlano) {
		this.codigoPlano = codigoPlano;
	}

	public Boolean geteTitular() {
		return eTitular;
	}

	public void seteTitular(Boolean eTitular) {
		this.eTitular = eTitular;
	}

	public EnderecoProposta getEnderecoProposta() {
		return enderecoProposta;
	}

	public void setEnderecoProposta(EnderecoProposta enderecoProposta) {
		this.enderecoProposta = enderecoProposta;
	}

	@Override
	public String toString() {
		return "BeneficiarioProposta [" 
				+ "nome=" + nome 
				+ ", cpf=" + cpf 
				+ ", sexo=" + sexo 
				+ ", dataNascimento=" + dataNascimento 
				+ ", nomeMae=" + nomeMae 
				+ ", celular=" + celular 
				+ ", email=" + email
				+ ", codigoPlano=" + codigoPlano 
				+ ", eTitular=" + eTitular 
				+ ", enderecoProposta=" + enderecoProposta
				+ "]";
	}
		
}

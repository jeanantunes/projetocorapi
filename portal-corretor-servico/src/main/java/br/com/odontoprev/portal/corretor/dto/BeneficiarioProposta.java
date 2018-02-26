package br.com.odontoprev.portal.corretor.dto;

public class BeneficiarioProposta {
	
	private String nome; //Nome do dependente

	private String cpf; //CPF do dependente

	private String sexo; //char(1) (M)asculino / (F)eminino Sexo/Genero do dependente

	//private Date dataNascimento; //Data de nascimento do dependente
	private String dataNascimento; //Data de nascimento do dependente //DD/MM/YYYY

	private String nomeMae; //Nome mãe do dependente

	private String celular; //Celular do dependente

	private String email; //E-mail do dependente

	private String codigoPlano; //Codigo do plano

	private Boolean titular; //Flag indicativa se dependente é titular
	
	private EnderecoProposta endereco;

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

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
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

	public Boolean getTitular() {
		return titular;
	}

	public void setTitular(Boolean titular) {
		this.titular = titular;
	}

	public EnderecoProposta getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoProposta endereco) {
		this.endereco = endereco;
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
				+ ", eTitular=" + titular 
				+ ", enderecoProposta=" + endereco
				+ "]";
	}
		
}

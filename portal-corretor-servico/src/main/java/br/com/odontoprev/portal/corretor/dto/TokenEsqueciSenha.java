package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class TokenEsqueciSenha implements Serializable {

	private static final long serialVersionUID = 1301142981650851635L;
	
	private Long cdForca;
	private Long cdCorretora;
	private String email;
	private String token;
	private String dataReset;	
	private String dataEnvioEmail;
	private String cpf;
	private String cnpj; //201807171752 - esert - COR-317
	
	public Long getCdForca() {
		return cdForca;
	}
	public void setCdForca(Long cdForca) {
		this.cdForca = cdForca;
	}
	public Long getCdCorretora() {
		return cdCorretora;
	}
	public void setCdCorretora(Long cdCorretora) {
		this.cdCorretora = cdCorretora;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getDataReset() {
		return dataReset;
	}
	public void setDataReset(String dataReset) {
		this.dataReset = dataReset;
	}
	public String getDataEnvioEmail() {
		return dataEnvioEmail;
	}
	public void setDataEnvioEmail(String dataEnvioEmail) {
		this.dataEnvioEmail = dataEnvioEmail;
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	@Override
	public String toString() {
		return "TokenEsqueciSenha [" 
				+ "cdForca=" + cdForca 
				+ ", cdCorretora=" + cdCorretora 
				+ ", email=" + email 
				+ ", token=" + token 
				+ ", dataReset=" + dataReset 
				+ ", dataEnvioEmail=" + dataEnvioEmail 
				+ ", cpf=" + cpf 
				+ ", cnpj=" + cnpj //201807171718 - esert - COR-317 
				+ "]";
	}
	
}

package br.com.odontoprev.portal.corretor.dto;

public class LoginRetorno {

	private String nomeUsuario;
	private String documento;
	private long codigo;

	public LoginRetorno() {
		super();
	}

	public LoginRetorno(String nomeUsuario, String documento, long codigo) {
		this.nomeUsuario = nomeUsuario;
		this.documento = documento;
		this.codigo = codigo;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

}
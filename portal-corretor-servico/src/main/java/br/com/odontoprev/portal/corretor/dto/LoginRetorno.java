package br.com.odontoprev.portal.corretor.dto;

public class LoginRetorno {

	private long codigo;
	private String nomeUsuario;

	public LoginRetorno() {
		super();
	}

	private String documento;

	public LoginRetorno(long codigoUsuario, String nomeUsuario, String documento) {
		this.codigo = codigoUsuario;
		this.nomeUsuario = nomeUsuario;
		this.documento = documento;
	}

	public long getCodigoUsuario() {
		return codigo;
	}

	public void setCodigoUsuario(long codigoUsuario) {
		this.codigo = codigoUsuario;
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
}
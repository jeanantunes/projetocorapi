package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.Objects;

public class EmpresaResponse implements Serializable {

	private static final long serialVersionUID = -8687258848218725652L;

	private long id;
	private String mensagem;

	public EmpresaResponse(long id) {
		this.id = id;
	}

	public EmpresaResponse(long id, String mensagem) {
		this.id = id;
		this.mensagem = mensagem;
	}

	public long getId() {
		return id;
	}

	public String getMensagem() {
		return mensagem;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof EmpresaResponse)) return false;
		EmpresaResponse that = (EmpresaResponse) o;
		return getId() == that.getId() &&
				Objects.equals(getMensagem(), that.getMensagem());
	}

	@Override
	public int hashCode() {

		return Objects.hash(getId(), getMensagem());
	}
}

package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;

public class TxtImportacao implements Serializable {

	private static final long serialVersionUID = 7593722617403593195L;

	private Long nrSeqRegistro;
	private Long nrImportacao;
	private String nrAtendimento;
	private String dsErroRegistro;
	private String nome;

	public TxtImportacao() {
	}

	public Long getNrSeqRegistro() {
		return nrSeqRegistro;
	}

	public void setNrSeqRegistro(Long nrSeqRegistro) {
		this.nrSeqRegistro = nrSeqRegistro;
	}

	public Long getNrImportacao() {
		return nrImportacao;
	}

	public void setNrImportacao(Long nrImportacao) {
		this.nrImportacao = nrImportacao;
	}

	public String getNrAtendimento() {
		return nrAtendimento;
	}

	public void setNrAtendimento(String nrAtendimento) {
		this.nrAtendimento = nrAtendimento;
	}

	public String getDsErroRegistro() {
		return dsErroRegistro;
	}

	public void setDsErroRegistro(String dsErroRegistro) {
		this.dsErroRegistro = dsErroRegistro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "TxtImportacao [nrSeqRegistro=" + nrSeqRegistro + ", nrImportacao=" + nrImportacao + ", nrAtendimento="
				+ nrAtendimento + ", dsErroRegistro=" + dsErroRegistro + ", nome=" + nome + "]";
	}
	
}
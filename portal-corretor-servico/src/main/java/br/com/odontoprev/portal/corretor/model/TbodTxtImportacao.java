package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the TBOD_VENDA database table.
 * 
 */
@Entity
@Table(name = "TBOD_TXT_IMPORTACAO") //201805081631 - esert
//@NamedQuery(name = "TbodTxtImportacao.findAll", query = "SELECT t FROM TbodTxtImportacao t")
public class TbodTxtImportacao implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7410082491116072043L;

	@Id	
	@Column(name = "NR_SEQ_REGISTRO")
	private Long nrSeqRegistro;
	
	@Column(name = "NR_IMPORTACAO")
	private Long nrImportacao;

	@Column(name = "NR_ATENDIMENTO")
	private String nrAtendimento;
	
	@Column(name = "DS_ERRO_REGISTRO")
	private String dsErroRegistro;

	@Column(name = "NOME")
	private String nome;
	
	public TbodTxtImportacao() {
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
		return "TbodTxtImportacao [nrSeqRegistro=" + nrSeqRegistro + ", nrImportacao=" + nrImportacao
				+ ", nrAtendimento=" + nrAtendimento + ", dsErroRegistro=" + dsErroRegistro + ", nome=" + nome + "]";
	}


	
}
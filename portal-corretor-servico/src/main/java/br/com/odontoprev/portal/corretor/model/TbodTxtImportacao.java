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
	@Column(name = "NR_ATENDIMENTO")
	private String nrAtendimento;
	
	@Column(name = "DS_ERRO_REGISTRO")
	private String dsErroRegistro;

	public TbodTxtImportacao() {
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

	@Override
	public String toString() {
		return "TbodTxtImportacao [nrAtendimento=" + nrAtendimento + ", dsErroRegistro=" + dsErroRegistro + "]";
	}
	
}
package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBOD_ORIGEM_ASSOCIADO database table.
 * 
 */
@Entity
@Table(name="TBOD_ORIGEM_ASSOCIADO")
@NamedQuery(name="TbodOrigemAssociado.findAll", query="SELECT t FROM TbodOrigemAssociado t")
public class TbodOrigemAssociado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_ORIGEM_ASSOCIADO")
	private long cdOrigemAssociado;

	@Column(name="DECRICAO")
	private String decricao;

	//bi-directional many-to-one association to TbodDocumentoAssociado
	@OneToMany(mappedBy="tbodOrigemAssociado")
	private List<TbodDocumentoAssociado> tbodDocumentoAssociados;

	public TbodOrigemAssociado() {
	}

	public long getCdOrigemAssociado() {
		return this.cdOrigemAssociado;
	}

	public void setCdOrigemAssociado(long cdOrigemAssociado) {
		this.cdOrigemAssociado = cdOrigemAssociado;
	}

	public String getDecricao() {
		return this.decricao;
	}

	public void setDecricao(String decricao) {
		this.decricao = decricao;
	}

	public List<TbodDocumentoAssociado> getTbodDocumentoAssociados() {
		return this.tbodDocumentoAssociados;
	}

	public void setTbodDocumentoAssociados(List<TbodDocumentoAssociado> tbodDocumentoAssociados) {
		this.tbodDocumentoAssociados = tbodDocumentoAssociados;
	}

	public TbodDocumentoAssociado addTbodDocumentoAssociado(TbodDocumentoAssociado tbodDocumentoAssociado) {
		getTbodDocumentoAssociados().add(tbodDocumentoAssociado);
		tbodDocumentoAssociado.setTbodOrigemAssociado(this);

		return tbodDocumentoAssociado;
	}

	public TbodDocumentoAssociado removeTbodDocumentoAssociado(TbodDocumentoAssociado tbodDocumentoAssociado) {
		getTbodDocumentoAssociados().remove(tbodDocumentoAssociado);
		tbodDocumentoAssociado.setTbodOrigemAssociado(null);

		return tbodDocumentoAssociado;
	}

}
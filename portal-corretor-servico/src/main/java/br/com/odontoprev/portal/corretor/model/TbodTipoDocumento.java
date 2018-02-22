package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBOD_TIPO_DOCUMENTO database table.
 * 
 */
@Entity
@Table(name="TBOD_TIPO_DOCUMENTO")
@NamedQuery(name="TbodTipoDocumento.findAll", query="SELECT t FROM TbodTipoDocumento t")
public class TbodTipoDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_TIPO_DOCUMENTO")
	private long cdTipoDocumento;

	@Column(name="DESCRICAO")
	private String descricao;

	//bi-directional many-to-one association to TbodDocumento
	@OneToMany(mappedBy="tbodTipoDocumento")
	private List<TbodDocumento> tbodDocumentos;

	public TbodTipoDocumento() {
	}

	public long getCdTipoDocumento() {
		return this.cdTipoDocumento;
	}

	public void setCdTipoDocumento(long cdTipoDocumento) {
		this.cdTipoDocumento = cdTipoDocumento;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<TbodDocumento> getTbodDocumentos() {
		return this.tbodDocumentos;
	}

	public void setTbodDocumentos(List<TbodDocumento> tbodDocumentos) {
		this.tbodDocumentos = tbodDocumentos;
	}

	public TbodDocumento addTbodDocumento(TbodDocumento tbodDocumento) {
		getTbodDocumentos().add(tbodDocumento);
		tbodDocumento.setTbodTipoDocumento(this);

		return tbodDocumento;
	}

	public TbodDocumento removeTbodDocumento(TbodDocumento tbodDocumento) {
		getTbodDocumentos().remove(tbodDocumento);
		tbodDocumento.setTbodTipoDocumento(null);

		return tbodDocumento;
	}

}
package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TBOD_DOCUMENTO_ASSOCIADO database table.
 * 
 */
@Entity
@Table(name="TBOD_DOCUMENTO_ASSOCIADO")
@NamedQuery(name="TbodDocumentoAssociado.findAll", query="SELECT t FROM TbodDocumentoAssociado t")
public class TbodDocumentoAssociado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_DOCUMENTO_ASSOCIADO")
	private long cdDocumentoAssociado;

	//bi-directional many-to-one association to TbodCorretora
	@ManyToOne
	@JoinColumn(name="CD_ASSOCIADO", insertable=false, updatable=false)
	private TbodCorretora tbodCorretora;

	//bi-directional many-to-one association to TbodDocumento
	@ManyToOne
	@JoinColumn(name="CD_DOCUMENTO")
	private TbodDocumento tbodDocumento;

	//bi-directional many-to-one association to TbodOrigemAssociado
	@ManyToOne
	@JoinColumn(name="CD_ORIGEM_ASSOCIADO")
	private TbodOrigemAssociado tbodOrigemAssociado;

	//bi-directional many-to-one association to TbodVida
	@ManyToOne
	@JoinColumn(name="CD_ASSOCIADO", insertable=false, updatable=false)
	private TbodVida tbodVida;

	public TbodDocumentoAssociado() {
	}

	public long getCdDocumentoAssociado() {
		return this.cdDocumentoAssociado;
	}

	public void setCdDocumentoAssociado(long cdDocumentoAssociado) {
		this.cdDocumentoAssociado = cdDocumentoAssociado;
	}

	public TbodCorretora getTbodCorretora() {
		return this.tbodCorretora;
	}

	public void setTbodCorretora(TbodCorretora tbodCorretora) {
		this.tbodCorretora = tbodCorretora;
	}

	public TbodDocumento getTbodDocumento() {
		return this.tbodDocumento;
	}

	public void setTbodDocumento(TbodDocumento tbodDocumento) {
		this.tbodDocumento = tbodDocumento;
	}

	public TbodOrigemAssociado getTbodOrigemAssociado() {
		return this.tbodOrigemAssociado;
	}

	public void setTbodOrigemAssociado(TbodOrigemAssociado tbodOrigemAssociado) {
		this.tbodOrigemAssociado = tbodOrigemAssociado;
	}

	public TbodVida getTbodVida() {
		return this.tbodVida;
	}

	public void setTbodVida(TbodVida tbodVida) {
		this.tbodVida = tbodVida;
	}

}
package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the TBOD_DOCUMENTO database table.
 * 
 */
@Entity
@Table(name="TBOD_DOCUMENTO")
@NamedQuery(name="TbodDocumento.findAll", query="SELECT t FROM TbodDocumento t")
public class TbodDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_DOCUMENTO")
	private long cdDocumento;

	@Column(name="BASE64")
	private String base64;

	@Column(name="CD_TIPO_ARQUIVO")
	private BigDecimal cdTipoArquivo;

	@Column(name="NOME_ARQUIVO")
	private String nomeArquivo;

	//bi-directional many-to-one association to TbodTipoDocumento
	@ManyToOne
	@JoinColumn(name="CD_TIPO_DOCUMENTO")
	private TbodTipoDocumento tbodTipoDocumento;

	//bi-directional many-to-one association to TbodDocumentoAssociado
	@OneToMany(mappedBy="tbodDocumento")
	private List<TbodDocumentoAssociado> tbodDocumentoAssociados;

	public TbodDocumento() {
	}

	public long getCdDocumento() {
		return this.cdDocumento;
	}

	public void setCdDocumento(long cdDocumento) {
		this.cdDocumento = cdDocumento;
	}

	public String getBase64() {
		return this.base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public BigDecimal getCdTipoArquivo() {
		return this.cdTipoArquivo;
	}

	public void setCdTipoArquivo(BigDecimal cdTipoArquivo) {
		this.cdTipoArquivo = cdTipoArquivo;
	}

	public String getNomeArquivo() {
		return this.nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public TbodTipoDocumento getTbodTipoDocumento() {
		return this.tbodTipoDocumento;
	}

	public void setTbodTipoDocumento(TbodTipoDocumento tbodTipoDocumento) {
		this.tbodTipoDocumento = tbodTipoDocumento;
	}

	public List<TbodDocumentoAssociado> getTbodDocumentoAssociados() {
		return this.tbodDocumentoAssociados;
	}

	public void setTbodDocumentoAssociados(List<TbodDocumentoAssociado> tbodDocumentoAssociados) {
		this.tbodDocumentoAssociados = tbodDocumentoAssociados;
	}

	public TbodDocumentoAssociado addTbodDocumentoAssociado(TbodDocumentoAssociado tbodDocumentoAssociado) {
		getTbodDocumentoAssociados().add(tbodDocumentoAssociado);
		tbodDocumentoAssociado.setTbodDocumento(this);

		return tbodDocumentoAssociado;
	}

	public TbodDocumentoAssociado removeTbodDocumentoAssociado(TbodDocumentoAssociado tbodDocumentoAssociado) {
		getTbodDocumentoAssociados().remove(tbodDocumentoAssociado);
		tbodDocumentoAssociado.setTbodDocumento(null);

		return tbodDocumentoAssociado;
	}

}
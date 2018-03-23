package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the TBOD_SERASA_REPRES_LEGAL database table.
 * 
 */
@Entity
@Table(name="TBOD_SERASA_REPRES_LEGAL")
@NamedQuery(name="TbodSerasaRepresLegal.findAll", query="SELECT t FROM TbodSerasaRepresLegal t")
public class TbodSerasaRepresLegal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_SERASA_REPRES_LEGAL", sequenceName = "SEQ_TBOD_SERASA_REPRES_LEGAL", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_SERASA_REPRES_LEGAL")
	private long id;

	private BigDecimal documento;

	private String nome;

	//bi-directional many-to-one association to TbodSerasaInfo
	@ManyToOne
	@JoinColumn(name="CNPJ")
	private TbodSerasaInfo tbodSerasaInfo;

	public TbodSerasaRepresLegal() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getDocumento() {
		return this.documento;
	}

	public void setDocumento(BigDecimal documento) {
		this.documento = documento;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TbodSerasaInfo getTbodSerasaInfo() {
		return this.tbodSerasaInfo;
	}

	public void setTbodSerasaInfo(TbodSerasaInfo tbodSerasaInfo) {
		this.tbodSerasaInfo = tbodSerasaInfo;
	}

}
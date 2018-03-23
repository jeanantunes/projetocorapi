package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the TBOD_SERASA_TELEFONE database table.
 * 
 */
@Entity
@Table(name="TBOD_SERASA_TELEFONE")
@NamedQuery(name="TbodSerasaTelefone.findAll", query="SELECT t FROM TbodSerasaTelefone t")
public class TbodSerasaTelefone implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_SERASA_TELEFONE", sequenceName = "SEQ_TBOD_SERASA_TELEFONE", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_SERASA_TELEFONE")
	private long id;

	private BigDecimal ddd;

	private BigDecimal numero;

	//bi-directional many-to-one association to TbodSerasaInfo
	@ManyToOne
	@JoinColumn(name="CNPJ")
	private TbodSerasaInfo tbodSerasaInfo;

	public TbodSerasaTelefone() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getDdd() {
		return this.ddd;
	}

	public void setDdd(BigDecimal ddd) {
		this.ddd = ddd;
	}

	public BigDecimal getNumero() {
		return this.numero;
	}

	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}

	public TbodSerasaInfo getTbodSerasaInfo() {
		return this.tbodSerasaInfo;
	}

	public void setTbodSerasaInfo(TbodSerasaInfo tbodSerasaInfo) {
		this.tbodSerasaInfo = tbodSerasaInfo;
	}

}
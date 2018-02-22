package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the TBOD_PLANO_VIDA database table.
 * 
 */
@Entity
@Table(name="TBOD_PLANO_VIDA")
@NamedQuery(name="TbodPlanoVida.findAll", query="SELECT t FROM TbodPlanoVida t")
public class TbodPlanoVida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_PLANO_VIDA", sequenceName = "SEQ_TBOD_PLANO_VIDA",  allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_PLANO_VIDA")
	@Column(name="CD_PLANO_VIDA")
	private long cdPlanoVida;

	@Column(name="CD_PLANO")
	private BigDecimal cdPlano;

	//bi-directional many-to-one association to TbodVida
	@ManyToOne
	@JoinColumn(name="CD_VIDA")
	private TbodVida tbodVida;

	public TbodPlanoVida() {
	}

	public long getCdPlanoVida() {
		return this.cdPlanoVida;
	}

	public void setCdPlanoVida(long cdPlanoVida) {
		this.cdPlanoVida = cdPlanoVida;
	}

	public BigDecimal getCdPlano() {
		return this.cdPlano;
	}

	public void setCdPlano(BigDecimal cdPlano) {
		this.cdPlano = cdPlano;
	}

	public TbodVida getTbodVida() {
		return this.tbodVida;
	}

	public void setTbodVida(TbodVida tbodVida) {
		this.tbodVida = tbodVida;
	}

}
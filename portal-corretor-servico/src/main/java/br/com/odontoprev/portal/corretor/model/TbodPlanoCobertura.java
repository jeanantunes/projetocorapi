package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TBOD_PLANO_COBERTURA database table.
 * 
 */
@Entity
@Table(name="TBOD_PLANO_COBERTURA")
@NamedQuery(name="TbodPlanoCobertura.findAll", query="SELECT t FROM TbodPlanoCobertura t")
public class TbodPlanoCobertura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_PLANO_COBERTURA")
	private long cdPlanoCobertura;

	//bi-directional many-to-one association to TbodCobertura
	@ManyToOne
	@JoinColumn(name="CD_COBERTURA")
	private TbodCobertura tbodCobertura;

	//bi-directional many-to-one association to TbodPlano
	@ManyToOne
	@JoinColumn(name="CD_PLANO")
	private TbodPlano tbodPlano;

	public TbodPlanoCobertura() {
	}

	public long getCdPlanoCobertura() {
		return this.cdPlanoCobertura;
	}

	public void setCdPlanoCobertura(long cdPlanoCobertura) {
		this.cdPlanoCobertura = cdPlanoCobertura;
	}

	public TbodCobertura getTbodCobertura() {
		return this.tbodCobertura;
	}

	public void setTbodCobertura(TbodCobertura tbodCobertura) {
		this.tbodCobertura = tbodCobertura;
	}

	public TbodPlano getTbodPlano() {
		return this.tbodPlano;
	}

	public void setTbodPlano(TbodPlano tbodPlano) {
		this.tbodPlano = tbodPlano;
	}

}
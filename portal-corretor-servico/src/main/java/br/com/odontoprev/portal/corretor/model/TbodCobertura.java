package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBOD_COBERTURA database table.
 * 
 */
@Entity
@Table(name="TBOD_COBERTURA")
@NamedQuery(name="TbodCobertura.findAll", query="SELECT t FROM TbodCobertura t")
public class TbodCobertura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_COBERTURA")
	private long cdCobertura;

	@Column(name="DESCRICAO")
	private String descricao;

	//bi-directional many-to-one association to TbodPlanoCobertura
	@OneToMany(mappedBy="tbodCobertura")
	private List<TbodPlanoCobertura> tbodPlanoCoberturas;

	public TbodCobertura() {
	}

	public long getCdCobertura() {
		return this.cdCobertura;
	}

	public void setCdCobertura(long cdCobertura) {
		this.cdCobertura = cdCobertura;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<TbodPlanoCobertura> getTbodPlanoCoberturas() {
		return this.tbodPlanoCoberturas;
	}

	public void setTbodPlanoCoberturas(List<TbodPlanoCobertura> tbodPlanoCoberturas) {
		this.tbodPlanoCoberturas = tbodPlanoCoberturas;
	}

	public TbodPlanoCobertura addTbodPlanoCobertura(TbodPlanoCobertura tbodPlanoCobertura) {
		getTbodPlanoCoberturas().add(tbodPlanoCobertura);
		tbodPlanoCobertura.setTbodCobertura(this);

		return tbodPlanoCobertura;
	}

	public TbodPlanoCobertura removeTbodPlanoCobertura(TbodPlanoCobertura tbodPlanoCobertura) {
		getTbodPlanoCoberturas().remove(tbodPlanoCobertura);
		tbodPlanoCobertura.setTbodCobertura(null);

		return tbodPlanoCobertura;
	}

}
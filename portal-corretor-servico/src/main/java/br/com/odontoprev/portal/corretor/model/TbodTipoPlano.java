package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBOD_TIPO_PLANO database table.
 * 
 */
@Entity
@Table(name="TBOD_TIPO_PLANO")
@NamedQuery(name="TbodTipoPlano.findAll", query="SELECT t FROM TbodTipoPlano t")
public class TbodTipoPlano implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_TIPO_PLANO")
	private long cdTipoPlano;

	@Column(name="DESCRICAO")
	private String descricao;

	//bi-directional many-to-one association to TbodPlano
	@OneToMany(mappedBy="tbodTipoPlano")
	private List<TbodPlano> tbodPlanos;

	public TbodTipoPlano() {
	}

	public long getCdTipoPlano() {
		return this.cdTipoPlano;
	}

	public void setCdTipoPlano(long cdTipoPlano) {
		this.cdTipoPlano = cdTipoPlano;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<TbodPlano> getTbodPlanos() {
		return this.tbodPlanos;
	}

	public void setTbodPlanos(List<TbodPlano> tbodPlanos) {
		this.tbodPlanos = tbodPlanos;
	}

	public TbodPlano addTbodPlano(TbodPlano tbodPlano) {
		getTbodPlanos().add(tbodPlano);
		tbodPlano.setTbodTipoPlano(this);

		return tbodPlano;
	}

	public TbodPlano removeTbodPlano(TbodPlano tbodPlano) {
		getTbodPlanos().remove(tbodPlano);
		tbodPlano.setTbodTipoPlano(null);

		return tbodPlano;
	}

}
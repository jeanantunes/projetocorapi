package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBOD_STATUS_FORCA_VENDA database table.
 * 
 */
@Entity
@Table(name="TBOD_STATUS_FORCA_VENDA")
@NamedQuery(name="TbodStatusForcaVenda.findAll", query="SELECT t FROM TbodStatusForcaVenda t")
public class TbodStatusForcaVenda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_STATUS_FORCA_VENDAS")
	private long cdStatusForcaVendas;

	@Column(name="DESCRICAO")
	private String descricao;

	//bi-directional many-to-one association to TbodForcaVenda
	@OneToMany(mappedBy="tbodStatusForcaVenda")
	private List<TbodForcaVenda> tbodForcaVendas;

	public TbodStatusForcaVenda() {
	}

	public long getCdStatusForcaVendas() {
		return this.cdStatusForcaVendas;
	}

	public void setCdStatusForcaVendas(long cdStatusForcaVendas) {
		this.cdStatusForcaVendas = cdStatusForcaVendas;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<TbodForcaVenda> getTbodForcaVendas() {
		return this.tbodForcaVendas;
	}

	public void setTbodForcaVendas(List<TbodForcaVenda> tbodForcaVendas) {
		this.tbodForcaVendas = tbodForcaVendas;
	}

	public TbodForcaVenda addTbodForcaVenda(TbodForcaVenda tbodForcaVenda) {
		getTbodForcaVendas().add(tbodForcaVenda);
		tbodForcaVenda.setTbodStatusForcaVenda(this);

		return tbodForcaVenda;
	}

	public TbodForcaVenda removeTbodForcaVenda(TbodForcaVenda tbodForcaVenda) {
		getTbodForcaVendas().remove(tbodForcaVenda);
		tbodForcaVenda.setTbodStatusForcaVenda(null);

		return tbodForcaVenda;
	}

}
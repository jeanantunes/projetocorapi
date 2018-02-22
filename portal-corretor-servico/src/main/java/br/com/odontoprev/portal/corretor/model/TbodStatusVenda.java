package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBOD_STATUS_VENDA database table.
 * 
 */
@Entity
@Table(name="TBOD_STATUS_VENDA")
@NamedQuery(name="TbodStatusVenda.findAll", query="SELECT t FROM TbodStatusVenda t")
public class TbodStatusVenda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_STATUS_VENDA")
	private long cdStatusVenda;

	@Column(name="DESCRICAO")
	private String descricao;

	//bi-directional many-to-one association to TbodVenda
	@OneToMany(mappedBy="tbodStatusVenda")
	private List<TbodVenda> tbodVendas;

	public TbodStatusVenda() {
	}

	public long getCdStatusVenda() {
		return this.cdStatusVenda;
	}

	public void setCdStatusVenda(long cdStatusVenda) {
		this.cdStatusVenda = cdStatusVenda;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<TbodVenda> getTbodVendas() {
		return this.tbodVendas;
	}

	public void setTbodVendas(List<TbodVenda> tbodVendas) {
		this.tbodVendas = tbodVendas;
	}

	public TbodVenda addTbodVenda(TbodVenda tbodVenda) {
		getTbodVendas().add(tbodVenda);
		tbodVenda.setTbodStatusVenda(this);

		return tbodVenda;
	}

	public TbodVenda removeTbodVenda(TbodVenda tbodVenda) {
		getTbodVendas().remove(tbodVenda);
		tbodVenda.setTbodStatusVenda(null);

		return tbodVenda;
	}

}
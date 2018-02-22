package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBOD_VENDA_VIDA database table.
 * 
 */
@Entity
@Table(name="TBOD_VENDA_VIDA")
@NamedQuery(name="TbodVendaVida.findAll", query="SELECT t FROM TbodVendaVida t")
public class TbodVendaVida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_VENDA_VIDA", sequenceName = "SEQ_TBOD_VENDA_VIDA",  allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_VENDA_VIDA")
	@Column(name="CD_VENDA_VIDA")
	private long cdVendaVida;

	@Column(name="CD_PLANO")
	private long cdPlano;
	
	//bi-directional many-to-one association to TbodVenda
	@OneToMany(mappedBy="tbodVendaVida")
	private List<TbodVenda> tbodVendas;

	//bi-directional many-to-one association to TbodVenda
	@ManyToOne
	@JoinColumn(name="CD_VENDA")
	private TbodVenda tbodVenda;

	//bi-directional many-to-one association to TbodVida
	@ManyToOne
	@JoinColumn(name="CD_VIDA")
	private TbodVida tbodVida;

	public TbodVendaVida() {
	}

	public long getCdVendaVida() {
		return this.cdVendaVida;
	}

	public void setCdVendaVida(long cdVendaVida) {
		this.cdVendaVida = cdVendaVida;
	}
	
	public long getCdPlano() {
		return cdPlano;
	}

	public void setCdPlano(long cdPlano) {
		this.cdPlano = cdPlano;
	}

	public List<TbodVenda> getTbodVendas() {
		return this.tbodVendas;
	}

	public void setTbodVendas(List<TbodVenda> tbodVendas) {
		this.tbodVendas = tbodVendas;
	}

	public TbodVenda addTbodVenda(TbodVenda tbodVenda) {
		getTbodVendas().add(tbodVenda);
		tbodVenda.setTbodVendaVida(this);

		return tbodVenda;
	}

	public TbodVenda removeTbodVenda(TbodVenda tbodVenda) {
		getTbodVendas().remove(tbodVenda);
		tbodVenda.setTbodVendaVida(null);

		return tbodVenda;
	}

	public TbodVenda getTbodVenda() {
		return this.tbodVenda;
	}

	public void setTbodVenda(TbodVenda tbodVenda) {
		this.tbodVenda = tbodVenda;
	}

	public TbodVida getTbodVida() {
		return this.tbodVida;
	}

	public void setTbodVida(TbodVida tbodVida) {
		this.tbodVida = tbodVida;
	}

}
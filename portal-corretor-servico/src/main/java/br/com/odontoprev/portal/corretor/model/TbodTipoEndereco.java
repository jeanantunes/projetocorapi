package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBOD_TIPO_ENDERECO database table.
 * 
 */
@Entity
@Table(name="TBOD_TIPO_ENDERECO")
@NamedQuery(name="TbodTipoEndereco.findAll", query="SELECT t FROM TbodTipoEndereco t")
public class TbodTipoEndereco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_TIPO_ENDERECO")
	private long cdTipoEndereco;

	@Column(name="DESCRICAO")
	private String descricao;

	//bi-directional many-to-one association to TbodEndereco
	@OneToMany(mappedBy="tbodTipoEndereco")
	private List<TbodEndereco> tbodEnderecos;

	public TbodTipoEndereco() {
	}

	public long getCdTipoEndereco() {
		return this.cdTipoEndereco;
	}

	public void setCdTipoEndereco(long cdTipoEndereco) {
		this.cdTipoEndereco = cdTipoEndereco;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<TbodEndereco> getTbodEnderecos() {
		return this.tbodEnderecos;
	}

	public void setTbodEnderecos(List<TbodEndereco> tbodEnderecos) {
		this.tbodEnderecos = tbodEnderecos;
	}

	public TbodEndereco addTbodEndereco(TbodEndereco tbodEndereco) {
		getTbodEnderecos().add(tbodEndereco);
		tbodEndereco.setTbodTipoEndereco(this);

		return tbodEndereco;
	}

	public TbodEndereco removeTbodEndereco(TbodEndereco tbodEndereco) {
		getTbodEnderecos().remove(tbodEndereco);
		tbodEndereco.setTbodTipoEndereco(null);

		return tbodEndereco;
	}

}
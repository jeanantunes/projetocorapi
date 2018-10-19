package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//201809181556 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)
@Entity
@Table(name="TBOD_TIPO_BLOQUEIO")
public class TbodTipoBloqueio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_TIPO_BLOQUEIO")
	private Long cdTipoBloqueio;

	@Column(name="DESCRICAO")
	private String descricao;

	//bi-directional many-to-one association to TbodPlano
	@OneToMany(mappedBy="tbodTipoBloqueio")
	private List<TbodLogin> tbodLogins;

	public TbodTipoBloqueio() {
	}

	public Long getCdTipoBloqueio() {
		return cdTipoBloqueio;
	}

	public void setCdTipoBloqueio(Long cdTipoBloqueio) {
		this.cdTipoBloqueio = cdTipoBloqueio;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "TbodTipoBloqueio [cdTipoBloqueio=" + cdTipoBloqueio + ", descricao=" + descricao + "]";
	}

	public List<TbodLogin> getTbodLogins() {
		return this.tbodLogins;
	}

	public void setTbodLogins(List<TbodLogin> tbodLogins) {
		this.tbodLogins = tbodLogins;
	}

	public TbodLogin addTbodLogin(TbodLogin tbodLogin) {
		getTbodLogins().add(tbodLogin);
		tbodLogin.setTbodTipoBloqueio(this);
		return tbodLogin;
	}

	public TbodLogin removeTbodLogin(TbodLogin tbodLogin) {
		getTbodLogins().remove(tbodLogin);
		tbodLogin.setTbodTipoBloqueio(null);
		return tbodLogin;
	}

}
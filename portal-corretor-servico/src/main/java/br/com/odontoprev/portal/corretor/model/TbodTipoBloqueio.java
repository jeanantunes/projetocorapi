package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

//201809181556 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)
@Entity
@Table(name="TBOD_TIPO_BLOQUEIO")
public class TbodTipoBloqueio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_TIPO_BLOQUEIO")
	private long cdTipoBloqueio;

	@Column(name="DESCRICAO")
	private String descricao;

	//bi-directional many-to-one association to TbodPlano
	@OneToMany(mappedBy="tbodLogin")
	private List<TbodLogin> tbodLogins;

	public TbodTipoBloqueio() {
	}

	public long getCdTipoBloqueio() {
		return cdTipoBloqueio;
	}

	public void setCdTipoBloqueio(long cdTipoBloqueio) {
		this.cdTipoBloqueio = cdTipoBloqueio;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
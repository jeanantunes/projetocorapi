package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the TBOD_LOGIN database table.
 * 
 */
@Entity
@Table(name = "TBOD_LOGIN")
@NamedQuery(name = "TbodLogin.findAll", query = "SELECT t FROM TbodLogin t")
public class TbodLogin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_LOGIN", sequenceName = "SEQ_TBOD_LOGIN", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_LOGIN")
	@Column(name = "CD_LOGIN")
	private Long cdLogin;

	@Column(name = "CD_TIPO_LOGIN")
	private Long cdTipoLogin;

	@Column(name = "FOTO_PERFIL_B64")
	private String fotoPerfilB64;

	@Column(name = "SENHA")
	private String senha;
	
	@Column(name = "TEM_BLOQUEIO") //201809181556 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)
	private String temBloqueio; //201809181556 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)
	
	@Column(name = "CD_TIPO_BLOQUEIO") //201809181556 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)
	private TbodTipoBloqueio tbodTipoBloqueio; //201809181556 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)

	// bi-directional many-to-one association to TbodCorretora
	@OneToMany(mappedBy = "tbodLogin")
	private List<TbodCorretora> tbodCorretoras;

	// bi-directional many-to-one association to TbodForcaVenda
	@OneToMany(mappedBy = "tbodLogin")
	private List<TbodForcaVenda> tbodForcaVendas;

	// bi-directional many-to-one association to TbodForcaVenda
	@OneToMany(mappedBy = "tbodLogin")
	private List<TbodTipoBloqueio> tbodTipoBloqueios; //201809181556 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)


	public TbodLogin() {
	}

	public Long getCdLogin() {
		return cdLogin;
	}

	public void setCdLogin(Long cdLogin) {
		this.cdLogin = cdLogin;
	}

	public Long getCdTipoLogin() {
		return cdTipoLogin;
	}

	public void setCdTipoLogin(Long cdTipoLogin) {
		this.cdTipoLogin = cdTipoLogin;
	}

	public String getFotoPerfilB64() {
		return this.fotoPerfilB64;
	}

	public void setFotoPerfilB64(String fotoPerfilB64) {
		this.fotoPerfilB64 = fotoPerfilB64;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<TbodCorretora> getTbodCorretoras() {
		return this.tbodCorretoras;
	}

	public void setTbodCorretoras(List<TbodCorretora> tbodCorretoras) {
		this.tbodCorretoras = tbodCorretoras;
	}

	public TbodCorretora addTbodCorretora(TbodCorretora tbodCorretora) {
		getTbodCorretoras().add(tbodCorretora);
		tbodCorretora.setTbodLogin(this);

		return tbodCorretora;
	}

	public TbodCorretora removeTbodCorretora(TbodCorretora tbodCorretora) {
		getTbodCorretoras().remove(tbodCorretora);
		tbodCorretora.setTbodLogin(null);

		return tbodCorretora;
	}

	public List<TbodForcaVenda> getTbodForcaVendas() {
		return this.tbodForcaVendas;
	}

	public void setTbodForcaVendas(List<TbodForcaVenda> tbodForcaVendas) {
		this.tbodForcaVendas = tbodForcaVendas;
	}

	public TbodForcaVenda addTbodForcaVenda(TbodForcaVenda tbodForcaVenda) {
		getTbodForcaVendas().add(tbodForcaVenda);
		tbodForcaVenda.setTbodLogin(this);

		return tbodForcaVenda;
	}

	public TbodForcaVenda removeTbodForcaVenda(TbodForcaVenda tbodForcaVenda) {
		getTbodForcaVendas().remove(tbodForcaVenda);
		tbodForcaVenda.setTbodLogin(null);

		return tbodForcaVenda;
	}

	public String getTemBloqueio() {
		return temBloqueio;
	}

	public void setTemBloqueio(String temBloqueio) {
		this.temBloqueio = temBloqueio;
	}

	public List<TbodTipoBloqueio> getTbodTipoBloqueios() {
		return tbodTipoBloqueios;
	}

	public void setTbodTipoBloqueios(List<TbodTipoBloqueio> tbodTipoBloqueios) {
		this.tbodTipoBloqueios = tbodTipoBloqueios;
	}

	public TbodTipoBloqueio getTbodTipoBloqueio() {
		return tbodTipoBloqueio;
	}

	public void setTbodTipoBloqueio(TbodTipoBloqueio tbodTipoBloqueio) {
		this.tbodTipoBloqueio = tbodTipoBloqueio;
	}

	// public TbodCorretora getTbodCorretora() {
	// return this.tbodCorretora;
	// }
	//
	// public void setTbodCorretora(TbodCorretora tbodCorretora) {
	// this.tbodCorretora = tbodCorretora;
	// }
	//
	// public TbodForcaVenda getTbodForcaVenda() {
	// return this.tbodForcaVenda;
	// }
	//
	// public void setTbodForcaVenda(TbodForcaVenda tbodForcaVenda) {
	// this.tbodForcaVenda = tbodForcaVenda;
	// }

}
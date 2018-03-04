package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the TBOD_FORCA_VENDA database table.
 * 
 */
@Entity
@Table(name = "TBOD_FORCA_VENDA")
@NamedQuery(name = "TbodForcaVenda.findAll", query = "SELECT t FROM TbodForcaVenda t")
public class TbodForcaVenda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_FORCA_VENDA", sequenceName = "SEQ_TBOD_FORCA_VENDA", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_FORCA_VENDA")
	@Column(name = "CD_FORCA_VENDA")
	private Long cdForcaVenda;

	@Column(name = "ATIVO")
	private String ativo;

	@Column(name = "CELULAR")
	private String celular;

	@Column(name = "CPF")
	private String cpf;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "NOME")
	private String nome;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_NASCIMENTO")
	private Date dataNascimento;

	@Column(name = "CARGO")
	private String cargo;

	@Column(name = "DEPARTAMENTO")
	private String departamento;

	// bi-directional many-to-one association to TbodCorretora
	@ManyToOne
	@JoinColumn(name = "CD_CORRETORA")
	private TbodCorretora tbodCorretora;

	// bi-directional many-to-one association to TbodStatusForcaVenda
	@ManyToOne
	@JoinColumn(name = "CD_STATUS_FORCA_VENDAS")
	private TbodStatusForcaVenda tbodStatusForcaVenda;

	// bi-directional many-to-one association to TbodLogin
	@ManyToOne
	@JoinColumn(name = "CD_LOGIN")
	private TbodLogin tbodLogin;
	
	@ManyToOne
	@JoinColumn(name = "CD_DCSS_USUARIO")
	private Long codigoDcssUsuario;

	// // bi-directional many-to-one association to TbodLogin
	// @OneToMany(mappedBy = "tbodForcaVenda")
	// private List<TbodLogin> tbodLogins;

	// bi-directional many-to-one association to TbodVenda
	@OneToMany(mappedBy = "tbodForcaVenda")
	private List<TbodVenda> tbodVendas;

	public TbodForcaVenda() {
	}

	public Long getCdForcaVenda() {
		return cdForcaVenda;
	}

	public void setCdForcaVenda(Long cdForcaVenda) {
		this.cdForcaVenda = cdForcaVenda;
	}

	public String getAtivo() {
		return this.ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public TbodCorretora getTbodCorretora() {
		return this.tbodCorretora;
	}

	public void setTbodCorretora(TbodCorretora tbodCorretora) {
		this.tbodCorretora = tbodCorretora;
	}

	public TbodStatusForcaVenda getTbodStatusForcaVenda() {
		return this.tbodStatusForcaVenda;
	}

	public void setTbodStatusForcaVenda(TbodStatusForcaVenda tbodStatusForcaVenda) {
		this.tbodStatusForcaVenda = tbodStatusForcaVenda;
	}

	public TbodLogin getTbodLogin() {
		return this.tbodLogin;
	}

	public void setTbodLogin(TbodLogin tbodLogin) {
		this.tbodLogin = tbodLogin;
	}

	// public List<TbodLogin> getTbodLogins() {
	// return this.tbodLogins;
	// }
	//
	// public void setTbodLogins(List<TbodLogin> tbodLogins) {
	// this.tbodLogins = tbodLogins;
	// }
	//
	// public TbodLogin addTbodLogin(TbodLogin tbodLogin) {
	// getTbodLogins().add(tbodLogin);
	// tbodLogin.setTbodForcaVenda(this);
	//
	// return tbodLogin;
	// }
	//
	// public TbodLogin removeTbodLogin(TbodLogin tbodLogin) {
	// getTbodLogins().remove(tbodLogin);
	// tbodLogin.setTbodForcaVenda(null);
	//
	// return tbodLogin;
	// }

	public List<TbodVenda> getTbodVendas() {
		return this.tbodVendas;
	}

	public void setTbodVendas(List<TbodVenda> tbodVendas) {
		this.tbodVendas = tbodVendas;
	}

	public TbodVenda addTbodVenda(TbodVenda tbodVenda) {
		getTbodVendas().add(tbodVenda);
		tbodVenda.setTbodForcaVenda(this);

		return tbodVenda;
	}

	public TbodVenda removeTbodVenda(TbodVenda tbodVenda) {
		getTbodVendas().remove(tbodVenda);
		tbodVenda.setTbodForcaVenda(null);

		return tbodVenda;
	}

	public Long getCodigoDcssUsuario() {
		return codigoDcssUsuario;
	}

	public void setCodigoDcssUsuario(Long codigoDcssUsuario) {
		this.codigoDcssUsuario = codigoDcssUsuario;
	}
	
	

}
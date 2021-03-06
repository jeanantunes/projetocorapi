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
 * The persistent class for the TBOD_CORRETORA database table.
 *
 */
@Entity
@Table(name = "TBOD_CORRETORA")
@NamedQuery(name = "TbodCorretora.findAll", query = "SELECT t FROM TbodCorretora t")
public class TbodCorretora implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_CORRETORA", sequenceName = "SEQ_TBOD_CORRETORA", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_CORRETORA")
	@Column(name = "CD_CORRETORA")
	private Long cdCorretora;

	@Column(name = "ATIVO")
	private String ativo;

	@Column(name = "CELULAR")
	private String celular;

	@Column(name = "CNPJ")
	private String cnpj;

	@Column(name = "CODIGO")
	private String codigo;

	@Column(name = "CPF_RESPONSAVEL")
	private String cpfResponsavel;

	@Column(name = "CPF_RESPONSAVEL2")
	private String cpfResponsavel2;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_ABERTURA")
	private Date dataAbertura;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "NOME_REPRESENTANTE_LEGAL1")
	private String nomeRepresentanteLegal1;

	@Column(name = "NOME_REPRESENTANTE_LEGAL2")
	private String nomeRepresentanteLegal2;

	@Column(name = "RAZAO_SOCIAL")
	private String razaoSocial;

	@Column(name = "REGIONAL")
	private String regional;

	@Column(name = "SIMPLES_NACIONAL")
	private String simplesNacional;

	@Column(name = "STATUS_CNPJ")
	private String statusCnpj;

	@Column(name = "TELEFONE")
	private String telefone;

	@Column(name = "CNAE")
	private String cnae;

	// bi-directional many-to-one association to TbodEndereco
	@ManyToOne
	@JoinColumn(name = "CD_ENDERECO")
	private TbodEndereco tbodEndereco;

	// bi-directional many-to-one association to TbodCorretoraBanco
	@OneToMany(mappedBy = "tbodCorretora")
	private List<TbodCorretoraBanco> tbodCorretoraBancos;

	// bi-directional many-to-one association to TbodDocumentoAssociado
	@OneToMany(mappedBy = "tbodCorretora")
	private List<TbodDocumentoAssociado> tbodDocumentoAssociados;

	// bi-directional many-to-one association to TbodForcaVenda
	@OneToMany(mappedBy = "tbodCorretora")
	private List<TbodForcaVenda> tbodForcaVendas;
	
	// bi-directional many-to-one association to TbodForcaVenda
	@OneToMany(mappedBy = "tbodCorretora")
	private List<TbodContratoCorretora> tbodContratoCorretoras; //201809112123 - esert

	// bi-directional many-to-one association to TbodLogin
	@ManyToOne
	@JoinColumn(name = "CD_LOGIN")
	private TbodLogin tbodLogin;

	@Column(name = "TEM_SUSEP")
	private String temSusep;

	@Column(name = "CODIGO_SUSEP")
	private String codigoSusep;

	// //bi-directional many-to-one association to TbodLogin
	// @OneToMany(mappedBy="tbodCorretora")
	// private List<TbodLogin> tbodLogins;

	public TbodCorretora() {
	}

	public Long getCdCorretora() {
		return this.cdCorretora;
	}

	public void setCdCorretora(Long cdCorretora) {
		this.cdCorretora = cdCorretora;
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

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCpfResponsavel() {
		return this.cpfResponsavel;
	}

	public void setCpfResponsavel(String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}

	public String getCpfResponsavel2() {
		return cpfResponsavel2;
	}

	public void setCpfResponsavel2(String cpfResponsavel2) {
		this.cpfResponsavel2 = cpfResponsavel2;
	}

	public Date getDataAbertura() {
		return this.dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
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

	public String getNomeRepresentanteLegal1() {
		return this.nomeRepresentanteLegal1;
	}

	public void setNomeRepresentanteLegal1(String nomeRepresentanteLegal1) {
		this.nomeRepresentanteLegal1 = nomeRepresentanteLegal1;
	}

	public String getNomeRepresentanteLegal2() {
		return this.nomeRepresentanteLegal2;
	}

	public void setNomeRepresentanteLegal2(String nomeRepresentanteLegal2) {
		this.nomeRepresentanteLegal2 = nomeRepresentanteLegal2;
	}

	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getRegional() {
		return this.regional;
	}

	public void setRegional(String regional) {
		this.regional = regional;
	}

	public String getSimplesNacional() {
		return this.simplesNacional;
	}

	public void setSimplesNacional(String simplesNacional) {
		this.simplesNacional = simplesNacional;
	}

	public String getStatusCnpj() {
		return this.statusCnpj;
	}

	public void setStatusCnpj(String statusCnpj) {
		this.statusCnpj = statusCnpj;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public TbodEndereco getTbodEndereco() {
		return this.tbodEndereco;
	}

	public void setTbodEndereco(TbodEndereco tbodEndereco) {
		this.tbodEndereco = tbodEndereco;
	}

	public List<TbodCorretoraBanco> getTbodCorretoraBancos() {
		return this.tbodCorretoraBancos;
	}

	public void setTbodCorretoraBancos(List<TbodCorretoraBanco> tbodCorretoraBancos) {
		this.tbodCorretoraBancos = tbodCorretoraBancos;
	}

	public TbodCorretoraBanco addTbodCorretoraBanco(TbodCorretoraBanco tbodCorretoraBanco) {
		getTbodCorretoraBancos().add(tbodCorretoraBanco);
		tbodCorretoraBanco.setTbodCorretora(this);

		return tbodCorretoraBanco;
	}

	public TbodCorretoraBanco removeTbodCorretoraBanco(TbodCorretoraBanco tbodCorretoraBanco) {
		getTbodCorretoraBancos().remove(tbodCorretoraBanco);
		tbodCorretoraBanco.setTbodCorretora(null);

		return tbodCorretoraBanco;
	}

	public List<TbodDocumentoAssociado> getTbodDocumentoAssociados() {
		return this.tbodDocumentoAssociados;
	}

	public void setTbodDocumentoAssociados(List<TbodDocumentoAssociado> tbodDocumentoAssociados) {
		this.tbodDocumentoAssociados = tbodDocumentoAssociados;
	}

	public TbodDocumentoAssociado addTbodDocumentoAssociado(TbodDocumentoAssociado tbodDocumentoAssociado) {
		getTbodDocumentoAssociados().add(tbodDocumentoAssociado);
		tbodDocumentoAssociado.setTbodCorretora(this);

		return tbodDocumentoAssociado;
	}

	public TbodDocumentoAssociado removeTbodDocumentoAssociado(TbodDocumentoAssociado tbodDocumentoAssociado) {
		getTbodDocumentoAssociados().remove(tbodDocumentoAssociado);
		tbodDocumentoAssociado.setTbodCorretora(null);

		return tbodDocumentoAssociado;
	}

	public List<TbodForcaVenda> getTbodForcaVendas() {
		return this.tbodForcaVendas;
	}

	public void setTbodForcaVendas(List<TbodForcaVenda> tbodForcaVendas) {
		this.tbodForcaVendas = tbodForcaVendas;
	}

	public TbodForcaVenda addTbodForcaVenda(TbodForcaVenda tbodForcaVenda) {
		getTbodForcaVendas().add(tbodForcaVenda);
		tbodForcaVenda.setTbodCorretora(this);

		return tbodForcaVenda;
	}

	public TbodForcaVenda removeTbodForcaVenda(TbodForcaVenda tbodForcaVenda) {
		getTbodForcaVendas().remove(tbodForcaVenda);
		tbodForcaVenda.setTbodCorretora(null);

		return tbodForcaVenda;
	}

	public String getCnae() {
		return cnae;
	}

	public void setCnae(String cnae) {
		this.cnae = cnae;
	}

	public TbodLogin getTbodLogin() {
		return this.tbodLogin;
	}

	public void setTbodLogin(TbodLogin tbodLogin) {
		this.tbodLogin = tbodLogin;
	}

	public String getTemSusep() {
		return temSusep;
	}

	public void setTemSusep(String temSusep) {
		this.temSusep = temSusep;
	}

	public String getCodigoSusep() {
		return codigoSusep;
	}

	public void setCodigoSusep(String codigoSusep) {
		this.codigoSusep = codigoSusep;
	}

	public List<TbodContratoCorretora> getTbodContratoCorretoras() {
		return tbodContratoCorretoras;
	}

	public void setTbodContratoCorretoras(List<TbodContratoCorretora> tbodContratoCorretoras) {
		this.tbodContratoCorretoras = tbodContratoCorretoras;
	}

	@Override
	public String toString() {
		return "TbodCorretora [" 
				+ "cdCorretora=" + cdCorretora 
				+ ", ativo=" + ativo 
				+ ", celular=" + celular 
				+ ", cnpj=" + cnpj 
				+ ", codigo=" + codigo 
				+ ", cpfResponsavel=" + cpfResponsavel 
				+ ", cpfResponsavel2=" + cpfResponsavel2 
				+ ", dataAbertura=" + dataAbertura 
				+ ", email=" + email 
				+ ", nome=" + nome
				+ ", nomeRepresentanteLegal1=" + nomeRepresentanteLegal1 
				+ ", nomeRepresentanteLegal2=" + nomeRepresentanteLegal2 
				+ ", razaoSocial=" + razaoSocial 
				+ ", regional=" + regional
				+ ", simplesNacional=" + simplesNacional 
				+ ", statusCnpj=" + statusCnpj 
				+ ", telefone=" + telefone
				+ ", cnae=" + cnae 
				+ ", tbodEndereco=" + tbodEndereco 
				+ ", tbodCorretoraBancos=" + tbodCorretoraBancos
				+ ", tbodDocumentoAssociados=" + tbodDocumentoAssociados 
				//+ ", tbodForcaVendas=" + tbodForcaVendas //201809131236 - esert - exc para aliviar o retorno
				+ ", tbodLogin=" + tbodLogin 
				+ ", temSusep=" + temSusep 
				+ ", codigoSusep=" + codigoSusep 
				+ ", tbodContratoCorretoras=" + tbodContratoCorretoras //201809112126 - esert 
				+ "]";
	}

	// public List<TbodLogin> getTbodLogins() {
	// return tbodLogins;
	// }
	//
	// public void setTbodLogins(List<TbodLogin> tbodLogins) {
	// this.tbodLogins = tbodLogins;
	// }
	//
	// public TbodLogin addTbodLogin(TbodLogin tbodLogin) {
	// getTbodLogins().add(tbodLogin);
	// tbodLogin.setTbodCorretora(this);
	//
	// return tbodLogin;
	// }
	//
	// public TbodLogin removeTbodLogin(TbodLogin tbodLogin) {
	// getTbodLogins().remove(tbodLogin);
	// tbodLogin.setTbodCorretora(null);
	//
	// return tbodLogin;
	// }

}
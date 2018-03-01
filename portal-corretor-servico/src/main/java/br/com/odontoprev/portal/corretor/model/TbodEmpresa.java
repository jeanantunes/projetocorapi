package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the TBOD_EMPRESA database table.
 * 
 */
@Entity
@Table(name = "TBOD_EMPRESA")
@NamedQuery(name = "TbodEmpresa.findAll", query = "SELECT t FROM TbodEmpresa t")
public class TbodEmpresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_EMPRESA", sequenceName = "SEQ_TBOD_EMPRESA", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_EMPRESA")
	@Column(name = "CD_EMPRESA")
	private Long cdEmpresa;

	@Column(name = "CELULAR")
	private String celular;

	@Column(name = "CNPJ")
	private String cnpj;

	@Column(name = "CONTATO_EMPRESA")
	private String contatoEmpresa;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "INC_ESTADUAL")
	private String incEstadual;

	@Column(name = "NOME_FANTASIA")
	private String nomeFantasia;

	@Column(name = "RAMO_ATIVIDADE")
	private String ramoAtividade;

	@Column(name = "RAZAO_SOCIAL")
	private String razaoSocial;

	@Column(name = "REPRESENTANTE_LEGAL")
	private String representanteLegal;

	@Column(name = "TELEFONE")
	private String telefone;

	@Column(name = "EMP_DCMS")
	private String empDcms;

	@Column(name = "CNAE")
	private String cnae;

	// bi-directional many-to-one association to TbodEndereco
	@ManyToOne
	@JoinColumn(name = "CD_ENDERECO")
	private TbodEndereco tbodEndereco;

	// bi-directional many-to-one association to TbodVenda
	@OneToMany(mappedBy = "tbodEmpresa")
	private List<TbodVenda> tbodVendas;

	public TbodEmpresa() {
	}

	public Long getCdEmpresa() {
		return this.cdEmpresa;
	}

	public void setCdEmpresa(Long cdEmpresa) {
		this.cdEmpresa = cdEmpresa;
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

	public String getContatoEmpresa() {
		return this.contatoEmpresa;
	}

	public void setContatoEmpresa(String contatoEmpresa) {
		this.contatoEmpresa = contatoEmpresa;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIncEstadual() {
		return this.incEstadual;
	}

	public void setIncEstadual(String incEstadual) {
		this.incEstadual = incEstadual;
	}

	public String getNomeFantasia() {
		return this.nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRamoAtividade() {
		return this.ramoAtividade;
	}

	public void setRamoAtividade(String ramoAtividade) {
		this.ramoAtividade = ramoAtividade;
	}

	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getRepresentanteLegal() {
		return this.representanteLegal;
	}

	public void setRepresentanteLegal(String representanteLegal) {
		this.representanteLegal = representanteLegal;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmpDcms() {
		return empDcms;
	}

	public void setEmpDcms(String empDcms) {
		this.empDcms = empDcms;
	}

	public String getCnae() {
		return cnae;
	}

	public void setCnae(String cnae) {
		this.cnae = cnae;
	}

	public TbodEndereco getTbodEndereco() {
		return this.tbodEndereco;
	}

	public void setTbodEndereco(TbodEndereco tbodEndereco) {
		this.tbodEndereco = tbodEndereco;
	}

	public List<TbodVenda> getTbodVendas() {
		return this.tbodVendas;
	}

	public void setTbodVendas(List<TbodVenda> tbodVendas) {
		this.tbodVendas = tbodVendas;
	}

	public TbodVenda addTbodVenda(TbodVenda tbodVenda) {
		getTbodVendas().add(tbodVenda);
		tbodVenda.setTbodEmpresa(this);

		return tbodVenda;
	}

	public TbodVenda removeTbodVenda(TbodVenda tbodVenda) {
		getTbodVendas().remove(tbodVenda);
		tbodVenda.setTbodEmpresa(null);

		return tbodVenda;
	}

}
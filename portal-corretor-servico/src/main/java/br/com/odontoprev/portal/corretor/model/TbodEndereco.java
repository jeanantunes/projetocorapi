package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TBOD_ENDERECO database table.
 * 
 */
@Entity
@Table(name="TBOD_ENDERECO")
@NamedQuery(name="TbodEndereco.findAll", query="SELECT t FROM TbodEndereco t")
public class TbodEndereco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_ENDERECO ", sequenceName = "SEQ_TBOD_ENDERECO ",  allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_ENDERECO ")
	@Column(name="CD_ENDERECO")
	private Long cdEndereco;

	@Column(name="BAIRRO")
	private String bairro;

	@Column(name="CEP")
	private String cep;

	@Column(name="CIDADE")
	private String cidade;

	@Column(name="COMPLEMENTO")
	private String complemento;

	@Column(name="LOGRADOURO")
	private String logradouro;

	@Column(name="NUMERO")
	private String numero;

	@Column(name="UF")
	private String uf;

	//bi-directional many-to-one association to TbodCorretora
	@OneToMany(mappedBy="tbodEndereco")
	private List<TbodCorretora> tbodCorretoras;

	//bi-directional many-to-one association to TbodEmpresa
	@OneToMany(mappedBy="tbodEndereco")
	private List<TbodEmpresa> tbodEmpresas;

	//bi-directional many-to-one association to TbodTipoEndereco
	@ManyToOne
	@JoinColumn(name="CD_TIPO_ENDERECO")
	private TbodTipoEndereco tbodTipoEndereco;

	//bi-directional many-to-one association to TbodVida
	@OneToMany(mappedBy="tbodEndereco")
	private List<TbodVida> tbodVidas;

	public TbodEndereco() {
	}

	public Long getCdEndereco() {
		return this.cdEndereco;
	}

	public void setCdEndereco(Long cdEndereco) {
		this.cdEndereco = cdEndereco;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public List<TbodCorretora> getTbodCorretoras() {
		return this.tbodCorretoras;
	}

	public void setTbodCorretoras(List<TbodCorretora> tbodCorretoras) {
		this.tbodCorretoras = tbodCorretoras;
	}

	public TbodCorretora addTbodCorretora(TbodCorretora tbodCorretora) {
		getTbodCorretoras().add(tbodCorretora);
		tbodCorretora.setTbodEndereco(this);

		return tbodCorretora;
	}

	public TbodCorretora removeTbodCorretora(TbodCorretora tbodCorretora) {
		getTbodCorretoras().remove(tbodCorretora);
		tbodCorretora.setTbodEndereco(null);

		return tbodCorretora;
	}

	public List<TbodEmpresa> getTbodEmpresas() {
		return this.tbodEmpresas;
	}

	public void setTbodEmpresas(List<TbodEmpresa> tbodEmpresas) {
		this.tbodEmpresas = tbodEmpresas;
	}

	public TbodEmpresa addTbodEmpresa(TbodEmpresa tbodEmpresa) {
		getTbodEmpresas().add(tbodEmpresa);
		tbodEmpresa.setTbodEndereco(this);

		return tbodEmpresa;
	}

	public TbodEmpresa removeTbodEmpresa(TbodEmpresa tbodEmpresa) {
		getTbodEmpresas().remove(tbodEmpresa);
		tbodEmpresa.setTbodEndereco(null);

		return tbodEmpresa;
	}

	public TbodTipoEndereco getTbodTipoEndereco() {
		return this.tbodTipoEndereco;
	}

	public void setTbodTipoEndereco(TbodTipoEndereco tbodTipoEndereco) {
		this.tbodTipoEndereco = tbodTipoEndereco;
	}

	public List<TbodVida> getTbodVidas() {
		return this.tbodVidas;
	}

	public void setTbodVidas(List<TbodVida> tbodVidas) {
		this.tbodVidas = tbodVidas;
	}

	public TbodVida addTbodVida(TbodVida tbodVida) {
		getTbodVidas().add(tbodVida);
		tbodVida.setTbodEndereco(this);

		return tbodVida;
	}

	public TbodVida removeTbodVida(TbodVida tbodVida) {
		getTbodVidas().remove(tbodVida);
		tbodVida.setTbodEndereco(null);

		return tbodVida;
	}

}
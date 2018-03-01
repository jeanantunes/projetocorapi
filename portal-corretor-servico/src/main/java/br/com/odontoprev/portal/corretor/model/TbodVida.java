package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the TBOD_VIDA database table.
 * 
 */
@Entity
@Table(name = "TBOD_VIDA")
@NamedQuery(name = "TbodVida.findAll", query = "SELECT t FROM TbodVida t")
public class TbodVida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_VIDA", sequenceName = "SEQ_TBOD_VIDA", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_VIDA")
	@Column(name = "CD_VIDA")
	private Long cdVida;

	@Column(name = "CELULAR")
	private String celular;

	@Column(name = "CPF")
	private String cpf;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_NASCIMENTO")
	private Date dataNascimento;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "NOME_MAE")
	private String nomeMae;

	@Column(name = "PF_PJ")
	private String pfPj;

	@Column(name = "SEXO")
	private String sexo;

	@Transient
	private String siglaPlano;

	// bi-directional many-to-one association to TbodDocumentoAssociado
	@OneToMany(mappedBy = "tbodVida")
	private List<TbodDocumentoAssociado> tbodDocumentoAssociados;

	// bi-directional many-to-one association to TbodPlanoVida
	@OneToMany(mappedBy = "tbodVida")
	private List<TbodPlanoVida> tbodPlanoVidas;

	// bi-directional many-to-one association to TbodVendaVida
	@OneToMany(mappedBy = "tbodVida")
	private List<TbodVendaVida> tbodVendaVidas;

	// bi-directional many-to-one association to TbodEndereco
	@ManyToOne
	@JoinColumn(name = "CD_ENDERECO")
	private TbodEndereco tbodEndereco;

	// bi-directional many-to-one association to TbodVida
	@ManyToOne
	@JoinColumn(name = "CD_TITULAR")
	private TbodVida tbodVida;

	// bi-directional many-to-one association to TbodVida
	@OneToMany(mappedBy = "tbodVida")
	private List<TbodVida> tbodVidas;

	public TbodVida() {
	}

	public Long getCdVida() {
		return cdVida;
	}

	public void setCdVida(Long cdVida) {
		this.cdVida = cdVida;
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

	public Date getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
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

	public String getNomeMae() {
		return this.nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getPfPj() {
		return this.pfPj;
	}

	public void setPfPj(String pfPj) {
		this.pfPj = pfPj;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getSiglaPlano() {
		return siglaPlano;
	}

	public void setSiglaPlano(String siglaPlano) {
		this.siglaPlano = siglaPlano;
	}

	public List<TbodDocumentoAssociado> getTbodDocumentoAssociados() {
		return this.tbodDocumentoAssociados;
	}

	public void setTbodDocumentoAssociados(List<TbodDocumentoAssociado> tbodDocumentoAssociados) {
		this.tbodDocumentoAssociados = tbodDocumentoAssociados;
	}

	public TbodDocumentoAssociado addTbodDocumentoAssociado(TbodDocumentoAssociado tbodDocumentoAssociado) {
		getTbodDocumentoAssociados().add(tbodDocumentoAssociado);
		tbodDocumentoAssociado.setTbodVida(this);

		return tbodDocumentoAssociado;
	}

	public TbodDocumentoAssociado removeTbodDocumentoAssociado(TbodDocumentoAssociado tbodDocumentoAssociado) {
		getTbodDocumentoAssociados().remove(tbodDocumentoAssociado);
		tbodDocumentoAssociado.setTbodVida(null);

		return tbodDocumentoAssociado;
	}

	public List<TbodPlanoVida> getTbodPlanoVidas() {
		return this.tbodPlanoVidas;
	}

	public void setTbodPlanoVidas(List<TbodPlanoVida> tbodPlanoVidas) {
		this.tbodPlanoVidas = tbodPlanoVidas;
	}

	public TbodPlanoVida addTbodPlanoVida(TbodPlanoVida tbodPlanoVida) {
		getTbodPlanoVidas().add(tbodPlanoVida);
		tbodPlanoVida.setTbodVida(this);

		return tbodPlanoVida;
	}

	public TbodPlanoVida removeTbodPlanoVida(TbodPlanoVida tbodPlanoVida) {
		getTbodPlanoVidas().remove(tbodPlanoVida);
		tbodPlanoVida.setTbodVida(null);

		return tbodPlanoVida;
	}

	public List<TbodVendaVida> getTbodVendaVidas() {
		return this.tbodVendaVidas;
	}

	public void setTbodVendaVidas(List<TbodVendaVida> tbodVendaVidas) {
		this.tbodVendaVidas = tbodVendaVidas;
	}

	public TbodVendaVida addTbodVendaVida(TbodVendaVida tbodVendaVida) {
		getTbodVendaVidas().add(tbodVendaVida);
		tbodVendaVida.setTbodVida(this);

		return tbodVendaVida;
	}

	public TbodVendaVida removeTbodVendaVida(TbodVendaVida tbodVendaVida) {
		getTbodVendaVidas().remove(tbodVendaVida);
		tbodVendaVida.setTbodVida(null);

		return tbodVendaVida;
	}

	public TbodEndereco getTbodEndereco() {
		return this.tbodEndereco;
	}

	public void setTbodEndereco(TbodEndereco tbodEndereco) {
		this.tbodEndereco = tbodEndereco;
	}

	public TbodVida getTbodVida() {
		return this.tbodVida;
	}

	public void setTbodVida(TbodVida tbodVida) {
		this.tbodVida = tbodVida;
	}

	public List<TbodVida> getTbodVidas() {
		return this.tbodVidas;
	}

	public void setTbodVidas(List<TbodVida> tbodVidas) {
		this.tbodVidas = tbodVidas;
	}

	public TbodVida addTbodVida(TbodVida tbodVida) {
		getTbodVidas().add(tbodVida);
		tbodVida.setTbodVida(this);

		return tbodVida;
	}

	public TbodVida removeTbodVida(TbodVida tbodVida) {
		getTbodVidas().remove(tbodVida);
		tbodVida.setTbodVida(null);

		return tbodVida;
	}

}
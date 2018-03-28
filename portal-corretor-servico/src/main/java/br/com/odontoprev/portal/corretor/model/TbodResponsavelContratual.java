package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the TBOD_RESPONSAVEL_CONTRATUAL database table.
 * 
 */
@Entity
@Table(name="TBOD_RESPONSAVEL_CONTRATUAL")
@NamedQuery(name="TbodResponsavelContratual.findAll", query="SELECT t FROM TbodResponsavelContratual t")
public class TbodResponsavelContratual implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_RESP_CONTR", sequenceName = "SEQ_TBOD_RESP_CONTR", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_RESP_CONTR")
	@Column(name="CD_RESPONSAVEL_CONTRATUAL")
	private Long cdResponsavelContratual;

	private String celular;

	private String cpf;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_NASCIMENTO")
	private Date dataNascimento;

	private String email;

	private String nome;

	private String sexo;

	//bi-directional many-to-one association to TbodEndereco
	@ManyToOne
	@JoinColumn(name="CD_ENDERECO")
	private TbodEndereco tbodEndereco;

	//bi-directional many-to-one association to TbodVenda
	@OneToMany(mappedBy="tbodResponsavelContratual")
	private List<TbodVenda> tbodVendas;

	public TbodResponsavelContratual() {
	}

	public Long getCdResponsavelContratual() {
		return this.cdResponsavelContratual;
	}

	public void setCdResponsavelContratual(Long cdResponsavelContratual) {
		this.cdResponsavelContratual = cdResponsavelContratual;
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

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
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
		tbodVenda.setTbodResponsavelContratual(this);

		return tbodVenda;
	}

	public TbodVenda removeTbodVenda(TbodVenda tbodVenda) {
		getTbodVendas().remove(tbodVenda);
		tbodVenda.setTbodResponsavelContratual(null);

		return tbodVenda;
	}

}
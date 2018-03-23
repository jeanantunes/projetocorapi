package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the TBOD_SERASA_ENDERECO database table.
 * 
 */
@Entity
@Table(name="TBOD_SERASA_ENDERECO")
@NamedQuery(name="TbodSerasaEndereco.findAll", query="SELECT t FROM TbodSerasaEndereco t")
public class TbodSerasaEndereco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_SERASA_ENDERECO", sequenceName = "SEQ_TBOD_SERASA_ENDERECO", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_SERASA_ENDERECO")
	private long id;

	private String bairro;

	private BigDecimal cep;

	@Column(name="CEP_NOTA")
	private BigDecimal cepNota;

	private String cidade;

	private String logradouro;

	private String numero;

	private String tipo;

	private String uf;

	//bi-directional many-to-one association to TbodSerasaInfo
	@ManyToOne
	@JoinColumn(name="CNPJ")
	private TbodSerasaInfo tbodSerasaInfo;

	public TbodSerasaEndereco() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public BigDecimal getCep() {
		return this.cep;
	}

	public void setCep(BigDecimal cep) {
		this.cep = cep;
	}

	public BigDecimal getCepNota() {
		return this.cepNota;
	}

	public void setCepNota(BigDecimal cepNota) {
		this.cepNota = cepNota;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
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

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public TbodSerasaInfo getTbodSerasaInfo() {
		return this.tbodSerasaInfo;
	}

	public void setTbodSerasaInfo(TbodSerasaInfo tbodSerasaInfo) {
		this.tbodSerasaInfo = tbodSerasaInfo;
	}

}
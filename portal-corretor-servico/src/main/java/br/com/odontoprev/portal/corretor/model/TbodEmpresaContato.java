package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TBOD_EMPRESA_CONTATO database table.
 * 
 */
@Entity
@Table(name="TBOD_EMPRESA_CONTATO")
@NamedQuery(name="TbodEmpresaContato.findAll", query="SELECT t FROM TbodEmpresaContato t")
public class TbodEmpresaContato implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_TBOD_EMPRESA_CONTATO ", sequenceName = "SEQ_TBOD_EMPRESA_CONTATO ",  allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_EMPRESA_CONTATO ")
	@Column(name="CD_CONTATO")
	private long cdContato;

	private String celular;

	private String email;

	private String nome;

	private String telefone;

	//bi-directional many-to-one association to TbodEmpresa
	/*@OneToMany(mappedBy="tbodEmpresaContato")
	private List<TbodEmpresa> tbodEmpresas;*/

	public TbodEmpresaContato() {
	}

	public long getCdContato() {
		return this.cdContato;
	}

	public void setCdContato(long cdContato) {
		this.cdContato = cdContato;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
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

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/*public List<TbodEmpresa> getTbodEmpresas() {
		return this.tbodEmpresas;
	}

	public void setTbodEmpresas(List<TbodEmpresa> tbodEmpresas) {
		this.tbodEmpresas = tbodEmpresas;
	}*/

	/*public TbodEmpresa addTbodEmpresa(TbodEmpresa tbodEmpresa) {
		getTbodEmpresas().add(tbodEmpresa);
		tbodEmpresa.setTbodEmpresaContato(this);

		return tbodEmpresa;
	}*/

	/*public TbodEmpresa removeTbodEmpresa(TbodEmpresa tbodEmpresa) {
		getTbodEmpresas().remove(tbodEmpresa);
		tbodEmpresa.setTbodEmpresaContato(null);

		return tbodEmpresa;
	}*/

}
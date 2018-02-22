package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TBOD_CORRETORA_BANCO database table.
 * 
 */
@Entity
@Table(name="TBOD_CORRETORA_BANCO")
@NamedQuery(name="TbodCorretoraBanco.findAll", query="SELECT t FROM TbodCorretoraBanco t")
public class TbodCorretoraBanco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_CORRETORA_BANCO")
	@SequenceGenerator(name = "SEQ_TBOD_CORRETORA_BANCO", sequenceName = "SEQ_TBOD_CORRETORA_BANCO",  allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_CORRETORA_BANCO")
	private long cdCorretoraBanco;

	@Column(name="CNAE")
	private String cnae;

	@Column(name="CPF_RESPONSAVEL_LEGAL1")
	private String cpfResponsavelLegal1;

	@Column(name="CPF_RESPONSAVEL_LEGAL2")
	private String cpfResponsavelLegal2;

	//bi-directional many-to-one association to TbodBancoConta
	@ManyToOne
	@JoinColumn(name="CD_BANCO_CONTA")
	private TbodBancoConta tbodBancoConta;

	//bi-directional many-to-one association to TbodCorretora
	@ManyToOne
	@JoinColumn(name="CD_CORRETORA")
	private TbodCorretora tbodCorretora;

	public TbodCorretoraBanco() {
	}

	public long getCdCorretoraBanco() {
		return this.cdCorretoraBanco;
	}

	public void setCdCorretoraBanco(long cdCorretoraBanco) {
		this.cdCorretoraBanco = cdCorretoraBanco;
	}

	public String getCnae() {
		return this.cnae;
	}

	public void setCnae(String cnae) {
		this.cnae = cnae;
	}

	public String getCpfResponsavelLegal1() {
		return this.cpfResponsavelLegal1;
	}

	public void setCpfResponsavelLegal1(String cpfResponsavelLegal1) {
		this.cpfResponsavelLegal1 = cpfResponsavelLegal1;
	}

	public String getCpfResponsavelLegal2() {
		return this.cpfResponsavelLegal2;
	}

	public void setCpfResponsavelLegal2(String cpfResponsavelLegal2) {
		this.cpfResponsavelLegal2 = cpfResponsavelLegal2;
	}

	public TbodBancoConta getTbodBancoConta() {
		return this.tbodBancoConta;
	}

	public void setTbodBancoConta(TbodBancoConta tbodBancoConta) {
		this.tbodBancoConta = tbodBancoConta;
	}

	public TbodCorretora getTbodCorretora() {
		return this.tbodCorretora;
	}

	public void setTbodCorretora(TbodCorretora tbodCorretora) {
		this.tbodCorretora = tbodCorretora;
	}

}
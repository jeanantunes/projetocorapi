package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the TBOD_BANCO_CONTA database table.
 * 
 */
@Entity
@Table(name="TBOD_BANCO_CONTA")
@NamedQuery(name="TbodBancoConta.findAll", query="SELECT t FROM TbodBancoConta t")
public class TbodBancoConta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_BANCO_CONTA", sequenceName = "SEQ_TBOD_BANCO_CONTA",  allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_BANCO_CONTA")
	@Column(name="CD_BANCO_CONTA")
	private long cdBancoConta;

	@Column(name="AGENCIA")
	private String agencia;

	@Column(name="CODIGO_BANCO")
	private BigDecimal codigoBanco;

	@Column(name="CONTA")
	private String conta;

	//bi-directional many-to-one association to TbodCorretoraBanco
	@OneToMany(mappedBy="tbodBancoConta")
	private List<TbodCorretoraBanco> tbodCorretoraBancos;

	public TbodBancoConta() {
	}

	public long getCdBancoConta() {
		return this.cdBancoConta;
	}

	public void setCdBancoConta(long cdBancoConta) {
		this.cdBancoConta = cdBancoConta;
	}

	public String getAgencia() {
		return this.agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public BigDecimal getCodigoBanco() {
		return this.codigoBanco;
	}

	public void setCodigoBanco(BigDecimal codigoBanco) {
		this.codigoBanco = codigoBanco;
	}

	public String getConta() {
		return this.conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public List<TbodCorretoraBanco> getTbodCorretoraBancos() {
		return this.tbodCorretoraBancos;
	}

	public void setTbodCorretoraBancos(List<TbodCorretoraBanco> tbodCorretoraBancos) {
		this.tbodCorretoraBancos = tbodCorretoraBancos;
	}

	public TbodCorretoraBanco addTbodCorretoraBanco(TbodCorretoraBanco tbodCorretoraBanco) {
		getTbodCorretoraBancos().add(tbodCorretoraBanco);
		tbodCorretoraBanco.setTbodBancoConta(this);

		return tbodCorretoraBanco;
	}

	public TbodCorretoraBanco removeTbodCorretoraBanco(TbodCorretoraBanco tbodCorretoraBanco) {
		getTbodCorretoraBancos().remove(tbodCorretoraBanco);
		tbodCorretoraBanco.setTbodBancoConta(null);

		return tbodCorretoraBanco;
	}

}
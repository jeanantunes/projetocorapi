package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

/*201810221532 - esert - COR-932:API - Novo GET /planoinfo*/
@Entity
@Table(name = "TBOD_PLANO_INFO")
public class TbodPlanoInfo implements Serializable {
	private static final long serialVersionUID = 1L;

//"CD_PLANO_INFO" NUMBER(10) NOT NULL, -- PK será referenciada pela TBOD_PLANO.
//"NOME_PLANO_INFO" VARCHAR(255) NOT NULL,
//"DESCRICAO" VARCHAR(2000) NOT NULL, -- \n para quebra de linha
//"CD_ARQUIVO_GERAL" NUMBER(10) NULL, -- FK TBOD_ARQUIVO.CD_ARQUIVO
//"CD_ARQUIVO_CARENCIA" NUMBER(10) NULL, -- FK TBOD_ARQUIVO.CD_ARQUIVO
//"CD_ARQUIVO_ICONE" NUMBER(10) NULL, -- FK TBOD_ARQUIVO.CD_ARQUIVO
//"TIPO_SEGMENTO" VARCHAR(3) NOT NULL,
//"ATIVO" VARCHAR(1) NOT NULL
	
	@Id
	@Column(name = "CD_PLANO_INFO")
	@SequenceGenerator(name = "SEQ_TBOD_PLANO_INFO", sequenceName = "SEQ_TBOD_PLANO_INFO", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_PLANO_INFO")
	private long cdPlanoInfo;

	@Column(name = "ATIVO")
	private String ativo; //201803031736 trocado de BigInteger para String seguindo padrao outras tabelas Corretora e ForcaVenda (esertorio@vector)(moliveira@odpv)

	@Column(name = "CODIGO")
	private String codigo;

	@Column(name = "NOME_PLANO")
	private String nomePlano;

	@Column(name = "TITULO")
	private String titulo;

	@Column(name = "VALOR_ANUAL")
	private BigDecimal valorAnual;

	@Column(name = "VALOR_MENSAL")
	private BigDecimal valorMensal;

	@Column(name = "SIGLA")
	private String sigla;

	// bi-directional many-to-one association to TbodTipoPlano
	@ManyToOne
	@JoinColumn(name = "CD_TIPO_PLANO")
	private TbodTipoPlano tbodTipoPlano;

	// bi-directional many-to-one association to TbodPlanoCobertura
	@OneToMany(mappedBy = "tbodPlano")
	private List<TbodPlanoCobertura> tbodPlanoCoberturas;

	// bi-directional many-to-one association to TbodVenda
	@OneToMany(mappedBy = "tbodPlano")
	private List<TbodVenda> tbodVendas;

	public TbodPlanoInfo() {
	}

	public long getCdPlano() {
		return this.cdPlano;
	}

	public void setCdPlano(long cdPlano) {
		this.cdPlano = cdPlano;
	}

	public String getAtivo() {
		return this.ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNomePlano() {
		return this.nomePlano;
	}

	public void setNomePlano(String nomePlano) {
		this.nomePlano = nomePlano;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public BigDecimal getValorAnual() {
		return this.valorAnual;
	}

	public void setValorAnual(BigDecimal valorAnual) {
		this.valorAnual = valorAnual;
	}

	public BigDecimal getValorMensal() {
		return this.valorMensal;
	}

	public void setValorMensal(BigDecimal valorMensal) {
		this.valorMensal = valorMensal;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public TbodTipoPlano getTbodTipoPlano() {
		return this.tbodTipoPlano;
	}

	public void setTbodTipoPlano(TbodTipoPlano tbodTipoPlano) {
		this.tbodTipoPlano = tbodTipoPlano;
	}

	public List<TbodPlanoCobertura> getTbodPlanoCoberturas() {
		return this.tbodPlanoCoberturas;
	}

	public void setTbodPlanoCoberturas(List<TbodPlanoCobertura> tbodPlanoCoberturas) {
		this.tbodPlanoCoberturas = tbodPlanoCoberturas;
	}

	public TbodPlanoCobertura addTbodPlanoCobertura(TbodPlanoCobertura tbodPlanoCobertura) {
		getTbodPlanoCoberturas().add(tbodPlanoCobertura);
		tbodPlanoCobertura.setTbodPlano(this);

		return tbodPlanoCobertura;
	}

	public TbodPlanoCobertura removeTbodPlanoCobertura(TbodPlanoCobertura tbodPlanoCobertura) {
		getTbodPlanoCoberturas().remove(tbodPlanoCobertura);
		tbodPlanoCobertura.setTbodPlano(null);

		return tbodPlanoCobertura;
	}

	public List<TbodVenda> getTbodVendas() {
		return this.tbodVendas;
	}

	public void setTbodVendas(List<TbodVenda> tbodVendas) {
		this.tbodVendas = tbodVendas;
	}

	public TbodVenda addTbodVenda(TbodVenda tbodVenda) {
		getTbodVendas().add(tbodVenda);
		tbodVenda.setTbodPlano(this);

		return tbodVenda;
	}

	public TbodVenda removeTbodVenda(TbodVenda tbodVenda) {
		getTbodVendas().remove(tbodVenda);
		tbodVenda.setTbodPlano(null);

		return tbodVenda;
	}

}
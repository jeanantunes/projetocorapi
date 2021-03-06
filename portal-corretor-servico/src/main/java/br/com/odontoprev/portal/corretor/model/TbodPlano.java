package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the TBOD_PLANO database table.
 * 
 */
//201810221552 - esert - COR-932:API - Novo GET /planoinfo - incluido CD_PLANO_INFO tbodPlanoInfo
@Entity
@Table(name = "TBOD_PLANO")
@NamedQuery(name = "TbodPlano.findAll", query = "SELECT t FROM TbodPlano t")
public class TbodPlano implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CD_PLANO")
	private long cdPlano;

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

	// bi-directional many-to-one association to TbodTipoPlano
	@ManyToOne
	@JoinColumn(name = "CD_PLANO_INFO") //201810221552 - esert - COR-932:API - Novo GET /planoinfo
	private TbodPlanoInfo tbodPlanoInfo;

	public TbodPlano() {
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

	public TbodPlanoInfo getTbodPlanoInfo() {
		return tbodPlanoInfo;
	}

	public void setTbodPlanoInfo(TbodPlanoInfo tbodPlanoInfo) {
		this.tbodPlanoInfo = tbodPlanoInfo;
	}

	@Override
	public String toString() {
		return "TbodPlano [" 
				+ "cdPlano=" + cdPlano 
				+ ", ativo=" + ativo 
				+ ", codigo=" + codigo 
				+ ", nomePlano=" + nomePlano
				+ ", titulo=" + titulo 
				+ ", valorAnual=" + valorAnual 
				+ ", valorMensal=" + valorMensal 
				+ ", sigla=" + sigla 
				+ ", tbodTipoPlano=" + tbodTipoPlano 
				+ ", tbodPlanoCoberturas=" + tbodPlanoCoberturas
				+ ", tbodVendas=" + tbodVendas 
				+ ", tbodPlanoInfo=" + tbodPlanoInfo 
				+ "]";
	}

}
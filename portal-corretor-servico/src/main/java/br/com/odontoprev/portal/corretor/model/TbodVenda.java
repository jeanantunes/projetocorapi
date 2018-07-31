package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the TBOD_VENDA database table.
 * 
 */
@Entity
@Table(name = "TBOD_VENDA")
@NamedQuery(name = "TbodVenda.findAll", query = "SELECT t FROM TbodVenda t")
public class TbodVenda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TBOD_VENDA", sequenceName = "SEQ_TBOD_VENDA", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBOD_VENDA")
	@Column(name = "CD_VENDA")
	private Long cdVenda;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_VENDA")
	private Date dtVenda;

	@Column(name = "FATURA_VENCIMENTO")
	private Long faturaVencimento;

	@Column(name = "TIPO_CONTA")
	private String tipoConta;

	@Column(name = "BANCO")
	private String banco;

	@Column(name = "AGENCIA")
	private String agencia;

	@Column(name = "AGENCIA_DV")
	private String agenciaDv;

	@Column(name = "CONTA")
	private String conta;

	@Column(name = "CONTA_DV")
	private String contaDv;

	@Column(name = "TIPO_PAGAMENTO")
	private String tipoPagamento;
	
	@Column(name = "PROPOSTA_DCMS")
	private String propostaDcms;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_VIGENCIA")
	private Date dtVigencia; //201806141529 - esert - [COR-301 alter table TBOD_VENDA]  

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_MOVIMENTACAO")
	private Date dtMovimentacao; //201806141529 - esert - [COR-301 alter table TBOD_VENDA]
	
	@Column(name = "PLATAFORMA")
	private String plataforma; //201807201122 - esert - COR-431
	
	// bi-directional many-to-one association to TbodEmpresa
	@ManyToOne
	@JoinColumn(name = "CD_EMPRESA")
	private TbodEmpresa tbodEmpresa;

	// bi-directional many-to-one association to TbodForcaVenda
	@ManyToOne
	@JoinColumn(name = "CD_FORCA_VENDAS")
	private TbodForcaVenda tbodForcaVenda;

	// bi-directional many-to-one association to TbodPlano
	@ManyToOne
	@JoinColumn(name = "CD_PLANO")
	private TbodPlano tbodPlano;

	// bi-directional many-to-one association to TbodStatusVenda
	@ManyToOne
	@JoinColumn(name = "CD_STATUS_VENDA")
	private TbodStatusVenda tbodStatusVenda;

	// bi-directional many-to-one association to TbodVendaVida
	@ManyToOne
	@JoinColumn(name = "CD_VENDA_VIDA")
	private TbodVendaVida tbodVendaVida;

	// bi-directional many-to-one association to TbodVendaVida
	@OneToMany(mappedBy = "tbodVenda")
	private List<TbodVendaVida> tbodVendaVidas;
	
	//bi-directional many-to-one association to TbodTokenAceite
	@OneToMany(mappedBy="tbodVenda")
	private List<TbodTokenAceite> tbodTokenAceites;
	
	//bi-directional many-to-one association to TbodResponsavelContratual
	@ManyToOne
	@JoinColumn(name="CD_RESPONSAVEL_CONTRATUAL")
	private TbodResponsavelContratual tbodResponsavelContratual;
	
	// bi-directional many-to-one association to TbodEmpresa
	@ManyToOne
	@JoinColumn(name = "CD_CORRETORA")
	private TbodCorretora tbodCorretora; //201807311613 - esert - COR-468:Atrelar Venda com a Corretora

	public TbodVenda() {
	}

	public Long getCdVenda() {
		return this.cdVenda;
	}

	public void setCdVenda(Long cdVenda) {
		this.cdVenda = cdVenda;
	}

	public Date getDtVenda() {
		return this.dtVenda;
	}

	public void setDtVenda(Date dtVenda) {
		this.dtVenda = dtVenda;
	}

	public Long getFaturaVencimento() {
		return faturaVencimento;
	}

	public void setFaturaVencimento(Long faturaVencimento) {
		this.faturaVencimento = faturaVencimento;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getAgenciaDv() {
		return agenciaDv;
	}

	public void setAgenciaDv(String agenciaDv) {
		this.agenciaDv = agenciaDv;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getContaDv() {
		return contaDv;
	}

	public void setContaDv(String contaDv) {
		this.contaDv = contaDv;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
	
	public String getPropostaDcms() {
		return propostaDcms;
	}

	public void setPropostaDcms(String propostaDcms) {
		this.propostaDcms = propostaDcms;
	}

	public TbodEmpresa getTbodEmpresa() {
		return this.tbodEmpresa;
	}

	public void setTbodEmpresa(TbodEmpresa tbodEmpresa) {
		this.tbodEmpresa = tbodEmpresa;
	}

	public TbodForcaVenda getTbodForcaVenda() {
		return this.tbodForcaVenda;
	}

	public void setTbodForcaVenda(TbodForcaVenda tbodForcaVenda) {
		this.tbodForcaVenda = tbodForcaVenda;
	}

	public TbodPlano getTbodPlano() {
		return this.tbodPlano;
	}

	public void setTbodPlano(TbodPlano tbodPlano) {
		this.tbodPlano = tbodPlano;
	}

	public TbodStatusVenda getTbodStatusVenda() {
		return this.tbodStatusVenda;
	}

	public void setTbodStatusVenda(TbodStatusVenda tbodStatusVenda) {
		this.tbodStatusVenda = tbodStatusVenda;
	}

	public TbodVendaVida getTbodVendaVida() {
		return this.tbodVendaVida;
	}

	public void setTbodVendaVida(TbodVendaVida tbodVendaVida) {
		this.tbodVendaVida = tbodVendaVida;
	}

	public List<TbodVendaVida> getTbodVendaVidas() {
		return this.tbodVendaVidas;
	}

	public void setTbodVendaVidas(List<TbodVendaVida> tbodVendaVidas) {
		this.tbodVendaVidas = tbodVendaVidas;
	}

	public TbodVendaVida addTbodVendaVida(TbodVendaVida tbodVendaVida) {
		getTbodVendaVidas().add(tbodVendaVida);
		tbodVendaVida.setTbodVenda(this);

		return tbodVendaVida;
	}

	public TbodVendaVida removeTbodVendaVida(TbodVendaVida tbodVendaVida) {
		getTbodVendaVidas().remove(tbodVendaVida);
		tbodVendaVida.setTbodVenda(null);

		return tbodVendaVida;
	}

	public List<TbodTokenAceite> getTbodTokenAceites() {
		return this.tbodTokenAceites;
	}

	public void setTbodTokenAceites(List<TbodTokenAceite> tbodTokenAceites) {
		this.tbodTokenAceites = tbodTokenAceites;
	}

	public TbodTokenAceite addTbodTokenAceite(TbodTokenAceite tbodTokenAceite) {
		getTbodTokenAceites().add(tbodTokenAceite);
		tbodTokenAceite.setTbodVenda(this);

		return tbodTokenAceite;
	}

	public TbodTokenAceite removeTbodTokenAceite(TbodTokenAceite tbodTokenAceite) {
		getTbodTokenAceites().remove(tbodTokenAceite);
		tbodTokenAceite.setTbodVenda(null);

		return tbodTokenAceite;
	}
	
	public TbodResponsavelContratual getTbodResponsavelContratual() {
		return this.tbodResponsavelContratual;
	}

	public void setTbodResponsavelContratual(TbodResponsavelContratual tbodResponsavelContratual) {
		this.tbodResponsavelContratual = tbodResponsavelContratual;
	}

	public Date getDtVigencia() {
		return dtVigencia;
	}

	public void setDtVigencia(Date dtVigencia) {
		this.dtVigencia = dtVigencia;
	}

	public Date getDtMovimentacao() {
		return dtMovimentacao;
	}

	public void setDtMovimentacao(Date dtMovimentacao) {
		this.dtMovimentacao = dtMovimentacao;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public TbodCorretora getTbodCorretora() {
		return tbodCorretora;
	}

	public void setTbodCorretora(TbodCorretora tbodCorretora) {
		this.tbodCorretora = tbodCorretora;
	}

	//201806141628 - esert
	@Override
	public String toString() {
		return "TbodVenda [" 
				+ "cdVenda=" + cdVenda 
				+ ", dtVenda=" + dtVenda 
				+ ", faturaVencimento=" + faturaVencimento
				+ ", tipoConta=" + tipoConta 
				+ ", banco=" + banco 
				+ ", agencia=" + agencia 
				+ ", agenciaDv=" + agenciaDv
				+ ", conta=" + conta 
				+ ", contaDv=" + contaDv 
				+ ", tipoPagamento=" + tipoPagamento 
				+ ", propostaDcms=" + propostaDcms 
				+ ", dtVigencia=" + dtVigencia 
				+ ", dtMovimentacao=" + dtMovimentacao 
				+ ", tbodEmpresa=" + tbodEmpresa 
				+ ", tbodForcaVenda=" + tbodForcaVenda 
				+ ", tbodPlano=" + tbodPlano 
				+ ", tbodStatusVenda="	+ tbodStatusVenda 
				+ ", tbodVendaVida=" + tbodVendaVida 
				+ ", tbodVendaVidas=" + tbodVendaVidas
				+ ", tbodTokenAceites=" + tbodTokenAceites 
				+ ", tbodResponsavelContratual=" + tbodResponsavelContratual
				+ ", plataforma=" + plataforma //201807201122 - esert - COR-431
				+ ", tbodCorretora=" + tbodCorretora //201807311613 - esert - COR-468:Atrelar Venda com a Corretora
				+ "]";
	}
	
	

}
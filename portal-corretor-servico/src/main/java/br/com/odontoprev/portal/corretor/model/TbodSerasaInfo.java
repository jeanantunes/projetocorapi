package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the TBOD_SERASA_INFO database table.
 * 
 */
@Entity
@Table(name="TBOD_SERASA_INFO")
@NamedQuery(name="TbodSerasaInfo.findAll", query="SELECT t FROM TbodSerasaInfo t")
public class TbodSerasaInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String cnpj;

	@Column(name="CNAE_CODIGO")
	private String cnaeCodigo;

	@Column(name="CNAE_DESCRICAO")
	private String cnaeDescricao;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_ABERTURA")
	private Date dataAbertura;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_CONSULTA")
	private Date dataConsulta;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_SITUACAO")
	private Date dataSituacao;

	@Column(name="FONTE_PESQUISADA")
	private String fontePesquisada;

	@Column(name="NOME_FANTASIA")
	private String nomeFantasia;

	@Column(name="RAZAO_SOCIAL")
	private String razaoSocial;

	private String situacao;
	
	@Column(name="REQUEST_JSON")
	private String requestJson;

	//bi-directional many-to-one association to TbodSerasaEndereco
	@OneToMany(mappedBy="tbodSerasaInfo")
	private List<TbodSerasaEndereco> tbodSerasaEnderecos;

	//bi-directional many-to-one association to TbodSerasaRepresLegal
	@OneToMany(mappedBy="tbodSerasaInfo")
	private List<TbodSerasaRepresLegal> tbodSerasaRepresLegals;

	//bi-directional many-to-one association to TbodSerasaTelefone
	@OneToMany(mappedBy="tbodSerasaInfo")
	private List<TbodSerasaTelefone> tbodSerasaTelefones;

	public TbodSerasaInfo() {
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCnaeCodigo() {
		return this.cnaeCodigo;
	}

	public void setCnaeCodigo(String cnaeCodigo) {
		this.cnaeCodigo = cnaeCodigo;
	}

	public String getCnaeDescricao() {
		return this.cnaeDescricao;
	}

	public void setCnaeDescricao(String cnaeDescricao) {
		this.cnaeDescricao = cnaeDescricao;
	}

	public Date getDataAbertura() {
		return this.dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Date getDataConsulta() {
		return this.dataConsulta;
	}

	public void setDataConsulta(Date dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	public Date getDataSituacao() {
		return this.dataSituacao;
	}

	public void setDataSituacao(Date dataSituacao) {
		this.dataSituacao = dataSituacao;
	}

	public String getFontePesquisada() {
		return this.fontePesquisada;
	}

	public void setFontePesquisada(String fontePesquisada) {
		this.fontePesquisada = fontePesquisada;
	}

	public String getNomeFantasia() {
		return this.nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getSituacao() {
		return this.situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public String getRequestJson() {
		return requestJson;
	}

	public void setRequestJson(String requestJson) {
		this.requestJson = requestJson;
	}

	public List<TbodSerasaEndereco> getTbodSerasaEnderecos() {
		return this.tbodSerasaEnderecos;
	}

	public void setTbodSerasaEnderecos(List<TbodSerasaEndereco> tbodSerasaEnderecos) {
		this.tbodSerasaEnderecos = tbodSerasaEnderecos;
	}

	public TbodSerasaEndereco addTbodSerasaEndereco(TbodSerasaEndereco tbodSerasaEndereco) {
		getTbodSerasaEnderecos().add(tbodSerasaEndereco);
		tbodSerasaEndereco.setTbodSerasaInfo(this);

		return tbodSerasaEndereco;
	}

	public TbodSerasaEndereco removeTbodSerasaEndereco(TbodSerasaEndereco tbodSerasaEndereco) {
		getTbodSerasaEnderecos().remove(tbodSerasaEndereco);
		tbodSerasaEndereco.setTbodSerasaInfo(null);

		return tbodSerasaEndereco;
	}

	public List<TbodSerasaRepresLegal> getTbodSerasaRepresLegals() {
		return this.tbodSerasaRepresLegals;
	}

	public void setTbodSerasaRepresLegals(List<TbodSerasaRepresLegal> tbodSerasaRepresLegals) {
		this.tbodSerasaRepresLegals = tbodSerasaRepresLegals;
	}

	public TbodSerasaRepresLegal addTbodSerasaRepresLegal(TbodSerasaRepresLegal tbodSerasaRepresLegal) {
		getTbodSerasaRepresLegals().add(tbodSerasaRepresLegal);
		tbodSerasaRepresLegal.setTbodSerasaInfo(this);

		return tbodSerasaRepresLegal;
	}

	public TbodSerasaRepresLegal removeTbodSerasaRepresLegal(TbodSerasaRepresLegal tbodSerasaRepresLegal) {
		getTbodSerasaRepresLegals().remove(tbodSerasaRepresLegal);
		tbodSerasaRepresLegal.setTbodSerasaInfo(null);

		return tbodSerasaRepresLegal;
	}

	public List<TbodSerasaTelefone> getTbodSerasaTelefones() {
		return this.tbodSerasaTelefones;
	}

	public void setTbodSerasaTelefones(List<TbodSerasaTelefone> tbodSerasaTelefones) {
		this.tbodSerasaTelefones = tbodSerasaTelefones;
	}

	public TbodSerasaTelefone addTbodSerasaTelefone(TbodSerasaTelefone tbodSerasaTelefone) {
		getTbodSerasaTelefones().add(tbodSerasaTelefone);
		tbodSerasaTelefone.setTbodSerasaInfo(this);

		return tbodSerasaTelefone;
	}

	public TbodSerasaTelefone removeTbodSerasaTelefone(TbodSerasaTelefone tbodSerasaTelefone) {
		getTbodSerasaTelefones().remove(tbodSerasaTelefone);
		tbodSerasaTelefone.setTbodSerasaInfo(null);

		return tbodSerasaTelefone;
	}

}
package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//201806121119 - esert - view para contagem de vidas venda PF
//201806121639 - esert - padronizacao do nome da view de (VWOD_COR_TOTAL_VIDAS_PF) para (VWOD_EMP_TOTAL_VIDAS_PF)
@Table(name="VWOD_EMP_TOTAL_VIDAS_PF")
@Entity
public class VwodCorretoraTotalVidasPF implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8868075872512703005L;

	//v.proposta_dcms NUM_PROPOSTA
	@Column(name="NUM_PROPOSTA") //01
	private String num_proposta;
	
	//v.dt_venda
	@Id
	@Column(name="DT_VENDA") //02
	private Date dt_venda;
	
	//--i.dt_inicio_cobranca PRIMEIRO_VENCIMENTO, 
	@Column(name="PRIMEIRO_VENCIMENTO") //03
	private Date primeiro_vencimento;

	//cor.nome CORRETORA, 
	@Column(name="CORRETORA") //04
	private String corretora;

	//cor.cnpj CNPJ_CORRETORA, 
	@Column(name="CNPJ_CORRETORA") //05
	private String cnpj_corretora;

	//forca.nome NOME_FORCA, 
	@Column(name="NOME_FORCA") //06
	private String nome_forca;

	//forca.cpf CPF_FORCA, 
	@Column(name="CPF_FORCA") //07
	private String cpf_forca;

	//p.titulo PLANO_PF, 
	@Column(name="PLANO_PF") //08
	private String plano_pf;

	//CASE p.cd_tipo_plano WHEN 1 THEN 'MENSAL' WHEN 2 THEN 'ANUAL' END AS tipo_plano, 
	@Column(name="TIPO_PLANO") //09
	private String tipo_plano;

	//Count(1) VIDAS, 
	@Column(name="VIDAS") //10
	private String vidas;

	//vida.cpf CPF_TITULAR,  
	@Column(name="CPF_TITULAR") //11
	private String cpf_titular;

	//vida.nome NOME_TITULAR,
	@Column(name="NOME_TITULAR") //12
	private String nome_titular;

	//ende.logradouro, 
	@Column(name="LOGRADOURO") //13
	private String logradouro;

	//ende.numero, 
	@Column(name="NUMERO") //14
	private String numero;

	//ende.complemento, 
	@Column(name="COMPLEMENTO") //15
	private String complemento;

	//ende.bairro, 
	@Column(name="BAIRRO") //16
	private String bairro;

	//ende.cidade, 
	@Column(name="CIDADE") //17
	private String cidade;

	//ende.uf, 
	@Column(name="UF") //18
	private String uf;

	//ende.cep, 
	@Column(name="CEP") //19
	private String cep;

	//vida.email 
	@Column(name="EMAIL") //20
	private String email;

	public String getNum_proposta() {
		return num_proposta;
	}

	public void setNum_proposta(String num_proposta) {
		this.num_proposta = num_proposta;
	}

	public Date getDt_venda() {
		return dt_venda;
	}

	public void setDt_venda(Date dt_venda) {
		this.dt_venda = dt_venda;
	}

	public Date getPrimeiro_vencimento() {
		return primeiro_vencimento;
	}

	public void setPrimeiro_vencimento(Date primeiro_vencimento) {
		this.primeiro_vencimento = primeiro_vencimento;
	}

	public String getCorretora() {
		return corretora;
	}

	public void setCorretora(String corretora) {
		this.corretora = corretora;
	}

	public String getCnpj_corretora() {
		return cnpj_corretora;
	}

	public void setCnpj_corretora(String cnpj_corretora) {
		this.cnpj_corretora = cnpj_corretora;
	}

	public String getNome_forca() {
		return nome_forca;
	}

	public void setNome_forca(String nome_forca) {
		this.nome_forca = nome_forca;
	}

	public String getCpf_forca() {
		return cpf_forca;
	}

	public void setCpf_forca(String cpf_forca) {
		this.cpf_forca = cpf_forca;
	}

	public String getPlano_pf() {
		return plano_pf;
	}

	public void setPlano_pf(String plano_pf) {
		this.plano_pf = plano_pf;
	}

	public String getTipo_plano() {
		return tipo_plano;
	}

	public void setTipo_plano(String tipo_plano) {
		this.tipo_plano = tipo_plano;
	}

	public String getVidas() {
		return vidas;
	}

	public void setVidas(String vidas) {
		this.vidas = vidas;
	}

	public String getCpf_titular() {
		return cpf_titular;
	}

	public void setCpf_titular(String cpf_titular) {
		this.cpf_titular = cpf_titular;
	}

	public String getNome_titular() {
		return nome_titular;
	}

	public void setNome_titular(String nome_titular) {
		this.nome_titular = nome_titular;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "VwodCorretoraTotalVidasPF [num_proposta=" + num_proposta + ", dt_venda=" + dt_venda
				+ ", primeiro_vencimento=" + primeiro_vencimento + ", corretora=" + corretora + ", cnpj_corretora="
				+ cnpj_corretora + ", nome_forca=" + nome_forca + ", cpf_forca=" + cpf_forca + ", plano_pf=" + plano_pf
				+ ", tipo_plano=" + tipo_plano + ", vidas=" + vidas + ", cpf_titular=" + cpf_titular + ", nome_titular="
				+ nome_titular + ", logradouro=" + logradouro + ", numero=" + numero + ", complemento=" + complemento
				+ ", bairro=" + bairro + ", cidade=" + cidade + ", uf=" + uf + ", cep=" + cep + ", email=" + email
				+ "]";
	}
	
	
}

//filosofia T__ELA & TABELA
package br.com.odontoprev.portal.corretor.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//201806071143 - esert - view para contagem de vidas por dt_venda, cnpj corretora, cpf corretor forca venda
@Table(name="VWOD_CORRETORA_TOTAL_VIDAS")
@Entity
public class VwodCorretoraTotalVidas implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7980931302249763552L;

	//venda.dt_venda,
	@Id
	@Column(name="DT_VENDA")
	private String dtVenda;

	//cor.cnpj cnpj_corretora,
	@Column(name="CNPJ_CORRETORA")
	private String cnpj_corretora;
	
	//cor.nome corretora,
	@Column(name="CORRETORA")
	private String corretora;
	
	//forca.cpf,
	@Column(name="CPF")
	private String cpf;
	
	//forca.nome vendedor,	
	@Column(name="VENDEDOR")
	private String vendedor;
	
	//empc.cnpj CNPJ_CLIENTE, 
	@Column(name="CNPJ_CLIENTE")
	private String cnpj_cliente;
	
	//empc.razao_social RAZAO_SOCIAL_CLIENTE, 
	@Column(name="RAZAO_SOCIAL_CLIENTE")
	private String razao_social_cliente;

	//emp.cd_empresa LOGIN_DCMS, 
	@Column(name="LOGIN_DCMS")
	private String login_dcms;

	//ende.logradouro, 
	@Column(name="LOGRADOURO")
	private String logradouro;

	//ende.numero, 
	@Column(name="NUMERO")
	private String numero;

	//ende.complemento, 
	@Column(name="COMPLEMENTO")
	private String complemento;

	//ende.bairro, 
	@Column(name="BAIRRO")
	private String bairro;

	//ende.cidade, 
	@Column(name="CIDADE")
	private String cidade;
	
	//ende.UF, 
	@Column(name="UF")
	private String uf;

	//ende.cep, 
	@Column(name="CEP")
	private String cep;

	//empc.representante_legal, 
	@Column(name="REPRESENTANTE_LEGAL")
	private String representante_legal;

	//empc.celular,
	@Column(name="CELULAR")
	private String celular;
	
	//empc.email,
	@Column(name="EMAIL")
	private String email;

	//--venda.cd_plano TOTAL_PLANOS, 

	//plano.titulo PLANO,
	@Column(name="PLANO")
	private String plano;

	//Count(vida.cd_vida) TOTAL_VIDAS, 
	@Column(name="TOTAL_VIDAS")
	private Integer total_vidas;

	//venda.fatura_vencimento DIA_FATURA 
	@Column(name="DIA_FATURA")
	private Integer dia_fatura;
}

//filosofia T__ELA & TABELA
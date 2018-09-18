package br.com.odontoprev.portal.corretor.util;

public final class Constantes {
	
	//Status Tela Forca Venda
	public static final String ATIVO = "S"; //true
	public static final String INATIVO = "N"; //false
	
	//
	public static final String SIM = "S";
	public static final String NAO = "N";
	
	//Sexo
	public static final String FEMININO = "F";
	public static final String MASCULINO = "M";
	
	//201806291953 - esert - COR-358 Serviço - Alterar serviço /empresa-dcms para atualizar o campo CD_STATUS_VENDA da tabela TBOD_VENDA.
	//status venda
//  //TBOD_STATUS_VENDA.CD_STATUS_VENDA NUMBER(10,0) Not Null
//	public static final long STATUS_VENDA_APROVADO = 1; //1 Aprovado //TBOD_STATUS_VENDA.CD_STATUS_VENDA NUMBER(10,0) Not Null
//	public static final long STATUS_VENDA_CRITICADO = 2; //2 Criticado //TBOD_STATUS_VENDA.CD_STATUS_VENDA NUMBER(10,0) Not Null
	
	public static final long STATUS_VENDA_ENVIADO = 1; //1 Enviado //201807051747 - esert - (COR-357 Serviço - Definição de Códigos/Status)  
	public static final long STATUS_VENDA_CRITICADO = 2; //2 Criticado //201807051747 - esert - (COR-357 Serviço - Definição de Códigos/Status)
	public static final long STATUS_VENDA_APROVADO = 3; //3 Aprovado //201807051747 - esert - (COR-357 Serviço - Definição de Códigos/Status)
	public static final long STATUS_VENDA_AGUARDANDO = 5;

	public static final Object TIPO_INTERFACE_APP = "app"; //201807161655 - esert - COR-222
	public static final Object TIPO_INTERFACE_WEB = "web"; //201807161655 - esert - COR-222
	
	public static final int TAMANHO_CPF = 11; //123.456.789-01=11 //201807171752 - esert - COR-317
	public static final int TAMANHO_CNPJ = 14; //12.345.678/9012-14=14 //201807171752 - esert - COR-317
	
	public static final Long TIPO_LOGIN_UM = 1L; //201807181255 - esert - COR-319


	public static final String BRADESCO ="237";
	public static final String ITAU = "341";
	public static final String SANTANDER = "033";
	
	public static final Long CONTRATO_CORRETAGEM_V1 = 1l; //201809111832 - esert - COR-752 - DB - alterar tabela TBOD_CONTRATO_CORRETORA
	public static final Long CONTRATO_INTERMEDIACAO_V1 = 2L; //201809111832 - esert - COR-752 - DB - alterar tabela TBOD_CONTRATO_CORRETORA
	
	public static final Long TIPO_BLOQUEIO_SEM_BLOQUEIO = 0L; //201809181744 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)
	public static final Long TIPO_BLOQUEIO_CORRETAGEM_INTERMEDIACAO = 1L; //201809181744 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)
}

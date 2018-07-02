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
	public static final long STATUS_VENDA_APROVADO = 1; //1 Aprovado //TBOD_STATUS_VENDA.CD_STATUS_VENDA NUMBER(10,0) Not Null
	public static final long STATUS_VENDA_CRITICADO = 2; //2 Criticado //TBOD_STATUS_VENDA.CD_STATUS_VENDA NUMBER(10,0) Not Null	
}

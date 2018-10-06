package br.com.odontoprev.portal.corretor.dto;

//201810052053 - esert - COR-861:Serviço - Receber / Retornar Planilha
public class EmpresaDcmsRetorno extends EmpresaDcmsEntrada {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4744491088010820121L;

	//COR-232 coluna F da planilha (Layout Arquivo Input DCMS em Massa.xlsx) , aba (Layout Retorno)
	//COR-232 : Deverá incluir uma coluna no arquivo com o nome "RETORNO" com o resultado "OK" para os códigos inputados com sucesso, e o retorno "ERRO" para os códigos não inputados com sucesso.
	private String retorno; //OK, ERRO
	
	//COR-232 coluna G da planilha (Layout Arquivo Input DCMS em Massa.xlsx) , aba (Layout Retorno)
	//COR-232 : Deverá incluir depois da coluna "RETORNO" uma coluna "MENSAGEM RETORNO" que deverá trazer a mensagem do porque não foi possível inputar o código DCMS.
	private String mensagemRetorno;
	
	public String getRetorno() {
		return retorno;
	}
	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}
	public String getMensagemRetorno() {
		return mensagemRetorno;
	}
	public void setMensagemRetorno(String mensagemRetorno) {
		this.mensagemRetorno = mensagemRetorno;
	}
	
	@Override
	public String toString() {
		return "EmpresaDcmsRetorno [" 
				+ "retorno=" + retorno 
				+ ", retornoMensagem=" + mensagemRetorno 
				+ "]";
	}		
}

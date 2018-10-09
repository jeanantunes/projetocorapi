package br.com.odontoprev.portal.corretor.dto;

//201810052053 - esert - COR-861:Serviço - Receber / Retornar Planilha
//201810091238 - esert - COR-861:Serviço - Receber / Retornar Planilha - rename EmpresaDcmsEntrada para EmpresaDcmsLote 
public class EmpresaDcmsLote extends EmpresaDcms {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7701367340673723094L;
	
	private Long cdVenda; //COR-232 coluna A da planilha (Layout Arquivo Input DCMS em Massa.xlsx) , aba (Layout Upload)
	private String razaoSocial; //COR-232 coluna D da planilha (Layout Arquivo Input DCMS em Massa.xlsx) , aba (Layout Upload)
	
	public Long getCdVenda() {
		return cdVenda;
	}
	public void setCdVenda(Long cdVenda) {
		this.cdVenda = cdVenda;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	
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

	//COR-232 coluna F da planilha (Layout Arquivo Input DCMS em Massa.xlsx) , aba (Layout Retorno)
	//COR-232 : Deverá incluir uma coluna no arquivo com o nome "RETORNO" com o resultado "OK" para os códigos inputados com sucesso, e o retorno "ERRO" para os códigos não inputados com sucesso.
	private String retorno; //OK, ERRO
	
	//COR-232 coluna G da planilha (Layout Arquivo Input DCMS em Massa.xlsx) , aba (Layout Retorno)
	//COR-232 : Deverá incluir depois da coluna "RETORNO" uma coluna "MENSAGEM RETORNO" que deverá trazer a mensagem do porque não foi possível inputar o código DCMS.
	private String mensagemRetorno;

	@Override
	public String toString() {
		return "EmpresaDcmsEntrada [" 
				+ "cdVenda=" + cdVenda 
				+ ", razaoSocial=" + razaoSocial 
				+ ", super=" + super.toString() //201810081555 - esert - ver todos os dados do ancestral tambem
				+ ", retorno=" + retorno 
				+ ", mensagemRetorno=" + mensagemRetorno 
				+ "]";
	}
	
}

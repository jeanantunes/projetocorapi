package br.com.odontoprev.portal.corretor.dto;

//201810052053 - esert - COR-861:Serviço - Receber / Retornar Planilha
public class EmpresaDcmsEntrada extends EmpresaDcms {

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
	@Override
	public String toString() {
		return "EmpresaDcmsEntrada [cdVenda=" + cdVenda + ", razaoSocial=" + razaoSocial + "]";
	}
	
}

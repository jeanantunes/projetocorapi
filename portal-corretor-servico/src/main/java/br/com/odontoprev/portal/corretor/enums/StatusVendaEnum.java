package br.com.odontoprev.portal.corretor.enums;

public enum StatusVendaEnum {
	
	APROVADO(1, "Aprovado"), 
	CRITICADO(2, "Criticado"), 
	AGUARD_EMPRESA(3, "Aguardando Empresa"),	//PME
	IMPLANTADA(4, "Proposta Implantada"), 		//PME
	VIDAS_OK(5, "Vidas OK"), 					//PME
	VIDAS_CRITICADAS(6, "Vidas com criticas"),	//PME
	CRIT_ENVIO(6, "Criticada Envio"),			//PF
	CRIT_NEGOCIO(7, "Criticada Negocio");		//PF
	
	private StatusVendaEnum(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	private long codigo;

	private String descricao;

	public long getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

}

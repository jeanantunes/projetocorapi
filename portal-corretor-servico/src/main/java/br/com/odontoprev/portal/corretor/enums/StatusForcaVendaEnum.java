package br.com.odontoprev.portal.corretor.enums;

public enum StatusForcaVendaEnum {

	AGUARDANDO_APRO(1, "Aguardando Aprovação"), ATIVO(2, "Ativo"), INATIVO(3, "Inativo"), PENDENTE(4, "Pendente"), PRE_CADASTRO(5, "Pre Cadastro");

	private StatusForcaVendaEnum(int codigo, String descricao) {
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

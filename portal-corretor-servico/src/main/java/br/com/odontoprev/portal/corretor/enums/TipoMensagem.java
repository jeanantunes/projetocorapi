package br.com.odontoprev.portal.corretor.enums;

public enum  TipoMensagem {
    //Enum para auxiliar a obter as mensagens de acordo com o tipo
    ATIVO("Ativo");

    private String tipo;

    TipoMensagem(String tipo) {
        this.tipo = tipo;
    }
}

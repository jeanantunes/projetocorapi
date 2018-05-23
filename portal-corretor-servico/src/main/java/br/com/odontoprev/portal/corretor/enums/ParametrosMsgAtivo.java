package br.com.odontoprev.portal.corretor.enums;

public enum ParametrosMsgAtivo {

    //Enum para auxiliar a obter/alterar parametros da mensagem
    NOMEFORCAVENDA("<nomeforcavenda>");

    private String parametro;

   ParametrosMsgAtivo(String parametro) {
        this.parametro = parametro;
    }

    public String getParametro() {
        return parametro;
    }
}

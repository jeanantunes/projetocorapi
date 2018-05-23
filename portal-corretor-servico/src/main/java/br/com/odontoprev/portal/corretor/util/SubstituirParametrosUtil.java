package br.com.odontoprev.portal.corretor.util;

import java.util.Map;

public class SubstituirParametrosUtil {


    public String substituirParametrosMensagem(String mensagemComParametros, Map<String, String> mensagem) {
        String mensagemFinal = "";
        for (Map.Entry<String, String> msg : mensagem.entrySet()) {
            mensagemFinal = mensagemComParametros.replace(msg.getKey(), msg.getValue());
        }
        return mensagemFinal;
    }
}

package br.com.odontoprev.portal.corretor.util;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class SubstituirParametrosUtil {


    public String substituirParametrosMensagem(String mensagemComParametros, Map<String, String> mensagem) {
        String mensagemFinal = mensagemComParametros.replace("<","").replace(">","");
        for (Map.Entry<String, String> msg : mensagem.entrySet()) {
            mensagemFinal = mensagemFinal.replace(msg.getKey(), msg.getValue());
            }
        return mensagemFinal;
    }
}

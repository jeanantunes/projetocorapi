package br.com.odontoprev.portal.corretor.util;

import java.util.Base64;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GerarTokenUtils {
	
	private static final Log log = LogFactory.getLog(GerarTokenUtils.class);
	
	/**
	 * Gera um hash.
	 * 
	 * @param chave
	 * @return String
	 */
	public static String gerarHashToken(String chave) {
		
		log.info("Gerando hash - token.");
		
		// Encode
        byte[] chaveEmBytes = chave.getBytes();
        String tokenCodificado = Base64.getEncoder().encodeToString(chaveEmBytes);
        
        return tokenCodificado;
	}

}

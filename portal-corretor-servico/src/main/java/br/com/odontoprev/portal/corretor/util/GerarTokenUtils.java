package br.com.odontoprev.portal.corretor.util;

import java.util.Base64;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor={Exception.class}) //201806281838 - esert - COR-348
public class GerarTokenUtils {
	
	private static final Log log = LogFactory.getLog(GerarTokenUtils.class);
	
	/**
	 * Gera um hash.
	 * 
	 * @param chave
	 * @return String
	 */
	@Transactional(rollbackFor={Exception.class}) //201806281838 - esert - COR-348
	public static String gerarHashToken(String chave) {
		
		log.info("Gerando hash - token.");
		
		// Encode
        byte[] chaveEmBytes = chave.getBytes();
        String tokenCodificado = Base64.getEncoder().encodeToString(chaveEmBytes);
        
        return tokenCodificado;
	}

}

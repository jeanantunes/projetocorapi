package br.com.odontoprev.portal.corretor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.TokenAceite;
import br.com.odontoprev.portal.corretor.dto.TokenAceiteResponse;
import br.com.odontoprev.portal.corretor.service.TokenAceiteService;
import br.com.odontoprev.portal.corretor.service.VendaService;

@RestController
public class TokenAceiteController {

	private static final Log log = LogFactory.getLog(TokenAceiteController.class);
	
	@Autowired
	TokenAceiteService tokenAceiteService;
	
	@Autowired
	VendaService vendaService;
	
	@RequestMapping(value = "/token", method = { RequestMethod.POST })
	public TokenAceiteResponse addTokenAceite(@RequestBody TokenAceite tokenAceite) {
		//TODO: outras validaçoes
		log.info(tokenAceite);				
		return tokenAceiteService.addTokenAceite(tokenAceite);		
	}
	
	@RequestMapping(value = "/token", method = { RequestMethod.PUT })
	public ResponseEntity<TokenAceiteResponse> updateTokenAceite(@RequestBody TokenAceite tokenAceite) {
		//TODO: outras validaçoes
		log.info(tokenAceite);		
		TokenAceiteResponse tokenAceiteResponse = tokenAceiteService.updateTokenAceite(tokenAceite);
		return ResponseEntity.ok(tokenAceiteResponse);
	}
	
	@RequestMapping(value = "/token/{token}", method = { RequestMethod.GET })
	public TokenAceite findTokenAceite(@PathVariable String token) {
		
		log.info(token);	
		
		TokenAceite tokenAceite = tokenAceiteService.buscarTokenAceitePorChave(token);
		
		return tokenAceite;
	}
	
}

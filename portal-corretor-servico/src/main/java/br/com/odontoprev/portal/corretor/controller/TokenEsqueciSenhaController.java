package br.com.odontoprev.portal.corretor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.TokenEsqueciSenha;
import br.com.odontoprev.portal.corretor.dto.TokenEsqueciSenhaResponse;
import br.com.odontoprev.portal.corretor.service.TokenEsqueciSenhaService;

@RestController
public class TokenEsqueciSenhaController {
	
	private static final Log log = LogFactory.getLog(TokenEsqueciSenhaController.class);
	
	@Autowired
	TokenEsqueciSenhaService tokenEsqueciSenhaService;
	
	@RequestMapping(value = "/tokenEsqueciSenha", method = { RequestMethod.POST })
	public TokenEsqueciSenhaResponse addTokenEsqueciSenha(@RequestBody TokenEsqueciSenha tokenEsqueciSenha) throws Exception {
		log.info("[API addTokenEsqueciSenha]");				
		return tokenEsqueciSenhaService.addTokenEsqueciSenha(tokenEsqueciSenha);		
	}
	
	

}

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

import br.com.odontoprev.portal.corretor.dto.ForcaDadosTrocaSenha;
import br.com.odontoprev.portal.corretor.dto.TokenEsqueciSenha;
import br.com.odontoprev.portal.corretor.dto.TokenEsqueciSenhaResponse;
import br.com.odontoprev.portal.corretor.model.TbodTokenResetSenha;
import br.com.odontoprev.portal.corretor.service.ForcaDadosTrocaSenhaService;
import br.com.odontoprev.portal.corretor.service.TokenEsqueciSenhaService;

@RestController
public class TokenEsqueciSenhaController {
	
	private static final Log log = LogFactory.getLog(TokenEsqueciSenhaController.class);
	
	@Autowired
	TokenEsqueciSenhaService tokenEsqueciSenhaService;
	
	@Autowired
	ForcaDadosTrocaSenhaService forcaDadosTrocaSenhaService;
	
	@RequestMapping(value = "/esqueciMinhaSenha", method = { RequestMethod.POST })
	public TokenEsqueciSenhaResponse addTokenEsqueciSenha(@RequestBody TokenEsqueciSenha tokenEsqueciSenha) throws Exception {
		log.info("[API addTokenEsqueciSenha]");				
		return tokenEsqueciSenhaService.addTokenEsqueciSenha(tokenEsqueciSenha);		
	}
	
	@RequestMapping(value = "/esqueciMinhaSenha/{token}", method = { RequestMethod.GET })
	public ResponseEntity<ForcaDadosTrocaSenha> findDadosForcaTrocaSenha(@PathVariable String token) {
	
		log.info("[API findDadosForcaTrocaSenha]");	
		
		log.info("[Busca CPF atraves do token - TBOD_TOKEN_RESET_SENHA]");
		
		TbodTokenResetSenha tokenEsqueciSenha = tokenEsqueciSenhaService.buscarTokenEsqueciSenha(token);
		
		log.info("[Busca dados forca venda por cpf]");
		
		ForcaDadosTrocaSenha forcaDadosTrocaSenha = forcaDadosTrocaSenhaService.buscarDadosForcaVendaPorCPF(tokenEsqueciSenha.getCpf());
		
		return ResponseEntity.ok(forcaDadosTrocaSenha);
	}
	
	@RequestMapping(value = "/dataResetSenha", method = { RequestMethod.PUT })
	public ResponseEntity<TokenEsqueciSenhaResponse> updateDataResetSenha(@RequestBody TokenEsqueciSenha tokenEsqueciSenha) {
		log.info("[API updateDataResetSenha]");			
		TokenEsqueciSenhaResponse tokenEsqueciSenhaResponse = tokenEsqueciSenhaService.updateDataResetSenha(tokenEsqueciSenha.getToken());
		return ResponseEntity.ok(tokenEsqueciSenhaResponse);
	}

}

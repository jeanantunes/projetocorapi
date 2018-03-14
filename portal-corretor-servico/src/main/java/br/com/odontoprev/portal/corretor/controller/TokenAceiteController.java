package br.com.odontoprev.portal.corretor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.TokenAceite;
import br.com.odontoprev.portal.corretor.dto.TokenAceiteResponse;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
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
		
		log.info(tokenAceite);
		
		TbodVenda venda = vendaService.buscarVendaPorCodigo(tokenAceite.getCdVenda());
				
		if(venda == null) {
			return new TokenAceiteResponse(204, "Código de venda não encontrado");
		}
		
		return tokenAceiteService.addTokenAceite(tokenAceite);		
	}
}

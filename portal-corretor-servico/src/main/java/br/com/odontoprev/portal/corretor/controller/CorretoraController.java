package br.com.odontoprev.portal.corretor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.CorretoraResponse;
import br.com.odontoprev.portal.corretor.service.CorretoraService;

@RestController
public class CorretoraController {
	
	private static final Log log = LogFactory.getLog(CorretoraController.class);
	
	@Autowired
	CorretoraService corretoraService;
	
	@RequestMapping(value = "/corretora", method = { RequestMethod.POST })
	public ResponseEntity<CorretoraResponse> addCorretora(@RequestBody Corretora corretora) {
		
		log.info(corretora);
		
		try {
			
			CorretoraResponse corretoraResponse = corretoraService.addCorretora(corretora);
			
			return ResponseEntity.ok(corretoraResponse);
			
		} catch (final Exception e) {
			log.error("ERROR: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}	
		
	}

	@RequestMapping(value = "/corretora", method = { RequestMethod.PUT })
	public CorretoraResponse updateCorretora(@RequestBody Corretora corretora) {
		
		log.info(corretora);
		
		return corretoraService.updateCorretora(corretora);
	}
	
	@RequestMapping(value = "/corretora/{cnpj}", method = { RequestMethod.GET })
	public Corretora buscaPorCnpj(@PathVariable String cnpj) {
		
		log.info("cnpj: [" + cnpj + "]");
		
		return corretoraService.buscaCorretoraPorCnpj(cnpj);
	}

}

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

	@RequestMapping(value = "/corretora/login", method = { RequestMethod.PUT })
	public ResponseEntity<CorretoraResponse> updateCorretoraLogin(@RequestBody Corretora corretora) { //201807181255 - esert - COR-319
		
		log.info("updateCorretoraLogin - ini");
		log.info(corretora);
		
		try {
			
			CorretoraResponse corretoraResponse = corretoraService.updateCorretoraLogin(corretora); //201807181255 - esert - COR-319

			log.info("updateCorretoraLogin - fim");
			return ResponseEntity.ok(corretoraResponse);
			
		} catch (final Exception e) {
			log.info("updateCorretoraLogin - erro");
			log.error("ERROR: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}

	@RequestMapping(value = "/corretora/dados", method = { RequestMethod.PUT })
	public ResponseEntity<CorretoraResponse> updateCorretoraEmail(@RequestBody Corretora corretora) {

		log.info("updateCorretoraEmail - ini");
		log.info(corretora);

		try {

			if(corretora.getCdCorretora() == 0) {

				return ResponseEntity.badRequest().body(new CorretoraResponse(0,"CdCorretora nao informado. Corretora nao atualizada!"));

			}

			CorretoraResponse corretoraResponse = corretoraService.updateCorretoraDados(corretora);

			if(corretoraResponse == null){
				log.info("updateCorretoraEmail - Corretora nao encontrada");
				return ResponseEntity.noContent().build();
			}

			log.info("updateCorretoraEmail - fim");
			return ResponseEntity.ok(corretoraResponse);

		} catch (final Exception e) {
			log.info("updateCorretoraEmail - erro");
			log.error("ERROR: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	
	@RequestMapping(value = "/corretora/{cnpj}", method = { RequestMethod.GET })
	public ResponseEntity<Corretora> buscaPorCnpj(@PathVariable String cnpj) {
		
		log.info("cnpj: [" + cnpj + "]");

		Corretora corretora;

		try {

			corretora = corretoraService.buscaCorretoraPorCnpj(cnpj);

			if(corretora == null){

				return ResponseEntity.noContent().build();
			}

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(500).build();

		}

		return ResponseEntity.ok(corretora);
	}

}

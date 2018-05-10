package br.com.odontoprev.portal.corretor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.PropostaCritica;
import br.com.odontoprev.portal.corretor.service.PropostaService;

//201805081530 - esert
@RestController
public class PropostaCriticaController {

	private static final Log log = LogFactory.getLog(PropostaCriticaController.class);
	
	@Autowired
	PropostaService propostaService;

	//201805081530 - esert
	//201805101441 - esert - REST - ResponseEntity
	@RequestMapping(value = "/propostaCritica/buscarPropostaCritica/{cd_venda}", method = { RequestMethod.GET })
	public ResponseEntity<PropostaCritica> buscarPropostaCritica_CD_VENDA(@PathVariable String cd_venda) {
		log.info("cd_venda:[" + cd_venda + "]");
		
		try {
			PropostaCritica propostaCritica = propostaService.buscarPropostaCritica(cd_venda);
			
			if(propostaCritica==null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			} else {
				return ResponseEntity.ok(propostaCritica);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}

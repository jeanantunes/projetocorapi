package br.com.odontoprev.portal.corretor.controller;



import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.Plano;
import br.com.odontoprev.portal.corretor.dto.PlanoInfos;
import br.com.odontoprev.portal.corretor.service.PlanoService;

@RestController
public class PlanoController {
	
	private static final Log log = LogFactory.getLog(PlanoController.class);

	@Autowired
	PlanoService planoService;
			
	@RequestMapping(value = "/plano", method = { RequestMethod.GET })
	public List<Plano> plano() {
		
		List<Plano> planos = planoService.getPlanos();
		
		return planos;
	}
	
	@RequestMapping(value = "/plano-empresa/{cdEmpresa}", method = { RequestMethod.GET })
	public List<Plano> findPlanosByEmpresa(@PathVariable Integer cdEmpresa) {
		
		log.info("cdEmpresa" + cdEmpresa);
		
		List<Plano> planos = planoService.findPlanosByEmpresa(cdEmpresa);
		
		return planos;
		
	}
	
	/*201810221655 - esert - COR-932:API - Novo GET /planoinfo */
	@RequestMapping(value = "/planoinfo", method = { RequestMethod.GET })
	public ResponseEntity<PlanoInfos> getPlanoInfos() {
		PlanoInfos planoInfos = null;
		log.info("getPlanoInfos - ini");
		
		try {
			planoInfos = planoService.getpPlanoInfos();
			if(planoInfos == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
		} catch (Exception e) {
			log.info("getPlanoInfos - erro");
			log.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		log.info("getPlanoInfos - fim");
		return ResponseEntity.ok(planoInfos);		
	}
	
}

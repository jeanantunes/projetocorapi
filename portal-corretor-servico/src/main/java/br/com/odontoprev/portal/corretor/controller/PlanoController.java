package br.com.odontoprev.portal.corretor.controller;



import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.Plano;
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
	
}

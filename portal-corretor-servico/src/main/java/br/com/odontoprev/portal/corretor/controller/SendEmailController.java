package br.com.odontoprev.portal.corretor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.EmpresaDcms;
import br.com.odontoprev.portal.corretor.service.EmpresaService;

//201805101947 - esert - controller para reenvio de emails vide Fernando@ODPV
@RestController
public class SendEmailController {
	
	private static final Log log = LogFactory.getLog(SendEmailController.class);
	
	@Autowired
	EmpresaService empresaService;
	
	//201805101947 - esert - rota para reenvio de emails BoasVindasPME vide Fernando@ODPV
	@RequestMapping(value = "/sendMail/BoasVindasPME/{cdEmpresa}", method = { RequestMethod.GET })
	public ResponseEntity<EmpresaDcms> sendMailBoasVindasPME(@PathVariable Long cdEmpresa) {
		
		log.info("Inicio sendMailBoasVindasPME");
		
		ResponseEntity<EmpresaDcms> res = empresaService.sendEmailBoasVindasPME(cdEmpresa);
		
		log.info("Fim sendMailBoasVindasPME");
		
		return res;
	}

}

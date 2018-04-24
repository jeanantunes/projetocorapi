package br.com.odontoprev.portal.corretor.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioResponse;
import br.com.odontoprev.portal.corretor.service.BeneficiarioService;

@RestController
public class BeneficiarioController {
	
	private static final Log log = LogFactory.getLog(BeneficiarioController.class);
	
	@Autowired
	BeneficiarioService beneficiarioService;

	@RequestMapping(value = "/beneficiario", method = { RequestMethod.POST })
	public BeneficiarioResponse addBeneficiario(@RequestBody List<Beneficiario> beneficiarios) {
		
		log.info(beneficiarios);
		
		return beneficiarioService.add(beneficiarios);
	}
	
}

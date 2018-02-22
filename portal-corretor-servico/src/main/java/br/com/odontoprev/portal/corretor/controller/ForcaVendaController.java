package br.com.odontoprev.portal.corretor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.dto.ForcaVendaResponse;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;

@RestController
public class ForcaVendaController {
	
	private static final Log log = LogFactory.getLog(ForcaVendaController.class);
	
	@Autowired
	ForcaVendaService forcaVendaService;
	
	@RequestMapping(value = "/forcavenda", method = { RequestMethod.POST })
	public ForcaVendaResponse addForcaVenda(@RequestBody ForcaVenda forcaVenda) {
		
		log.info(forcaVenda);
		
		return forcaVendaService.addForcaVenda(forcaVenda);
	}

}

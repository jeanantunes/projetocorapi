package br.com.odontoprev.portal.corretor.controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.DashboardPropostaResponse;
import br.com.odontoprev.portal.corretor.service.DashboardPropostaService;

@RestController
public class DashboardPropostaController {
	
	private static final Log log = LogFactory.getLog(DashboardPropostaController.class);
	
	@Autowired
	DashboardPropostaService dashboardService;
	
	@RequestMapping(value = "/dashboardPropostaPME/{status}", method = { RequestMethod.GET })
	public DashboardPropostaResponse buscaPorStatusPME(@PathVariable long status) {
		
		log.info(status);
		
		return dashboardService.buscaPropostaPorStatusPME(status);
	}
	
	
	@RequestMapping(value = "/dashboardPropostaPF/{status}", method = { RequestMethod.GET })
	public DashboardPropostaResponse buscaPorStatusPF(@PathVariable long status) {
		
		log.info(status);
		
		return dashboardService.buscaPropostaPorStatusPF(status);
	}
	
	
}


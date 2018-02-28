package br.com.odontoprev.portal.corretor.controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.DashboardPropostaPFResponse;
import br.com.odontoprev.portal.corretor.dto.DashboardPropostaPMEResponse;
import br.com.odontoprev.portal.corretor.service.DashboardPropostaService;

@RestController
public class DashboardPropostaController {
	
	private static final Log log = LogFactory.getLog(DashboardPropostaController.class);
	
	@Autowired
	DashboardPropostaService dashboardService;
	
	@RequestMapping(value = "/dashboardPropostaPME/{status}/{cnpj}", method = { RequestMethod.GET })
	public DashboardPropostaPMEResponse buscaPorStatusPME(@PathVariable long status, @PathVariable String cnpj) {
		
		log.info(status);
		
		return dashboardService.buscaPropostaPorStatusPME(status, cnpj);
	}
	
	
	@RequestMapping(value = "/dashboardPropostaPF/{status}/{cpf}", method = { RequestMethod.GET })
	public DashboardPropostaPFResponse buscaPorStatusPF(@PathVariable long status, @PathVariable String cpf) {
		
		log.info(status);
		
		return dashboardService.buscaPropostaPorStatusPF(status, cpf);
	}
	
	
}


package br.com.odontoprev.portal.corretor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.DashboardResponse;
import br.com.odontoprev.portal.corretor.service.DashboardPropostaService;

@RestController
public class DashboardController {
	
	@Autowired
	DashboardPropostaService dashboardService;
	
	private static final Log log = LogFactory.getLog(DashboardPropostaController.class);
	
	@RequestMapping(value = "/dashboard/forcavenda/aguardando-aprovacao/{cdCorretora}", method = { RequestMethod.GET })
	public DashboardResponse buscarForcaVendaAguardandoAprovacaoByCdEmpresa(@PathVariable long cdCorretora) {

		log.info(cdCorretora);

		return dashboardService.buscarForcaVendaAguardandoAprovacaoByCdEmpresa(cdCorretora);
	}

}

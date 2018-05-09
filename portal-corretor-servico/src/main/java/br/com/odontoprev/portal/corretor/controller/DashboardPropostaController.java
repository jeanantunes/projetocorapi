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
	public DashboardPropostaPMEResponse buscaPorStatus_PME(@PathVariable long status, @PathVariable String cnpj) {

		log.info(status);

		return dashboardService.buscaPropostaPorStatusPME(status, cnpj);
	}

	@RequestMapping(value = "/dashboardPropostaPF/{status}/{cpf}", method = { RequestMethod.GET })
	public DashboardPropostaPFResponse buscaPorStatusPF(@PathVariable long status, @PathVariable String cpf) {

		log.info(status);

		return dashboardService.buscaPropostaPorStatusPF(status, cpf);
	}

	@RequestMapping(value = "/dashboardPropostaPF/buscaPorCriticaPF_CPF/{cpf}", method = { RequestMethod.GET })
	public DashboardPropostaPFResponse buscaPorCriticaPF_CPF(@PathVariable String cpf) {
		log.info(cpf);

		return dashboardService.buscaPorCriticaPF( null,cpf);
	}

	@RequestMapping(value = "/dashboardPropostaPF/buscaPorCriticaPF_CNPJ/{cnpj}", method = { RequestMethod.GET })
	public DashboardPropostaPFResponse buscaPorCriticaPF_CNPJ(@PathVariable String cnpj) {
		log.info(cnpj);

		return dashboardService.buscaPorCriticaPF(cnpj, null);
	}

	@RequestMapping(value = "/dashboardPropostaPF/buscaPorCriticaPF_CNPJ_CPF/{cnpj}/{cpf}", method = { RequestMethod.GET })
	public DashboardPropostaPFResponse buscaPorCriticaPF_CNPJ_CPF(@PathVariable String cnpj, @PathVariable String cpf) {
		log.info("cnpj:" + cnpj);
		log.info("cpf:" + cpf);

		return dashboardService.buscaPorCriticaPF(cnpj, cpf);
	}
	
	@RequestMapping(value = "/dashboardPropostaPME/buscaPorCriticaPME_CPF/{cpf}", method = { RequestMethod.GET })
	public DashboardPropostaPMEResponse buscaPorCriticaPME_CPF(@PathVariable String cpf) {
		log.info(cpf);

		return dashboardService.buscaPorCriticaPME(null,cpf);
	}

	@RequestMapping(value = "/dashboardPropostaPME/buscaPorCriticaPME_CNPJ/{cnpj}", method = { RequestMethod.GET })
	public DashboardPropostaPMEResponse buscaPorCriticaPME_CNPJ(@PathVariable String cnpj) {
		log.info(cnpj);

		return dashboardService.buscaPorCriticaPME(cnpj, null);
	}

	@RequestMapping(value = "/dashboardPropostaPME/buscaPorCriticaPME_CNPJ_CPF/{cnpj}/{cpf}", method = { RequestMethod.GET })
	public DashboardPropostaPMEResponse buscaPorCriticaPME_CNPJ_CPF(@PathVariable String cnpj, @PathVariable String cpf) {
		log.info("cnpj:" + cnpj);
		log.info("cpf:" + cpf);

		return dashboardService.buscaPorCriticaPME(cnpj, cpf);
	}
}

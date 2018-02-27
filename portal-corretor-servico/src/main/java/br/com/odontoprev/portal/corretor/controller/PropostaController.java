package br.com.odontoprev.portal.corretor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.DashBoardProposta;
import br.com.odontoprev.portal.corretor.dto.PropostasDashBoard;
import br.com.odontoprev.portal.corretor.service.PropostaService;

@RestController
public class PropostaController {

	private static final Log log = LogFactory.getLog(PropostaController.class);
	
	@Autowired
	PropostaService propostaService;
	
	@RequestMapping(value = "/propostasDashBoard", method = { RequestMethod.POST })
	public PropostasDashBoard findPropostasByFiltro(@RequestBody DashBoardProposta dashBoardProposta) {
		return propostaService.findPropostasByFiltro(dashBoardProposta);
	}
}

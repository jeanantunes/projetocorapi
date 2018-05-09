package br.com.odontoprev.portal.corretor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.PropostaCritica;
import br.com.odontoprev.portal.corretor.service.PropostaService;

//201805081530 - esert
@RestController
public class PropostaCriticaController {

	private static final Log log = LogFactory.getLog(PropostaCriticaController.class);
	
	@Autowired
	PropostaService propostaService;

	//201805081530 - esert
	@RequestMapping(value = "/propostaCritica/buscarPropostaCritica/{cd_venda}", method = { RequestMethod.GET })
	public PropostaCritica buscarPropostaCritica_CD_VENDA(@PathVariable String cd_venda) {
		log.info("cd_venda:[" + cd_venda + "]");
		
		return propostaService.buscarPropostaCritica(cd_venda);
	}
}

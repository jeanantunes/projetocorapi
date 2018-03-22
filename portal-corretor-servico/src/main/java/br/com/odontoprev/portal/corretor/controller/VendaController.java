package br.com.odontoprev.portal.corretor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaPME;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;
import br.com.odontoprev.portal.corretor.service.ConvertObjectService;
import br.com.odontoprev.portal.corretor.service.VendaPFService;

@RestController
public class VendaController {
	
	private static final Log log = LogFactory.getLog(VendaController.class);
	
	@Autowired
	VendaPFService vendaPFService;
	
	@Autowired
	ConvertObjectService convertObjectToJson;
	
	@RequestMapping(value = "/vendapf", method = { RequestMethod.POST })
	public VendaResponse addVendaPF(@RequestBody Venda venda, @RequestHeader(value="User-Agent") String userAgent) {
		
		log.info(venda);
		
		convertObjectToJson.addJsonInTable(venda, null, userAgent);
		
		return vendaPFService.addVenda(venda);
	}
	
	@RequestMapping(value = "/vendapme", method = { RequestMethod.POST })
	public VendaResponse addVendaPME(@RequestBody VendaPME vendaPME, @RequestHeader(value="User-Agent") String userAgent) {
		
		log.info(vendaPME);
		
		convertObjectToJson.addJsonInTable(null, vendaPME, userAgent);
		
		return vendaPFService.addVendaPME(vendaPME);
	}
	
}

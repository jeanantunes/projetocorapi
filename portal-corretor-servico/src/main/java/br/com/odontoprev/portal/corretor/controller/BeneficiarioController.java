package br.com.odontoprev.portal.corretor.controller;

import java.util.List;

import javax.transaction.RollbackException;

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

	//201805281830 - esert - informação: por conta da trstativa de erro, verificou-se nesta data que esta rota nao tem chamada nem web nem app
	@RequestMapping(value = "/beneficiario", method = { RequestMethod.POST })
	public BeneficiarioResponse addBeneficiario(@RequestBody List<Beneficiario> beneficiarios) {
		BeneficiarioResponse beneficiarioResponse = new BeneficiarioResponse(0);
		
		log.info(beneficiarios);
		
		try { //201805281830 - esert - incluido try/catch para tratar throws que causara rollback de toda transacao - teste
			beneficiarioResponse = beneficiarioService.add(beneficiarios);
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e); //201805281830 - esert - incluido log
		}
		
		return beneficiarioResponse; 
	}
	
}

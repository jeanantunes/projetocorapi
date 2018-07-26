package br.com.odontoprev.portal.corretor.controller;

import java.util.List;

import javax.transaction.RollbackException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.business.BeneficiarioBusiness;
import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioResponse;
import br.com.odontoprev.portal.corretor.dto.Beneficiarios;
import br.com.odontoprev.portal.corretor.service.BeneficiarioService;

@RestController
public class BeneficiarioController {
	
	private static final Log log = LogFactory.getLog(BeneficiarioController.class);
	
	@Autowired
	BeneficiarioService beneficiarioService;
	
	@Autowired
	BeneficiarioBusiness beneficiarioBusiness;

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


	//201807241851 - esert - COR-398 - COR-471 - PoC
	@RequestMapping(value = "/beneficiarios/empresa/{cdEmpresa}/tampag/{tamPag}/numpag/{numPag}", method = { RequestMethod.GET })
	public ResponseEntity<Beneficiarios> getBeneficiariosEmpresa2(
			@PathVariable Long cdEmpresa, 
			@PathVariable Long tamPag, 
			@PathVariable Long numPag) {
		
		return getBeneficiariosEmpresa(cdEmpresa, tamPag, numPag);
	}
	
	//201807241723 - esert - COR-398 - COR-471
	@RequestMapping(value = "/beneficiarios/empresa/{cdEmpresa}", method = { RequestMethod.GET })
	public ResponseEntity<Beneficiarios> getBeneficiariosEmpresa(
			@PathVariable Long cdEmpresa, 
			@RequestParam("tamPag") Long tamPag, 
			@RequestParam("numPag") Long numPag) {
		try {
			Beneficiarios beneficiarios = beneficiarioBusiness.getPageFake(cdEmpresa, tamPag, numPag);
			if(beneficiarios==null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return ResponseEntity.ok(beneficiarios);
		} catch (Exception e) {
			log.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} 
	}

	//201807241859 - esert - COR-398 - COR-479 - COR-477
	@RequestMapping(value = "/beneficiario/{cdVida}", method = { RequestMethod.GET })
	public ResponseEntity<Beneficiario> getBeneficiario(@PathVariable Long cdVida) {
		try {
			Beneficiario beneficiario = beneficiarioService.get(cdVida);
			if(beneficiario==null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return ResponseEntity.ok(beneficiario);
		} catch (Exception e) {
			log.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} 
	}

}

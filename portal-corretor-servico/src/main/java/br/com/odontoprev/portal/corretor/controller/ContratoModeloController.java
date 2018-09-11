package br.com.odontoprev.portal.corretor.controller;

import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.ContratoModelo;
import br.com.odontoprev.portal.corretor.service.ContratoModeloService;

@RestController
public class ContratoModeloController {

	private static final Log log = LogFactory.getLog(ContratoModeloController.class);

	@Autowired
	private ContratoModeloService contratoModeloService;

	//201809111030 - esert - COR-760 - Serviço - cria POST/contratomodelo
	@RequestMapping(value = "/contratomodelo/carregar/arquivo", method = { RequestMethod.POST})
	public ResponseEntity<ContratoModelo> carregarContratoModeloArquivo(@RequestBody ContratoModelo contratoModelo) throws ParseException {
		log.info("carregarContratoModeloArquivo - ini");	
		log.info("materialDivulgacao:[".concat(contratoModelo.toString()).concat("]"));	
		ContratoModelo responseObject = new ContratoModelo();		
		
		try {
			responseObject = contratoModeloService.saveArquivo(contratoModelo);
		} catch (Exception e) {
			log.error("ERRO em getMaterialDivulgacao()", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		log.info("carregarMaterialDivulgacaoArquivo - fim");	
		return ResponseEntity.ok(responseObject);
	}

}

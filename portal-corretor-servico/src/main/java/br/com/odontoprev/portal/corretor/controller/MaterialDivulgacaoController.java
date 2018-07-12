package br.com.odontoprev.portal.corretor.controller;

import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.MateriaisDivulgacao;
import br.com.odontoprev.portal.corretor.dto.MaterialDivulgacao;
import br.com.odontoprev.portal.corretor.service.MaterialDivulgacaoService;

@RestController
public class MaterialDivulgacaoController {
	private static final Log log = LogFactory.getLog(MaterialDivulgacaoController.class);
	
	@Autowired
	private MaterialDivulgacaoService materialDivulgacaoService;

	@RequestMapping(value = "/materiaisdivulgacao", method = { RequestMethod.GET })
	public ResponseEntity<MateriaisDivulgacao> obterMateriaisDivulgacao() throws ParseException {
		log.info("obterMateriaisDivulgacao - ini");	
		MateriaisDivulgacao responseObject = new MateriaisDivulgacao();		
		
		try {
			responseObject = materialDivulgacaoService.getMateriaisDivulgacao();
		} catch (Exception e) {
			log.error("ERRO em obterMateriaisDivulgacao()", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		log.info("obterMateriaisDivulgacao - fim");	
		return ResponseEntity.ok(responseObject);
	}

	@RequestMapping(value = "/materialdivulgacao/{codigoMaterialDivulgacao}", method = { RequestMethod.GET })
	public ResponseEntity<MaterialDivulgacao> obterMaterialDivulgacao(@PathVariable Long codigoMaterialDivulgacao) throws ParseException {
		log.info("obterMaterialDivulgacao - ini");	
		MaterialDivulgacao responseObject = new MaterialDivulgacao();		
		
		try {
			responseObject = materialDivulgacaoService.getMaterialDivulgacao(codigoMaterialDivulgacao);
		} catch (Exception e) {
			log.error("ERRO em getMaterialDivulgacao()", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		log.info("obterMaterialDivulgacao - fim");	
		return ResponseEntity.ok(responseObject);
	}

}

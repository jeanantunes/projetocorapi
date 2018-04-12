package br.com.odontoprev.portal.corretor.controller;


import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.CnpjDados;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.dto.EmpresaDcms;
import br.com.odontoprev.portal.corretor.dto.EmpresaResponse;
import br.com.odontoprev.portal.corretor.service.EmpresaService;

@RestController
public class EmpresaController {
	
	private static final Log log = LogFactory.getLog(EmpresaController.class);
	
	@Autowired
	EmpresaService empresaService;
	
	@RequestMapping(value = "/empresa", method = { RequestMethod.POST })
	public EmpresaResponse addEmpresa(@RequestBody Empresa empresa) {
		
		log.info(empresa);
		
		EmpresaResponse response = empresaService.add(empresa);
		
		return response;
	}
	
	@RequestMapping(value = "/empresa-dcms", method = { RequestMethod.PUT })
	public EmpresaResponse updateEmpresa(@RequestBody EmpresaDcms empresaDcms) {
		
		log.info(empresaDcms);
		
		EmpresaResponse response = empresaService.updateEmpresa(empresaDcms);
		
		return response;
	}
	
	@RequestMapping(value = "/cnpj-dados/{cnpj}", method = { RequestMethod.GET })
	public ResponseEntity<CnpjDados> findCnpjDados(@PathVariable String cnpj) throws ParseException {
		
		log.info("#### findCnpjDados ####");	
		
		CnpjDados cnpjDados = empresaService.findDadosEmpresaByCnpj(cnpj);
		
		return ResponseEntity.ok(cnpjDados);
	}

}

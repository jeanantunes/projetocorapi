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
import br.com.odontoprev.portal.corretor.dto.CnpjDadosAceite;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.dto.EmpresaDcms;
import br.com.odontoprev.portal.corretor.dto.EmpresaEmailAceite;
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

	@RequestMapping(value = "/cnpj-dadosaceite/{cnpj}", method = { RequestMethod.GET })
	public ResponseEntity<CnpjDadosAceite> findCnpjDadosAceite(@PathVariable String cnpj) throws ParseException { //201805111244 - esert - COR-172
		
		log.info("#### findCnpjDadosAceite #### ini");	
		
		CnpjDadosAceite cnpjDadosAceite = empresaService.findDadosEmpresaAceiteByCnpj(cnpj); //201805111244 - esert - COR-172

		log.info("#### findCnpjDadosAceite #### fim");	

		return ResponseEntity.ok(cnpjDadosAceite);
	}
	
	@RequestMapping(value = "/empresa-emailaceite", method = { RequestMethod.PUT })
	public EmpresaResponse updateEmpresaEmailAceite(@RequestBody EmpresaEmailAceite empresaEmailAceite) {
		
		log.info("#### updateEmpresaEmailAceite #### ini");	
		
		log.info(empresaEmailAceite);
		
		EmpresaResponse response = empresaService.updateEmpresaEmailAceite(empresaEmailAceite);
		
		log.info("#### updateEmpresaEmailAceite #### fim");	

		return response;
	}

	@RequestMapping(value = "/empresa/{cdEmpresa}", method = { RequestMethod.GET })
	public ResponseEntity<Empresa> findEmpresa(@PathVariable Long cdEmpresa) throws ParseException { //201807241620 - esert - COR-398
		
		log.info("findEmpresa - ini");	
		
		Empresa empresa = empresaService.findByCdEmpresa(cdEmpresa);

		log.info("findEmpresa - fim");	

		return ResponseEntity.ok(empresa);
	}

}

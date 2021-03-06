package br.com.odontoprev.portal.corretor.controller;


import br.com.odontoprev.portal.corretor.dto.*;
import br.com.odontoprev.portal.corretor.service.EmpresaService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


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

	@RequestMapping(value = "/empresa/arquivo", method = { RequestMethod.POST })
	public ResponseEntity<EmpresaArquivoResponse> gerarArquivo(@RequestBody EmpresaArquivo listCdEmpresasArquivo) {

		log.info("gerarArquivo - ini");

		try {

			if(listCdEmpresasArquivo==null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
			if(listCdEmpresasArquivo.getListCdEmpresa()==null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

			if(listCdEmpresasArquivo.getListCdEmpresa().size() < 1) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

			EmpresaArquivoResponse response = empresaService.gerarArquivoEmpresa(listCdEmpresasArquivo);

			if(response==null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}

			log.info("gerarArquivo - fim");
			return ResponseEntity.ok(response);

		}catch (Exception e) {
			log.info("gerarArquivo - erro");
			log.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
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

	//201807241859 - esert - COR-398 - COR-479 - COR-472
	@RequestMapping(value = "/empresa/{cdEmpresa}", method = { RequestMethod.GET })
	public ResponseEntity<Empresa> findEmpresa(@PathVariable Long cdEmpresa) throws ParseException { //201807241620 - esert - COR-398
		try {
			log.info("findEmpresa - ini");	
			Empresa empresa = empresaService.findByCdEmpresa(cdEmpresa);
			
			if(empresa==null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();			
			}

			log.info("findEmpresa - fim");	
			return ResponseEntity.ok(empresa);
			
		}catch (Exception e) {
			log.info("findEmpresa - erro");	
			log.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();			
		}
	}

	//
	@RequestMapping(value = "/empresa", method = { RequestMethod.PUT })
	public ResponseEntity<EmpresaResponse> updateEmpresa(@RequestBody Empresa empresa) throws ParseException { //201807241620 - esert - COR-398
		try {

			log.info("updateEmpresaEmail - ini");

			if (empresa.getCdEmpresa() == null) {
				log.error("updateEmpresaEmail - cdEmpresa nao informado"); // TODO: Mover para controller
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

			EmpresaResponse empresaRetorno = empresaService.updateEmpresa(empresa);

			if(empresaRetorno==null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}

			log.info("findEmpresa - fim");
			return ResponseEntity.ok(empresaRetorno);

		}catch (Exception e) {
			log.info("findEmpresa - erro");
			log.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	//201809251831 - esert - COR-820 Criar POST /empresa-emailaceite
	@RequestMapping(value = "/empresa-emailaceite", method = { RequestMethod.POST })
	public ResponseEntity<EmpresaResponse> enviarEmpresaEmailAceite(@RequestBody Empresa empresa) {
		try {
			log.info("enviarEmpresaEmailAceite - ini");	
			
			log.info(empresa);
			
			if(empresa.getCdEmpresa()==null) {
				log.info("empresa.getCdEmpresa()==null");
				return ResponseEntity.badRequest().build();
			}
			
			EmpresaResponse response = empresaService.enviarEmpresaEmailAceite(empresa);
			
			if(response==null) {
				return ResponseEntity.noContent().build();
			}
			
			log.info("enviarEmpresaEmailAceite - fim");	
			return ResponseEntity.ok(response);
		
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}

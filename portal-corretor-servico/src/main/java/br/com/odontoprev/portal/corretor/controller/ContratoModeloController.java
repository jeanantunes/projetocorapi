package br.com.odontoprev.portal.corretor.controller;

import java.io.File;
import java.text.ParseException;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraPreenchido;
import br.com.odontoprev.portal.corretor.dto.ContratoModelo;
import br.com.odontoprev.portal.corretor.service.ContratoCorretoraService;
import br.com.odontoprev.portal.corretor.service.ContratoModeloService;

@RestController
public class ContratoModeloController {

	private static final Log log = LogFactory.getLog(ContratoModeloController.class);

	@Autowired
	private ContratoModeloService contratoModeloService;

	@Autowired
	private ContratoCorretoraService contratoCorretoraService;

	//201809111030 - esert - COR-760 - Serviço - cria POST/contratomodelo
	@RequestMapping(value = "/contratomodelo/carregar/arquivo", method = { RequestMethod.POST})
	public ResponseEntity<ContratoModelo> carregarContratoModeloArquivo(@RequestBody ContratoModelo contratoModelo) throws ParseException {
		log.info("carregarContratoModeloArquivo - ini");	
		log.info("contratoModelo:[".concat(contratoModelo.toString()).concat("]"));	
		ContratoModelo responseObject = new ContratoModelo();		
		
		if(contratoModelo.getNomeArquivo()==null) {
			log.info("BAD_REQUEST: contratoModelo.getNomeArquivo()==null");	
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		if(contratoModelo.getNomeArquivo().trim().isEmpty()) {
			log.info("BAD_REQUEST: contratoModelo.getNomeArquivo().trim().isEmpty()");	
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		if(contratoModelo.getCaminhoCarga()==null) {
			log.info("BAD_REQUEST: contratoModelo.getCaminhoCarga()==null");	
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		if(contratoModelo.getCaminhoCarga().trim().isEmpty()) {
			log.info("BAD_REQUEST: contratoModelo.getCaminhoCarga().trim().isEmpty()");	
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		if(contratoModelo.getTipoConteudo()==null) {
			log.info("BAD_REQUEST: contratoModelo.getTipoConteudo()==null");	
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		if(contratoModelo.getTipoConteudo().trim().isEmpty()) {
			log.info("BAD_REQUEST: contratoModelo.getTipoConteudo().trim().isEmpty()");	
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		try {
			File file = new File(contratoModelo.getCaminhoCarga().concat(contratoModelo.getNomeArquivo()));
			log.info("file.toString()" + file.toString());	
			log.info("file.length()" + file.length());	
		} catch (Exception e) {
			log.info("BAD_REQUEST: contratoModelo.getCaminhoCarga().trim().isEmpty()");	
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		try {
			responseObject = contratoModeloService.saveArquivo(contratoModelo);
		} catch (Exception e) {
			log.error("carregarContratoModeloArquivo()", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		log.info("carregarMaterialDivulgacaoArquivo - fim");	
		return ResponseEntity.ok(responseObject);
	}

	//201809112252 - esert - COR-709 - Serviço - Novo serviço GET/contratocorretora/cdCorr/tipo/cdTipo/arquivo?susep=cdSusep
	//201809112315 - esert - COR-759 - Serviço - cria GET/contratomodelo/id/tipo/cdTipo/arquivo para rapida validacao modelo html
	@RequestMapping(value = "/contratomodelo/{cdCorretora}/tipo/{cdContratoModelo}/arquivo", method = {RequestMethod.GET})
	public ResponseEntity<byte[]> getContratoModeloPreenchidoByteArray(@PathVariable Long cdCorretora, @PathVariable Long cdContratoModelo, @RequestParam("susep") Optional<String> cdSusep) throws ParseException {
		log.info("getContratoPreenchidoByteArray - ini");
		try {
			ContratoCorretoraPreenchido contratoCorretoraPreenchido = contratoCorretoraService.getContratoPreenchido(cdCorretora, cdContratoModelo, cdSusep.orElse(null));
			if(contratoCorretoraPreenchido==null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //201808241726 - esert
			}
		    //byte[] arquivoHTML = Base64.getDecoder().decode(contratoCorretoraPreenchido.getContratoPreenchido());
		    byte[] arquivoHTML = contratoCorretoraPreenchido.getContratoPreenchido().getBytes();
		    String[] tipoConteudo = contratoCorretoraPreenchido.getTipoConteudo().split("/");
		    String type = tipoConteudo[0];
		    String subType = tipoConteudo[1];
			log.info("getArquivoByteArray - fim");	
		    return ResponseEntity
		    		.ok()
		    		.contentType(new MediaType(type, subType))
		    		.header(
		    				"Content-Disposition", 
		    				String.format("attachment; filename=%s", contratoCorretoraPreenchido.getNomeArquivo())
		    				)
		    		.body(arquivoHTML);
		} catch (Exception e) {
			log.info("getContratoPreenchidoByteArray - erro");	
		    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); //201808241726 - esert
		}
	}

}

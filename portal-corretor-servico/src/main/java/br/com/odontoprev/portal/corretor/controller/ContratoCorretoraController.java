package br.com.odontoprev.portal.corretor.controller;

import java.text.ParseException;
import java.util.Base64;
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

import br.com.odontoprev.portal.corretor.dto.ContratoCorretora;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraDataAceite;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraPreenchido;
import br.com.odontoprev.portal.corretor.service.ContratoCorretoraService;

@RestController
public class ContratoCorretoraController {

    private static final Log log = LogFactory.getLog(ContratoCorretoraController.class);

    @Autowired
    private ContratoCorretoraService contratoCorretoraService;

    @RequestMapping(value = "/contratocorretora/{cdCorretora}/dataaceite", method = {RequestMethod.GET})
    public ResponseEntity<ContratoCorretoraDataAceite> getDataAceiteContratoByCdCorretora(@PathVariable Long cdCorretora) throws ParseException {

        try {

            if (cdCorretora == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            ContratoCorretoraDataAceite dtoDataAceiteContrato = contratoCorretoraService.getDataAceiteContratoByCdCorretora(cdCorretora);

            if (dtoDataAceiteContrato == null) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(dtoDataAceiteContrato);

        } catch (Exception e) {

            log.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    //201809101646 - esert - COR-709 - Serviço - Novo serviço GET/contratocorretora/cdCor/tipo/cdTipo
    @RequestMapping(value = "/contratocorretora/{cdCorretora}/tipo/{cdContratoModelo}", method = {RequestMethod.GET})
    public ResponseEntity<ContratoCorretoraPreenchido> getContratoPreenchido(@PathVariable Long cdCorretora, @PathVariable Long cdContratoModelo, @RequestParam("cdSusep") Optional<String> cdSusep) throws ParseException {
    	log.info("getContratoPreenchido - ini");

        try {

            if (cdCorretora == null) {
            	log.info("getContratoPreenchido - BAD_REQUEST - cdCorretora == null");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            
            if (cdContratoModelo == null) {
                log.info("getContratoPreenchido - BAD_REQUEST - cdContratoModelo == null");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            //ContratoCorretoraPreenchido contratoCorretoraPreenchido = contratoCorretoraService.getContratoPreenchidoDummy(cdCorretora, cdContratoModelo);
            ContratoCorretoraPreenchido contratoCorretoraPreenchido = contratoCorretoraService.getContratoPreenchido(cdCorretora, cdContratoModelo, cdSusep.orElse(null)); //201809112202 - esert

            if (contratoCorretoraPreenchido == null) {
                log.info("getContratoPreenchido - noContent - contratoCorretoraPreenchido == null");
                return ResponseEntity.noContent().build();
            }

        	log.info("getContratoPreenchido - fim");
            return ResponseEntity.ok(contratoCorretoraPreenchido);

        } catch (Exception e) {
            log.info("getContratoPreenchido - INTERNAL_SERVER_ERROR - Exception");
            log.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //201809111530 - jantu/esert - COR-711 - Serviço - Novo serviço POST/contratocorretora
    @RequestMapping(value = "/contratocorretora", method = {RequestMethod.POST})
    public ResponseEntity<ContratoCorretora> postContratoCorretora(@RequestBody ContratoCorretora contratoCorretora) {
        log.info("postContratoCorretora - ini");
        ContratoCorretora contratoCorretoraResponse = null;
        
        if(contratoCorretora.getCdCorretora()==null) {
            log.error("postContratoCorretora - BAD_REQUEST - contratoCorretora.getCdCorretora()==null");
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        try {
        	contratoCorretoraResponse = contratoCorretoraService.postContratoCorretora(contratoCorretora);
        }catch (Exception e) {
			// TODO: handle exception
        	ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        
        log.info("postContratoCorretora - fim");
        return ResponseEntity.ok(contratoCorretoraResponse);
    }

    //201809112252 - esert - COR-709 - Serviço - Novo serviço GET/contratocorretora/cdCorr/tipo/cdTipo/arquivo?susep=cdSusep
    @RequestMapping(value = "/contratocorretora/{cdCorretora}/tipo/{cdContratoModelo}/arquivo", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> getContratoPreenchidoByteArray(@PathVariable Long cdCorretora, @PathVariable Long cdContratoModelo, @RequestParam("susep") Optional<String> cdSusep) throws ParseException {
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

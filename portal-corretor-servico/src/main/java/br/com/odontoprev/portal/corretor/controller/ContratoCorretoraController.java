package br.com.odontoprev.portal.corretor.controller;

import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public ResponseEntity<ContratoCorretoraPreenchido> getContratoPreenchido(@PathVariable Long cdCorretora, @PathVariable Long cdContratoModelo) throws ParseException {

        try {

            if (cdCorretora == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            ContratoCorretoraPreenchido dtoContratoCorretoraPreenchido = contratoCorretoraService.getContratoPreenchido(cdCorretora, cdContratoModelo);

            if (dtoContratoCorretoraPreenchido == null) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(dtoContratoCorretoraPreenchido);

        } catch (Exception e) {

            log.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }


    @RequestMapping(value = "/contratocorretora", method = {RequestMethod.POST})
    public ResponseEntity<ContratoCorretora> postContratoCorretora(@RequestBody ContratoCorretora contratoCorretora) {
        log.info("postContratoCorretora - ini");
        ContratoCorretora contratoCorretoraResponse = null;
        
        if(contratoCorretora.getCdCorretora()==null) {
            log.error("postContratoCorretora - BAD_REQUEST - contratoCorretora.getCdCorretora()==null");
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        if(contratoCorretora.getCdSusep()==null) {
            log.error("postContratoCorretora - BAD_REQUEST - contratoCorretora.getCdSusep()==null");
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

}

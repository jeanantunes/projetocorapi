package br.com.odontoprev.portal.corretor.controller;

import br.com.odontoprev.portal.corretor.dao.ContratoCorretoraDAO;
import br.com.odontoprev.portal.corretor.dao.ContratoModeloDAO;
import br.com.odontoprev.portal.corretor.dao.CorretoraDAO;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretora;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraDataAceite;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraPreenchido;
import br.com.odontoprev.portal.corretor.model.TbodContratoCorretora;
import br.com.odontoprev.portal.corretor.model.TbodContratoModelo;
import br.com.odontoprev.portal.corretor.model.TbodCorretora;
import br.com.odontoprev.portal.corretor.service.ContratoCorretoraService;
import br.com.odontoprev.portal.corretor.util.Constantes;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class ContratoCorretoraController {

    private static final Log log = LogFactory.getLog(ContratoCorretoraController.class);

    @Autowired
    private ContratoCorretoraService contratoCorretoraService;

    @Autowired
    private CorretoraDAO corretoraDAO;

    @Autowired
    private ContratoModeloDAO contratoModeloDAO;

    @Autowired
    private ContratoCorretoraDAO contratoCorretoraDAO;

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
        TbodCorretora tbodCorretora = corretoraDAO.findOne(contratoCorretora.getCdCorretora());

        if (tbodCorretora == null) {
            log.error("postContratoCorretora - BAD_REQUEST - tbodCorretora null");
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (contratoCorretora.getCdSusep() != null) {
            tbodCorretora.setTemSusep(Constantes.SIM);
            tbodCorretora.setCodigoSusep(contratoCorretora.getCdSusep());
            contratoCorretora.setCdContratoModelo(1L); // 1 - Contrato Corretagem
        } else if (contratoCorretora.getCdSusep() == null) {
            tbodCorretora.setTemSusep(Constantes.NAO);
            tbodCorretora.setCodigoSusep(null);
            contratoCorretora.setCdContratoModelo(2L); // 2 - Contrato Intermediação
        }

        tbodCorretora = corretoraDAO.save(tbodCorretora);

        TbodContratoCorretora tbodContratoCorretora = new TbodContratoCorretora();

        tbodContratoCorretora.setTbodCorretora(tbodCorretora);

        TbodContratoModelo tbodContratoModelo = contratoModeloDAO.findOne(contratoCorretora.getCdContratoModelo());

        if (tbodContratoModelo == null) {
            log.error("ERROR: tbodContratoModelo == null para " + contratoCorretora.getCdContratoModelo());
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        tbodContratoCorretora.setTbodContratoModelo(tbodContratoModelo);
        tbodContratoCorretora.setDtAceiteContrato(new Date());

        tbodContratoCorretora = contratoCorretoraDAO.save(tbodContratoCorretora);

        contratoCorretora.setCdContratoCorretora(tbodContratoCorretora.getCdContratoCorretora());
        contratoCorretora.setDtAceiteContrato(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tbodContratoCorretora.getDtAceiteContrato()));

        log.info("postContratoCorretora - fim");

        return ResponseEntity.ok(contratoCorretora);
    }

}

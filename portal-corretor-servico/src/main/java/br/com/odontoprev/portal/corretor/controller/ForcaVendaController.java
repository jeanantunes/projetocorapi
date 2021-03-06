package br.com.odontoprev.portal.corretor.controller;

import br.com.odontoprev.portal.corretor.dto.EmailForcaVendaCorretora;
import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.dto.ForcaVendaResponse;
import br.com.odontoprev.portal.corretor.dto.Login;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ForcaVendaController {

    private static final Log log = LogFactory.getLog(ForcaVendaController.class);

    @Autowired
    ForcaVendaService forcaVendaService;

    @RequestMapping(value = "/forcavenda", method = {RequestMethod.POST})
    public ForcaVendaResponse addForcaVenda(@RequestBody ForcaVenda forcaVenda) {

        log.info(forcaVenda);

        return forcaVendaService.addForcaVenda(forcaVenda);
    }

    @RequestMapping(value = "/forcavenda", method = {RequestMethod.PUT})
    public ResponseEntity<ForcaVendaResponse> updateForcaVenda(@RequestBody ForcaVenda forcaVenda) {
        log.info("updateForcaVenda - ini");
        log.info(forcaVenda);
        try {
            ForcaVendaResponse forcaVendaResponse = forcaVendaService.updateForcaVenda(forcaVenda);
            //TODO: tratar retorno erro com APP@Yago //201808101839
            if (forcaVendaResponse == null) {
                log.info("updateForcaVenda - fim");
                return ResponseEntity.noContent().build();
            }
            log.info("updateForcaVenda - fim");
            return ResponseEntity.ok(forcaVendaResponse);
        } catch (final Exception e) {
            log.error("ERROR: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/forcavenda/login", method = {RequestMethod.PUT})
    public ForcaVendaResponse updateForcaVendaLogin(@RequestBody ForcaVenda forcaVenda) {

        log.info(forcaVenda);

        return forcaVendaService.updateForcaVendaLogin(forcaVenda);
    }

    @RequestMapping(value = "/forcavenda/status-ativo", method = {RequestMethod.PUT})
    public ForcaVendaResponse updateForcaVendaStatus(@RequestBody ForcaVenda forcaVenda) {

        log.info(forcaVenda);

        return forcaVendaService.updateForcaVendaStatusByCpf(forcaVenda);
    }

    @RequestMapping(value = "/forcavenda/{cpf}", method = {RequestMethod.GET})
    public ForcaVenda findForcaVendaByCpf(@PathVariable String cpf) {

        log.info(cpf);

        return forcaVendaService.findForcaVendaByCpf(cpf); // TODO: Debito tecnico retornar response entity 20180919
    }

    @RequestMapping(value = "/forcavenda/{cdForcaVenda}/corretora/{cnpj}", method = {RequestMethod.GET})
    public ForcaVendaResponse findAssocForcaVendaCorretora(@PathVariable Long cdForcaVenda, @PathVariable String cnpj) {

        log.info("cdForcaVenda [" + cdForcaVenda + "] + cnpj [" + cnpj + "]");

        return forcaVendaService.findAssocForcaVendaCorretora(cdForcaVenda, cnpj);
    }

    @RequestMapping(value = "/forcavendastatus/{cdStatusForcaVenda}/corretora/{cdCorretora}", method = {RequestMethod.GET})
    public List<ForcaVenda> findForcaVendasByCdCorretoraStatusAprovacao(@PathVariable Long cdStatusForcaVenda, @PathVariable Long cdCorretora) {

        log.info("cdStatusForcaVenda [" + cdStatusForcaVenda + "], cdCorretora [" + cdCorretora + "]");

        return forcaVendaService.findForcaVendasByCdStatusForcaCdCorretora(cdStatusForcaVenda, cdCorretora);
    }

    @RequestMapping(value = "/forcavenda/corretora/{cdCorretora}", method = {RequestMethod.GET})
    public List<ForcaVenda> findForcaVendasByCdCorretora(@PathVariable Long cdCorretora) {

        log.info("cdCorretora [" + cdCorretora + "]");

        //return forcaVendaService.findForcaVendasByCdStatusForcaCdCorretora(cdCorretora);
        return forcaVendaService.findForcaVendasByForcaCdCorretora(cdCorretora);
    }

    @RequestMapping(value = "/forcavenda/status-excluido", method = {RequestMethod.PUT})
    public ForcaVendaResponse updateForcaVendaStatusExcluir(@RequestBody ForcaVenda forcaVenda) throws Exception {
        log.info("[updateForcaVendaStatusExcluir]");
        return forcaVendaService.updateForcaVendaStatusByCpf(forcaVenda, "EXCLUIR");
    }

    @RequestMapping(value = "/forcavenda/status-reprovado", method = {RequestMethod.PUT})
    public ForcaVendaResponse updateForcaVendaStatusReprovar(@RequestBody ForcaVenda forcaVenda) throws Exception {
        log.info("[updateForcaVendaStatusReprovar]");
        return forcaVendaService.updateForcaVendaStatusByCpf(forcaVenda, "REPROVAR");
    }

    @RequestMapping(value = "/forcavenda/{cdForcaVenda}/email", method = {RequestMethod.GET})
    public ResponseEntity<EmailForcaVendaCorretora> getEmailForcaVendaCorretora(@PathVariable Long cdForcaVenda) throws Exception {
        log.info("[getEmailForcaVendaCorretora] - ini");

        EmailForcaVendaCorretora emailForcaVendaCorretora = null;

        try {

            emailForcaVendaCorretora = forcaVendaService.findByCdForcaVendaEmail(cdForcaVenda);

            if (emailForcaVendaCorretora == null){

                log.info("[getEmailForcaVendaCorretora] - forcaVenda nao encontrado");
                log.info("[getEmailForcaVendaCorretora] - fim");
                return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
            }

            log.info("[getEmailForcaVendaCorretora] - fim");
            ResponseEntity.ok(emailForcaVendaCorretora);

        }catch (Exception e){

            log.error("[getEmailForcaVendaCorretora] - error");
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

        log.info("[getEmailForcaVendaCorretora] - fim");
        return ResponseEntity.ok(emailForcaVendaCorretora);
    }

    @RequestMapping(value = "/forcavenda/bloqueio", method = {RequestMethod.GET})
    public ResponseEntity<Login> getForcaVendaBloqueio(@RequestParam("cpf") Optional<String> cpf,
                                                       @RequestParam("cdForcaVenda") Optional<Long> cdForcaVenda) {
        log.info("[getForcaVendaBloqueio]");
        Login responseObject = null;

        try {
            if (cpf.isPresent()) {
                ForcaVenda forcaVenda = forcaVendaService.findForcaVendaByCpf(cpf.get());
                if (forcaVenda == null) {
                    log.info("findForcaVendaByCpf == NULL - fim");
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }
                responseObject = new Login();
                responseObject.setTemBloqueio(forcaVenda.getLogin().getTemBloqueio());
                responseObject.setCodigoTipoBloqueio(forcaVenda.getLogin().getCodigoTipoBloqueio());
                responseObject.setDescricaoTipoBloqueio(forcaVenda.getLogin().getDescricaoTipoBloqueio());
            } else if (cdForcaVenda.isPresent()) {
                ForcaVenda forcaVenda = forcaVendaService.findByCdForcaVenda(cdForcaVenda.get());
                if (forcaVenda == null) {
                    log.info("findByCdForcaVenda == NULL - fim");
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }
                responseObject = new Login();
                responseObject.setTemBloqueio(forcaVenda.getLogin().getTemBloqueio());
                responseObject.setCodigoTipoBloqueio(forcaVenda.getLogin().getCodigoTipoBloqueio());
                responseObject.setDescricaoTipoBloqueio(forcaVenda.getLogin().getDescricaoTipoBloqueio());
            }else {
                log.info("cpf == NULL AND CdForcaVenda == null - fim");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            log.error("ERRO em getForcaVendaBloqueio()", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        log.info("getForcaVendaBloqueio - fim");
        return ResponseEntity.ok(responseObject);
    }
}

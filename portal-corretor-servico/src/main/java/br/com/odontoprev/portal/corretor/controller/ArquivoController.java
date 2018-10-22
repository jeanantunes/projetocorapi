package br.com.odontoprev.portal.corretor.controller;

import br.com.odontoprev.portal.corretor.service.ArquivoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ArquivoController {

    private static final Log log = LogFactory.getLog(ArquivoController.class);

    @Autowired
    private ArquivoService arquivoService;

    //TODO: Completar ArquivoService
    /*
    @RequestMapping(value = "/arquivo/carregar", method = {RequestMethod.POST})
    public ResponseEntity<Arquivo> carregarArquivo(@RequestBody Arquivo arquivo) throws ParseException {
        log.info("carregarArquivo - ini");
        log.info("arquivoInfo:[".concat(arquivo.toString()).concat("]"));
        Arquivo responseObject;

        try {
            responseObject = arquivoService.save(arquivo, true);
        } catch (Exception e) {
            log.error("ERRO em carregarArquivo()", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        log.info("carregarArquivo - fim");
        return ResponseEntity.ok(responseObject);
    }
    */

    //TODO: Completar ArquivoService
    /*
    @RequestMapping(value = "/arquivo/{cdArquivo}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getArquivoByteArray(@PathVariable("cdArquivo") Long cdArquivo) {
        log.info("getArquivoByteArray - ini");
        try {
            Arquivo arquivo = arquivoService.getByCdArquivo(cdArquivo, true);
            if (arquivo == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            byte[] arquivoPDF = Base64.getDecoder().decode(arquivo.getArquivoBase64());
            String[] tipoConteudo = arquivo.getTipoConteudo().split("/");
            String type = tipoConteudo[0];
            String subType = tipoConteudo[1];
            log.info("getArquivoByteArray - fim");
            return ResponseEntity
                    .ok()
                    .contentType(new MediaType(type, subType))
                    .header(
                            "Content-Disposition",
                            String.format("attachment; filename=%s", arquivo.getNomeArquivo())
                    )
                    .body(arquivoPDF);
        } catch (Exception e) {
            log.info("getArquivoByteArray - erro");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    */
}

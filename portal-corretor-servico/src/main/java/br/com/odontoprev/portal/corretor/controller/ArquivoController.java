package br.com.odontoprev.portal.corretor.controller;

import java.text.ParseException;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.Arquivo;
import br.com.odontoprev.portal.corretor.service.ArquivoService;

//201810220000 - jota - COR-723:API - Novo GET/ARQUIVO/(ID) - assinaturas dos metodos
//201810231800 - esert - COR-723:API - Novo GET/ARQUIVO/(ID)
@RestController
public class ArquivoController {

    private static final Logger log = LoggerFactory.getLogger(ArquivoController.class);

    @Autowired
    private ArquivoService arquivoService;

    //201810231800 - esert - COR-723:API - Novo GET/ARQUIVO/(ID)
    @RequestMapping(value = "/arquivo/arquivo/carregar", method = {RequestMethod.POST})
    public ResponseEntity<Arquivo> carregarArquivo(@RequestBody Arquivo arquivo) throws ParseException {
        log.info("carregarArquivo - ini");
        log.info("arquivoInfo:[".concat(arquivo.toString()).concat("]"));
        Arquivo responseObject;

        try {
            responseObject = arquivoService.saveArquivo(arquivo);
        } catch (Exception e) {
            log.error("ERRO em carregarArquivo()", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        log.info("carregarArquivo - fim");
        return ResponseEntity.ok(responseObject);
    }

    //201810231800 - esert - COR-723:API - Novo GET/ARQUIVO/(ID)
    @RequestMapping(value = "/arquivo/{cdArquivo}/arquivo", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getArquivoByteArray(@PathVariable("cdArquivo") Long cdArquivo) {
        log.info("getArquivoByteArray - ini");
        try {
            Arquivo arquivo = arquivoService.getByCdArquivo(cdArquivo);
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
    
    //201810231800 - esert - COR-723:API - Novo GET/ARQUIVO/(ID)
    @RequestMapping(value = "/arquivo/{cdArquivo}/json", method = RequestMethod.GET)
    public ResponseEntity<Arquivo> getArquivo(@PathVariable("cdArquivo") Long cdArquivo) {
    	log.info("getArquivo - ini");
    	try {
    		Arquivo arquivo = arquivoService.getByCdArquivo(cdArquivo);
    		if (arquivo == null) {
    			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    		}
    		log.info("getArquivo - fim");
    		return ResponseEntity.ok(arquivo);
    	} catch (Exception e) {
    		log.info("getArquivo - erro");
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    	}
    }
}

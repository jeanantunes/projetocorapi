package br.com.odontoprev.portal.corretor.controller;

import br.com.odontoprev.portal.corretor.dto.ArquivoContratacao;
import br.com.odontoprev.portal.corretor.service.ArquivoContratacaoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Base64;

//201808241147 - esert - COR-620 tdd servico obter pdf pme
//201808241147 - esert - COR-619 criar servico obter pdf pme
@RestController
public class ArquivoContratacaoController {
	private static final Log log = LogFactory.getLog(ArquivoContratacaoController.class);
	
	@Autowired
	private ArquivoContratacaoService arquivoContratacaoService;

	@RequestMapping(value = "/arquivocontratacao/empresa/{cdEmpresa}/json", method = { RequestMethod.GET })
	public ResponseEntity<ArquivoContratacao> getArquivoContratacao(@PathVariable Long cdEmpresa) throws ParseException {
		log.info("getArquivoContratacao - ini");	
		ArquivoContratacao responseObject = new ArquivoContratacao();		
		
		try {
			responseObject = arquivoContratacaoService.getByCdEmpresa(cdEmpresa, true);
			if(responseObject==null) {
				log.info("getArquivoContratacao - fim");	
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //201808241728 - esert
			}
		} catch (Exception e) {
			log.error("ERRO em getArquivoContratacao()", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		log.info("getArquivoContratacao - fim");	
		return ResponseEntity.ok(responseObject);
	}

	@RequestMapping(value = "/arquivocontratacao/carregar/arquivo", method = { RequestMethod.POST})
	public ResponseEntity<ArquivoContratacao> carregarArquivoContratacaoArquivo(@RequestBody ArquivoContratacao arquivoContratacao) throws ParseException {
		log.info("carregarArquivoContratacaoArquivo - ini");	
		log.info("materialDivulgacao:[".concat(arquivoContratacao.toString()).concat("]"));	
		ArquivoContratacao responseObject = new ArquivoContratacao();		
		
		try {
			responseObject = arquivoContratacaoService.save(arquivoContratacao, true);
		} catch (Exception e) {
			log.error("ERRO em carregarArquivoContratacaoArquivo()", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		log.info("carregarArquivoContratacaoArquivo - fim");	
		return ResponseEntity.ok(responseObject);
	}

	@RequestMapping(value = "/arquivocontratacao/empresa/{cdEmpresa}/arquivo", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getArquivoByteArray(@PathVariable("cdEmpresa") Long cdEmpresa) {
		log.info("getArquivoByteArray - ini");
		try {
			ArquivoContratacao arquivoContratacao = arquivoContratacaoService.getByCdEmpresa(cdEmpresa, true);
			if(arquivoContratacao==null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //201808241726 - esert
			}
		    byte[] arquivoPDF = Base64.getDecoder().decode(arquivoContratacao.getArquivoBase64()); //201808311641 - bugfix
		    String[] tipoConteudo = arquivoContratacao.getTipoConteudo().split("/");
		    String type = tipoConteudo[0];
		    String subType = tipoConteudo[1];
			log.info("getArquivoByteArray - fim");	
		    return ResponseEntity
		    		.ok()
		    		.contentType(new MediaType(type, subType))
		    		.header(
		    				"Content-Disposition", 
		    				String.format("attachment; filename=%s", arquivoContratacao.getNomeArquivo())
		    				)
		    		.body(arquivoPDF);
		} catch (Exception e) {
			log.info("getArquivoByteArray - erro");	
		    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); //201808241726 - esert
		}
	}

	//2018082711100 - esert - COR-617 - rota para facilitar testes sem uso funcional
	@RequestMapping(value = "/arquivocontratacao/empresa/{cdEmpresa}/test", method = { RequestMethod.GET })
	public ResponseEntity<ArquivoContratacao> createArquivoContratacaoByEmpresa(@PathVariable Long cdEmpresa) throws ParseException {
		try {
			ArquivoContratacao dto = arquivoContratacaoService.createPdfPmePorEmpresa(cdEmpresa);
			if(dto==null) {
				return ResponseEntity.noContent().build(); //201808271933 - esert
			}
			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); //201808271933 - esert
		}
	}

}

package br.com.odontoprev.portal.corretor.controller;

import java.text.ParseException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.MateriaisDivulgacao;
import br.com.odontoprev.portal.corretor.dto.MateriaisDivulgacaoCarga;
import br.com.odontoprev.portal.corretor.dto.MaterialDivulgacao;
import br.com.odontoprev.portal.corretor.service.MaterialDivulgacaoService;

@RestController
public class MaterialDivulgacaoController {
	private static final Log log = LogFactory.getLog(MaterialDivulgacaoController.class);
	
	@Autowired
	private MaterialDivulgacaoService materialDivulgacaoService;

	@RequestMapping(value = "/materiaisdivulgacao/{tipoInterface}", method = { RequestMethod.GET })
	public ResponseEntity<MateriaisDivulgacao> obterMateriaisDivulgacao(@PathVariable("tipoInterface") String tipoInterface) throws ParseException {
		log.info("obterMateriaisDivulgacao - ini");	
		MateriaisDivulgacao responseObject = new MateriaisDivulgacao();		
		
		try {
			responseObject = materialDivulgacaoService.getMateriaisDivulgacao(tipoInterface);
		} catch (Exception e) {
			log.error("ERRO em obterMateriaisDivulgacao()", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		log.info("obterMateriaisDivulgacao - fim");	
		return ResponseEntity.ok(responseObject);
	}

	@RequestMapping(value = "/materialdivulgacao/{codigoMaterialDivulgacao}", method = { RequestMethod.GET })
	public ResponseEntity<MaterialDivulgacao> obterMaterialDivulgacao(@PathVariable Long codigoMaterialDivulgacao) throws ParseException {
		log.info("obterMaterialDivulgacao - ini");	
		MaterialDivulgacao responseObject = new MaterialDivulgacao();		
		
		try {
			responseObject = materialDivulgacaoService.getMaterialDivulgacao(codigoMaterialDivulgacao, true);
		} catch (Exception e) {
			log.error("ERRO em getMaterialDivulgacao()", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		log.info("obterMaterialDivulgacao - fim");	
		return ResponseEntity.ok(responseObject);
	}

	@RequestMapping(value = "/materialdivulgacao/carregar/arquivo", method = { RequestMethod.POST})
	public ResponseEntity<MaterialDivulgacao> carregarMaterialDivulgacaoArquivo(@RequestBody MaterialDivulgacao materialDivulgacao) throws ParseException {
		log.info("carregarMaterialDivulgacaoArquivo - ini");	
		log.info("materialDivulgacao:[".concat(materialDivulgacao.toString()).concat("]"));	
		MaterialDivulgacao responseObject = new MaterialDivulgacao();		
		
		try {
			responseObject = materialDivulgacaoService.save(materialDivulgacao, false, true);
		} catch (Exception e) {
			log.error("ERRO em getMaterialDivulgacao()", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		log.info("carregarMaterialDivulgacaoArquivo - fim");	
		return ResponseEntity.ok(responseObject);
	}

	@RequestMapping(value = "/materialdivulgacao/carregar/thumbnail", method = { RequestMethod.POST})
	public ResponseEntity<MaterialDivulgacao> carregarMaterialDivulgacaoThumbnail(@RequestBody MaterialDivulgacao materialDivulgacao) throws ParseException {
		log.info("carregarMaterialDivulgacaoThumbnail - ini");	
		log.info("materialDivulgacao:[".concat(materialDivulgacao.toString()).concat("]"));	
		MaterialDivulgacao responseObject = new MaterialDivulgacao();		
		
		try {
			responseObject = materialDivulgacaoService.save(materialDivulgacao, true, false);
		} catch (Exception e) {
			log.error("ERRO em getMaterialDivulgacao()", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		log.info("carregarMaterialDivulgacaoThumbnail - fim");	
		return ResponseEntity.ok(responseObject);
	}

	//201807161117 - esert - qg para validacao da imagem obtida
	//201810241452 - esert/yalm - COR-721:API - Novo POST/ARQUIVO Fazer Carga - especializar a rota para evitar duplicidade com novas rotas do ArquivoController.
	@RequestMapping(value = "/materialdivulgacao/arquivo/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getArquivo(@PathVariable("id") Long id) {
	    byte[] image = Base64.decodeBase64(materialDivulgacaoService.getMaterialDivulgacao(id, true).getArquivo());
	    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
	}

	//201807181117 - esert - qg para carga de imagens em atacado
	@RequestMapping(value = "/thumbnail/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getThumbnail(@PathVariable("id") Long id) {
	    byte[] image = Base64.decodeBase64(materialDivulgacaoService.getMaterialDivulgacao(id, false).getThumbnail());
	    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
	}

	@RequestMapping(value = "/materialdivulgacao/carregar/lista", method = { RequestMethod.POST})
	public ResponseEntity<MateriaisDivulgacaoCarga> carregarMaterialDivulgacaoLista(@RequestBody MateriaisDivulgacaoCarga materiaisDivulgacaoCarga) throws ParseException {
		log.info("carregarMaterialDivulgacaoLista - ini");	
		log.info("materiaisDivulgacaoCarga.size:[".concat(materiaisDivulgacaoCarga.toString()).concat("]"));
		
		MateriaisDivulgacaoCarga responseObject = new MateriaisDivulgacaoCarga();		
		responseObject.setMateriaisDivulgacao(new ArrayList<MaterialDivulgacao>());
		try {
			if(materiaisDivulgacaoCarga.getMateriaisDivulgacao()!=null) {
				for(MaterialDivulgacao materialDivulgacao : materiaisDivulgacaoCarga.getMateriaisDivulgacao()){
					responseObject.getMateriaisDivulgacao().add(materialDivulgacaoService.saveArquivoThumbnail(materialDivulgacao));				
				}
			}
		} catch (Exception e) {
			log.error("ERRO em materialDivulgacaoService.save()", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		log.info("carregarMaterialDivulgacaoLista - fim");	
		return ResponseEntity.ok(responseObject);
	}
}

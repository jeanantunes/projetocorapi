package br.com.odontoprev.portal.corretor.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.UploadForcaCriticados;
import br.com.odontoprev.portal.corretor.service.UploadService;

@RestController
public class CriticadosController {
	
	private static final Log log = LogFactory.getLog(CriticadosController.class);
	
	@Autowired
	UploadService uploadService;
	
	@RequestMapping(value = "/forcaCriticados", method = { RequestMethod.GET })
	public List<UploadForcaCriticados> buscaCriticados() {
		
		log.info("##### força criticados #####");
		
		List<UploadForcaCriticados> criticados = uploadService.getCriticados();
		
		return criticados;
	}

}

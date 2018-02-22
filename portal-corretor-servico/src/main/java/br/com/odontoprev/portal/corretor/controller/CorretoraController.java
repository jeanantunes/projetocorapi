package br.com.odontoprev.portal.corretor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.CorretoraResponse;
import br.com.odontoprev.portal.corretor.service.CorretoraService;

@RestController
public class CorretoraController {
	
	@Autowired
	CorretoraService corretoraService;
	
	@RequestMapping(value = "/corretora", method = { RequestMethod.POST })
	public CorretoraResponse addCorretora(@RequestBody Corretora corretora) {
		return corretoraService.addCorretora(corretora);
	}
	
	
	@RequestMapping(value = "/corretor", method = { RequestMethod.POST })
	public CorretoraResponse addCorretor(@RequestBody Corretora corretora) {
		return corretoraService.addCorretor(corretora);
	}
	

}

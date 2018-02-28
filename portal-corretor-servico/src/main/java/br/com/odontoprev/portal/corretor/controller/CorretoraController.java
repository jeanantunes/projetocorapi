package br.com.odontoprev.portal.corretor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping(value = "/corretora", method = { RequestMethod.PUT })
	public CorretoraResponse updateCorretora(@RequestBody Corretora corretora) {
		return corretoraService.updateCorretora(corretora);
	}
	
	@RequestMapping(value = "/corretor", method = { RequestMethod.POST })
	public CorretoraResponse addCorretor(@RequestBody Corretora corretora) {
		return corretoraService.addCorretor(corretora);
	}
	
	@RequestMapping(value = "/corretora/{cnpj}", method = { RequestMethod.GET })
	public Corretora buscaPorCnpj(@PathVariable String cnpj) {
		return corretoraService.buscaCorretoraPorCnpj(cnpj);
	}

}

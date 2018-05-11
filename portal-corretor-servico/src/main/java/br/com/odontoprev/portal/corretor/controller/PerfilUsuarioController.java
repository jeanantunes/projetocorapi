package br.com.odontoprev.portal.corretor.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.odontoprev.portal.corretor.dto.Perfil;
import br.com.odontoprev.portal.corretor.service.PerfilUsuarioService;

@Controller
public class PerfilUsuarioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PerfilUsuarioService service;
	
	
	@RequestMapping(value = "/perfil/usuario/{usuario}", method = { RequestMethod.GET })	
	public @ResponseBody Perfil findByUsuario(@PathVariable String usuario) {
		return service.buscarPorUsuario(usuario);
	}

}

package br.com.odontoprev.portal.corretor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.PerfilUsuarioDAO;
import br.com.odontoprev.portal.corretor.dto.Perfil;
import br.com.odontoprev.portal.corretor.model.TbodPerfilUsuario;

@Service
public class PerfilUsuarioService {
	
	@Autowired
	private PerfilUsuarioDAO dao;
	
	

	public Perfil buscarPorUsuario(String usuario) {
		TbodPerfilUsuario perfilUsuario = dao.findByUsuario(usuario);
		Perfil perfil = new Perfil(perfilUsuario.getPerfil());
		return perfil;
	}
	
}

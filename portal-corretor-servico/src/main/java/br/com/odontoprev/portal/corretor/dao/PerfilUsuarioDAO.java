package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodPerfilUsuario;

@Repository
public interface PerfilUsuarioDAO extends CrudRepository<TbodPerfilUsuario, Integer> {


	public TbodPerfilUsuario findByUsuario(String usuario);
}

package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodVendaVida;

@Repository
public interface VendaVidaDAO extends CrudRepository<TbodVendaVida, Long> {
	
	public TbodVendaVida findByTbodVidaCdVida(Long cdVida);

}

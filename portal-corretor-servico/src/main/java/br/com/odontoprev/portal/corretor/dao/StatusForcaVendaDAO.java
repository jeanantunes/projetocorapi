package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodStatusForcaVenda;

@Repository
public interface StatusForcaVendaDAO extends CrudRepository<TbodStatusForcaVenda, Long> {

}

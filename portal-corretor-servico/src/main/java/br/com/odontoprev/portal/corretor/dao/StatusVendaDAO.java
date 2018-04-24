package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodStatusVenda;

@Repository
public interface StatusVendaDAO extends CrudRepository<TbodStatusVenda, Long> {

}

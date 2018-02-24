package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodVenda;

@Repository
public interface VendaDAO extends CrudRepository<TbodVenda, Long> {

}

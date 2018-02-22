package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.odontoprev.portal.corretor.model.TbodVenda;

public interface VendaDAO extends CrudRepository<TbodVenda, Long> {

}

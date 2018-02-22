package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.odontoprev.portal.corretor.model.TbodBancoConta;

public interface BancoContaDAO extends CrudRepository<TbodBancoConta, Long>{

}

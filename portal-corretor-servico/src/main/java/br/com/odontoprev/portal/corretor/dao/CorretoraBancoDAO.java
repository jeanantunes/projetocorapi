package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodCorretoraBanco;

@Repository
public interface CorretoraBancoDAO extends CrudRepository<TbodCorretoraBanco, Long>{



}

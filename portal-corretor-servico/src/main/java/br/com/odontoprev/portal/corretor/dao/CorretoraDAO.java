package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodCorretora;

@Repository
public interface CorretoraDAO extends CrudRepository<TbodCorretora, Long>{
	
	public TbodCorretora findByCnpj(String cnpj);
}

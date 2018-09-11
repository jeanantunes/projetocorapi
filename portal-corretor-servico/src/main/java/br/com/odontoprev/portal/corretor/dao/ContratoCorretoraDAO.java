package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodContratoCorretora;

@Repository
public interface ContratoCorretoraDAO extends CrudRepository<TbodContratoCorretora, Long>{
	
	public TbodContratoCorretora findByTbodCorretoraCdCorretora(Long cdCorretora);
	
}

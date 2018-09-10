package br.com.odontoprev.portal.corretor.dao;

import br.com.odontoprev.portal.corretor.model.TbodContratoCorretora;
import br.com.odontoprev.portal.corretor.model.TbodCorretora;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoCorretoraDAO extends CrudRepository<TbodContratoCorretora, Long>{
	
	public TbodContratoCorretora findByCdCorretora(Long cdCorretora);
	
}

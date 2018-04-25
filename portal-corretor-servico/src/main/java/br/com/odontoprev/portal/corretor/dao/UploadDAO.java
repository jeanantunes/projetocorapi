package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.odontoprev.portal.corretor.model.TbodUploadForcavenda;

public interface UploadDAO extends CrudRepository<TbodUploadForcavenda, Long> {
	
	
}

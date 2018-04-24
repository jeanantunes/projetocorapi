package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.odontoprev.portal.corretor.model.TbodUpload;

public interface UploadDAO extends CrudRepository<TbodUpload, Long> {

	
	
}

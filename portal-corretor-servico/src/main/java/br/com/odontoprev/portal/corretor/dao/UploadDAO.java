package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodUploadForcavenda;

@Repository
public interface UploadDAO extends CrudRepository<TbodUploadForcavenda, Long> {
	
	List<TbodUploadForcavenda> findAll();
}

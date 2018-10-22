package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodArquivo;

//201810221718 - esert - COR-932:API - Novo GET /planoinfo
@Repository
public interface ArquivoDAO extends CrudRepository<TbodArquivo, Long> {
	
}

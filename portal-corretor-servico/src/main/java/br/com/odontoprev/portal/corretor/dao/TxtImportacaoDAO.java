package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodTxtImportacao;

//201805081643 - esert
//201805081754 - esert
@Repository
public interface TxtImportacaoDAO extends CrudRepository<TbodTxtImportacao, String> {

	public List<TbodTxtImportacao> findByNrAtendimento(String nrAtendimento);
	
}

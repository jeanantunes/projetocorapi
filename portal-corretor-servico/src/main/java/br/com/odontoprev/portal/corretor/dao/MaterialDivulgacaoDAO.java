package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodMaterialDivulgacao;

@Repository
public interface MaterialDivulgacaoDAO extends CrudRepository<TbodMaterialDivulgacao, Long> {

	public List<TbodMaterialDivulgacao> findAllAtivo();
	
}

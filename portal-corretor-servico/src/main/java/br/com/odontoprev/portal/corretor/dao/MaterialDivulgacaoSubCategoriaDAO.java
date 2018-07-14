package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodMaterialDivulgacaoSubCategoria;

@Repository
public interface MaterialDivulgacaoSubCategoriaDAO extends CrudRepository<TbodMaterialDivulgacaoSubCategoria, Long> {

	public List<TbodMaterialDivulgacaoSubCategoria> findAll();
	
}

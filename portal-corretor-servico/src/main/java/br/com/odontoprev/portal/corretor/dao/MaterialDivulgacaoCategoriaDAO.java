package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodMaterialDivulgacaoCategoria;

@Repository
public interface MaterialDivulgacaoCategoriaDAO extends CrudRepository<TbodMaterialDivulgacaoCategoria, Long> {

	public List<TbodMaterialDivulgacaoCategoria> findAll();
	
}

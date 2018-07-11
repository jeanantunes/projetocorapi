package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.repository.Repository;

import br.com.odontoprev.portal.corretor.model.TbodMaterialDivulgacao;

@org.springframework.stereotype.Repository
public interface MaterialDivulgacaoDAO extends Repository<TbodMaterialDivulgacao, Long> {

	public List<TbodMaterialDivulgacao> findAll();
	
}

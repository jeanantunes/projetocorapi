package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodArquivoContratacao;

//201808231711 - esert - COR-617 - nova tabela TBOD_ARQUIVO_CONTRATACAO
@Repository
public interface ArquivoContratacaoDAO extends CrudRepository<TbodArquivoContratacao, Long> {
	
}

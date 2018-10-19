package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodTipoBloqueio;

//201809181556 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)
@Repository
public interface TipoBloqueioDAO extends CrudRepository<TbodTipoBloqueio, Long>  {

}

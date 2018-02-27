package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import br.com.odontoprev.portal.corretor.model.TbodVenda;


@org.springframework.stereotype.Repository
public interface PropostaDAO extends Repository<TbodVenda, Long> {

	public List<TbodVenda> propostasByFiltro(String dtInicio, String dtFim, long cdCorretora, long cdForcaVenda);

}

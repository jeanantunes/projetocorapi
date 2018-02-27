package br.com.odontoprev.portal.corretor.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.Repository;

import br.com.odontoprev.portal.corretor.model.TbodVenda;


@org.springframework.stereotype.Repository
public interface PropostaDAO extends Repository<TbodVenda, Long> {

	public List<TbodVenda> propostasByFiltro(Date dtInicio, Date dtFim, long cdCorretora, long cdForcaVenda);

}

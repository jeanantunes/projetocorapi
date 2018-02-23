package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;

public interface ForcaVendaDAO extends CrudRepository<TbodForcaVenda, Long> {

	public List<TbodForcaVenda> findByCpf(String cpf);

}

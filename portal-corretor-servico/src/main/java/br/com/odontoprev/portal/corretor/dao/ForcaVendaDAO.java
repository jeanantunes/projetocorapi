package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;

@Repository
public interface ForcaVendaDAO extends CrudRepository<TbodForcaVenda, Long> {

	public List<TbodForcaVenda> findByCpf(String cpf);
	
	public TbodForcaVenda findByCdForcaVendaAndTbodCorretoraCnpj(Long cdForcaVenda, String cnpj);

	public List<TbodForcaVenda> findByTbodCorretoraCdCorretora(Long cdCorretora);

	public List<TbodForcaVenda> findByTbodStatusForcaVendaCdStatusForcaVendasAndTbodCorretoraCdCorretora(Long cdStatusForcaVenda, Long cdCorretora);
	
}

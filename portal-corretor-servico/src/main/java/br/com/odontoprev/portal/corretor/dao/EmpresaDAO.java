package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodEmpresa;

@Repository
public interface EmpresaDAO extends CrudRepository<TbodEmpresa, Long> {
	
	public TbodEmpresa findBycdEmpresaAndCnpj(Long cdEmpresa, String cnpj);
	
	public TbodEmpresa findByCnpj(String cnpj);

}

package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.odontoprev.portal.corretor.model.TbodEmpresaContato;

public interface EmpresaContatoDAO extends CrudRepository<TbodEmpresaContato, Long>  {
	
	

}

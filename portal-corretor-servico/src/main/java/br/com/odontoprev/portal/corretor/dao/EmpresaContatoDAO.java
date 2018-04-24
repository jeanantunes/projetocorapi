package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.odontoprev.portal.corretor.model.TbodEmpresaContato;

public interface EmpresaContatoDAO extends CrudRepository<TbodEmpresaContato, Long>  {
	
	  @Query(value = " select max(cont.cdContato) from TbodEmpresaContato cont")
	  int buscamaxCod();

}

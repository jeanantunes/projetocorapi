package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.odontoprev.portal.corretor.model.TbodEmpresa;

public interface EmpresaDAO extends CrudRepository<TbodEmpresa, Long> {

}

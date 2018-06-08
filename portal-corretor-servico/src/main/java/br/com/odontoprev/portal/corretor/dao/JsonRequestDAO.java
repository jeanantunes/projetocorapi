package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodJsonRequest;

//201806061156 - esert - rename de ConvertObjectDAO para JsonRequestDAO mantendo referencia ao model TbodJsonRequest correspondente 
@Repository
public interface JsonRequestDAO extends CrudRepository<TbodJsonRequest, Long> {

}

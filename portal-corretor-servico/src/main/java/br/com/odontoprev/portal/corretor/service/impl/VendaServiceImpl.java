package br.com.odontoprev.portal.corretor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.service.VendaService;

@Service
public class VendaServiceImpl implements VendaService{

	@Autowired
	VendaDAO vendaDAO;

	@Override
	public TbodVenda buscarVendaPorCodigo(Long cdVenda) {		
		return vendaDAO.findOne(cdVenda);
	}
	
	/*@Override
	public Optional<TbodVenda> buscarVendaPorCodigo(Long cdVenda) {		
		return Optional.ofNullable(this.vendaDAO.findByCodigo(cdVenda));		
	}*/
	
	
	
	

}

package br.com.odontoprev.portal.corretor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.StatusVendaDAO;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.model.TbodStatusVenda;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.service.VendaService;

@Service
public class VendaServiceImpl implements VendaService{

	@Autowired
	VendaDAO vendaDAO;
	
	@Autowired
	StatusVendaDAO statusVendaDAO; //201806291953 - esert - COR-358 Serviço - Alterar serviço /empresa-dcms para atualizar o campo CD_STATUS_VENDA da tabela TBOD_VENDA.

	@Override
	public TbodVenda buscarVendaPorCodigo(Long cdVenda) {		
		return vendaDAO.findOne(cdVenda);
	}
	
	/*@Override
	public Optional<TbodVenda> buscarVendaPorCodigo(Long cdVenda) {		
		return Optional.ofNullable(this.vendaDAO.findByCodigo(cdVenda));		
	}*/
	
	//201806291953 - esert - COR-358 Serviço - Alterar serviço /empresa-dcms para atualizar o campo CD_STATUS_VENDA da tabela TBOD_VENDA. 
	public TbodVenda atualizarStatusVenda(Long cdVenda, long cdStatusVenda ) {
		TbodVenda tbodVenda = vendaDAO.findOne(cdVenda);
		TbodStatusVenda tbodStatusVenda = statusVendaDAO.findOne(cdStatusVenda);
		tbodVenda.setTbodStatusVenda(tbodStatusVenda);
		tbodVenda = vendaDAO.save(tbodVenda); 
		return tbodVenda;		
	}
	

}

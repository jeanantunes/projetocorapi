package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.model.TbodVenda;

public interface VendaService {

	/*busca venda por codigo*/
	TbodVenda buscarVendaPorCodigo(Long cdVenda);
}

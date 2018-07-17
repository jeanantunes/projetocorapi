package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.model.TbodVenda;

public interface VendaService {

	/*busca venda por codigo*/
	TbodVenda buscarVendaPorCodigo(Long cdVenda);
	
	TbodVenda atualizarStatusVenda(Long cdVenda, long cdStatusVenda ); //201806291953 - esert - COR-358 Serviço - Alterar serviço /empresa-dcms para atualizar o campo CD_STATUS_VENDA da tabela TBOD_VENDA.
}

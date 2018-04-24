package br.com.odontoprev.portal.corretor.service;

import java.util.List;

import br.com.odontoprev.portal.corretor.dto.RelatorioGestaoVenda;

public interface RelatorioGestaoVendaService {
	
	/*gestão de vendas por corretora*/
	List<RelatorioGestaoVenda> findVendasByCorretora(String cnpj);
}

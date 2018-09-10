package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraDataAceite;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraPreenchido;

public interface ContratoCorretoraService {

	public ContratoCorretoraDataAceite getDataAceiteContratoByCdCorretora(long cdCorretora) throws Exception;

	//201809101646 - esert - COR-709 - Serviço - Novo serviço GET/contratocorretora/cdCor/tipo/cdTipo
	//201809101700 - esert - COR-710 - Serviço - TDD Novo serviço GET/contratocorretora/cdCor/tipo/cdTipo
	public ContratoCorretoraPreenchido getContratoPreenchido(Long cdCorretora, Long cdContratoModelo) throws Exception;

}

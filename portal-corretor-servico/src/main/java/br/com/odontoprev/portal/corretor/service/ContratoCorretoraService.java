package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraDataAceite;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraPreenchido;

public interface ContratoCorretoraService {

	public ContratoCorretoraDataAceite getDataAceiteContratoByCdCorretora(long cdCorretora) throws Exception;

	//201809101646 - esert - COR-709 - Serviço - Novo serviço GET /CONTRATO CORRETORA/{IDCORRETORA}/TIPO/{IDTIPO}
	public ContratoCorretoraPreenchido getContratoPreenchido(Long cdCorretora, Long cdContratoModelo) throws Exception;

}

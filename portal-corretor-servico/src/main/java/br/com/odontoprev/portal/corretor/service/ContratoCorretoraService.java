package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraDataAceite;

public interface ContratoCorretoraService {

	public ContratoCorretoraDataAceite getDataAceiteContratoByCdCorretora(long cdCorretora) throws Exception;

}

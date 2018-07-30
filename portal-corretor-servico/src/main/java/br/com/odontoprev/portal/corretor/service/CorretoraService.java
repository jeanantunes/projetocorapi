package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.CorretoraResponse;

public interface CorretoraService {

	public CorretoraResponse addCorretora(Corretora corretora);

	public CorretoraResponse updateCorretoraLogin(Corretora corretora);

	public Corretora buscaCorretoraPorCnpj(String cnpj);
	
}

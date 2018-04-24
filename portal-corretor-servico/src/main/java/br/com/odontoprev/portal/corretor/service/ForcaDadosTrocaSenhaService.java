package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.ForcaDadosTrocaSenha;

public interface ForcaDadosTrocaSenhaService {
	
	ForcaDadosTrocaSenha buscarDadosForcaVendaPorCPF(String cpf);

}

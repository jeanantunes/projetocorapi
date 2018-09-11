package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.ContratoModelo;

//201809111204 - esert - COR-760 - Serviço - cria POST/contratomodelo
public interface ContratoModeloService {
	
	ContratoModelo getByCdContratoModelo(Long cdContratoModelo);

	ContratoModelo saveArquivo(ContratoModelo contratoModelo);
}



package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.ForcaVenda;

//201809181556 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)
public interface BloqueioService {
	
	public boolean doBloqueioCorretora(Corretora corretora) throws Exception;

	public boolean doBloqueioCorretora(String cnpj) throws Exception;
	
	public boolean doBloqueioForcaVenda(ForcaVenda forcaVenda);
	
	public boolean doBloqueioForcaVenda(String cpf);

}



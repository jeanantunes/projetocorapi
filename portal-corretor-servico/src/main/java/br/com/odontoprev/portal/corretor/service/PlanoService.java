package br.com.odontoprev.portal.corretor.service;

import java.util.List;

import br.com.odontoprev.portal.corretor.dto.Plano;

public interface PlanoService {
	
	public List<Plano> getPlanos();

	public List<Plano> findPlanosByEmpresa(long cdEmpresa);

}



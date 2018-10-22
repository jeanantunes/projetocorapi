package br.com.odontoprev.portal.corretor.service;

import java.util.List;

import br.com.odontoprev.portal.corretor.dto.Plano;
import br.com.odontoprev.portal.corretor.dto.PlanoInfos;

public interface PlanoService {
	
	public List<Plano> getPlanos();

	public List<Plano> findPlanosByEmpresa(long cdEmpresa);

	public PlanoInfos getpPlanoInfos();

}



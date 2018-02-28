package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.DashboardPropostaResponse;

public interface DashboardPropostaService {

	DashboardPropostaResponse buscaPropostaPorStatusPME(long status);

	DashboardPropostaResponse buscaPropostaPorStatusPF(long status);

}

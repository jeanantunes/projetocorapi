package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.DashboardPropostaPFResponse;
import br.com.odontoprev.portal.corretor.dto.DashboardPropostaPMEResponse;

public interface DashboardPropostaService {

	DashboardPropostaPMEResponse buscaPropostaPorStatusPME(long status);

	DashboardPropostaPFResponse buscaPropostaPorStatusPF(long status);

}

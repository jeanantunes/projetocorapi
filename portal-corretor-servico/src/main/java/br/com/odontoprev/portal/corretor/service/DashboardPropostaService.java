package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.DashboardPropostaPFResponse;
import br.com.odontoprev.portal.corretor.dto.DashboardPropostaPMEResponse;
import br.com.odontoprev.portal.corretor.dto.DashboardResponse;

public interface DashboardPropostaService {

	DashboardPropostaPMEResponse buscaPropostaPorStatusPME(long status, String cnpj);

	DashboardPropostaPFResponse buscaPropostaPorStatusPF(long status, String cpf);

	DashboardPropostaPFResponse buscaPorCriticaPF(String cnpj, String cpf);

	DashboardPropostaPMEResponse buscaPorCriticaPME(String cnpj, String cpf);

	DashboardResponse buscarForcaVendaAguardandoAprovacaoByCdEmpresa(long cdCorretora);

}

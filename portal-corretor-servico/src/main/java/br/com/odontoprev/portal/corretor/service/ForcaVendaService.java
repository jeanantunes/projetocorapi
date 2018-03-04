package br.com.odontoprev.portal.corretor.service;

import java.util.List;

import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.dto.ForcaVendaResponse;

public interface ForcaVendaService {

	public ForcaVendaResponse addForcaVenda(ForcaVenda forcaVenda);

	public ForcaVendaResponse updateForcaVenda(ForcaVenda forcaVenda) throws Exception;

	public ForcaVenda findForcaVendaByCpf(String cpf);

	public ForcaVendaResponse findAssocForcaVendaCorretora(Long cdForcaVenda, String cnpj);

	public ForcaVendaResponse updateForcaVendaLogin(ForcaVenda forcaVenda);

	public List<ForcaVenda> findForcaVendasByCdStatusForcaCdCorretora(Long cdStatusForcaVenda, Long cdCorretora);

	public List<ForcaVenda> findForcaVendasByForcaCdCorretora(Long cdCorretora);

	public ForcaVendaResponse updateForcaVendaStatusByCpf(ForcaVenda forcaVenda);

}

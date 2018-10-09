package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.EmailForcaVendaCorretora;
import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.dto.ForcaVendaResponse;
import br.com.odontoprev.portal.corretor.exceptions.ApiTokenException;
import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;

import java.util.List;

public interface ForcaVendaService {

	public ForcaVendaResponse addForcaVenda(ForcaVenda forcaVenda);

	public ForcaVendaResponse updateForcaVenda(ForcaVenda forcaVenda) throws Exception;

	public ForcaVenda findForcaVendaByCpf(String cpf);

	public ForcaVendaResponse findAssocForcaVendaCorretora(Long cdForcaVenda, String cnpj);

	public ForcaVendaResponse updateForcaVendaLogin(ForcaVenda forcaVenda);

	public List<ForcaVenda> findForcaVendasByCdStatusForcaCdCorretora(Long cdStatusForcaVenda, Long cdCorretora);

	public List<ForcaVenda> findForcaVendasByForcaCdCorretora(Long cdCorretora);

	public ForcaVendaResponse updateForcaVendaStatusByCpf(ForcaVenda forcaVenda);

	public String envioMensagemAtivo(TbodForcaVenda forcaVenda) throws ApiTokenException;
	
	/***** status excluir ou reprovar - Força *****/
	ForcaVendaResponse updateForcaVendaStatusByCpf(ForcaVenda forcaVenda, String opcaoStatus) throws Exception;

	public ForcaVenda findByCdForcaVenda(Long cdForcaVenda);

    public EmailForcaVendaCorretora findByCdForcaVendaEmail(Long cdForcaVenda);
}

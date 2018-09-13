package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.ContratoCorretora;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraDataAceite;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraPreenchido;
import br.com.odontoprev.portal.corretor.model.TbodContratoCorretora;

public interface ContratoCorretoraService {

	public ContratoCorretoraDataAceite getDataAceiteContratoByCdCorretora(long cdCorretora) throws Exception;

	//201809101646 - esert - COR-709 - Serviço - Novo serviço GET/contratocorretora/cdCor/tipo/cdTipo
	//201809101700 - esert - COR-710 - Serviço - TDD Novo serviço GET/contratocorretora/cdCor/tipo/cdTipo
	public ContratoCorretoraPreenchido getContratoPreenchidoDummy(Long cdCorretora, Long cdContratoModelo) throws Exception;

	public ContratoCorretoraPreenchido getContratoPreenchido(Long cdCorretora, Long cdContratoModelo, String cdSusep) throws Exception;

	public ContratoCorretora postContratoCorretora(ContratoCorretora contratoCorretora);

	//201809121519 - esert - COR-714 - Serviço - TDD Novo serviço gerar enviar contrato corretora - (apenasMiolo) define se html deve ser =(true=>para tela) ou (false>=para pdf)
	public String montarHtmlContratoCorretagemIntermediacao(Long cdCorretora, Long cdContratoModelo, String cdSusep, String dataAceite, boolean apenasMiolo);

	//201809121519 - esert - COR-714 - Serviço - TDD Novo serviço gerar enviar contrato corretora - (apenasMiolo) define se html deve ser =(true=>para tela) ou (false>=para pdf)
	public ContratoCorretora createPdfContratoCorretora(TbodContratoCorretora tbodContratoCorretora);

	//201809121519 - esert - COR-714 - Serviço - TDD Novo serviço gerar enviar contrato corretora - (apenasMiolo) define se html deve ser =(true=>para tela) ou (false>=para pdf)
	public ContratoCorretora enviarEmailContratoCorretagemIntermediacao(Long cdCorretora, Long cdContratoCorretora); //201809122217

	//201809121030 - esert - COR-718 - Serviço - Novo serviço GET/contratocorretora/cdCorretora/arquivo retorna PDF
	public ContratoCorretora getContratoCorretoraPreenchidoByteArray(Long cdCorretora);

}

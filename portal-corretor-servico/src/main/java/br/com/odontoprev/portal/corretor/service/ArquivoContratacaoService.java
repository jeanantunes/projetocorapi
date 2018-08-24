package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.ArquivoContratacao;

//201808231715 - esert - COR-617 - novo servico para nova tabela TBOD_ARQUIVO_CONTRATACAO
public interface ArquivoContratacaoService {
	
	ArquivoContratacao get(Long codigoEmpresa, boolean isArquivo);

	ArquivoContratacao save(ArquivoContratacao arquivoContratacao, boolean isArquivo);

	ArquivoContratacao saveArquivo(ArquivoContratacao arquivoContratacao);

	ArquivoContratacao gerarSalvarPdfPmePelaVenda(Long cdVenda);

}



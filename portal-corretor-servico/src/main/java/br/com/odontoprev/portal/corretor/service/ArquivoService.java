package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.Arquivo;

public interface ArquivoService {

	public Arquivo saveArquivo(Arquivo arquivo);

	public Arquivo getByCdArquivo(Long cdArquivo);
}

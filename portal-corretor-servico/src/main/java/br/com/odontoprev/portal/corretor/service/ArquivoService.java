package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.Arquivo;
import br.com.odontoprev.portal.corretor.dto.Arquivos;

public interface ArquivoService {

	public Arquivos saveArquivo(Arquivos arquivos);

	public Arquivo getByCdArquivo(Long cdArquivo);
}

package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.Arquivo;
import br.com.odontoprev.portal.corretor.dto.Arquivos;

public interface ArquivoService {

	//201810241700 - esert - COR-721:API POST/arquivo/carregar - alterado para suportar List<Arquivo>
	public Arquivos saveArquivo(Arquivos arquivos); 

	public Arquivo getByCdArquivo(Long cdArquivo);
}

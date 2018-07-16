package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.MateriaisDivulgacao;
import br.com.odontoprev.portal.corretor.dto.MaterialDivulgacao;

public interface MaterialDivulgacaoService {
	
	MateriaisDivulgacao getMateriaisDivulgacao();

	MaterialDivulgacao getMaterialDivulgacao(Long codigoMaterialDivulgacao, boolean isArquivo);

	MaterialDivulgacao save(MaterialDivulgacao materialDivulgacao, boolean isThumbnail, boolean isArquivo);

}



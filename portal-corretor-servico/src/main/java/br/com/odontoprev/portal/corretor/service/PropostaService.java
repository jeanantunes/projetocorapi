package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.DashBoardProposta;
import br.com.odontoprev.portal.corretor.dto.PropostasDashBoard;

public interface PropostaService {

	public PropostasDashBoard findPropostasByFiltro(DashBoardProposta dashBoardProposta);
}

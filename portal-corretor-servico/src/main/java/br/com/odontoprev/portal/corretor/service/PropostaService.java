package br.com.odontoprev.portal.corretor.service;

import java.text.ParseException;
import java.util.List;

import br.com.odontoprev.portal.corretor.dto.DashBoardProposta;
import br.com.odontoprev.portal.corretor.dto.PropostasDashBoard;
import br.com.odontoprev.portal.corretor.model.ViewCorSumarioVenda;

public interface PropostaService {

	public PropostasDashBoard findPropostasByFiltro(DashBoardProposta dashBoardProposta) throws ParseException;

	List<ViewCorSumarioVenda> findViewCorSumarioByFiltro(DashBoardProposta dashBoardProposta) throws ParseException;
}

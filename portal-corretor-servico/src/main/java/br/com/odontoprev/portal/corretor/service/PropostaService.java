package br.com.odontoprev.portal.corretor.service;

import java.text.ParseException;
import java.util.List;

import br.com.odontoprev.portal.corretor.dto.CorretoraTotalVidasPME;
import br.com.odontoprev.portal.corretor.dto.DashBoardProposta;
import br.com.odontoprev.portal.corretor.dto.PropostaCritica;
import br.com.odontoprev.portal.corretor.dto.PropostasDashBoard;
import br.com.odontoprev.portal.corretor.model.ViewCorSumarioVenda;
import br.com.odontoprev.portal.corretor.model.VwodCorretoraTotalVidas;

public interface PropostaService {

	public PropostasDashBoard findPropostasByFiltro(DashBoardProposta dashBoardProposta) throws ParseException;

	List<ViewCorSumarioVenda> findViewCorSumarioByFiltro(DashBoardProposta dashBoardProposta) throws ParseException;

	public PropostaCritica buscarPropostaCritica(String cd_venda); //201805081530 - esert

	public List<VwodCorretoraTotalVidas> findVwodCorretoraTotalVidasByFiltro(CorretoraTotalVidasPME corretoraTotalVidasPME) throws ParseException; //201806081640 - esert - relatorio vendas pme
}

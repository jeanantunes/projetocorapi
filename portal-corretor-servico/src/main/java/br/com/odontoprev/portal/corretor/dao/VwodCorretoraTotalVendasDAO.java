package br.com.odontoprev.portal.corretor.dao;

import java.util.Date;
import java.util.List;

import br.com.odontoprev.portal.corretor.model.VwodCorretoraTotalVidasPME;
import br.com.odontoprev.portal.corretor.model.VwodCorretoraTotalVidasPF;

//201806081606 - esert - relatorio vendas
public interface VwodCorretoraTotalVendasDAO {

	//201806081606 - esert - relatorio vendas pme
	List<VwodCorretoraTotalVidasPME> vwodCorretoraTotalVidasPMEByFiltro(
			Date dtVendaInicio, 
			Date dtVendaFim, 
			String cnpj
	);

	//201806121139 - esert - relatorio vendas pf
	List<VwodCorretoraTotalVidasPF> vwodCorretoraTotalVidasPFByFiltro(
			Date dtVendaInicio, 
			Date dtVendaFim,
			String cnpjCorretora
	);
}

package br.com.odontoprev.portal.corretor.dao;

import java.util.Date;
import java.util.List;

import br.com.odontoprev.portal.corretor.model.VwodCorretoraTotalVidas;

//201806081606 - esert - relatorio vendas pme
public interface VwodCorretoraTotalVendasDAO {

	public List<VwodCorretoraTotalVidas> vwodCorretoraTotalVendasByFiltro(
			Date dtInicio, 
			Date dtFim, 
			long cdCorretora,
			long cdForcaVenda, 
			String cpf, 
			String cnpj, 
			Date dtVenda
			);

}

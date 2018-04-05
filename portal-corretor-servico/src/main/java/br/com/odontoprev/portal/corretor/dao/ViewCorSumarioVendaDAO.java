package br.com.odontoprev.portal.corretor.dao;

import java.util.Date;
import java.util.List;

import br.com.odontoprev.portal.corretor.model.ViewCorSumarioVenda;

public interface ViewCorSumarioVendaDAO {

	public List<ViewCorSumarioVenda> viewCorSumarioVendasByFiltro(Date dtInicio, Date dtFim, long cdCorretora,
			long cdForcaVenda, String cpf, String cnpj, Date dtVenda);

}

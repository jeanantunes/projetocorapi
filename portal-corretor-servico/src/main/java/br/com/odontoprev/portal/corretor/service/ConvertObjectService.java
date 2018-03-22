package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaPME;

public interface ConvertObjectService {

	/*save Json vendas PF PME*/
	void addJsonInTable(Venda vendaPF, VendaPME vendaPME, String userAgent);
}

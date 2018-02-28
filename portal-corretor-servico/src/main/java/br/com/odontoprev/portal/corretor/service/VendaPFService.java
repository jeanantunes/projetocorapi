package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaPME;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;

public interface VendaPFService {

	public VendaResponse addVenda(Venda vendaPF);
	
	public VendaResponse addVendaPME(VendaPME vendaPME);


}
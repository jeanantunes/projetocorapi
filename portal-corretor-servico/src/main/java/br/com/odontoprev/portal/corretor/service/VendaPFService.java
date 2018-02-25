package br.com.odontoprev.portal.corretor.service;

import java.util.List;

import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;

public interface VendaPFService {

	public VendaResponse addVenda(Venda vendaPF);
	
	public VendaResponse addVendaPME(List<Empresa> empresas, List<Beneficiario> titulares);


}
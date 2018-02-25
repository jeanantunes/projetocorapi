package br.com.odontoprev.portal.corretor.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.business.VendaPFBusiness;
import br.com.odontoprev.portal.corretor.business.VendaPMEBusiness;
import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;
import br.com.odontoprev.portal.corretor.service.VendaPFService;

@Service
public class VendaPFServiceImpl implements VendaPFService {

	private static final Log log = LogFactory.getLog(VendaPFServiceImpl.class);

	@Autowired
	VendaPFBusiness vendaPFBusiness;

	@Autowired
	VendaPMEBusiness vendaPMEBusiness;
	
	@Override
	public VendaResponse addVenda(Venda venda) {

		log.info("[VendaPFServiceImpl::addVenda]");

		return vendaPFBusiness.salvarVendaComTitularesComDependentes(venda);
	}

	@Override
	public VendaResponse addVendaPME(List<Empresa> empresas, List<Beneficiario> titulares) {
	
		log.info("[VendaPFServiceImpl::addVendaPME]");
	
		return vendaPMEBusiness.salvarVendaPMEComEmpresasPlanosTitularesDependentes(empresas, titulares);
	}
}

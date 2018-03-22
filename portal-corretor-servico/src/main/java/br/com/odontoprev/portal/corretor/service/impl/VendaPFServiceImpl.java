package br.com.odontoprev.portal.corretor.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.business.VendaPFBusiness;
import br.com.odontoprev.portal.corretor.business.VendaPMEBusiness;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaPME;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;
import br.com.odontoprev.portal.corretor.service.ConvertObjectService;
import br.com.odontoprev.portal.corretor.service.VendaPFService;

@Service
public class VendaPFServiceImpl implements VendaPFService {

	private static final Log log = LogFactory.getLog(VendaPFServiceImpl.class);

	@Autowired
	VendaPFBusiness vendaPFBusiness;

	@Autowired
	VendaPMEBusiness vendaPMEBusiness;
	
	@Autowired
	ConvertObjectService convertObjectToJson;
	
	@Override
	public VendaResponse addVenda(Venda venda) {

		log.info("[VendaPFServiceImpl::addVenda]");
		
		convertObjectToJson.addJsonInTable(venda);

		return vendaPFBusiness.salvarVendaComTitularesComDependentes(venda, Boolean.TRUE);
	}

	@Override
	public VendaResponse addVendaPME(VendaPME vendaPME) {
	
		log.info("[VendaPFServiceImpl::addVendaPME]");
	
		return vendaPMEBusiness.salvarVendaPMEComEmpresasPlanosTitularesDependentes(vendaPME);
	}
}

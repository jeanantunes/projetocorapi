package br.com.odontoprev.portal.corretor.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.business.VendaPFBusiness;
import br.com.odontoprev.portal.corretor.business.VendaPMEBusiness;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaPME;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;
import br.com.odontoprev.portal.corretor.service.VendaPFService;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE) //201805242036 - inc //201805242118 - alt
public class VendaPFServiceImpl implements VendaPFService {

	private static final Log log = LogFactory.getLog(VendaPFServiceImpl.class);

	@Autowired
	VendaPFBusiness vendaPFBusiness;

	@Autowired
	VendaPMEBusiness vendaPMEBusiness;
	
	@Override
	public VendaResponse addVenda(Venda venda) {

		log.info("[VendaPFServiceImpl::addVenda]");
		
		return vendaPFBusiness.salvarVendaComTitularesComDependentes(venda, Boolean.TRUE);
	}

	@Override
	@Transactional //201805241930 - inc //201805242012 - exc
	public VendaResponse addVendaPME(VendaPME vendaPME) {
	
		log.info("[VendaPFServiceImpl::addVendaPME]");
		
		return vendaPMEBusiness.salvarVendaPMEComEmpresasPlanosTitularesDependentes(vendaPME);
	}
}

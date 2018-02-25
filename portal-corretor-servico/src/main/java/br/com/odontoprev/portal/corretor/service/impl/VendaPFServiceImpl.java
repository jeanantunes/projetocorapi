package br.com.odontoprev.portal.corretor.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.business.VendaPFBusiness;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;
import br.com.odontoprev.portal.corretor.service.VendaPFService;

@Service
public class VendaPFServiceImpl implements VendaPFService {

	private static final Log log = LogFactory.getLog(VendaPFServiceImpl.class);

	@Autowired
	VendaPFBusiness vendaPFBusiness;
	
	@Override
	public VendaResponse addVenda(Venda venda) {

		log.info("[VendaPFServiceImpl::addVenda]");

		return vendaPFBusiness.salvarVendaPFComTitularesComDependentes(venda);
	}
}

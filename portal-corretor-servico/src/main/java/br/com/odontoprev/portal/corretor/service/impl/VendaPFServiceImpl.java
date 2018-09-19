package br.com.odontoprev.portal.corretor.service.impl;

import br.com.odontoprev.portal.corretor.business.VendaPFBusiness;
import br.com.odontoprev.portal.corretor.business.VendaPMEBusiness;
import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaPME;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;
import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
import br.com.odontoprev.portal.corretor.service.VendaPFService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor={Exception.class}) //201805242036 - inc //201805242118 - alt //208106291623 - alt
public class VendaPFServiceImpl implements VendaPFService {

	private static final Log log = LogFactory.getLog(VendaPFServiceImpl.class);

	@Autowired
	VendaPFBusiness vendaPFBusiness;

	@Autowired
	VendaPMEBusiness vendaPMEBusiness;

	@Autowired
	ForcaVendaDAO forcaVendaDAO;
	
	@Override
	@Transactional(rollbackFor={Exception.class}) //201806290926 - esert - COR-352 rollback pf
	public VendaResponse addVenda(Venda venda) throws Exception {

		TbodForcaVenda forcaVenda = forcaVendaDAO.findByCdForcaVenda(venda.getCdForcaVenda());

		log.info("[VendaPFServiceImpl::addVenda]");
		
		return vendaPFBusiness.salvarVendaComTitularesComDependentes(venda, Boolean.TRUE);
	}

	@Override
	@Transactional(rollbackFor={Exception.class}) //201806280926 - esert - COR-348 rollback pme
	public VendaResponse addVendaPME(VendaPME vendaPME) {
	
		log.info("[VendaPFServiceImpl::addVendaPME]");

		TbodForcaVenda forcaVenda = forcaVendaDAO.findByCdForcaVenda(vendaPME.getCdForcaVenda());
		
		return vendaPMEBusiness.salvarVendaPMEComEmpresasPlanosTitularesDependentes(vendaPME);
	}
}

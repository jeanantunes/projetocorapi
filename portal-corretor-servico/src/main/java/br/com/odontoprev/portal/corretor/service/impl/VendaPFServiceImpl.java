package br.com.odontoprev.portal.corretor.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.business.VendaPFBusiness;
import br.com.odontoprev.portal.corretor.business.VendaPMEBusiness;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaPME;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;
import br.com.odontoprev.portal.corretor.service.VendaPFService;

@Service
@Transactional(rollbackFor={Exception.class}) //201805242036 - inc //201805242118 - alt //208106291623 - alt
public class VendaPFServiceImpl implements VendaPFService {

	private static final Log log = LogFactory.getLog(VendaPFServiceImpl.class);

	@Autowired
	private VendaPFBusiness vendaPFBusiness; // COR-883 Adicionado private

	@Autowired
	private VendaPMEBusiness vendaPMEBusiness; // COR-883 Adicionado private

	@Autowired
	private ForcaVendaService forcaVendaService; // COR-883 Adicionado private

	@Override
	@Transactional(rollbackFor={Exception.class}) //201806290926 - esert - COR-352 rollback pf
	public VendaResponse addVenda(Venda venda) throws Exception {

		log.info("[VendaPFServiceImpl::addVenda::checando status forca venda]");

		//201810101622 - esert - COR-883:Serviço - Alterar POST/vendapme Validar E-mail (Com TDD 200)
		VendaResponse vendaResponseBloqueio = forcaVendaService.verificarBloqueio(venda.getCdForcaVenda());
		if(vendaResponseBloqueio!=null) {
			return vendaResponseBloqueio;
		}

		log.info("[VendaPFServiceImpl::addVendaPME::checando erros antes salvar]");

		VendaResponse vendaResponseErro = vendaPFBusiness.verificarErro(venda);
		if(vendaResponseErro!=null) {
			return vendaResponseErro;
		}

		log.info("[VendaPFServiceImpl::addVenda::salvar]");
		
		return vendaPFBusiness.salvarVendaComTitularesComDependentes(venda, Boolean.TRUE);
	}

	//201810101622 - esert - COR-883:Serviço - Alterar POST/vendapme Validar E-mail (Com TDD 200)
	@Override
	@Transactional(rollbackFor={Exception.class}) //201806280926 - esert - COR-348 rollback pme
	public VendaResponse addVendaPME(VendaPME vendaPME) {

		log.info("[VendaPFServiceImpl::addVendaPME::checando status forca venda]");

		VendaResponse vendaResponseBloqueio = forcaVendaService.verificarBloqueio(vendaPME.getCdForcaVenda());
		if(vendaResponseBloqueio!=null) {
			return vendaResponseBloqueio;
		}
		
		log.info("[VendaPFServiceImpl::addVendaPME::checando erros antes salvar]");

		VendaResponse vendaResponseErro = vendaPMEBusiness.verificarErro(vendaPME);
		if(vendaResponseErro!=null) {
			return vendaResponseErro;
		}
		
		log.info("[VendaPFServiceImpl::addVendaPME::salvar]");

		return vendaPMEBusiness.salvarVendaPMEComEmpresasPlanosTitularesDependentes(vendaPME);
	}
}

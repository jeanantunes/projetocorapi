package br.com.odontoprev.portal.corretor.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.business.BeneficiarioBusiness;
import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioResponse;
import br.com.odontoprev.portal.corretor.service.BeneficiarioService;

@Service
public class BeneficiarioServiceImpl implements BeneficiarioService {

	private static final Log log = LogFactory.getLog(BeneficiarioServiceImpl.class);

	@Autowired
	BeneficiarioBusiness beneficiarioBusiness;

	@Override
	@Transactional
	public BeneficiarioResponse add(List<Beneficiario> titulares) {

		log.info("[BeneficiarioServiceImpl::add]");

		BeneficiarioResponse beneficiarioResponse = beneficiarioBusiness.salvarTitularComDependentes(titulares);

		return beneficiarioResponse;
	}

}

package br.com.odontoprev.portal.corretor.service;

import java.util.List;

import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioResponse;

public interface BeneficiarioService {

	public BeneficiarioResponse add(List<Beneficiario> beneficiarios);
}

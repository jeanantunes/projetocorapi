package br.com.odontoprev.portal.corretor.service;

import java.util.List;

import javax.transaction.RollbackException;

import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioResponse;

public interface BeneficiarioService {

	//201805281830 - esert - incluido throws para subir erro e causar rollback de toda transacao - teste
	public BeneficiarioResponse add(List<Beneficiario> beneficiarios) throws RollbackException;
}

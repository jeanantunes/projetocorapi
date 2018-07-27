package br.com.odontoprev.portal.corretor.service;

import java.util.List;

import javax.transaction.RollbackException;

import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioResponse;
import br.com.odontoprev.portal.corretor.dto.Beneficiarios;

public interface BeneficiarioService {

	//201805281830 - esert - incluido throws para subir erro e causar rollback de toda transacao - teste
	public BeneficiarioResponse add(List<Beneficiario> beneficiarios) throws RollbackException;

	//201807241900 - esert - COR-398	
	public Beneficiario get(Long cdVida);
	
	//201807251650 - esert - COR-471 beneficiarios da empresa PME paginados FAKE
	//201807271700 - esert - COR-475 getPage() migrado de business para service
	public Beneficiarios getPage(Long cdEmpresa, Long tamPag, Long numPag);
	
	//201807251650 - esert - COR-471 beneficiarios da empresa PME paginados FAKE
	//201807271700 - esert - COR-475 getPageFake() migrado de business para service
	public Beneficiarios getPageFake(Long cdEmpresa, Long tamPag, Long numPag);

}

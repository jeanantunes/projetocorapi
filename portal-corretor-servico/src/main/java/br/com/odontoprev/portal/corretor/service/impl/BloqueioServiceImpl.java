package br.com.odontoprev.portal.corretor.service.impl;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.dao.CorretoraDAO;
import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.model.TbodContratoCorretora;
import br.com.odontoprev.portal.corretor.model.TbodCorretora;
import br.com.odontoprev.portal.corretor.service.BloqueioService;
import br.com.odontoprev.portal.corretor.service.CorretoraService;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;
import br.com.odontoprev.portal.corretor.util.Constantes;

//201809181556 - esert - COR-730 : Serviço - Novo serviço (processar bloqueio)
@Service
@Transactional(rollbackFor={Exception.class})
public class BloqueioServiceImpl implements BloqueioService {
	
	private static final Log log = LogFactory.getLog(BloqueioServiceImpl.class);
	
	@Autowired
	private ForcaVendaService forcaVendaService;

	@Autowired
	private CorretoraDAO corretoraDAO;

	@Autowired
	private CorretoraService corretoraService;

	@Override
	public boolean doBloqueioCorretora(Corretora corretoraDTO) throws Exception {
		log.info("doBloqueioCorretora - ini");
		log.info(corretoraDTO);
		
		TbodCorretora tbodCorretora = corretoraDAO.findByCnpj(corretoraDTO.getCnpj());

		if(tbodCorretora==null) {
			log.error("tbodCorretora==null para corretoraDTO.getCnpj():[" + corretoraDTO.getCnpj() + "]");
			return false;
		}

		Optional<TbodContratoCorretora> optionalTbodContratoCorretora = tbodCorretora.getTbodContratoCorretoras().stream().filter(
			item ->
			item.getTbodContratoModelo().getCdContratoModelo().equals(Constantes.CONTRATO_CORRETAGEM_V1)
			||
			item.getTbodContratoModelo().getCdContratoModelo().equals(Constantes.CONTRATO_INTERMEDIACAO_V1)
		).findFirst();
		
		if(!optionalTbodContratoCorretora.isPresent()) {
			log.info("!optionalTbodContratoCorretora.isPresent() para corretoraDTO.getCnpj():[" + corretoraDTO.getCnpj() + "]");
			return false;
		}
		
		log.info("doBloqueioCorretora - fim");
		return true;
	}

	@Override
	public boolean doBloqueioCorretora(String cnpj) throws Exception {
		log.info("doBloqueioCorretora(cnpj:["+ cnpj +"]) - ini");
		log.info("doBloqueioCorretora(cnpj:["+ cnpj +"]) - fim");
		return this.doBloqueioCorretora(corretoraService.buscaCorretoraPorCnpj(cnpj));
	}

	@Override
	public boolean doBloqueioForcaVenda(ForcaVenda forcaVenda) {
		log.info("doBloqueioForcaVenda(ForcaVenda) - ini");
		log.info(forcaVenda);
		
		log.info("doBloqueioForcaVenda - fim");
		return true;
	}

	@Override
	public boolean doBloqueioForcaVenda(String cpf) {
		log.info("doBloqueioForcaVenda(cpf:["+ cpf +"]) - ini");
		log.info("doBloqueioForcaVenda(cpf:["+ cpf +"]) - fim");
		return this.doBloqueioForcaVenda(forcaVendaService.findForcaVendaByCpf(cpf));
	}

}


package br.com.odontoprev.portal.corretor.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.dao.CorretoraDAO;
import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dao.LoginDAO;
import br.com.odontoprev.portal.corretor.dao.TipoBloqueioDAO;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretora;
import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.model.TbodContratoCorretora;
import br.com.odontoprev.portal.corretor.model.TbodCorretora;
import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
import br.com.odontoprev.portal.corretor.model.TbodLogin;
import br.com.odontoprev.portal.corretor.model.TbodTipoBloqueio;
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

	@Autowired
	private LoginDAO loginDAO;

	@Autowired
	private TipoBloqueioDAO tipoBloqueioDAO;
	
	@Autowired
	private ForcaVendaDAO forcaVendaDAO;
	
	@Override
	public boolean doBloqueioCorretora(Corretora corretoraDTO) throws Exception {
		try {
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
	
			TbodLogin tbodLoginCorretora = tbodCorretora.getTbodLogin();
	
			if(optionalTbodContratoCorretora.isPresent()) {
				log.info("optionalTbodContratoCorretora.isPresent() para corretoraDTO.getCnpj():[" + corretoraDTO.getCnpj() + "]");
				tbodLoginCorretora.setTemBloqueio(Constantes.NAO);
				TbodTipoBloqueio tbodTipoBloqueio = tipoBloqueioDAO.findOne(Constantes.TIPO_BLOQUEIO_SEM_BLOQUEIO);
				if(tbodTipoBloqueio==null) {
					log.info("tbodTipoBloqueio==null para Constantes.TIPO_BLOQUEIO_SEM_BLOQUEIO:[" + Constantes.TIPO_BLOQUEIO_SEM_BLOQUEIO + "]");
					return false;
				}
				tbodLoginCorretora.setTbodTipoBloqueio(tbodTipoBloqueio);
			} else {
				log.info("!optionalTbodContratoCorretora.isPresent() para corretoraDTO.getCnpj():[" + corretoraDTO.getCnpj() + "]");
				tbodLoginCorretora.setTemBloqueio(Constantes.SIM);
				TbodTipoBloqueio tbodTipoBloqueio = tipoBloqueioDAO.findOne(Constantes.TIPO_BLOQUEIO_CORRETAGEM_INTERMEDIACAO);
				if(tbodTipoBloqueio==null) {
					log.info("tbodTipoBloqueio==null para Constantes.TIPO_BLOQUEIO_CORRETAGEM_INTERMEDIACAO:[" + Constantes.TIPO_BLOQUEIO_CORRETAGEM_INTERMEDIACAO + "]");
					return false;
				}
				tbodLoginCorretora.setTbodTipoBloqueio(tbodTipoBloqueio);
			}
			
			tbodLoginCorretora = loginDAO.save(tbodLoginCorretora);
	
			log.info("doBloqueioCorretora - fim");
			return true;
			
		}catch (Exception e) {
			log.error("doBloqueioCorretora - erro", e); //201809201053 - esert - COR-730 - protecao 
			return false;
		}
	}

	@Override
	public boolean doBloqueioCorretora(String cnpj) throws Exception {
		log.info("doBloqueioCorretora(cnpj:["+ cnpj +"]) - ini");
		log.info("doBloqueioCorretora(cnpj:["+ cnpj +"]) - fim");
		return this.doBloqueioCorretora(corretoraService.buscaCorretoraPorCnpj(cnpj));
	}

	@Override
	public boolean doBloqueioForcaVenda(ForcaVenda forcaVendaDTO) {
		try {
			log.info("doBloqueioForcaVenda(ForcaVenda) - ini");
			log.info(forcaVendaDTO);
			
			List<TbodForcaVenda> listTbodForcaVenda = forcaVendaDAO.findByCpf(forcaVendaDTO.getCpf());
	
			if(listTbodForcaVenda==null || listTbodForcaVenda.size()==0) {
				log.error("listTbodForcaVenda==null || size()==0 para forcaVendaDTO.getCpf():[" + forcaVendaDTO.getCpf() + "]");
				return false;
			}
	
			if(listTbodForcaVenda.size()>1) {
				log.error("listTbodForcaVenda.size()>1 para forcaVendaDTO.getCpf():[" + forcaVendaDTO.getCpf() + "]");
				return false;
			}
			
			TbodForcaVenda tbodForcaVenda = listTbodForcaVenda.get(0);
			
			if(tbodForcaVenda.getTbodCorretora()==null) {
				log.error("listTbodForcaVenda.get(0).getTbodCorretora()==null para forcaVendaDTO.getCpf():[" + forcaVendaDTO.getCpf() + "]");
				return false;
			}
				
			Optional<TbodContratoCorretora> optionalTbodContratoCorretora = tbodForcaVenda.getTbodCorretora().getTbodContratoCorretoras().stream().filter(
				item ->
				item.getTbodContratoModelo().getCdContratoModelo().equals(Constantes.CONTRATO_CORRETAGEM_V1)
				||
				item.getTbodContratoModelo().getCdContratoModelo().equals(Constantes.CONTRATO_INTERMEDIACAO_V1)
			).findFirst();
	
			TbodLogin tbodLoginForcaVenda = tbodForcaVenda.getTbodLogin();
			
			if(optionalTbodContratoCorretora.isPresent()) {
				log.info("optionalTbodContratoCorretora.isPresent() para forcaVendaDTO.getCpf():[" + forcaVendaDTO.getCpf() + "]");
				tbodLoginForcaVenda.setTemBloqueio(Constantes.NAO);
				TbodTipoBloqueio tbodTipoBloqueio = tipoBloqueioDAO.findOne(Constantes.TIPO_BLOQUEIO_SEM_BLOQUEIO);
				if(tbodTipoBloqueio==null) {
					log.info("tbodTipoBloqueio==null para Constantes.TIPO_BLOQUEIO_SEM_BLOQUEIO:[" + Constantes.TIPO_BLOQUEIO_SEM_BLOQUEIO + "]");
					return false;
				}
				tbodLoginForcaVenda.setTbodTipoBloqueio(tbodTipoBloqueio);
			} else {
				log.info("!optionalTbodContratoCorretora.isPresent() para forcaVendaDTO.getCpf():[" + forcaVendaDTO.getCpf() + "]");
				tbodLoginForcaVenda.setTemBloqueio(Constantes.SIM);
				TbodTipoBloqueio tbodTipoBloqueio = tipoBloqueioDAO.findOne(Constantes.TIPO_BLOQUEIO_CORRETAGEM_INTERMEDIACAO);
				if(tbodTipoBloqueio==null) {
					log.info("tbodTipoBloqueio==null para Constantes.TIPO_BLOQUEIO_CORRETAGEM_INTERMEDIACAO:[" + Constantes.TIPO_BLOQUEIO_CORRETAGEM_INTERMEDIACAO + "]");
					return false;
				}
				tbodLoginForcaVenda.setTbodTipoBloqueio(tbodTipoBloqueio);
			}
			
			tbodLoginForcaVenda = loginDAO.save(tbodLoginForcaVenda);
	
			log.info("doBloqueioForcaVenda - fim");
			return true;
			
		}catch (Exception e) {
			log.error("doBloqueioForcaVenda - erro", e); //201809201053 - esert - COR-730 - protecao 
			return false;
		}
	}

	@Override
	public boolean doBloqueioForcaVenda(String cpf) {
		log.info("doBloqueioForcaVenda(cpf:["+ cpf +"]) - ini");
		log.info("doBloqueioForcaVenda(cpf:["+ cpf +"]) - fim");
		return this.doBloqueioForcaVenda(forcaVendaService.findForcaVendaByCpf(cpf));
	}

	//201809271155 - esert/jota/yalm - COR-833 : Desbloquear Corretora e Força após aceite
	@Override
	public boolean doDesbloqueioCorretoraForcaVenda(ContratoCorretora contratoCorretora) throws Exception {
		log.info("doDesbloqueioCorretora - ini");

        if (contratoCorretora == null) {
            log.error("doDesbloqueioCorretora - BAD_REQUEST - tbodCorretora null");
            return false;
        }

		log.info("contratoCorretora[" + contratoCorretora.toString() + "]");

        TbodCorretora tbodCorretora = corretoraDAO.findOne(contratoCorretora.getCdCorretora());

        if (tbodCorretora == null) {
            log.error("doDesbloqueioCorretora - NO_CONTENT - tbodCorretora == null");
            return false;
        }
        
        this.doBloqueioCorretora(tbodCorretora.getCnpj());
        List<TbodForcaVenda> listTbodForcaVenda = tbodCorretora.getTbodForcaVendas();
      
        if (listTbodForcaVenda == null) {
            log.error("doDesbloqueioCorretora - NO_CONTENT - listTbodForcaVenda == null");
            //return false; Corretora sem ForcaVenda
        }else {
    		log.info("listTbodForcaVenda.size():[" + listTbodForcaVenda.size() + "]");
        	for(TbodForcaVenda tbodForcaVenda : listTbodForcaVenda) {
        		this.doBloqueioForcaVenda(tbodForcaVenda.getCpf());
        	}
        }
        
		log.info("doDesbloqueioCorretora - fim");
        return true;
	}

}


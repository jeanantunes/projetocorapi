package br.com.odontoprev.portal.corretor.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.ConvertObjectDAO;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.model.TbodJsonRequest;
import br.com.odontoprev.portal.corretor.service.ConvertObjectService;
import br.com.odontoprev.portal.corretor.util.ConvertObjectUtil;

@Service
public class ConvertObjectServiceImpl implements ConvertObjectService {
	
	private static final Log log = LogFactory.getLog(ConvertObjectServiceImpl.class);

	@Autowired
	ConvertObjectDAO convertObjectDAO;
	
	@Autowired
	ConvertObjectUtil convertService;

	@Override
	public void addJsonInTable(Venda venda) {

		log.info("[addJsonInTable - venda PF e PME]");
		
		try {
			TbodJsonRequest jsonRequest = new TbodJsonRequest();
			jsonRequest.setCdForcaVenda(venda.getCdForcaVenda().toString());
			jsonRequest.setDtCriacao(new Date());
			jsonRequest.setJson(convertService.ConvertObjectToJson(venda).toString());			
			convertObjectDAO.save(jsonRequest);
		} catch (Exception e) {
			log.error(e);
			log.error("Erro ao cadastrar json de vendas PF e PME :: Detalhe: [" + e.getMessage() + "]");
		}		
	}
	
}

package br.com.odontoprev.portal.corretor.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.ConvertObjectDAO;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaPME;
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
	public void addJsonInTable(Venda vendaPF, VendaPME vendaPME, String userAgent) {

		log.info("[addJsonInTable - venda PF e PME]");
		
		try {
			TbodJsonRequest jsonRequest = new TbodJsonRequest();
			jsonRequest.setCdForcaVenda(vendaPF != null ? vendaPF.getCdForcaVenda().toString() : vendaPME.getCdForcaVenda().toString());			
			jsonRequest.setDtCriacao(new Date());
			jsonRequest.setModeloCelular(userAgent);
			if (vendaPF != null) {
				jsonRequest.setJson(convertService.ConvertObjectToJson(vendaPF,null));
			}else {
				jsonRequest.setJson(convertService.ConvertObjectToJson(null,vendaPME));
			}								
			convertObjectDAO.save(jsonRequest);
		} catch (Exception e) {
			log.error(e);
			log.error("Erro ao cadastrar json de vendas PF e PME :: Detalhe: [" + e.getMessage() + "]");
		}		
	}
	
}

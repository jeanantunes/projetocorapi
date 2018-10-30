package br.com.odontoprev.portal.corretor.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.JsonRequestDAO;
import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaPME;
import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
import br.com.odontoprev.portal.corretor.model.TbodJsonRequest;
import br.com.odontoprev.portal.corretor.service.ConvertObjectService;
import br.com.odontoprev.portal.corretor.util.ConvertObjectUtil;

@Service
public class ConvertObjectServiceImpl implements ConvertObjectService {
	
	private static final Log log = LogFactory.getLog(ConvertObjectServiceImpl.class);
 
	@Autowired
	JsonRequestDAO jsonRequestDAO; //201806061156 - esert - rename de ConvertObjectDAO para JsonRequestDAO mantendo referencia ao model TbodJsonRequest correspondente
	
	@Autowired
	ConvertObjectUtil convertService;
	
	@Autowired
	ForcaVendaDAO forcaVendaDAO;

//201810301710 - esert - exc por desuso //2KILL	
//	@Override
//	public void addJsonInTable(Venda vendaPF, VendaPME vendaPME, String userAgent) {
//
//		log.info("[addJsonInTable - venda PF e PME]");
//		
//		try {
//			TbodForcaVenda tbodForcaVenda = forcaVendaDAO.findOne(vendaPF != null ? vendaPF.getCdForcaVenda() : vendaPME.getCdForcaVenda());
//			
//			TbodJsonRequest jsonRequest = new TbodJsonRequest();
//			jsonRequest.setCdForcaVenda(vendaPF != null ? vendaPF.getCdForcaVenda().toString() : vendaPME.getCdForcaVenda().toString());			
//			jsonRequest.setDtCriacao(new Date());
//			jsonRequest.setModeloCelular(userAgent);
//			if(tbodForcaVenda.getTbodCorretora() != null && tbodForcaVenda.getTbodCorretora().getCdCorretora() != null) {
//				jsonRequest.setCdCorretora(tbodForcaVenda.getTbodCorretora().getCdCorretora());
//			}
//			jsonRequest.setUrl(vendaPF != null ? "/vendapf" : "/vendapme");
//			if (vendaPF != null) {
//				jsonRequest.setJson(convertService.ConvertObjectToJson(vendaPF,null));
//			}else {
//				jsonRequest.setJson(convertService.ConvertObjectToJson(null,vendaPME));
//			}			
//			jsonRequestDAO.save(jsonRequest);
//		} catch (Exception e) {
//			log.error(e);
//			log.error("Erro ao cadastrar json de vendas PF e PME :: Detalhe: [" + e.getMessage() + "]");
//		}		
//	}
	
}

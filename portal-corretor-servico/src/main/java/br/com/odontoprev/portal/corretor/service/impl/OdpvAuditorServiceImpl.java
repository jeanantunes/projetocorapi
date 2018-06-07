package br.com.odontoprev.portal.corretor.service.impl;

import java.security.Principal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.JsonRequestDAO;
import br.com.odontoprev.portal.corretor.model.TbodJsonRequest;
import br.com.odontoprev.portal.corretor.service.OdpvAuditorService;

//201806061517 - esert
@Service
public class OdpvAuditorServiceImpl implements OdpvAuditorService {
	
	private static final Log log = LogFactory.getLog(OdpvAuditorServiceImpl.class);
	
	@Autowired
	JsonRequestDAO jsonRequestDAO;

	/* (non-Javadoc)
	 * @see br.com.odontoprev.portal.corretor.service.impl.OdpvAuditor#audit(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void audit(String requestURI, String stringJsonBody, String stringUserAgent) { //201806061502 - esert
		audit(requestURI, null, stringJsonBody, stringUserAgent);
	}

	/* (non-Javadoc)
	 * @see br.com.odontoprev.portal.corretor.service.impl.OdpvAuditor#audit(java.lang.String, java.security.Principal, java.lang.String, java.lang.String)
	 */
	@Override
	public void audit(String requestURI, Principal principal, String stringJsonBody, String stringUserAgent) {
		// TODO Auto-generated method stub
		log.info("[audit] requestURI:[" + requestURI + "]"); //201806041509 - esert
		log.info("[audit] principal.getName():[" + ((principal != null) ? principal.getName() : "NuLL") + "]"); //201806061120 - esert
		log.info("[audit] body:[" + stringJsonBody + "]"); //201806041519 - esert
		
		try {
			TbodJsonRequest jsonRequest = new TbodJsonRequest();
//			TbodForcaVenda tbodForcaVenda = forcaVendaDAO.findOne(vendaPF != null ? vendaPF.getCdForcaVenda() : vendaPME.getCdForcaVenda());			
//			jsonRequest.setCdForcaVenda(vendaPF != null ? vendaPF.getCdForcaVenda() : vendaPME.getCdForcaVenda());			
			jsonRequest.setDtCriacao(new Date());
			jsonRequest.setModeloCelular(stringUserAgent);
//			if(
//				tbodForcaVenda.getTbodCorretora() != null 
//				&& 
//				tbodForcaVenda.getTbodCorretora().getCdCorretora() != null
//			) {
//				jsonRequest.setCdCorretora(tbodForcaVenda.getTbodCorretora().getCdCorretora());
//			}
//			jsonRequest.setUrl(vendaPF != null ? "/vendapf" : "/vendapme");
			jsonRequest.setUrl(requestURI);
			jsonRequest.setJson(stringJsonBody);
			jsonRequestDAO.save(jsonRequest);
		} catch (Exception e) {
			log.error("[audit] Erro ao cadastrar json de vendas PF e PME :: Detalhe: [" + e.getMessage() + "]", e);
		}	
	}

	/* (non-Javadoc)
	 * @see br.com.odontoprev.portal.corretor.service.impl.OdpvAuditor#audit(java.lang.String)
	 */
	@Override
	public void audit(String body) {
		// TODO Auto-generated method stub
		log.info("[audit] body:[" + body + "]"); //201806041519 - esert		
	}

}

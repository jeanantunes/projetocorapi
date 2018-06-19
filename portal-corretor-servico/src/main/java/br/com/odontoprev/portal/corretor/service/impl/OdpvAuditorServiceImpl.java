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


//	@Override
//	public void audit(String body) {
//		// TODO Auto-generated method stub
//		//log.info("[audit] body:[" + body + "]"); //201806041519 - esert
//		this.audit(
//				null, //String requestURI, 
//				null, //Principal principal, 
//				body, //stringJsonBody, 
//				null, //String stringUserAgent, 
//				null, //String headers, 
//				null //String parameters
//		);
//	}

	@Override
	public void audit( String requestURI, String stringJsonBody, String stringUserAgent) { //201806061502 - esert
		this.audit(
				requestURI, 
				null, 
				stringJsonBody, 
				stringUserAgent, 
				null, 
				null
		); //201806121747 - esert - inc Header + Parameter
	}

//	@Override
//	public void audit(String requestURI, Principal principal, String stringJsonBody, String stringUserAgent) {
//		// TODO Auto-generated method stub
//		this.audit(
//				requestURI, 
//				principal, 
//				stringJsonBody, 
//				stringUserAgent, 
//				null, //String headers, 
//				null //String parameters
//		);
//	}

	//201806121747 - esert - inc Header + Parameter
	@Override
	public void audit( String requestURI, Principal principal, String stringJsonBody, String stringUserAgent, String headers, String parameters) {
		// TODO Auto-generated method stub
		log.info("[audit] requestURI:[" + requestURI + "]"); //201806041509 - esert
		log.info("[audit] principal.getName():[" + ((principal != null) ? principal.getName() : "NuLL") + "]"); //201806061120 - esert
		log.info("[audit] body:[" + stringJsonBody + "]"); //201806041519 - esert
		log.info("[audit] stringUserAgent:[" + stringUserAgent + "]"); //201806121855 - esert
		log.info("[audit] headers:[" + headers + "]"); //201806121747 - esert - inc Header + Parameter
		log.info("[audit] parameters:[" + parameters + "]"); //201806121747 - esert - inc Header + Parameter
		
		try {
			TbodJsonRequest jsonRequest = new TbodJsonRequest();

			jsonRequest.setDtCriacao(new Date());
			
			jsonRequest.setModeloCelular(stringUserAgent);

			int maxLen = requestURI.length();
			//2018061122234 - esert - campo URL tabela TBOD_JSON_REQUEST aumentado de 20 vinte para 200 duzentos caracteres vide fernando@odpv
			if(maxLen > 200) {
				maxLen = 200; //201806111649 - esert/vrodrigues - max 20 so quando maior que 20
			}
			jsonRequest.setUrl(requestURI.substring(0,maxLen)); //201806081223 - esert/vrodrigues - max 20 por enquanto so pra nao quebrar

			jsonRequest.setJson(stringJsonBody);
			
			jsonRequest.setHeader(headers); //201806121747 - esert - inc Header + Parameter  
			
			jsonRequest.setParameter(parameters); //201806121747 - esert - inc Header + Parameter
			
			jsonRequestDAO.save(jsonRequest);
		} catch (Exception e) {
			log.error("[audit(6parm)] Erro ao salvar jsonRequestDAO; e.getMessage:[" + e.getMessage() + "]", e);
		}	
	}

}

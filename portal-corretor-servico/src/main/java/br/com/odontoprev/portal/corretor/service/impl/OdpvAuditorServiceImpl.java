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

			int maxLenRequestURI = requestURI.length();
			//201806122234 - esert - campo URL tabela TBOD_JSON_REQUEST aumentado de 20 vinte para 200 duzentos caracteres vide fernando@odpv
			if(maxLenRequestURI > 200) {
				maxLenRequestURI = 200;
			}
			jsonRequest.setUrl(requestURI.substring(0,maxLenRequestURI)); //201806122234 - esert - campo URL tabela TBOD_JSON_REQUEST aumentado de 20 vinte para 200 duzentos

			jsonRequest.setJson(stringJsonBody);
			
			int maxLenHeaders = headers.length();
			//201806191615 - esert - alt protecao limite tamanho header em 3000 vide bug gerado com cerca de 2200 em 2018061915xx visto em homolog por esert + fsetai@odpv + rmarques@odpv
			if(maxLenHeaders > 3000) {
				maxLenHeaders = 3000;
			}
			jsonRequest.setHeader(headers.substring(0, maxLenHeaders)); //201806191615 - esert - alt protecao limite tamanho headers em 3000  
			
			//201806191615 - esert - alt protecao limite tamanho parameters em 3000 
			int maxLenParameters = parameters.length();
			if(maxLenParameters > 3000) {
				maxLenParameters = 3000; 
			}
			jsonRequest.setParameter(parameters.substring(0, maxLenParameters)); //201806191615 - esert - alt protecao limite tamanho parameters em 3000
			
			jsonRequestDAO.save(jsonRequest);
		} catch (Exception e) {
			log.error("[audit(6parm)] Erro ao salvar jsonRequestDAO; e.getMessage:[" + e.getMessage() + "]", e);
		}	
	}

}

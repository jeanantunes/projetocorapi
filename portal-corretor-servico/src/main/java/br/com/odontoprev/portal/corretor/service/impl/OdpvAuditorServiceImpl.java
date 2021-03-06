package br.com.odontoprev.portal.corretor.service.impl;

import java.security.Principal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.dao.JsonRequestDAO;
import br.com.odontoprev.portal.corretor.model.TbodJsonRequest;
import br.com.odontoprev.portal.corretor.service.OdpvAuditorService;

//201806061517 - esert
@Service
//@Transactional(noRollbackFor= {Exception.class}) //201810181847 - esert - COR-763:Isolar Inserção JSON Request DCMS
public class OdpvAuditorServiceImpl implements OdpvAuditorService {
	
	private static final Log log = LogFactory.getLog(OdpvAuditorServiceImpl.class);
	
	@Autowired
	JsonRequestDAO jsonRequestDAO;

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

	//201806121747 - esert - inc Header + Parameter
	@Override
	@Transactional(isolation=Isolation.DEFAULT, readOnly=false, propagation=Propagation.REQUIRES_NEW) //201810191204 - esert - COR-763:Isolar Inserção JSON Request DCMS
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

			int maxLenRequestURI = 0;
			if(requestURI != null) {//201806291132 - esert - protecao - pq na chamada dcss nao tem header deu nullPointerException
				maxLenRequestURI = requestURI.length();
				//201806122234 - esert - campo URL tabela TBOD_JSON_REQUEST aumentado de 20 vinte para 200 duzentos caracteres vide fernando@odpv
				if(maxLenRequestURI > 200) {
					maxLenRequestURI = 200;
				}
				jsonRequest.setUrl(requestURI.substring(0,maxLenRequestURI)); //201806122234 - esert - campo URL tabela TBOD_JSON_REQUEST aumentado de 20 vinte para 200 duzentos
			}

			int maxLenJsonBody = 0;
			if(stringJsonBody != null) { //201810301731 - esert - protecao - alt protecao limite tamanho Json em 4000
				maxLenJsonBody = stringJsonBody.length();
				if(maxLenJsonBody > 4000) {
					maxLenJsonBody = 4000;
				}
				jsonRequest.setJson(stringJsonBody.substring(0, maxLenJsonBody)); //201810301731 - esert - alt protecao limite tamanho Json em 4000  
			}
			
			int maxLenHeaders = 0;
			if(headers != null) { //201806291132 - esert - protecao - pq na chamada dcss nao tem header deu nullPointerException
				maxLenHeaders = headers.length();
				//201806191615 - esert - alt protecao limite tamanho header em 3000 vide bug gerado com cerca de 2200 em 2018061915xx visto em homolog por esert + fsetai@odpv + rmarques@odpv
				if(maxLenHeaders > 3000) {
					maxLenHeaders = 3000;
				}
				jsonRequest.setHeader(headers.substring(0, maxLenHeaders)); //201806191615 - esert - alt protecao limite tamanho headers em 3000  
			}
			
			//201806191615 - esert - alt protecao limite tamanho parameters em 3000 
			int maxLenParameters = 0;
			if(parameters != null) { //201806291132 - esert - protecao - pq na chamada dcss nao tem header deu nullPointerException
				maxLenParameters = parameters.length();
				if(maxLenParameters > 3000) {
					maxLenParameters = 3000; 
				}
				jsonRequest.setParameter(parameters.substring(0, maxLenParameters)); //201806191615 - esert - alt protecao limite tamanho parameters em 3000
			}
			
			jsonRequestDAO.save(jsonRequest);
		} catch (Exception e) {
			log.error("[audit(6parm)] Erro ao salvar jsonRequestDAO; e.getMessage:[" + e.getMessage() + "]", e);
		}	
	}

}

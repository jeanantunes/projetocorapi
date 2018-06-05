package br.com.odontoprev.portal.corretor.interceptor;

import java.security.Principal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Auditor {
	private static final Log log = LogFactory.getLog(Auditor.class);

	public static void audit(String requestURI, Principal userPrincipal, String body) {
		// TODO Auto-generated method stub
		log.info("[audit] requestURI:[" + requestURI + "]"); //201806041509 - esert
		log.info("[audit] userPrincipal.getName():[" + userPrincipal.getName() + "]"); //201806041509 - esert
		log.info("[audit] body:[" + body + "]"); //201806041519 - esert
		
	}

	public static void audit(String body) {
		// TODO Auto-generated method stub
		log.info("[audit] body:[" + body + "]"); //201806041519 - esert		
	}

}

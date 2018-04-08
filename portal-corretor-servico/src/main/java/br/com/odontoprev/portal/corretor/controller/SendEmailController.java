/*package br.com.odontoprev.portal.corretor.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.business.SendMailEsqueciSenha;



@RestController
public class SendEmailController {
	
	private static final Log log = LogFactory.getLog(SendEmailController.class);
	
	@Autowired
	SendMailEsqueciSenha sendMail;
	
	@RequestMapping(value = "/sendMail", method = { RequestMethod.GET })
	public void sendMail() {
		
		log.info("Inicio sendMail");
		
		sendMail.sendMail(null);
		
		log.info("Fim sendMail");
	}

}
*/
package br.com.odontoprev.portal.corretor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.business.SendMailEsqueciSenha;



@RestController
public class SendEmailEsqueciSenhaTesteController {
	
	private static final Log log = LogFactory.getLog(SendEmailEsqueciSenhaTesteController.class);
	
	@Autowired
	SendMailEsqueciSenha sendMailEsqueciSenha;
	
//	@RequestMapping(value = "/sendMailEsqueciSenhaTeste", method = { RequestMethod.GET })
//	public void sendMailEsqueciSenhaTeste() {
//		
//		log.info("Inicio sendMail");
//		
//		sendMailEsqueciSenha.sendMail("izaura.fsilva@gmail.com", "MzgzMzA5ODI4NzQyMDE4LTA0LTA2VDE3OjQxOjI5LjgwNg");
//		
//		log.info("Fim sendMail");
//	}

}

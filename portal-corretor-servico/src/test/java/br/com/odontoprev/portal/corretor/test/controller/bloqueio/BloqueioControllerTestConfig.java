package br.com.odontoprev.portal.corretor.test.controller.bloqueio;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.odontoprev.portal.corretor.controller.LoginController;
import br.com.odontoprev.portal.corretor.service.BloqueioService;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;
import br.com.odontoprev.portal.corretor.service.LoginService;

//201809181600 - esert - COR-731 : TDD - Novo serviço (processar bloqueio)
//Classe responsável por configurar objetos que queremos mockar
@Configuration
@ComponentScan(
	    value = "br.com.odontoprev.portal.corretor.controller",
	    useDefaultFilters = false,
	    includeFilters = {
	    	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = LoginController.class)
//	    	,@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = LoginDAO.class)
	    })

@EnableWebMvc
public class BloqueioControllerTestConfig {

	 //Mocando service que injetado no Controller
	 @Bean
	 @Primary
	 @Scope("application")
     public LoginService loginServiceMock() { 
		 return Mockito.mock(LoginService.class); 
	 }	 	 
	 
//	 @Bean
//	 @Primary
//	 @Scope("application")
//	 public BloqueioService bloqueioServiceMock() { 
//		 return Mockito.mock(BloqueioService.class); 
//	 }	 	 
//	 
//	 @Bean
//	 @Primary
//	 @Scope("application")
//	 public ForcaVendaService forcaVendaServiceMock() { 
//		 return Mockito.mock(ForcaVendaService.class); 
//	 }	 	 
}

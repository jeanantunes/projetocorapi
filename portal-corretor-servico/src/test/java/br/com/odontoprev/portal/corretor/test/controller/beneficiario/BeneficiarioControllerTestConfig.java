package br.com.odontoprev.portal.corretor.test.controller.beneficiario;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.odontoprev.portal.corretor.business.BeneficiarioBusiness;
import br.com.odontoprev.portal.corretor.controller.BeneficiarioController;
import br.com.odontoprev.portal.corretor.service.BeneficiarioService;

//Classe responsável por configurar objetos que queremos mockar
@Configuration
@ComponentScan(
		value = "br.com.odontoprev.portal.corretor.controller",
	    useDefaultFilters = false,
	    includeFilters = {
	        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = BeneficiarioController.class)
	    })

@EnableWebMvc
public class BeneficiarioControllerTestConfig {
	 //Mocando service que injetado no Controller
	 @Bean
	 @Primary
	 @Scope("application")
     public BeneficiarioService service() { 
		 return Mockito.mock(BeneficiarioService.class); 
	 }	 	 

	 //Mocando business que injetado no Controller
	 @Bean
	 @Primary
	 @Scope("application")
     public BeneficiarioBusiness business() { 
		 return Mockito.mock(BeneficiarioBusiness.class); 
	 }	 	 
}

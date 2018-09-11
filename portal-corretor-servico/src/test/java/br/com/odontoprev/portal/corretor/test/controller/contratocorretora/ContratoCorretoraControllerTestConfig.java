package br.com.odontoprev.portal.corretor.test.controller.contratocorretora;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.odontoprev.portal.corretor.controller.ContratoCorretoraController;
import br.com.odontoprev.portal.corretor.service.ContratoCorretoraService;

//Classe responsável por configurar objetos que queremos mockar
@Configuration

@ComponentScan(
		value = "br.com.odontoprev.portal.corretor.controller",
	    useDefaultFilters = false,
	    includeFilters = {
	        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ContratoCorretoraController.class),
	    })

@EnableWebMvc
public class ContratoCorretoraControllerTestConfig {
	 //Mocando service que injetado no Controller
	 @Bean
	 @Primary
	 @Scope("application")
     public ContratoCorretoraService service() {
		 return Mockito.mock(ContratoCorretoraService.class);
	 }	 	 
}

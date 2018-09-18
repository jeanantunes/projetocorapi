package br.com.odontoprev.portal.corretor.test.controller.bloqueio;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.odontoprev.portal.corretor.controller.ForcaVendaController;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;


//Classe responsável por configurar objetos que queremos mockar
@Configuration
@ComponentScan(
	    value = "br.com.odontoprev.portal.corretor.controller",
	    useDefaultFilters = false,
	    includeFilters = {
	        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ForcaVendaController.class)
	    })

@EnableWebMvc
public class ForcaVendaControllerTestConfig {

	 //Mocando service que injetado no Controller
	 @Bean
	 @Primary
	 @Scope("application")
     public ForcaVendaService service() { 
		 return Mockito.mock(ForcaVendaService.class); 
	 }	 	 
}

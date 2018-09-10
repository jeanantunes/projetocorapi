package br.com.odontoprev.portal.corretor.test.controller.contratocorretora;

import br.com.odontoprev.portal.corretor.controller.ContratoCorretoraController;
import br.com.odontoprev.portal.corretor.service.ContratoCorretoraService;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//Classe responsável por configurar objetos que queremos mockar
@Configuration
@ComponentScan(
		value = "br.com.odontoprev.portal.corretor.controller",
	    useDefaultFilters = false,
	    includeFilters = {
	        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ContratoCorretoraController.class)
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

package br.com.odontoprev.portal.corretor.test.controller.plano;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.odontoprev.portal.corretor.controller.PlanoController;
import br.com.odontoprev.portal.corretor.service.PlanoService;

//201810231601 - esert - COR-932:API - Novo GET /planoinfo
//Classe responsável por configurar objetos que queremos mockar
@Configuration
@ComponentScan(
	    value = "br.com.odontoprev.portal.corretor.controller",
	    useDefaultFilters = false,
	    includeFilters = {
	        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = PlanoController.class)
	    })

@EnableWebMvc
public class PlanoControllerTestConfig {

	 //Mocando service que injetado no Controller
	 @Bean
	 @Primary
	 @Scope("application")
     public PlanoService service() { 
		 return Mockito.mock(PlanoService.class); 
	}
}

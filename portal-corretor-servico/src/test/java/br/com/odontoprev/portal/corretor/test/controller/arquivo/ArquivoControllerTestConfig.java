package br.com.odontoprev.portal.corretor.test.controller.arquivo;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.odontoprev.portal.corretor.controller.ArquivoController;
import br.com.odontoprev.portal.corretor.service.ArquivoService;

//201810231900 - esert - COR-723:API - Novo GET/ARQUIVO/(ID)
//Classe responsável por configurar objetos que queremos mockar
@Configuration
@ComponentScan(
	    value = "br.com.odontoprev.portal.corretor.controller",
	    useDefaultFilters = false,
	    includeFilters = {
	        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ArquivoController.class)
	    })

@EnableWebMvc
public class ArquivoControllerTestConfig {

	 //Mocando service que injetado no Controller
	 @Bean
	 @Primary
	 @Scope("application")
     public ArquivoService service() { 
		 return Mockito.mock(ArquivoService.class); 
	 }
}

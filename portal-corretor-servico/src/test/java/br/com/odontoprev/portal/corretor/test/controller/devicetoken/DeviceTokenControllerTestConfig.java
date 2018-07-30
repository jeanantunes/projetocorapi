package br.com.odontoprev.portal.corretor.test.controller.devicetoken;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.odontoprev.portal.corretor.controller.DeviceTokenController;
import br.com.odontoprev.portal.corretor.service.DeviceTokenService;


//Classe responsável por configurar objetos que queremos mockar
@Configuration
@ComponentScan(
	    value = "br.com.odontoprev.portal.corretor.controller",
	    useDefaultFilters = false,
	    includeFilters = {
	        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = DeviceTokenController.class)
	    })
@EnableWebMvc
public class DeviceTokenControllerTestConfig {

	 //Mocando service que injetado no Controller
	 @Bean
	 @Primary
	 @Scope("application")
     public DeviceTokenService service() { 
		 return Mockito.mock(DeviceTokenService.class); 
	}
	 
	 
}

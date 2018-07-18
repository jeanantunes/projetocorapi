package br.com.odontoprev.portal.corretor.test.controller.devicetoken;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import br.com.odontoprev.portal.corretor.service.DeviceTokenService;

@Configuration
public class MockitoServiceProvider {

	 @Bean
	 @Primary
     public DeviceTokenService service() { 
		 return Mockito.mock(DeviceTokenService.class); 
	}
}

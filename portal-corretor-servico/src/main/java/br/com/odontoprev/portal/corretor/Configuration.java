package br.com.odontoprev.portal.corretor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.odontoprev.portal.corretor.event.listener.EventInterceptorHandler;

@org.springframework.context.annotation.Configuration
@ComponentScan("br.com.odontoprev.portal.corretor")
public class Configuration  extends WebMvcConfigurerAdapter {

	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(eventInterceptorHandler());
	}
	
	@Bean
    public EventInterceptorHandler eventInterceptorHandler() {
        return new EventInterceptorHandler();
    }
}

package br.com.odontoprev.portal.corretor;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.odontoprev.portal.corretor.interceptor.LoggerInterceptor;
import br.com.odontoprev.portal.corretor.interceptor.OdpvLogFilter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(new LoggerInterceptor());
	}
	
	// h t t p s :// stackoverflow .com /questions /19825946 /how-to-add-a-filter-class-in-spring-boot
	//201806052013 - esert - interceptor com filter
	@Bean
	public FilterRegistrationBean someFilterRegistration() {

	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter((javax.servlet.Filter) odpvLogFilter());
//	    registration.addUrlPatterns("/vendapme/*");
	    registration.addUrlPatterns("/*");
	    registration.addInitParameter("paramName1", "paramValue1");
	    registration.addInitParameter("paramName2", "paramValue2");
	    registration.setName("odpvLogFilter");
	    registration.setOrder(1);
	    return registration;
	} 

	//201806052013 - esert - interceptor com filter
	@Bean //201806071200 - esert/rmarques - faltava esta anotacao bean aqui - dica by roberto
	public Filter odpvLogFilter() {
	    //return new SomeFilter();
	    return (Filter) new OdpvLogFilter();
	}
}

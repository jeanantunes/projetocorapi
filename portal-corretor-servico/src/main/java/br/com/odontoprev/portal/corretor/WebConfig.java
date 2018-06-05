package br.com.odontoprev.portal.corretor;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.odontoprev.portal.corretor.interceptor.CacheFilter;
import br.com.odontoprev.portal.corretor.interceptor.LoggerInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(new LoggerInterceptor());
	}
	
	// h t t p s :// stackoverflow .com /questions /19825946 /how-to-add-a-filter-class-in-spring-boot
	//201806052013 - esert
	@Bean
	public FilterRegistrationBean someFilterRegistration() {

	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter((javax.servlet.Filter) someFilter());
	    registration.addUrlPatterns("/vendapme/*");
	    registration.addInitParameter("paramName", "paramValue");
	    registration.setName("someFilter");
	    registration.setOrder(1);
	    return registration;
	} 

	//201806052013 - esert
	public Filter someFilter() {
	    //return new SomeFilter();
	    return (Filter) new CacheFilter();
	}
}

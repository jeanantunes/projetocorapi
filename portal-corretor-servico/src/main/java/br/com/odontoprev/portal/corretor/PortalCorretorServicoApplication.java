package br.com.odontoprev.portal.corretor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
@EnableAutoConfiguration
public class PortalCorretorServicoApplication extends SpringBootServletInitializer
		implements WebApplicationInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PortalCorretorServicoApplication.class, args);
	}
}

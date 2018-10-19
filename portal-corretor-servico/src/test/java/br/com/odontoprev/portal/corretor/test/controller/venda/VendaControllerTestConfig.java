package br.com.odontoprev.portal.corretor.test.controller.venda;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.odontoprev.portal.corretor.controller.VendaController;
import br.com.odontoprev.portal.corretor.service.VendaPFService;

//201808291727 - esert - COR-632 gerar pdf pme tdd POST/vendapme

//Classe responsável por configurar objetos que queremos mockar
@Configuration
@ComponentScan(value = "br.com.odontoprev.portal.corretor.controller", useDefaultFilters = false, includeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = VendaController.class) })

@EnableWebMvc
public class VendaControllerTestConfig {

	// Mocando service que injetado no Controller
	@Bean
	@Primary
	@Scope("application")
	public VendaPFService service() {
		return Mockito.mock(VendaPFService.class);
	}
}

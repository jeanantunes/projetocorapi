package br.com.odontoprev.portal.corretor.test.controller.corretora;

import br.com.odontoprev.portal.corretor.controller.CorretoraController;
import br.com.odontoprev.portal.corretor.service.CorretoraService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//Classe responsável por configurar objetos que queremos mockar
@Configuration
@ComponentScan(
        value = "br.com.odontoprev.portal.corretor.controller",
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CorretoraController.class)
        })
@EnableWebMvc
public class CorretoraControllerTestConfig {

    //Mocando service que injetado no Controller
    @Bean
    @Primary
    @Scope("application")
    public CorretoraService service() {
        return Mockito.mock(CorretoraService.class);
    }

}

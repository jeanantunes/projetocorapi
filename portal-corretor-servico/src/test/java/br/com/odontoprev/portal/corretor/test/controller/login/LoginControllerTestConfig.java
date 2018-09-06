package br.com.odontoprev.portal.corretor.test.controller.login;

import br.com.odontoprev.portal.corretor.controller.LoginController;
import br.com.odontoprev.portal.corretor.service.LoginService;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


//Classe responsável por configurar objetos que queremos mockar
@Configuration
@ComponentScan(
        value = "br.com.odontoprev.portal.corretor.controller",
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = LoginController.class)
        })
@EnableWebMvc
public class LoginControllerTestConfig {

    //Mocando service que injetado no Controller
    @Bean
    @Primary
    @Scope("application")
    public LoginService service() {
        return Mockito.mock(LoginService.class);
    }


}

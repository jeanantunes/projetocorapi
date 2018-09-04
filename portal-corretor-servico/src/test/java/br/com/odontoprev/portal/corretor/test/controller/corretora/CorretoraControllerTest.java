package br.com.odontoprev.portal.corretor.test.controller.corretora;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.service.CorretoraService;
import br.com.odontoprev.portal.corretor.test.controller.corretora.CorretoraControllerTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.google.gson.Gson;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;



@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CorretoraControllerTestConfig.class })
@WebAppConfiguration
public class CorretoraControllerTest {


    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUpClass() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        Mockito.reset(service);
    }


    @Autowired
    private CorretoraService service;

    @Test
    public void testGetCorretoraOk200() throws Exception {

        String cnpjCorretora = "64154543000146";

        //Mockando Service que busca no banco de dados
        given(service.buscaCorretoraPorCnpj(cnpjCorretora)).willReturn(new Corretora());

        //Efetua a requisição na rota e espera um status code
        mvc.perform(get("/corretora/"+cnpjCorretora)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
        ;

    }

    @Test
    public void testGetCorretoraNoContent204() throws Exception {

        String cnpjCorretora = "51551";

        //Efetua a requisição na rota e espera um status code
        mvc.perform(get("/corretora/"+cnpjCorretora)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
        ;

    }


}

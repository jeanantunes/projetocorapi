package br.com.odontoprev.portal.corretor.test.controller.corretora;

import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.CorretoraResponse;
import br.com.odontoprev.portal.corretor.dto.Login;
import br.com.odontoprev.portal.corretor.service.CorretoraService;
import com.google.gson.Gson;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



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

    @Test
    public void testPutEmailCorretoraOk200() throws Exception {

        long cdCorretora = 21L;

        Corretora corretora = new Corretora();

        corretora.setCdCorretora(cdCorretora);
        corretora.setEmail("fernandinha@odontoprev.com");

        String jsonRequest = new Gson().toJson(corretora);

        given(service.updateCorretoraDados(corretora)).willReturn(new CorretoraResponse(cdCorretora, "Corretora atualizada. CNPJ [ 64154543000146 ]"));


        //Efetua a requisição na rota e espera um status code
        mvc.perform(put("/corretora/dados")
                .content(jsonRequest)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
        ;

    }

    @Test
    public void testPutEmailCorretoraBadRequestSemCdCorretora400() throws Exception {

        long cdCorretora = 0L;

        Corretora corretora = new Corretora();

        corretora.setEmail("fernandinha@odontoprev.com");

        String jsonRequest = new Gson().toJson(corretora);

        given(service.updateCorretoraDados(corretora)).willReturn(new CorretoraResponse(cdCorretora, "CdCorretora nao informado. Corretora nao atualizada!"));


        //Efetua a requisição na rota e espera um status code
        mvc.perform(put("/corretora/dados")
                .content(jsonRequest)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;

    }

    @Test
    public void testPutEmailCorretoraNoContent204() throws Exception {

        long cdCorretora = 2551151L;

        Corretora corretora = new Corretora();

        corretora.setEmail("fernandinha@odontoprev.com");
        corretora.setCdCorretora(cdCorretora);

        String jsonRequest = new Gson().toJson(corretora);

        //Efetua a requisição na rota e espera um status code
        mvc.perform(put("/corretora/dados")
                .content(jsonRequest)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
        ;

    }

    @Test
    public void testGetCorretoraOk200retornandoSemBloqueio() throws Exception {

        String cnpjCorretora = "64154543000146";

        long cdCorretora = 2551151L;

        boolean temBloqueio = false;
        Long cdTipoBloqueio = 0L;
        String stringSemBloqueio = "SEM BLOQUEIO";

        Login loginGiven = new Login();
        loginGiven.setTemBloqueio(temBloqueio);
        loginGiven.setCodigoTipoBloqueio(cdTipoBloqueio);
        loginGiven.setDescricaoTipoBloqueio(stringSemBloqueio);

        Corretora corretora = new Corretora();
        corretora.setEmail("fernandinha@odontoprev.com");
        corretora.setCdCorretora(cdCorretora);
        corretora.setLogin(loginGiven);

        //Mockando Service que busca no banco de dados
        given(service.buscaCorretoraPorCnpj(cnpjCorretora)).willReturn(corretora);

        //Efetua a requisição na rota e espera um status code
        mvc.perform(get("/corretora/"+cnpjCorretora)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.login.temBloqueio").value(temBloqueio))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login.codigoTipoBloqueio").value(cdTipoBloqueio))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login.descricaoTipoBloqueio").value(stringSemBloqueio));

    }

    @Test
    public void testGetCorretoraOk200retornandoComBloqueio() throws Exception {

        String cnpjCorretora = "64154543000146";

        long cdCorretora = 2551151L;

        boolean temBloqueio = true;
        Long cdTipoBloqueio = 1L;
        String stringSemBloqueio = "PENDENTE ACEITAR CONTRATO DE CORRETAGEM OU INTERMEDIAÇÃO.";

        Login loginGiven = new Login();
        loginGiven.setTemBloqueio(temBloqueio);
        loginGiven.setCodigoTipoBloqueio(cdTipoBloqueio);
        loginGiven.setDescricaoTipoBloqueio(stringSemBloqueio);

        Corretora corretora = new Corretora();
        corretora.setEmail("fernandinha@odontoprev.com");
        corretora.setCdCorretora(cdCorretora);
        corretora.setLogin(loginGiven);

        //Mockando Service que busca no banco de dados
        given(service.buscaCorretoraPorCnpj(cnpjCorretora)).willReturn(corretora);

        //Efetua a requisição na rota e espera um status code
        mvc.perform(get("/corretora/"+cnpjCorretora)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.login.temBloqueio").value(temBloqueio))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login.codigoTipoBloqueio").value(cdTipoBloqueio))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login.descricaoTipoBloqueio").value(stringSemBloqueio));

    }


}

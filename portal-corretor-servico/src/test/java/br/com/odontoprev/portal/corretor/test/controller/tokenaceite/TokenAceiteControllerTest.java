package br.com.odontoprev.portal.corretor.test.controller.tokenaceite;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import br.com.odontoprev.portal.corretor.dto.TokenAceite;
import br.com.odontoprev.portal.corretor.dto.TokenAceiteResponse;
import br.com.odontoprev.portal.corretor.service.TokenAceiteService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TokenAceiteControllerTestConfig.class })
@WebAppConfiguration
public class TokenAceiteControllerTest {

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUpClass() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		Mockito.reset(service);
	}

	@Autowired
	private TokenAceiteService service;

	//201808281944 - esert - COR-657 teste automatico do COR-656 atualizar pdf pme ao click aceite 
	@Test
	public void testOk200UpdateTokenAceite() throws Exception {

		// Montando Given
		TokenAceite tokenAceiteGiven = new TokenAceite();
		tokenAceiteGiven.setToken("TOKEN");

		// Montando Request
		String jsonRequest = new Gson().toJson(tokenAceiteGiven);

		// Montando Response
		TokenAceiteResponse tokenAceiteResponse = new TokenAceiteResponse(HttpStatus.OK.value(), HttpStatus.OK.name());

		// Mockando Service que busca no banco de dados
		given(service.updateTokenAceite(tokenAceiteGiven)).willReturn(tokenAceiteResponse);

		// Efetua a requisição na rota e espera um status code
		mvc.perform(put("/token").content(jsonRequest).contentType(APPLICATION_JSON)).andExpect(status().isOk());

		// Verifica se os metódos da lógica interna foram chamados
		BDDMockito.verify(service).updateTokenAceite(tokenAceiteGiven);
	}
	
	//TODO: 201808281933 - esert - DEBITO TECNICO - nao foi possivel implementar outros testes pq o metodo nao esta adequado e a mudanca implica impactos nao previstos nem analisados

}

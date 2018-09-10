package br.com.odontoprev.portal.corretor.test.controller.contratocorretora;

import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraDataAceite;
import br.com.odontoprev.portal.corretor.service.ContratoCorretoraService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ContratoCorretoraControllerTestConfig.class })
@WebAppConfiguration
public class ContratoCorretoraControllerTest {
	
	   
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
	private ContratoCorretoraService service;

	@Test
	public void testOk200() throws Exception {

		   Long cdCorretora = 3L;
		   String dataDeAceite = "2011-09-20 00:00:00";

		   ContratoCorretoraDataAceite contratoCorretora = new ContratoCorretoraDataAceite();
		   contratoCorretora.setCdCorretora(cdCorretora);
		   contratoCorretora.setDtAceiteContrato(dataDeAceite);

		   //Mockando Service que busca no banco de dados 
		   given(service.getDataAceiteContratoByCdCorretora(cdCorretora)).willReturn(contratoCorretora);
		   
		   //Efetua a requisição na rota e espera um status code
		   mvc.perform(get("/contratocorretora/" + cdCorretora + "/dataaceite")
				   .contentType(APPLICATION_JSON))
				   .andExpect(MockMvcResultMatchers.jsonPath("$.cdCorretora").value(cdCorretora))
				   .andExpect(MockMvcResultMatchers.jsonPath("$.dtAceiteContrato").value(dataDeAceite))
		   .andExpect(status().isOk())
		   ;
	}

	@Test
	public void testBadRequest400() throws Exception {

		String cdCorretora = "a";

		//Efetua a requisição na rota e espera um status code
		mvc.perform(get("/contratocorretora/" + cdCorretora + "/dataaceite")
				.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
		;
	}

	@Test
	public void testNoContent204() throws Exception {

		Long cdCorretora = 488484L;
		String dataDeAceite = "2011-09-20 00:00:00";

		ContratoCorretoraDataAceite contratoCorretora = new ContratoCorretoraDataAceite();
		contratoCorretora.setCdCorretora(cdCorretora);
		contratoCorretora.setDtAceiteContrato(dataDeAceite);
		
		//Efetua a requisição na rota e espera um status code
		mvc.perform(get("/contratocorretora/" + cdCorretora + "/dataaceite")
				.contentType(APPLICATION_JSON))
				.andExpect(status().isNoContent())
		;
	}

}

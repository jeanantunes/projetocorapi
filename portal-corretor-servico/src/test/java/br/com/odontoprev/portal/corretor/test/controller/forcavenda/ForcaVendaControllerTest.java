package br.com.odontoprev.portal.corretor.test.controller.forcavenda;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.dto.ForcaVendaResponse;
import br.com.odontoprev.portal.corretor.enums.StatusForcaVendaEnum;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;
import br.com.odontoprev.portal.corretor.util.Constantes;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ForcaVendaControllerTestConfig.class })
@WebAppConfiguration
public class ForcaVendaControllerTest {

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUpClass() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		Mockito.reset(service);
	}

	@Autowired
	private ForcaVendaService service;

	@Test
	public void testOk200Update() throws Exception {
		//Given
		Long cdForcaVendaGiven = 6L;
		ForcaVenda forcaVendaGiven = new ForcaVenda();
		forcaVendaGiven.setCdForcaVenda(cdForcaVendaGiven);
		forcaVendaGiven.setNome("FERNANDO SETAI");
		forcaVendaGiven.setCelular("11980910754");
		forcaVendaGiven.setEmail("fernando.mota@odontoprev.com.br");
		forcaVendaGiven.setCorretora(new Corretora());
		forcaVendaGiven.getCorretora().setCdCorretora(21L);
		forcaVendaGiven.setStatusForcaVenda(String.valueOf(StatusForcaVendaEnum.ATIVO.getCodigo()));
		forcaVendaGiven.setCpf("38330982874");
		forcaVendaGiven.setStatus(Constantes.ATIVO);
		forcaVendaGiven.setDataNascimento(null);
		forcaVendaGiven.setCargo(null);
		forcaVendaGiven.setDepartamento(null);
		String json = new Gson().toJson(forcaVendaGiven);

		ForcaVendaResponse forcaVendaResponseGiven = new ForcaVendaResponse(cdForcaVendaGiven,"OK");
		
		// Mockando Service que busca no banco de dados
		given(service.updateForcaVenda(forcaVendaGiven))
			.willReturn(forcaVendaResponseGiven);

		// Efetua a requisição na rota e espera um status code
		mvc.perform(put("/forcavenda")
			.content(json)
			.contentType(APPLICATION_JSON))
			.andExpect(status().isOk());	
	}

	@Test
	public void testNoContent204Update() throws Exception {
		//Given
		Long cdForcaVendaGiven = 6L;
		ForcaVenda forcaVendaGiven = new ForcaVenda();
		forcaVendaGiven.setCdForcaVenda(cdForcaVendaGiven);
		forcaVendaGiven.setNome("FERNANDO SETAI");
		forcaVendaGiven.setCelular("11980910754");
		forcaVendaGiven.setEmail("fernando.mota@odontoprev.com.br");
		forcaVendaGiven.setCorretora(new Corretora());
		forcaVendaGiven.getCorretora().setCdCorretora(21L);
		forcaVendaGiven.setStatusForcaVenda(String.valueOf(StatusForcaVendaEnum.ATIVO.getCodigo()));
		forcaVendaGiven.setCpf("38330982874");
		forcaVendaGiven.setStatus(Constantes.ATIVO);
		forcaVendaGiven.setDataNascimento(null);
		forcaVendaGiven.setCargo(null);
		forcaVendaGiven.setDepartamento(null);
		//String jsonGiven = new Gson().toJson(forcaVendaGiven);

		ForcaVendaResponse forcaVendaResponseGiven = new ForcaVendaResponse(cdForcaVendaGiven,"OK");

		//Request
		Long cdForcaVendaRequest = 8L;
		ForcaVenda forcaVendaRequest = new ForcaVenda();
		forcaVendaRequest.setCdForcaVenda(cdForcaVendaRequest);
		forcaVendaRequest.setNome("ROBERTO DOS SANTOS MARQUES DA SILVA");
		forcaVendaRequest.setCelular("11960968592");
		forcaVendaRequest.setEmail("roberto.msilva@odontoprev.com.br");
		forcaVendaRequest.setCorretora(new Corretora());
		forcaVendaRequest.getCorretora().setCdCorretora(21L);
		forcaVendaRequest.setStatusForcaVenda(String.valueOf(StatusForcaVendaEnum.PRE_CADASTRO.getCodigo()));
		forcaVendaRequest.setCpf("37483733845");
		forcaVendaRequest.setStatus(Constantes.NAO);
		forcaVendaRequest.setDataNascimento(null);
		forcaVendaRequest.setCargo(null);
		forcaVendaRequest.setDepartamento(null);
		String jsonRequest = new Gson().toJson(forcaVendaRequest);

		// Mockando Service que busca no banco de dados
		given(service.updateForcaVenda(forcaVendaGiven))
			.willReturn(forcaVendaResponseGiven);

		// Efetua a requisição na rota e espera um status code
		mvc.perform(put("/forcavenda")
			.content(jsonRequest)
			.contentType(APPLICATION_JSON))
			.andExpect(status().isNoContent());	
	}

}

package br.com.odontoprev.portal.corretor.test.controller.bloqueio;

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
	}

}

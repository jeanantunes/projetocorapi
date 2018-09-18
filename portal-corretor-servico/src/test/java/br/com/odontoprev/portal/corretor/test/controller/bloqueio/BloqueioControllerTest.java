package br.com.odontoprev.portal.corretor.test.controller.bloqueio;

import static org.mockito.BDDMockito.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.service.BloqueioService;

//201809181600 - esert - COR-731 : TDD - Novo serviço (processar bloqueio)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { BloqueioControllerTestConfig.class })
@WebAppConfiguration
public class BloqueioControllerTest {

	//private MockMvc mvc;

	@Autowired
	//private WebApplicationContext context;

	@Before
	public void setUpClass() {
		//mvc = MockMvcBuilders.webAppContextSetup(context).build();
		Mockito.reset(service);
	}

	@Autowired
	private BloqueioService service;

	@Test
	public void testOkdoBloqueioForcaVenda() throws Exception {
		//Given
		Long cdForcaVendaGiven = 6L;
		ForcaVenda forcaVendaGiven = new ForcaVenda();
		forcaVendaGiven.setCdForcaVenda(cdForcaVendaGiven);

		// Mockando Service que busca no banco de dados
		given(service.doBloqueioForcaVenda(forcaVendaGiven)).willReturn(true);
		
		BDDMockito.verify(service).doBloqueioForcaVenda(forcaVendaGiven);
	}

	@Test
	public void testOkdoBloqueioForcaVendaCPF() throws Exception {
		//Given
		String CPFForcaVendaGiven = "38330982874"; //6	FERNANDO SETAI

		// Mockando Service que busca no banco de dados
		given(service.doBloqueioForcaVenda(CPFForcaVendaGiven)).willReturn(true);
		
		BDDMockito.verify(service).doBloqueioForcaVenda(CPFForcaVendaGiven);
	}
	
	@Test
	public void testOkdoBloqueioCorretora() throws Exception {
		//Given
		Long cdCorretoraGiven = 6L;
		Corretora corretoraGiven = new Corretora();
		corretoraGiven.setCdCorretora(cdCorretoraGiven);
		
		// Mockando Service que busca no banco de dados
		given(service.doBloqueioCorretora(corretoraGiven)).willReturn(true);
		
		BDDMockito.verify(service).doBloqueioCorretora(corretoraGiven);
	}
	
	@Test
	public void testOkdoBloqueioCorretoraCNPJ() throws Exception {
		//Given
		String CNPJCorretoraGiven = "64154543000146"; //21	Teste Corretora HML
		
		// Mockando Service que busca no banco de dados
		given(service.doBloqueioCorretora(CNPJCorretoraGiven)).willReturn(true);
		
		BDDMockito.verify(service).doBloqueioForcaVenda(CNPJCorretoraGiven);
	}

}

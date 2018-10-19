package br.com.odontoprev.portal.corretor.test.controller.bloqueio;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.dto.Login;
import br.com.odontoprev.portal.corretor.dto.LoginResponse;
import br.com.odontoprev.portal.corretor.service.BloqueioService;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;
import br.com.odontoprev.portal.corretor.service.LoginService;
import br.com.odontoprev.portal.corretor.service.impl.LoginServiceImpl;
import br.com.odontoprev.portal.corretor.util.Constantes;

//201809181600 - esert - COR-731 : TDD - Novo serviço (processar bloqueio)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { 
		BloqueioControllerTestConfig.class
//		,LoginServiceImpl.class
//		,LoginDAO.class 
		})

@WebAppConfiguration
public class BloqueioControllerTest {

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private LoginService loginServiceMock;
	
//	@Autowired
//	private BloqueioService bloqueioServiceMock;
//
//	@Autowired
//	private ForcaVendaService forcaVendaServiceMock;
	
	@Before
	public void setUpClass() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
//		Mockito.reset(loginServiceMock);
//		Mockito.reset(bloqueioServiceMock);
//		Mockito.reset(forcaVendaServiceMock);
	}

//	@Test
//	public void testOkdoBloqueioForcaVenda() throws Exception {
//		//Given
//		Long cdForcaVendaGiven = 6L;
//		ForcaVenda forcaVendaGiven = new ForcaVenda();
//		forcaVendaGiven.setCdForcaVenda(cdForcaVendaGiven);
//
//		// Mockando Service que busca no banco de dados
//		given(bloqueioServiceMock.doBloqueioForcaVenda(forcaVendaGiven)).willReturn(true);
//	}

	//201809201446 - esert - COR-731 : TDD - Novo serviço (processar bloqueio) (esert) //DEBITO TECNICO TESTE DE DAO
	@Test
	public void testOkdoBloqueioForcaVendaCPF() throws Exception {
		//Given
		String cpfForcaVendaGiven = "38330982874"; //6	FERNANDO SETAI
		String senhaForcaVendaGiven = "12345678"; //6	FERNANDO SETAI
		ForcaVenda forcaVendaGiven = new ForcaVenda();
		forcaVendaGiven.setCdForcaVenda(6L);
		forcaVendaGiven.setCpf(cpfForcaVendaGiven);

		Login loginGiven = new Login();
		loginGiven.setUsuario(cpfForcaVendaGiven);
		loginGiven.setSenha(senhaForcaVendaGiven);
		String jsonLoginRequest = new Gson().toJson(loginGiven);
		
		LoginResponse loginResponseGinven = new LoginResponse();
		loginResponseGinven.setCodigoUsuario(12345);
		loginResponseGinven.setTemBloqueio(true);
		loginResponseGinven.setCodigoTipoBloqueio(Constantes.TIPO_BLOQUEIO_CORRETAGEM_INTERMEDIACAO);
		loginResponseGinven.setDescricaoTipoBloqueio("TIPO_BLOQUEIO_CORRETAGEM_INTERMEDIACAO");
		
		// Mockando Service que login
		given(loginServiceMock.login(loginGiven)).willReturn(loginResponseGinven);
		
//		// Mockando Service que busca forca venda
//		given(forcaVendaServiceMock.findForcaVendaByCpf(cpfForcaVendaGiven)).willReturn(forcaVendaGiven);
//
//		// Mockando Service que processa bloqueio
//		given(bloqueioServiceMock.doBloqueioForcaVenda(cpfForcaVendaGiven)).willReturn(true);
		
		// Efetua a requisição na rota e espera um status code
		mvc.perform(post("/login")
			.content(jsonLoginRequest)
			.contentType(APPLICATION_JSON))
			.andExpect(status().isOk())	
			.andExpect(MockMvcResultMatchers.jsonPath("$.temBloqueio").value(loginResponseGinven.getTemBloqueio()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.codigoTipoBloqueio").value(loginResponseGinven.getCodigoTipoBloqueio()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricaoTipoBloqueio").value(loginResponseGinven.getDescricaoTipoBloqueio()))
			;
	}
	
//	@Test
//	public void testOkdoBloqueioCorretora() throws Exception {
//		//Given
//		Long cdCorretoraGiven = 6L;
//		Corretora corretoraGiven = new Corretora();
//		corretoraGiven.setCdCorretora(cdCorretoraGiven);
//		
//		// Mockando Service que busca no banco de dados
//		given(bloqueioServiceMock.doBloqueioCorretora(corretoraGiven)).willReturn(true);		
//	}
	
	//201809201446 - esert - COR-731 : TDD - Novo serviço (processar bloqueio) (esert) //DEBITO TECNICO TESTE DE DAO
	@Test
	public void testOkdoBloqueioCorretoraCNPJ() throws Exception {
		//Given
		String cnpjCorretoraGiven = "64154543000146"; //cdCorretora 121 Teste Corretora HML
		String senhaCorretoraGiven = "10203040"; //cdCorretora 121 Teste Corretora HML
		
		Corretora corretoraGiven = new Corretora();
		corretoraGiven.setCdCorretora(121L);
		corretoraGiven.setCnpj(cnpjCorretoraGiven);

		Login loginGiven = new Login();
		loginGiven.setUsuario(cnpjCorretoraGiven);
		loginGiven.setSenha(senhaCorretoraGiven);
		String jsonLoginRequest = new Gson().toJson(loginGiven);
		
		LoginResponse loginResponseGinven = new LoginResponse();
		loginResponseGinven.setCodigoUsuario(12345);
		loginResponseGinven.setTemBloqueio(true);
		loginResponseGinven.setCodigoTipoBloqueio(Constantes.TIPO_BLOQUEIO_CORRETAGEM_INTERMEDIACAO);
		loginResponseGinven.setDescricaoTipoBloqueio("TIPO_BLOQUEIO_CORRETAGEM_INTERMEDIACAO");
		
		// Mockando Service que login
		given(loginServiceMock.login(loginGiven)).willReturn(loginResponseGinven);
		
		// Efetua a requisição na rota e espera um status code
		mvc.perform(post("/login")
			.content(jsonLoginRequest)
			.contentType(APPLICATION_JSON))
			.andExpect(status().isOk())	
			.andExpect(MockMvcResultMatchers.jsonPath("$.temBloqueio").value(loginResponseGinven.getTemBloqueio()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.codigoTipoBloqueio").value(loginResponseGinven.getCodigoTipoBloqueio()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricaoTipoBloqueio").value(loginResponseGinven.getDescricaoTipoBloqueio()))
			;
	}

}

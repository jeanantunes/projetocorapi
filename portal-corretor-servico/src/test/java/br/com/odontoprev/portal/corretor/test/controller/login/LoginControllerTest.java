package br.com.odontoprev.portal.corretor.test.controller.login;

import br.com.odontoprev.portal.corretor.dto.Login;
import br.com.odontoprev.portal.corretor.service.LoginService;
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


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {LoginControllerTestConfig.class})
@WebAppConfiguration
public class LoginControllerTest {


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
    private LoginService service;

    @Test
    public void testLoginOk200() throws Exception {
        //Montando Request
        Login login = new Login();
        login.setCdLogin(21L);
        login.setCdTipoLogin(1L);
        login.setFotoPerfilB64("fotoemBase64");
        login.setUsuario("64154543000146");
        login.setSenha("10203040");

		/*
		//Mockando Service que busca no banco de dados
		given(service.buscarPorTokenLogin(deviceToken.getToken(), codigoForcaVenda)).willReturn(Arrays.asList(deviceToken));

		//Efetua a requisição na rota e espera um status code
		mvc.perform(post("/devicetoken/forcavenda/"+codigoForcaVenda).content(new Gson().toJson(deviceToken))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk());

		//Verifica se os metódos da lógica interna foram chamados
		BDDMockito.verify(service).buscarPorTokenLogin(deviceToken.getToken(), codigoForcaVenda);
		BDDMockito.verify(service).atualizar(deviceToken, codigoForcaVenda);
		*/

    }

	/*
	@Test
	public void testInclusaoDeviceToken() throws Exception {
		//Montando Request
		DeviceToken deviceToken = new DeviceToken();
		deviceToken.setToken("TOKEN");
		deviceToken.setModelo("MODELO");
		deviceToken.setSistemaOperacional("SO");
		deviceToken.setDataInclusao(null);
		deviceToken.setCodigo(2L);
		Long codigoForcaVenda = 1L;

		//Mockando Service que busca no banco de dados
		given(service.buscarPorTokenLogin(deviceToken.getToken(), codigoForcaVenda)).willReturn(Collections.emptyList());

		//Efetua a requisição na rota e espera um status code
		mvc.perform(post("/devicetoken/forcavenda/"+codigoForcaVenda).content(new Gson().toJson(deviceToken))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk());

		//Verifica se os metódos da lógica interna foram chamados
		BDDMockito.verify(service).buscarPorTokenLogin(deviceToken.getToken(), codigoForcaVenda);
		BDDMockito.verify(service).inserir(deviceToken, codigoForcaVenda);
	}

	@Test
	public void testBadRequest() throws Exception {
		Long codigoForcaVenda = 0L;
		//Efetua a requisição na rota e espera um status code
		mvc.perform(post("/devicetoken/forcavenda/"+codigoForcaVenda)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest());


	}

	@Test
	public void testBadRequestJsonNaoPassado() throws Exception {
		Long codigoForcaVenda = 0L;
		//Efetua a requisição na rota e espera um status code
		mvc.perform(post("/devicetoken/forcavenda/"+codigoForcaVenda)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testBadRequestCamposObrigatoriosNaoPassado() throws Exception {
		//Montando Request
		DeviceToken deviceToken = new DeviceToken();
		deviceToken.setToken("TOKEN");
		deviceToken.setSistemaOperacional("SO");
		deviceToken.setDataInclusao(null);
		deviceToken.setCodigo(2L);
		Long codigoForcaVenda = 1L;


		//Efetua a requisição na rota e espera um status code
		mvc.perform(post("/devicetoken/forcavenda/"+codigoForcaVenda).content(new Gson().toJson(deviceToken))
				.contentType(APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.mensagem").value("modelo nao informado"))
				.andExpect(status().isBadRequest());
	}

	//201808091539 - esert - COR-557 - nova rota DELETE/devicetoken/forcavenda/{id}
	//201808211630 - esert - COR-650 - alterado DELETE/devicetoken/forcavenda/{id}(+body) por DELETE/devicetoken/{codToken}/forcavenda/{codForcaVenda}
	@Test
	public void testOk200ExcluirDeviceToken() throws Exception {
		//Montando Request
		Long codigoForcaVendaRequest = 1L;
		String codigoTokenRequest = "TOKEN";

		//Montando Given
		Long codigoForcaVendaGiven = 1L;
		DeviceToken deviceTokenGiven = new DeviceToken();
		deviceTokenGiven.setCodigo(1L);
		deviceTokenGiven.setToken("TOKEN");
		List<DeviceToken> listDeviceTokenGiven = new ArrayList<DeviceToken>();
		listDeviceTokenGiven.add(deviceTokenGiven);

		//Mockando Service que busca no banco de dados
		given(service.buscarPorTokenLogin(deviceTokenGiven.getToken(), codigoForcaVendaGiven)).willReturn(listDeviceTokenGiven);

		//Efetua a requisição na rota e espera um status code
		mvc.perform(delete("/devicetoken/forcavenda/"+codigoForcaVendaRequest + "?token=" + codigoTokenRequest)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk());

		//Verifica se os metódos da lógica interna foram chamados
		BDDMockito.verify(service).buscarPorTokenLogin(deviceTokenGiven.getToken(), codigoForcaVendaGiven);
		BDDMockito.verify(service).excluir(deviceTokenGiven.getCodigo());
	}

	//201808091539 - esert - COR-557 - nova rota DELETE/devicetoken/forcavenda/{id}
	//201808211630 - esert - COR-650 - alterado DELETE/devicetoken/forcavenda/{id}(+body) por DELETE/devicetoken/{codToken}/forcavenda/{codForcaVenda}
	@Test
	public void testBadRequest400ExcluirDeviceTokenSemToken() throws Exception {
		//Montando Request
		String codigoTokenRequest = " ";
		Long codigoForcaVendaRequest = 1L;

		//Montando Request Given
		Long codigoForcaVendaGiven = 1L;
		DeviceToken deviceTokenGiven = new DeviceToken();
		deviceTokenGiven.setCodigo(1L);
		deviceTokenGiven.setToken("OUTROTOKEN");
		List<DeviceToken> listDeviceTokenGiven = new ArrayList<DeviceToken>();
		listDeviceTokenGiven.add(deviceTokenGiven);
		//Mockando Service que busca no banco de dados
		given(service.buscarPorTokenLogin(deviceTokenGiven.getToken(), codigoForcaVendaGiven)).willReturn(listDeviceTokenGiven);

		//Efetua a requisição na rota e espera um status code
		mvc.perform(delete("/devicetoken/forcavenda/"+codigoForcaVendaRequest + "?token=" + codigoTokenRequest)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest());

		//nao ha chamadas metodos neste caso
		//Verifica se os metódos da lógica interna foram chamados
		//BDDMockito.verify(service).buscarPorTokenLogin(deviceTokenRequest.getToken(), codigoForcaVendaRequest);
		//BDDMockito.verify(service).excluir(deviceTokenRequest.getCodigo());
	}

	//201808091700 - esert - COR-557 - nova rota DELETE/devicetoken/forcavenda/{id}
	//201808211630 - esert - COR-650 - alterado DELETE/devicetoken/forcavenda/{id}(+body) por DELETE/devicetoken/{codToken}/forcavenda/{codForcaVenda}
	@Test
	public void testNoContent204ExcluirDeviceTokenTokenDiferente() throws Exception {

		//Montando Request Given
		Long codigoForcaVendaGiven = 1L;
		DeviceToken deviceTokenGiven = new DeviceToken();
		deviceTokenGiven.setCodigo(2L);
		deviceTokenGiven.setToken("OUTROTOKEN");
		List<DeviceToken> listDeviceTokenGiven = new ArrayList<DeviceToken>();
		listDeviceTokenGiven.add(deviceTokenGiven);
		//Mockando Service que busca no banco de dados
		given(service.buscarPorTokenLogin(deviceTokenGiven.getToken(), codigoForcaVendaGiven)).willReturn(listDeviceTokenGiven);

		//Montando Request
		String codigoTokenRequest="naoExiste";//propositalmente sem token
		Long codigoForcaVendaRequest = 1L;

		//Efetua a requisição na rota e espera um status code
		mvc.perform(delete("/devicetoken/forcavenda/"+codigoForcaVendaRequest + "?token=" + codigoTokenRequest)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isNoContent());

		//Verifica se os metódos da lógica interna foram chamados
		BDDMockito.verify(service).buscarPorTokenLogin(codigoTokenRequest, codigoForcaVendaRequest);
		//BDDMockito.verify(service).excluir(deviceTokenRequest.getCodigo());
	}
	*/
}

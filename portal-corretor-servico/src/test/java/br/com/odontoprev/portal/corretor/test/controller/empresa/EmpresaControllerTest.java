package br.com.odontoprev.portal.corretor.test.controller.empresa;

import br.com.odontoprev.portal.corretor.dto.*;
import br.com.odontoprev.portal.corretor.service.EmpresaService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { EmpresaControllerTestConfig.class })
@WebAppConfiguration
public class EmpresaControllerTest {
	
	   
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
	   private EmpresaService service;


	  	   
	   	@Test
		public void testOk200() throws Exception {
	       Long codigoEmpresa = 1683L;
		   	    
		   //Mockando Service que busca no banco de dados 
		   given(service.findByCdEmpresa(1683L)).willReturn(new Empresa());	       

		   //Efetua a requisição na rota e espera um status code
	       mvc.perform(get("/empresa/"+codigoEmpresa)	               
	               .contentType(APPLICATION_JSON))
	               .andExpect(status().isOk())
	               ;
	   	}
	   
	   @Test
	   public void testNoContent204() throws Exception {  
	       Long codigoEmpresa = 0L;
	   	    
		   //Mockando Service que busca no banco de dados 
		   given(service.findByCdEmpresa(1683L)).willReturn(new Empresa());
		   
	       //Efetua a requisição na rota e espera um status code
	       mvc.perform(get("/empresa/"+codigoEmpresa) //chama com id zer0	               
	               .contentType(APPLICATION_JSON))
	               .andExpect(status().isNoContent()) //e nao deve retornar resultado
	               ;	       	       	               	       
	   }

	@Test
	public void testPutEmpresaBadRequest400() throws Exception {

	   	Empresa empresa = new Empresa();

		String json = new Gson().toJson(empresa);

		//Efetua a requisição na rota e espera um status code
		mvc.perform(put("/empresa") // chamando put empresa sem enviar cdEmpresa
				.content(json)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest()) //e nao deve retornar 400
		;
	}

	@Test
	public void testPutEmpresaNoContent204() throws Exception {

		Empresa empresa = new Empresa();
		empresa.setCdEmpresa(000L);
		empresa.setEmail("teste@nocontent.com");

		String json = new Gson().toJson(empresa);

		given(service.updateEmpresa(empresa)).willReturn(null);


		//Efetua a requisição na rota e espera um status code
		mvc.perform(put("/empresa") // chamando put empresa sem enviar nada
				.content(json)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isNoContent()) //e nao deve retornar 204
		;
	}

	@Test
	public void testPutEmpresaOk200() throws Exception {

	   	// Request put empresa
		Empresa empresa = new Empresa();
		empresa.setCdEmpresa(2548L);
		empresa.setEmail("teste@nocontent.com");
		String json = new Gson().toJson(empresa);

		// Response put empresa
		long statusReponse = HttpStatus.OK.value();
		String mensagemRetorno = String.format("Empresa: [%d], atualizada.", statusReponse);
		EmpresaResponse empresaResponse = new EmpresaResponse(statusReponse, mensagemRetorno);
		given(service.updateEmpresa(empresa)).willReturn(empresaResponse);

		//Efetua a requisição na rota e espera um status code
		mvc.perform(put("/empresa") // chamando put empresa enviando email e cdEmpresa
				.content(json)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk()) //e deve retornar 200
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(statusReponse))
				.andExpect(MockMvcResultMatchers.jsonPath("$.mensagem").value(mensagemRetorno))
		;
	}

	@Test
	public void testOk200EmpresaArquivo() throws Exception {

		EmpresaArquivo empresaArquivo = new EmpresaArquivo();
		empresaArquivo.setListCdEmpresa(new ArrayList<Long>());
		empresaArquivo.getListCdEmpresa().add(2414L);
		String json = new Gson().toJson(empresaArquivo);
		
		EmpresaArquivoResponse empresaArquivoResponse = new EmpresaArquivoResponse();
		empresaArquivoResponse.setEmpresas(new ArrayList<EmpresaArquivoResponseItem>());
		empresaArquivoResponse.getEmpresas().add(new EmpresaArquivoResponseItem(2414L, "1234"));
		//Mockando Service que busca no banco de dados
		given(service.gerarArquivoEmpresa(empresaArquivo)).willReturn(empresaArquivoResponse);

		//Efetua a requisição na rota e espera um status code
		mvc.perform(post("/empresa/arquivo")
				.content(json)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
		;

	}

	@Test
	public void testOk200EmpresaArquivoComEmpresaNaoEncontrada() throws Exception {

	   	Long empresaPesquisada = 5448448L;
	   	String mensagemEsperada = "Empresa nao encontrada.";
	   	
		EmpresaArquivo cdEmpresa = new EmpresaArquivo();
		cdEmpresa.setListCdEmpresa(new ArrayList<Long>());
		cdEmpresa.getListCdEmpresa().add(empresaPesquisada);
		String json = new Gson().toJson(cdEmpresa);
		//Mockando Service que busca no banco de dados

		EmpresaArquivoResponse empresaArquivoResponse = new EmpresaArquivoResponse();
		EmpresaArquivoResponseItem empresaArquivoResponseItem = new EmpresaArquivoResponseItem(empresaPesquisada, "Empresa nao encontrada.");
		empresaArquivoResponse.setEmpresas(new ArrayList<EmpresaArquivoResponseItem>());
		empresaArquivoResponse.getEmpresas().add(empresaArquivoResponseItem);

		given(service.gerarArquivoEmpresa(cdEmpresa)).willReturn(empresaArquivoResponse);


		//Efetua a requisição na rota e espera um status code
		mvc.perform(post("/empresa/arquivo")
				.content(json)
				.contentType(APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.empresas[0].mensagem").value(mensagemEsperada))
				.andExpect(status().isOk())
		;

	}

	@Test
	public void testBadResquest400EmpresaArquivo() throws Exception {

		EmpresaArquivo cdEmpresa = new EmpresaArquivo();
		cdEmpresa.setListCdEmpresa(new ArrayList<Long>());
		String json = new Gson().toJson(cdEmpresa);
		//Mockando Service que busca no banco de dados
		given(service.gerarArquivoEmpresa(cdEmpresa)).willReturn(new EmpresaArquivoResponse());

		//Efetua a requisição na rota e espera um status code
		mvc.perform(post("/empresa/arquivo")
				.content(json)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
		;

	}

    //201809251930 - esert - COR-820 Criar POST /empresa-emailaceite
	@Test
	public void testOk200PostEmpresaEmailAceite() throws Exception {
	
		Long cdEmpresaGiven = 2566L;
		Long cdVendaGiven = 4346L;
		EmpresaEmailAceite empresaEmailAceite = new EmpresaEmailAceite();
		empresaEmailAceite.setCdEmpresa(cdEmpresaGiven);
		empresaEmailAceite.setCdVenda(cdVendaGiven);
		String jsonRequest = new Gson().toJson(empresaEmailAceite);
		
		EmpresaResponse empresaResponse = new EmpresaResponse(HttpStatus.OK.value(), HttpStatus.OK.name());
		//Mockando Service que busca no banco de dados
		given(service.enviarEmpresaEmailAceite(empresaEmailAceite)).willReturn(empresaResponse);
	
		//Efetua a requisição na rota e espera um status code
		mvc.perform(post("/empresa-emailaceite")
				.content(jsonRequest)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
		;
	
	}
	
	//201809251945 - esert - COR-820 Criar POST /empresa-emailaceite
	@Test
	public void testBadRequest400PostEmpresaEmailAceite() throws Exception {
		
		Long cdEmpresaGiven = null;
		Long cdVendaGiven = null;
		EmpresaEmailAceite empresaEmailAceite = new EmpresaEmailAceite();
		empresaEmailAceite.setCdEmpresa(cdEmpresaGiven);
		empresaEmailAceite.setCdVenda(cdVendaGiven);
		String jsonRequest = new Gson().toJson(empresaEmailAceite);
		
		EmpresaResponse empresaResponse = new EmpresaResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
		
		//Mockando Service que busca no banco de dados
		given(service.enviarEmpresaEmailAceite(empresaEmailAceite)).willReturn(empresaResponse);
		
		//Efetua a requisição na rota e espera um status code
		mvc.perform(post("/empresa-emailaceite")
				.content(jsonRequest)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
		;
		
	}
	
	//201809251956 - esert - COR-820 Criar POST /empresa-emailaceite
	@Test
	public void testNoContent204PostEmpresaEmailAceite() throws Exception {
		
		Long cdEmpresaGiven = 2566L;
		Long cdVendaGiven = 4346L;
		EmpresaEmailAceite empresaEmailAceiteGiven = new EmpresaEmailAceite();
		empresaEmailAceiteGiven.setCdEmpresa(cdEmpresaGiven);
		empresaEmailAceiteGiven.setCdVenda(cdVendaGiven);

		EmpresaResponse empresaResponse = new EmpresaResponse(HttpStatus.OK.value(), HttpStatus.OK.name());

		Long cdEmpresaReq = 2566L;
		Long cdVendaReq = 2566L;
		EmpresaEmailAceite empresaEmailAceiteReq = new EmpresaEmailAceite();
		empresaEmailAceiteReq.setCdEmpresa(cdEmpresaReq);
		empresaEmailAceiteReq.setCdVenda(cdVendaReq);
		String jsonRequest = new Gson().toJson(empresaEmailAceiteReq);
		
		//Mockando Service que busca no banco de dados
		given(service.enviarEmpresaEmailAceite(empresaEmailAceiteGiven)).willReturn(empresaResponse);
		
		//Efetua a requisição na rota e espera um status code
		mvc.perform(post("/empresa-emailaceite")
				.content(jsonRequest)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isNoContent())
		;
		
	}
}

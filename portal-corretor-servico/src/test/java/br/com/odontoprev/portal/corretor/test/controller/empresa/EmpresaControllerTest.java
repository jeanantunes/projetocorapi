package br.com.odontoprev.portal.corretor.test.controller.empresa;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

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

import com.google.gson.Gson;

import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.dto.EmpresaArquivo;
import br.com.odontoprev.portal.corretor.dto.EmpresaArquivoResponse;
import br.com.odontoprev.portal.corretor.dto.EmpresaArquivoResponseItem;
import br.com.odontoprev.portal.corretor.dto.EmpresaEmailAceite;
import br.com.odontoprev.portal.corretor.dto.EmpresaResponse;
import br.com.odontoprev.portal.corretor.service.EmpresaService;


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

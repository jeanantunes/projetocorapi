package br.com.odontoprev.portal.corretor.test.controller.empresa;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.dto.EmpresaArquivo;
import br.com.odontoprev.portal.corretor.dto.EmpresaArquivoResponse;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.service.EmpresaService;

import java.util.ArrayList;
import java.util.List;


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
	public void testEmpresaArquivoOk200() throws Exception {

		EmpresaArquivo cdEmpresa = new EmpresaArquivo();
		cdEmpresa.setCdEmpresa(new ArrayList<Long>());
		cdEmpresa.getCdEmpresa().add(2414L);
		String json = new Gson().toJson(cdEmpresa);
		//Mockando Service que busca no banco de dados
		given(service.gerarArquivoEmpresa(cdEmpresa)).willReturn(new EmpresaArquivoResponse());

		//Efetua a requisição na rota e espera um status code
		mvc.perform(post("/empresa/arquivo")
				.content(new Gson().toJson(cdEmpresa))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isNoContent())
		;

	}

	@Test
	public void testEmpresaArquivoNoContent204() throws Exception {

		EmpresaArquivo cdEmpresa = new EmpresaArquivo();
		cdEmpresa.setCdEmpresa(new ArrayList<Long>());
		String json = new Gson().toJson(cdEmpresa);
		//Mockando Service que busca no banco de dados
		given(service.gerarArquivoEmpresa(cdEmpresa)).willReturn(new EmpresaArquivoResponse());

		//Efetua a requisição na rota e espera um status code
		mvc.perform(post("/empresa/arquivo")
				.content(new Gson().toJson(cdEmpresa))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isNoContent())
		;

	}
}

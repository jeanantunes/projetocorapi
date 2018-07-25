package br.com.odontoprev.portal.corretor.test.controller.empresa;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
	       //Efetua a requisição na rota e espera um status code
	       mvc.perform(get("/emnpresa/"+codigoEmpresa)	               
	               .contentType(APPLICATION_JSON))
	               .andExpect(status().isOk())
	               ;
	   }
	   
	   @Test
	   public void testBadRequestCodigoZer0() throws Exception {  
	       Long codigoEmpresa = 0L;
	       //Efetua a requisição na rota e espera um status code
	       mvc.perform(get("/empresa/"+codigoEmpresa)	               
	               .contentType(APPLICATION_JSON))
	               .andExpect(status().isNotFound())
	               ;	       	       	               	       
	   }
	   
	   @Test
	   public void testCamposComValor() throws Exception {  
	       Long codigoEmpresa = 1683L;
	       //Efetua a requisição na rota e espera um status code
	       mvc.perform(get("/empresa/"+codigoEmpresa)	               
	    		   .contentType(APPLICATION_JSON))
	       			.andExpect(status().isOk())
	       			.andExpect(MockMvcResultMatchers.jsonPath("$.cnpj").value("12.029.694/0001-79")) //"cnpj": "12.029.694/0001-79",
	       			.andExpect(MockMvcResultMatchers.jsonPath("$.razaoSocial").value("KAIUS MAGNO DE AMORIM OLIVEIRA 13469116733")) //"razaoSocial": "KAIUS MAGNO DE AMORIM OLIVEIRA 13469116733"
	       			.andExpect(MockMvcResultMatchers.jsonPath("$.nomeFantasia").value("KAIUS MAGNO DE AMORIM OLIVEIRA")) //"nomeFantasia": "KAIUS MAGNO DE AMORIM OLIVEIRA",
	       			;	       	       	               	       
	   }
	   

}

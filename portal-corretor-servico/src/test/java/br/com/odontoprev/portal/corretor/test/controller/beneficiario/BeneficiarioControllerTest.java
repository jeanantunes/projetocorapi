package br.com.odontoprev.portal.corretor.test.controller.beneficiario;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.odontoprev.portal.corretor.service.BeneficiarioService;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { BeneficiarioControllerTestConfig.class })
@WebAppConfiguration
public class BeneficiarioControllerTest {
	
	   
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
	   private BeneficiarioService service;
	  	   
	   @Test
	   public void testOk200() throws Exception {  
	       Long cdEmpresa = 1659L;
	       Long tampag = 3L;
	       Long numpag = 3L;
		   	    
		   //Mockando Service que busca no banco de dados 
		   //given(service.getPage(1659L, 3L, 3L)).willReturn(new Beneficiarios());	       

		   //Efetua a requisição na rota e espera um status code
	       mvc.perform(get("/beneficiarios/empresa/" + cdEmpresa + "?tampag=" + tampag + "&numpag=" + numpag)	               
	               .contentType(APPLICATION_JSON))
	               .andExpect(status().isOk())
	               ;
	   }
	   
	   @Test
	   public void testNoContent204() throws Exception {  
	       Long cdEmpresa = 1659L;
	       Long tampag = 0L;
	       Long numpag = 0L;
		   	    
		   //Mockando Service que busca no banco de dados 
		   //given(service.getPage(1659L, 3L, 3L)).willReturn(new Beneficiarios());	       

		   //Efetua a requisição na rota e espera um status code
	       mvc.perform(get("/beneficiarios/empresa/" + cdEmpresa + "?tampag=" + tampag + "&numpag=" + numpag)	               
	               .contentType(APPLICATION_JSON))
	               .andExpect(status().isNoContent()) //e nao deve retornar resultado
	               ;	       	       	               	       
	   }

}

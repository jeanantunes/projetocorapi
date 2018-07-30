package br.com.odontoprev.portal.corretor.test.controller.beneficiario;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

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

import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioPaginacao;
import br.com.odontoprev.portal.corretor.dto.Beneficiarios;
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
	   public void testOk200Tampag3Numpag3() throws Exception {  
		   Long cdEmpresa = 1659L;
		   Long tampag = 3L;
		   Long numpag = 3L;
//		   Long qtdpag = 3L;
//		   Long qtdreg = 8L;
		   
		   Beneficiarios beneficiarios = new Beneficiarios();
		   
		   //Mockando Service que busca no banco de dados 
		   given(service.getPage(1659L, 3L, 3L)).willReturn(beneficiarios);	       
		   
		   //Efetua a requisição na rota e espera um status code
		   mvc.perform(get("/beneficiarios/empresa/" + cdEmpresa + "?tampag=" + tampag + "&numpag=" + numpag)	               
				   .contentType(APPLICATION_JSON))
		   .andExpect(status().isOk())
		   ;
	   }
	  	   
	   @Test
	   public void testOk200Tampag3Numpag2() throws Exception {  
	       Long cdEmpresa = 1659L;
	       Long tampag = 3L;
	       Long numpag = 3L;
	       Long qtdpag = 3L;
	       Long qtdreg = 8L;
	       Long qtddepsporreg = 3L;

	       Beneficiarios benefs = new Beneficiarios();
	       benefs.setCodEmpresa(cdEmpresa);
	       benefs.setTamPagina(tampag);
	       benefs.setNumPagina(numpag);
	       benefs.setQtdPaginas(qtdpag);
	       benefs.setQtdRegistros(qtdreg);
	       benefs.setTitulares(new ArrayList<BeneficiarioPaginacao>());
	       for(int i=7; i<=qtdreg; i++) {
	    	   BeneficiarioPaginacao bptit = new BeneficiarioPaginacao();
	    	   bptit.setCdVida((long)i);
	    	   bptit.setNome("beneficiario titular [" + i + "]");
	    	   bptit.setDependentes(new ArrayList<Beneficiario>());
	    	   for(int j=1; j<=qtddepsporreg; j++) {
	    		   Beneficiario bdep = new Beneficiario();
	    		   bdep.setCdVida((long)i*10 + j);
	    		   bdep.setCdTitular((long)i);
	    		   bdep.setNome("beneficiario dependente [" + i + "-" + j + "]");
	    		   bptit.getDependentes().add(bdep);
	    	   }
	    	   benefs.getTitulares().add(bptit);
	       }
	       
		   //Mockando Service que busca no banco de dados 
		   given(service.getPage(cdEmpresa, tampag, numpag)).willReturn(benefs);	       

		   //Efetua a requisição na rota e espera um status code
	       mvc.perform(get("/beneficiarios/empresa/" + cdEmpresa + "?tampag=" + tampag + "&numpag=" + numpag)	               
	               .contentType(APPLICATION_JSON))
	               .andExpect(status().isOk())
	               .andExpect(MockMvcResultMatchers.jsonPath("$.qtdPaginas").value(qtdpag))
	               .andExpect(MockMvcResultMatchers.jsonPath("$.qtdRegistros").value(qtdreg))
	               .andExpect(MockMvcResultMatchers.jsonPath("$.titulares[1].nome").value("beneficiario titular [8]"))
	               ;
	   }
	   
	   @Test
	   public void testNoContent204Tampag0Numpag0() throws Exception {  
	       Long cdEmpresa = 1659L;
	       Long tampag = 0L;
	       Long numpag = 0L;
		   	    
		   //Mockando Service que busca no banco de dados 
		   given(service.getPage(1659L, 3L, 3L)).willReturn(new Beneficiarios());	       

		   //Efetua a requisição na rota e espera um status code
	       mvc.perform(get("/beneficiarios/empresa/" + cdEmpresa + "?tampag=" + tampag + "&numpag=" + numpag)	               
	               .contentType(APPLICATION_JSON))
	               .andExpect(status().isNoContent()) //e nao deve retornar resultado
	               ;	       	       	               	       
	   }

}
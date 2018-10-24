package br.com.odontoprev.portal.corretor.test.controller.arquivo;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.odontoprev.portal.corretor.dto.Arquivo;
import br.com.odontoprev.portal.corretor.service.ArquivoService;

//201810231900 - esert - COR-723:API - Novo GET/ARQUIVO/(ID)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ArquivoControllerTestConfig.class })
@WebAppConfiguration
public class ArquivoControllerTest {
	
	   
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
	   private ArquivoService service;
	  
	   @Test
	   public void testOk200GetArquivo() throws Exception {
	       //Montando Request
	       Long cdArquivoRequest = 2510L;

	       //Montando Given
	       Long cdArquivoGiven = 2510L;
		   Arquivo dtoGiven = new Arquivo();
		   dtoGiven.setCdArquivo(cdArquivoGiven);	       
		   dtoGiven.setDataCriacao(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));	       
	       dtoGiven.setNomeArquivo("arduino-uno-schematic.pdf");	       
	       dtoGiven.setTamanho(33516L);	       
	       dtoGiven.setTipoConteudo(MediaType.APPLICATION_PDF_VALUE.toString());	       
	       dtoGiven.setArquivoBase64("qwertyuiopasdfghjklzxcvbnm");	       
	       
	       //Mockando Service que busca no banco de dados 
	       given(service.getByCdArquivo(cdArquivoGiven)).willReturn(dtoGiven);	       
	       
	       //Efetua a requisição na rota e espera um status code
		   mvc.perform(get("/arquivo/" + cdArquivoRequest + "/json")
				   .contentType(APPLICATION_JSON))
	               .andExpect(status().isOk());
	       
	       //Verifica se os metódos da lógica interna foram chamados
	       BDDMockito.verify(service).getByCdArquivo(cdArquivoGiven);
	   }

	   @Test
	   public void testNoContent204GetArquivo() throws Exception {
	       //Montando Request
	       Long cdArquivoRequest = 9999L;

	       //Montando Given
	       Long cdArquivoGiven = 2510L;
		   Arquivo dtoGiven = new Arquivo();
		   dtoGiven.setCdArquivo(cdArquivoGiven);	       
		   dtoGiven.setDataCriacao(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));	       
	       dtoGiven.setNomeArquivo("arduino-uno-schematic.pdf");	       
	       dtoGiven.setTamanho(33516L);	       
	       dtoGiven.setTipoConteudo(MediaType.APPLICATION_PDF_VALUE.toString());	       
	       dtoGiven.setArquivoBase64("qwertyuiopasdfghjklzxcvbnm");	       
	       
	       //Mockando Service que busca no banco de dados 
	       given(service.getByCdArquivo(cdArquivoGiven)).willReturn(dtoGiven);	       
	       
	       //Efetua a requisição na rota e espera um status code
		   mvc.perform(get("/arquivo/" + cdArquivoRequest + "/json")
				   .contentType(APPLICATION_JSON))
	               .andExpect(status().isNoContent());
	   }
	   
	   @Test
	   public void testOk200GetArquivoByteArray() throws Exception {
	       //Montando Request
	       Long cdArquivoRequest = 2510L;
	
	       //Montando Given
	       Long cdArquivoGiven = 2510L;
		   Arquivo dtoGiven = new Arquivo();
		   dtoGiven.setCdArquivo(cdArquivoGiven);	       
		   dtoGiven.setDataCriacao(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));	       
	       dtoGiven.setNomeArquivo("arduino-uno-schematic.pdf");	       
	       dtoGiven.setTamanho(33516L);	       
	       dtoGiven.setTipoConteudo(MediaType.APPLICATION_PDF_VALUE.toString());	       
	       dtoGiven.setArquivoBase64("qwertyuiopasdfghjklzxcvbnm");	       
	       
	       //Mockando Service que busca no banco de dados 
	       given(service.getByCdArquivo(cdArquivoGiven)).willReturn(dtoGiven);	       
	       
	       //Efetua a requisição na rota e espera um status code
		   mvc.perform(get("/arquivo/" + cdArquivoRequest + "/arquivo")
				   .contentType(APPLICATION_JSON))
	               .andExpect(status().isOk());
	       
	       //Verifica se os metódos da lógica interna foram chamados
	       BDDMockito.verify(service).getByCdArquivo(cdArquivoGiven);
	   }
	   
	   @Test   
	   public void testNoContent204GetArquivoByteArray() throws Exception {
		       //Montando Request
		   Long cdArquivoRequest = 9999L;
		
		   //Montando Given
		   Long cdArquivoGiven = 2510L;
		   Arquivo dtoGiven = new Arquivo();
		   dtoGiven.setCdArquivo(cdArquivoGiven);	       
		   dtoGiven.setDataCriacao(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));	       
	       dtoGiven.setNomeArquivo("arduino-uno-schematic.pdf");	       
	       dtoGiven.setTamanho(33516L);	       
	       dtoGiven.setTipoConteudo(MediaType.APPLICATION_PDF_VALUE.toString());	       
	       dtoGiven.setArquivoBase64("qwertyuiopasdfghjklzxcvbnm");	       
		   
		   //Mockando Service que busca no banco de dados 
		   given(service.getByCdArquivo(cdArquivoGiven)).willReturn(dtoGiven);	       
		   
		   //Efetua a requisição na rota e espera um status code
		   mvc.perform(get("/arquivo/" + cdArquivoRequest + "/arquivo")
					   .contentType(APPLICATION_JSON))
		               .andExpect(status().isNoContent());
	   }
	   
}

package br.com.odontoprev.portal.corretor.test.controller.plano;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.odontoprev.portal.corretor.dto.Arquivo;
import br.com.odontoprev.portal.corretor.dto.PlanoInfo;
import br.com.odontoprev.portal.corretor.dto.PlanoInfos;
import br.com.odontoprev.portal.corretor.service.PlanoService;

//201810231601 - esert - COR-932:API - Novo GET /planoinfo
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { PlanoControllerTestConfig.class })
@WebAppConfiguration
public class PlanoControllerTest {
	
	   
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
	   private PlanoService service;
	  
	   @Test
	   public void testOk200getpPlanoInfos() throws Exception {
	       //Montando Request

	       //Montando Given
		   PlanoInfos dtoGiven = new PlanoInfos();
		   dtoGiven.setPlanoInfos(new ArrayList<PlanoInfo>());
		   dtoGiven.getPlanoInfos().add(new PlanoInfo());
		   dtoGiven.getPlanoInfos().get(0).setCodigoPlanoInfo(1L);
		   dtoGiven.getPlanoInfos().get(0).setNomePlanoInfo("Dente de Leite");
		   dtoGiven.getPlanoInfos().get(0).setDescricao("Dente de Leite lin 1\r\nDente de Leite lin 2\r\nDente de Leite lin 3\r\nDente de Leite lin 4\r\nDente de Leite lin 5\r\n.fim.\r\n.");
		   dtoGiven.getPlanoInfos().get(0).setCodigoArquivoCarencia(6L);
		   dtoGiven.getPlanoInfos().get(0).setCodigoArquivoGeral(7L);
		   dtoGiven.getPlanoInfos().get(0).setCodigoArquivoIcone(8L);
		   dtoGiven.getPlanoInfos().get(0).setTipoSegmento("PF");
		   dtoGiven.getPlanoInfos().get(0).setAtivo("S");
		   dtoGiven.getPlanoInfos().get(0).setArquivoIcone(new Arquivo());
		   dtoGiven.getPlanoInfos().get(0).getArquivoIcone().setCdArquivo(1000L);;
		   dtoGiven.getPlanoInfos().get(0).getArquivoIcone().setNomeArquivo("arduino-uno-schematic.pdf");
		   dtoGiven.getPlanoInfos().get(0).getArquivoIcone().setDataCriacao("2018-10-22 18:15:28");
		   dtoGiven.getPlanoInfos().get(0).getArquivoIcone().setTipoConteudo("application/pdf");
		   dtoGiven.getPlanoInfos().get(0).getArquivoIcone().setTamanho(33516L);
		   dtoGiven.getPlanoInfos().get(0).getArquivoIcone().setArquivoBase64("abcdefghijklmnopqrstuvwxyz");
	       
	       //Mockando Service que busca no banco de dados 
	       given(service.getpPlanoInfos()).willReturn(dtoGiven);	       
	       
	       //Efetua a requisição na rota e espera um status code
		   mvc.perform(get("/planoinfo")
				   .contentType(APPLICATION_JSON))
	               .andExpect(status().isOk());
	       
	       //Verifica se os metódos da lógica interna foram chamados
	       BDDMockito.verify(service).getpPlanoInfos();
	   }
	   
}

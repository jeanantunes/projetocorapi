package br.com.odontoprev.portal.corretor.test.controller.contratocorretora;

import static org.mockito.BDDMockito.given;
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

import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraDataAceite;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraPreenchido;
import br.com.odontoprev.portal.corretor.service.ContratoCorretoraService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ContratoCorretoraControllerTestConfig.class })
@WebAppConfiguration
public class ContratoCorretoraControllerTest {
	
	   
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
	private ContratoCorretoraService service;

	@Test
	public void testOk200getDataAceiteContratoByCdCorretora() throws Exception {

		   Long cdCorretora = 3L;
		   String dataDeAceite = "2011-09-20 00:00:00";

		   ContratoCorretoraDataAceite contratoCorretora = new ContratoCorretoraDataAceite();
		   contratoCorretora.setCdCorretora(cdCorretora);
		   contratoCorretora.setDtAceiteContrato(dataDeAceite);

		   //Mockando Service que busca no banco de dados 
		   given(service.getDataAceiteContratoByCdCorretora(cdCorretora)).willReturn(contratoCorretora);
		   
		   //Efetua a requisição na rota e espera um status code
		   mvc.perform(get("/contratocorretora/" + cdCorretora + "/dataaceite")
				   .contentType(APPLICATION_JSON))
				   .andExpect(MockMvcResultMatchers.jsonPath("$.cdCorretora").value(cdCorretora))
				   .andExpect(MockMvcResultMatchers.jsonPath("$.dtAceiteContrato").value(dataDeAceite))
		   .andExpect(status().isOk())
		   ;
	}

	@Test
	public void testBadRequest400getDataAceiteContratoByCdCorretora() throws Exception {

		String cdCorretora = "a";

		//Efetua a requisição na rota e espera um status code
		mvc.perform(get("/contratocorretora/" + cdCorretora + "/dataaceite")
				.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
		;
	}

	@Test
	public void testNoContent204getDataAceiteContratoByCdCorretora() throws Exception {

		Long cdCorretora = 488484L;
		String dataDeAceite = "2011-09-20 00:00:00";

		ContratoCorretoraDataAceite contratoCorretora = new ContratoCorretoraDataAceite();
		contratoCorretora.setCdCorretora(cdCorretora);
		contratoCorretora.setDtAceiteContrato(dataDeAceite);
		
		//Efetua a requisição na rota e espera um status code
		mvc.perform(get("/contratocorretora/" + cdCorretora + "/dataaceite")
				.contentType(APPLICATION_JSON))
				.andExpect(status().isNoContent())
		;
	}

	//201809101700 - esert - COR-710 - Serviço - TDD Novo serviço GET/contratocorretora/cdCor/tipo/cdTipo?susep=12.3456789012345-6
	@Test
	public void testOk200getContratoPreenchidoComSusep() throws Exception {
	
		   Long cdCorretora = 21L;
		   Long cdContratoModelo = 1L;
		   String cdSusep = "12.3456789012345-6";
	
		   ContratoCorretoraPreenchido contratoCorretoraPreenchido = new ContratoCorretoraPreenchido();
		   contratoCorretoraPreenchido.setCdCorretora(cdCorretora);
		   contratoCorretoraPreenchido.setCdContratoModelo(cdContratoModelo);
		   contratoCorretoraPreenchido.setContratoPreenchido("<p> Paragrafo 1 Nome da Minha Empresa</p> <p> Paragrafo 2 Susep 99999999999</p><p> Paragrafo 3 Data aceite 31/12/0001</p>");
		   contratoCorretoraPreenchido.setNomeArquivo("Modelo01ContratoCorretagemSusep.html");
		   contratoCorretoraPreenchido.setTipoConteudo(org.springframework.http.MediaType.TEXT_HTML_VALUE);
	
		   //Mockando Service que busca no banco de dados
		   given(service.getContratoPreenchido(cdCorretora, cdContratoModelo, cdSusep)).willReturn(contratoCorretoraPreenchido);
	
		   //Efetua a requisição na rota e espera um status code
		   mvc.perform(get("/contratocorretora/" + cdCorretora + "/tipo/" + cdContratoModelo + "?susep=" + cdSusep)
				   .contentType(APPLICATION_JSON))
				   .andExpect(MockMvcResultMatchers.jsonPath("$.cdCorretora").value(cdCorretora))
				   .andExpect(MockMvcResultMatchers.jsonPath("$.cdContratoModelo").value(cdContratoModelo))
				   .andExpect(status().isOk())
		   ;
	}

	//201809111109 - esert - COR-710 - Serviço - TDD Novo serviço GET/contratocorretora/cdCor/tipo/cdTipo (sem request parm susep)
	@Test
	public void testOk200getContratoPreenchidoSemSusep() throws Exception {
	
		   Long cdCorretora = 21L;
		   Long cdContratoModelo = 1L;
		   String cdSusep = null;
	
		   ContratoCorretoraPreenchido contratoCorretoraPreenchido = new ContratoCorretoraPreenchido();
		   contratoCorretoraPreenchido.setCdCorretora(cdCorretora);
		   contratoCorretoraPreenchido.setCdContratoModelo(cdContratoModelo);
		   contratoCorretoraPreenchido.setContratoPreenchido("<p> Paragrafo 1 Nome da Minha Empresa</p> <p> Paragrafo 3 Data aceite 31/12/0001</p>");
		   contratoCorretoraPreenchido.setNomeArquivo("Modelo02ContratoIntermediacao.html");
		   contratoCorretoraPreenchido.setTipoConteudo(org.springframework.http.MediaType.TEXT_HTML_VALUE);
	
		   //Mockando Service que busca no banco de dados
		   given(service.getContratoPreenchido(cdCorretora, cdContratoModelo, cdSusep)).willReturn(contratoCorretoraPreenchido);
	
		   //Efetua a requisição na rota e espera um status code
		   mvc.perform(get("/contratocorretora/" + cdCorretora + "/tipo/" + cdContratoModelo + "?susep=" + cdSusep)
				   .contentType(APPLICATION_JSON))
				   .andExpect(MockMvcResultMatchers.jsonPath("$.cdCorretora").value(cdCorretora))
				   .andExpect(MockMvcResultMatchers.jsonPath("$.cdContratoModelo").value(cdContratoModelo))
				   .andExpect(status().isOk())
		   ;
	}

	//201809101700 - esert - COR-710 - Serviço - TDD Novo serviço GET/contratocorretora/cdCor/tipo/cdTipo
	@Test
	public void testNoContent204getContratoPreenchido() throws Exception {
	
		Long cdCorretora = 488484L;
		Long cdContratoModelo = 1L;
		
		ContratoCorretoraPreenchido contratoCorretoraPreenchido = new ContratoCorretoraPreenchido();
		contratoCorretoraPreenchido.setCdCorretora(cdCorretora);
		contratoCorretoraPreenchido.setCdContratoModelo(cdContratoModelo);
		contratoCorretoraPreenchido.setContratoPreenchido("<p> Paragrafo 1 Nome da Minha Empresa</p> <p> Paragrafo 2 Susep 99999999999</p><p> Paragrafo 3 Data aceite 31/12/0001</p>");
		
		//Efetua a requisição na rota e espera um status code
		mvc.perform(get("/contratocorretora/" + cdCorretora + "/tipo/" + cdContratoModelo)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isNoContent())
		;
	}

	//201809101700 - esert - COR-710 - Serviço - TDD Novo serviço GET/contratocorretora/cdCor/tipo/cdTipo
	@Test
	public void testBadRequest400getContratoPreenchido() throws Exception {
	
		String cdCorretora = "a";
		Long cdContratoModelo = 1L;
	
		//Efetua a requisição na rota e espera um status code
		mvc.perform(get("/contratocorretora/" + cdCorretora + "/tipo/" + cdContratoModelo)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
		;
	}

	
}

package br.com.odontoprev.portal.corretor.test.controller.forcavenda;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.google.gson.Gson;

import br.com.odontoprev.portal.corretor.dto.EmpresaArquivo;
import br.com.odontoprev.portal.corretor.dto.EmpresaArquivoResponse;
import br.com.odontoprev.portal.corretor.dto.EmpresaArquivoResponseItem;
import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.dto.ForcaVendaResponse;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ForcaVendaControllerTestConfig.class })
@WebAppConfiguration
public class ForcaVendaControllerTest {

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUpClass() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		Mockito.reset(service);
	}

	private ForcaVendaService service;

	@Test
	public void testOk200() throws Exception {
		//Given
		Long codigoForcaVendaGiven = 1683L;
		ForcaVenda forcaVendaGiven = new ForcaVenda();
		ForcaVendaResponse forcaVendaResponseGiven = new ForcaVendaResponse(codigoForcaVendaGiven,"OK");
		// Mockando Service que busca no banco de dados
		given(service.updateForcaVenda(forcaVendaGiven)).willReturn(forcaVendaResponseGiven);

		// Efetua a requisição na rota e espera um status code
		mvc.perform(put("/forvavenda").contentType(APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void testNoContent204() throws Exception {
		Long codigoEmpresa = 0L;

		// Mockando Service que busca no banco de dados
		// given(service.findByCdEmpresa(1683L)).willReturn(new Empresa());

		// Efetua a requisição na rota e espera um status code
		mvc.perform(get("/empresa/" + codigoEmpresa) // chama com id zer0
			.contentType(APPLICATION_JSON))
			.andExpect(status().isNoContent()) // e nao deve retornar resultado
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
		// Mockando Service que busca no banco de dados
		// given(service.gerarArquivoEmpresa(empresaArquivo)).willReturn(empresaArquivoResponse);

		// Efetua a requisição na rota e espera um status code
		mvc.perform(post("/empresa/arquivo")
				.content(json)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void testEmpresaArquivo200ComEmpresaNaoEncontrada() throws Exception {

		Long empresaPesquisada = 5448448L;
		String mensagemEsperada = "Empresa nao encontrada.";

		EmpresaArquivo cdEmpresa = new EmpresaArquivo();
		cdEmpresa.setListCdEmpresa(new ArrayList<Long>());
		cdEmpresa.getListCdEmpresa().add(empresaPesquisada);
		String json = new Gson().toJson(cdEmpresa);
		// Mockando Service que busca no banco de dados

		EmpresaArquivoResponse empresaArquivoResponse = new EmpresaArquivoResponse();
		EmpresaArquivoResponseItem empresaArquivoResponseItem = 
				new EmpresaArquivoResponseItem(empresaPesquisada,"Empresa nao encontrada.");
		empresaArquivoResponse.setEmpresas(new ArrayList<EmpresaArquivoResponseItem>());
		empresaArquivoResponse.getEmpresas().add(empresaArquivoResponseItem);

		// given(service.gerarArquivoEmpresa(cdEmpresa)).willReturn(empresaArquivoResponse);

		// Efetua a requisição na rota e espera um status code
		mvc.perform(post("/empresa/arquivo").content(json).contentType(APPLICATION_JSON))
				.andExpect(
						MockMvcResultMatchers
						.jsonPath("$.empresas[0].mensagem").value(mensagemEsperada))
				.andExpect(status().isOk());

	}

	@Test
	public void testEmpresaArquivoBadResquest400() throws Exception {

		EmpresaArquivo cdEmpresa = new EmpresaArquivo();
		cdEmpresa.setListCdEmpresa(new ArrayList<Long>());
		String json = new Gson().toJson(cdEmpresa);
		// Mockando Service que busca no banco de dados
		// given(service.gerarArquivoEmpresa(cdEmpresa)).willReturn(new
		// EmpresaArquivoResponse());

		// Efetua a requisição na rota e espera um status code
		mvc.perform(post("/empresa/arquivo")
				.content(json)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}
}

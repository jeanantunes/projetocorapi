package br.com.odontoprev.portal.corretor.test.controller.venda;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.ContatoEmpresa;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.dto.Endereco;
import br.com.odontoprev.portal.corretor.dto.Plano;
import br.com.odontoprev.portal.corretor.dto.VendaPME;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;
import br.com.odontoprev.portal.corretor.service.VendaPFService;

//201808291727 - esert - COR-632 gerar pdf pme tdd POST/vendapme
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { VendaControllerTestConfig.class })
@WebAppConfiguration
public class VendaControllerTest {

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUpClass() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		Mockito.reset(service);
	}

	@Autowired
	private VendaPFService service;

	//201808291727 - esert - COR-657 teste automatico do COR-656 atualizar pdf pme ao click aceite 
	@Test
	public void testOk200VendaPME() throws Exception {

		// Montando Given
		long cdVendaGiven = 1234L;
		String numeroPropostaGiven = "654321";
		Long cdEmpresaGiven = 2345L;
		String dtVendaGiven = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
		VendaPME vendaPMEGiven = new VendaPME();
		vendaPMEGiven.setCdForcaVenda(6L);
		vendaPMEGiven.setPlataforma("APP ANDROID");
		vendaPMEGiven.setEmpresas(new ArrayList<Empresa>());
		vendaPMEGiven.getEmpresas().add(new Empresa());
		vendaPMEGiven.getEmpresas().get(0).setCnpj("19.095.399/0001-60");
		vendaPMEGiven.getEmpresas().get(0).setCnae("654321");
		vendaPMEGiven.getEmpresas().get(0).setRazaoSocial("Dione Ferreira da Silva");
		vendaPMEGiven.getEmpresas().get(0).setIncEstadual("");
		vendaPMEGiven.getEmpresas().get(0).setRamoAtividade("Cabeleireiro, Manicure");
		vendaPMEGiven.getEmpresas().get(0).setNomeFantasia("Salão Unissex Black White");
		vendaPMEGiven.getEmpresas().get(0).setRepresentanteLegal("Dione Ferreira da Silva");
		vendaPMEGiven.getEmpresas().get(0).setContatoEmpresa(true);
		vendaPMEGiven.getEmpresas().get(0).setTelefone("(31) 3774-0022");
		vendaPMEGiven.getEmpresas().get(0).setCelular("(31) 99749-7627");
		vendaPMEGiven.getEmpresas().get(0).setEmail("Jonnif32@gmail.com");
		vendaPMEGiven.getEmpresas().get(0).setVencimentoFatura(15);
		vendaPMEGiven.getEmpresas().get(0).setDataVigencia("15/09/2018");
		vendaPMEGiven.getEmpresas().get(0).setDataMovimentacao("04/09/2018");
		vendaPMEGiven.getEmpresas().get(0).setEnderecoEmpresa(new Endereco());
		vendaPMEGiven.getEmpresas().get(0).getEnderecoEmpresa().setCep("35700086");
		vendaPMEGiven.getEmpresas().get(0).getEnderecoEmpresa().setLogradouro("CATARINA");
		vendaPMEGiven.getEmpresas().get(0).getEnderecoEmpresa().setNumero("1282");
		vendaPMEGiven.getEmpresas().get(0).getEnderecoEmpresa().setComplemento("");
		vendaPMEGiven.getEmpresas().get(0).getEnderecoEmpresa().setBairro("BOA VISTA");
		vendaPMEGiven.getEmpresas().get(0).getEnderecoEmpresa().setCidade("SETE LAGOAS");
		vendaPMEGiven.getEmpresas().get(0).getEnderecoEmpresa().setEstado("MG");
		vendaPMEGiven.getEmpresas().get(0).setContactEmpresa(new ContatoEmpresa());
		vendaPMEGiven.getEmpresas().get(0).getContactEmpresa().setNome("");
		vendaPMEGiven.getEmpresas().get(0).getContactEmpresa().setEmail("");
		vendaPMEGiven.getEmpresas().get(0).getContactEmpresa().setCelular("");
		vendaPMEGiven.getEmpresas().get(0).setPlanos(new ArrayList<Plano>());
		vendaPMEGiven.getEmpresas().get(0).getPlanos().add(new Plano());
		vendaPMEGiven.getEmpresas().get(0).getPlanos().get(0).setCdPlano(101);
		vendaPMEGiven.getEmpresas().get(0).getPlanos().get(0).setCdPlano(102);
		vendaPMEGiven.getEmpresas().get(0).setCpfRepresentante("980.631.626-68");
		vendaPMEGiven.setTitulares(new ArrayList<Beneficiario>());
		vendaPMEGiven.getTitulares().add(new Beneficiario());
		vendaPMEGiven.getTitulares().get(0).setCdVida(0L);
		vendaPMEGiven.getTitulares().get(0).setCdTitular(0L);
		vendaPMEGiven.getTitulares().get(0).setCnpj("19.095.399/0001-60");
		vendaPMEGiven.getTitulares().get(0).setCelular("");
		vendaPMEGiven.getTitulares().get(0).setCpf("052.999.487-94");
		vendaPMEGiven.getTitulares().get(0).setDataNascimento("15/03/1982");
		vendaPMEGiven.getTitulares().get(0).setEmail("");
		vendaPMEGiven.getTitulares().get(0).setNome("ELAINE CRISTINA MARQUES DE LIMA");
		vendaPMEGiven.getTitulares().get(0).setNomeMae("Conceicao Aparecida Rodrigues Marques");
		vendaPMEGiven.getTitulares().get(0).setPfPj("f");
		vendaPMEGiven.getTitulares().get(0).setSexo("m");
		vendaPMEGiven.getTitulares().get(0).setCdPlano(101L);
		vendaPMEGiven.getTitulares().get(0).setCdVenda(0L);
		vendaPMEGiven.getTitulares().get(0).setEndereco(new Endereco());
		vendaPMEGiven.getTitulares().get(0).getEndereco().setCep("22711-250");
		vendaPMEGiven.getTitulares().get(0).getEndereco().setLogradouro("Rua Josafa");
		vendaPMEGiven.getTitulares().get(0).getEndereco().setNumero("1");
		vendaPMEGiven.getTitulares().get(0).getEndereco().setComplemento("");
		vendaPMEGiven.getTitulares().get(0).getEndereco().setBairro("Curicica");
		vendaPMEGiven.getTitulares().get(0).getEndereco().setCidade("Rio de Janeiro");
		vendaPMEGiven.getTitulares().get(0).getEndereco().setEstado("RJ");
		vendaPMEGiven.getTitulares().get(0).setDependentes(new ArrayList<Beneficiario>());
		vendaPMEGiven.getTitulares().get(0).getDependentes().add(new Beneficiario());
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(0).setCdVida(0L);
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(0).setCdTitular(0L);
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(0).setCelular("");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(0).setCpf("028.534.154-57");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(0).setDataNascimento("03/04/1979");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(0).setEmail("");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(0).setNome("IVAN CORREIA DE LIMA");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(0).setNomeMae("JOSEFA LEONARDO DE LIMA");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(0).setPfPj("f");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(0).setSexo("m");
		vendaPMEGiven.getTitulares().get(0).getDependentes().add(new Beneficiario());
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(1).setCdVida(0L);
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(1).setCdTitular(0L);
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(1).setCelular("");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(1).setCpf("141.062.757-83");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(1).setDataNascimento("01/12/1990");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(1).setEmail("");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(1).setNome("EMERSON RODRIGUES MARQUES");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(1).setNomeMae("CONCEICAO APARECIDA RODRIGUES MARQUES");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(1).setPfPj("f");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(1).setSexo("m");
		vendaPMEGiven.getTitulares().get(0).getDependentes().add(new Beneficiario());
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(2).setCdVida(0L);
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(2).setCdTitular(0L);
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(2).setCelular("");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(2).setCpf("764.323.867-72");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(2).setDataNascimento("24/03/1963");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(2).setEmail("");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(2).setNome("ALDO ADAO");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(2).setNomeMae("MARIA CHRISTINA FERREIRA ADAO");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(2).setPfPj("f");
		vendaPMEGiven.getTitulares().get(0).getDependentes().get(2).setSexo("m");
		
		// Montando Request
		String jsonRequest = new Gson().toJson(vendaPMEGiven);

		// Montando Response
		VendaResponse vendaResponse = new VendaResponse(
				cdVendaGiven
				, "Venda cadastrada." 
					+ " CdVenda:[" + cdVendaGiven + "];" 
					+ " NumeroProposta:[" + numeroPropostaGiven + "];" 
					+ " DtVenda:[" + dtVendaGiven + "];"
					+ " MensagemErro:[" + "" + "];"
				,cdVendaGiven //cdVenda 
				,numeroPropostaGiven //numeroProposta
				,dtVendaGiven //dtVenda
				,"" //mensagemErro
				,cdEmpresaGiven //cdEmpresa
				);

		// Mockando Service que busca no banco de dados
		given(service.addVendaPME(vendaPMEGiven)).willReturn(vendaResponse);

		// Efetua a requisição na rota e espera um status code
		mvc.perform(post("/vendapme")
				.content(jsonRequest)
				.header("User-Agent", "Synapse-PT-HttpComponents-NIO") //201808291908 - esert - COR-632
				.contentType(APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.cdVenda").value(String.valueOf(cdVendaGiven)))
				.andExpect(status().isOk())
				;

		// Verifica se os metódos da lógica interna foram chamados
		BDDMockito.verify(service).addVendaPME(vendaPMEGiven);
	}
	
	//TODO: 201808291715 - esert - DEBITO TECNICO - nao foi possivel implementar outros testes pq o metodo nao esta adequado e a mudanca implica impactos nao previstos nem analisados

}

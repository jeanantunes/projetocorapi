package br.com.odontoprev.portal.corretor.service.impl;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.odontoprev.portal.corretor.dto.SerasaInfoResponse;
import br.com.odontoprev.portal.corretor.model.TbodSerasaInfo;
import br.com.odontoprev.portal.corretor.service.SerasaInfoService;

@Service
public class SerasaInfoServiceImpl implements SerasaInfoService {

	private static final Log log = LogFactory.getLog(SerasaInfoServiceImpl.class);
	
	@Override
	public SerasaInfoResponse addInformacoesSerasa(String jsonSerasa) throws JsonProcessingException, IOException {
	
		log.info("[addInformacoesSerasa]");
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(jsonSerasa);
		
		/********	 DADOS CADASTRAIS    ********/
		System.out.println("********	 DADOS CADASTRAIS    ********");
		System.out.println(actualObj.get("cnpj"));
		System.out.println(actualObj.get("razaoSocial"));
		System.out.println(actualObj.get("nomeFantasia"));
		System.out.println(actualObj.get("dataAbertura"));
		
		System.out.println(actualObj.get("cnae").get("tnsCnae").get("codigo"));
		System.out.println(actualObj.get("cnae").get("tnsCnae").get("descricao"));
		
		TbodSerasaInfo tbodSerasaInfo = new TbodSerasaInfo();
		tbodSerasaInfo.setCnpj(actualObj.get("cnpj").toString());
		
		
		/********	 SITUAÇÃO CADASTRAL    ********/
		System.out.println("********	 SITUAÇÃO CADASTRAL    ********");
		System.out.println(actualObj.get("situacaoCadastral").get("situacao"));
		System.out.println(actualObj.get("situacaoCadastral").get("dataSituacao"));
		System.out.println(actualObj.get("situacaoCadastral").get("dataConsulta"));
		System.out.println(actualObj.get("situacaoCadastral").get("fontePesquisada"));		
		
		/********	 TELEFONES    ********/
		System.out.println("********	 TELEFONES    ********");
		JsonNode listTelefones = actualObj.get("telefones");
		JsonNode telefones = listTelefones.get("telefone");
		telefones.forEach(fone -> {
			System.out.println(fone.get("ddd"));
			System.out.println(fone.get("numero"));
		});
		
		/********	 REPRESENTAÇÃO LEGAL    ********/
		System.out.println("********	 REPRESENTAÇÃO LEGAL    ********");
		JsonNode listRepresLegal = actualObj.get("representanteLegal");
		JsonNode representantes = listRepresLegal.get("RepresentanteLegal");
		
		System.out.println(representantes.get("documento"));
		System.out.println(representantes.get("nome"));
		
		/*representantes.forEach(representante -> {
			System.out.println(representante.get("documento"));
			System.out.println(representante.get("nome"));
		});*/
		
		/********	 ENDERECO    ********/
		System.out.println("********	 ENDERECO    ********");
		JsonNode listEnderecos = actualObj.get("enderecos");
		JsonNode enderecos = listEnderecos.get("endereco");
		enderecos.forEach(endereco -> {
			System.out.println(endereco.get("logradouro").get("Tipo"));
			System.out.println(endereco.get("logradouro").get("Nome"));
			System.out.println(endereco.get("logradouro").get("Numero"));
			System.out.println(endereco.get("logradouro").get("CepNota"));
			System.out.println(endereco.get("bairro"));
			System.out.println(endereco.get("cidade"));
			System.out.println(endereco.get("uf"));
			System.out.println(endereco.get("cep"));
		});
		
		return null;
	}

	
	
	

}

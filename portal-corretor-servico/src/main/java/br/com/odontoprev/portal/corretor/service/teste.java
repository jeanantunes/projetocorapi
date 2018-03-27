package br.com.odontoprev.portal.corretor.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class teste {

	public static void main(String[] args) throws JsonProcessingException, IOException {
		
		String json = "{  \r\n" + 
				"   \"enderecos\":{  \r\n" + 
				"      \"endereco\":[  \r\n" + 
				"         {  \r\n" + 
				"            \"logradouro\":{  \r\n" + 
				"               \"Tipo\":\"R\",\r\n" + 
				"               \"Nome\":\"PIRAJUSSARA\",\r\n" + 
				"               \"Numero\":\"432\",\r\n" + 
				"               \"CepNota\":\"10\"\r\n" + 
				"            },\r\n" + 
				"            \"bairro\":\"BUTANTA\",\r\n" + 
				"            \"cidade\":\"SAO PAULO\",\r\n" + 
				"            \"uf\":\"SP\",\r\n" + 
				"            \"cep\":\"05501020\"\r\n" + 
				"         },\r\n" + 
				"         {  \r\n" + 
				"            \"logradouro\":{  \r\n" + 
				"               \"Tipo\":\"R\",\r\n" + 
				"               \"Nome\":\"PIRAJUSSARA\",\r\n" + 
				"               \"Numero\":\"462\",\r\n" + 
				"               \"CepNota\":\"10\"\r\n" + 
				"            },\r\n" + 
				"            \"bairro\":\"BUTANTA\",\r\n" + 
				"            \"cidade\":\"SAO PAULO\",\r\n" + 
				"            \"uf\":\"SP\",\r\n" + 
				"            \"cep\":\"05501020\"\r\n" + 
				"         },\r\n" + 
				"         {  \r\n" + 
				"            \"logradouro\":{  \r\n" + 
				"               \"Tipo\":\"R\",\r\n" + 
				"               \"Nome\":\"CAETANOPOLIS\",\r\n" + 
				"               \"Numero\":\"930\",\r\n" + 
				"               \"Complemento\":\"AP 123\",\r\n" + 
				"               \"CepNota\":\"10\"\r\n" + 
				"            },\r\n" + 
				"            \"bairro\":\"JAGUARE\",\r\n" + 
				"            \"cidade\":\"SAO PAULO\",\r\n" + 
				"            \"uf\":\"SP\",\r\n" + 
				"            \"cep\":\"05335120\"\r\n" + 
				"         },\r\n" + 
				"         {  \r\n" + 
				"            \"logradouro\":{  \r\n" + 
				"               \"Tipo\":\"AV\",\r\n" + 
				"               \"Titulo\":\"DR\",\r\n" + 
				"               \"Nome\":\"VITAL BRASIL\",\r\n" + 
				"               \"Numero\":\"325\",\r\n" + 
				"               \"CepNota\":\"10\"\r\n" + 
				"            },\r\n" + 
				"            \"bairro\":\"BUTANTA\",\r\n" + 
				"            \"cidade\":\"SAO PAULO\",\r\n" + 
				"            \"uf\":\"SP\",\r\n" + 
				"            \"cep\":\"05503001\"\r\n" + 
				"         },\r\n" + 
				"         {  \r\n" + 
				"            \"logradouro\":{  \r\n" + 
				"               \"Nome\":\"R PIRAJUSSARA\",\r\n" + 
				"               \"Numero\":\"432\",\r\n" + 
				"               \"CepNota\":\"10\"\r\n" + 
				"            },\r\n" + 
				"            \"bairro\":\"BUTANTA\",\r\n" + 
				"            \"cidade\":\"SAO PAULO\",\r\n" + 
				"            \"uf\":\"SP\",\r\n" + 
				"            \"cep\":\"05501020\"\r\n" + 
				"         }\r\n" + 
				"      ]\r\n" + 
				"   },\r\n" + 
				"   \"telefones\":{  \r\n" + 
				"      \"telefone\":[  \r\n" + 
				"         {  \r\n" + 
				"            \"ddd\":\"11\",\r\n" + 
				"            \"numero\":\"37969325\"\r\n" + 
				"         },\r\n" + 
				"         {  \r\n" + 
				"            \"ddd\":\"11\",\r\n" + 
				"            \"numero\":\"37969328\"\r\n" + 
				"         },\r\n" + 
				"         {  \r\n" + 
				"            \"ddd\":\"11\",\r\n" + 
				"            \"numero\":\"37966930\"\r\n" + 
				"         },\r\n" + 
				"         {  \r\n" + 
				"            \"ddd\":\"11\",\r\n" + 
				"            \"numero\":\"37966928\"\r\n" + 
				"         },\r\n" + 
				"         {  \r\n" + 
				"            \"ddd\":\"11\",\r\n" + 
				"            \"numero\":\"37966935\"\r\n" + 
				"         }\r\n" + 
				"      ]\r\n" + 
				"   },\r\n" + 
				"   \"cnpj\":\"68971092000190\",\r\n" + 
				"   \"razaoSocial\":\"13 CARTORIO DE REGISTRO CIVIL DO BUTANTA\",\r\n" + 
				"   \"nomeFantasia\":\"CARTORIO EVANDRO CUNHA\",\r\n" + 
				"   \"dataAbertura\":\"1992-10-27T00:00:00.000-02:00\",\r\n" + 
				"   \"cnae\":{  \r\n" + 
				"      \"tnsCnae\":{  \r\n" + 
				"         \"codigo\":\"6912500\",\r\n" + 
				"         \"descricao\":\"Cartórios\"\r\n" + 
				"      }\r\n" + 
				"   },\r\n" + 
				"   \"situacaoCadastral\":{  \r\n" + 
				"      \"situacao\":\"ATIVA\",\r\n" + 
				"      \"dataSituacao\":\"2003-04-12T00:00:00.000-03:00\",\r\n" + 
				"      \"dataConsulta\":\"2018-02-03T00:00:00.000-02:00\",\r\n" + 
				"      \"fontePesquisada\":\"HISTORICO\"\r\n" + 
				"   },\r\n" + 
				"   \"representanteLegal\":{  \r\n" + 
				"      \"RepresentanteLegal\":{  \r\n" + 
				"         \"documento\":\"00501786872\",\r\n" + 
				"         \"nome\":\"EVANDRO DA CUNHA\"\r\n" + 
				"      }\r\n" + 
				"   }\r\n" + 
				"}";
		
		//System.out.println(json);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(json);
		
		/********	 DADOS CADASTRAIS    ********/
		System.out.println("********	 DADOS CADASTRAIS    ********");
		System.out.println(actualObj.get("cnpj"));
		System.out.println(actualObj.get("razaoSocial"));
		System.out.println(actualObj.get("nomeFantasia"));
		System.out.println(actualObj.get("dataAbertura"));
		
		System.out.println(actualObj.get("cnae").get("tnsCnae").get("codigo"));
		System.out.println(actualObj.get("cnae").get("tnsCnae").get("descricao"));
		
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
		    
		  
		
		
		  
		//JSONObject object = new JSONArray(json).getJSONObject(0);
		
		//instancia um novo JSONObject passando a string como entrada
		//		JSONObject object = new JSONObject(json);
		
		//System.out.println(object);

		/*object.getJSONObject("value").getJSONArray("records").forEach(item -> {
		   JSONObject record = (JSONObject) item;

		   final String descricao = record.getString("descricao"),
		                ccusto = record.getString("cod-ccusto");

		   System.out.println("Descrição: " + descricao + " - Código: " + ccusto);
		});*/

	}

}

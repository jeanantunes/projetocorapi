package br.com.odontoprev.portal.corretor.util;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.odontoprev.portal.corretor.dto.Venda;

@Service
public class ConvertObjectUtil {
	
	public String ConvertObjectToJson(Venda venda) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(venda);
	}

}

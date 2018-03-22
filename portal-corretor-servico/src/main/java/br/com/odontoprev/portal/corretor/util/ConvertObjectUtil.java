package br.com.odontoprev.portal.corretor.util;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaPME;

@Service
public class ConvertObjectUtil {

	public String ConvertObjectToJson(Venda vendaPF, VendaPME vendaPME) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(vendaPF != null ? vendaPF : vendaPME);
	}
	
}

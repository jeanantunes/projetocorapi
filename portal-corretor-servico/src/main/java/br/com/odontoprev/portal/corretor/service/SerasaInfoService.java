package br.com.odontoprev.portal.corretor.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.odontoprev.portal.corretor.dto.SerasaInfoResponse;

public interface SerasaInfoService {

	/*add dados Serasa*/
	SerasaInfoResponse addInformacoesSerasa(String jsonSerasa) throws JsonProcessingException, IOException;
}

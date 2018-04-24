package br.com.odontoprev.portal.corretor.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.odontoprev.api.manager.client.token.ApiManagerToken;
import br.com.odontoprev.api.manager.client.token.ApiManagerTokenFactory;
import br.com.odontoprev.api.manager.client.token.ApiToken;
import br.com.odontoprev.api.manager.client.token.enumerator.ApiManagerTokenEnum;
import br.com.odontoprev.portal.corretor.exceptions.ApiTokenException;

@Service
@Component
public class ApiManagerTokenServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiManagerTokenServiceImpl.class);

	public String getToken() throws ApiTokenException {
			
			final ApiManagerToken apiManager = ApiManagerTokenFactory.create(ApiManagerTokenEnum.WSO2, "PORTAL_CORRETOR_SERVICO");
			final ApiToken apiToken;
			try {
				apiToken = apiManager.generateToken();
				return apiToken.getAccessToken();
			} catch (Exception e) {
				LOGGER.error("ERRO AO OBTER TOKEN", e);
				throw new ApiTokenException("ERRO AO OBTER TOKEN",e);
			} 	
	}
}

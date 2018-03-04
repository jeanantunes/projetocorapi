package br.com.odontoprev.portal.corretor.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public interface ApiManagerTokenService {

	public String getToken();
}

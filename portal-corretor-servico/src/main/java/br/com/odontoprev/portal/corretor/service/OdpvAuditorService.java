package br.com.odontoprev.portal.corretor.service;

import java.security.Principal;

public interface OdpvAuditorService {

	//void audit(String body);

	void audit(String requestURI, String stringJsonBody, String stringUserAgent);

	//void audit(String requestURI, Principal principal, String stringJsonBody, String stringUserAgent);

	void audit(String requestURI, Principal principal, String stringJsonBody, String stringUserAgent, String headers, String parameters);
}
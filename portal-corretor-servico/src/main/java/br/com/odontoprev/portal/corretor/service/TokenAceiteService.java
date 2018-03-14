package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.TokenAceite;
import br.com.odontoprev.portal.corretor.dto.TokenAceiteResponse;

public interface TokenAceiteService {
	
	/*add token aceite*/
	TokenAceiteResponse addTokenAceite(TokenAceite tokenAceite);
	
	/*busca token por chave*/
	TokenAceite buscarTokenAceitePorChave(String chave);

	/*Criptografa token*/
	String gerarToken(String chave);
}

package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.TokenEsqueciSenha;
import br.com.odontoprev.portal.corretor.dto.TokenEsqueciSenhaResponse;

public interface TokenEsqueciSenhaService {

	/*add token aceite*/
	TokenEsqueciSenhaResponse addTokenEsqueciSenha(TokenEsqueciSenha tokenEsqueciSenha) throws Exception;
	
	/*busca token por chave*/
	TokenEsqueciSenha buscarTokenEsqueciSenha(String token);

	/*Criptografa token*/
	String gerarToken(String chave);

	/*update token aceite*/
	TokenEsqueciSenhaResponse updateTokenEsqueciSenha(TokenEsqueciSenha tokenAceite);
}

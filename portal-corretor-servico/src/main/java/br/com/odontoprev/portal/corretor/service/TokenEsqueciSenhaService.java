package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.TokenEsqueciSenha;
import br.com.odontoprev.portal.corretor.dto.TokenEsqueciSenhaResponse;
import br.com.odontoprev.portal.corretor.model.TbodTokenResetSenha;

public interface TokenEsqueciSenhaService {

	/*add token esqueci senha*/
	TokenEsqueciSenhaResponse addTokenEsqueciSenha(TokenEsqueciSenha tokenEsqueciSenha) throws Exception;
	
	/*busca token por chave*/
	TbodTokenResetSenha buscarTokenEsqueciSenha(String token);

	/*Criptografa token*/
	String gerarToken(String chave);

	/*update token data reset de senha*/
	TokenEsqueciSenhaResponse updateDataResetSenha(String token);
}

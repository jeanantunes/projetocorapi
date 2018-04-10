package br.com.odontoprev.portal.corretor.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.business.SendMailEsqueciSenha;
import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dao.TokenEsqueciSenhaDAO;
import br.com.odontoprev.portal.corretor.dto.TokenEsqueciSenha;
import br.com.odontoprev.portal.corretor.dto.TokenEsqueciSenhaResponse;
import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
import br.com.odontoprev.portal.corretor.model.TbodTokenResetSenha;
import br.com.odontoprev.portal.corretor.service.TokenEsqueciSenhaService;
import br.com.odontoprev.portal.corretor.util.GerarTokenUtils;

@Service
public class TokenEsqueciSenhaServiceImpl implements TokenEsqueciSenhaService{
	
	private static final Log log = LogFactory.getLog(TokenEsqueciSenhaServiceImpl.class);
	
	@Autowired
	ForcaVendaDAO forcaVendaDAO;
	
	@Autowired
	TokenEsqueciSenhaDAO tokenEsqueciSenhaDAO;

	@Override
	public TokenEsqueciSenhaResponse addTokenEsqueciSenha(TokenEsqueciSenha tokenEsqueciSenha) throws Exception {

		log.info("[addTokenEsqueciSenha - validacao cpf existe]");
		
		List<TbodForcaVenda> tbForcaVendas = new ArrayList<TbodForcaVenda>();
		
		/***O retorno eh uma lista, mas nao deve existir mais de uma forca de venda na base com o mesmo cpf***/
		tbForcaVendas = forcaVendaDAO.findByCpf(tokenEsqueciSenha.getCpf());

		if (tbForcaVendas.isEmpty()) {
			throw new Exception("ForcaVenda nao encontrada!");
		} else if (tbForcaVendas.size() > 1) {
			throw new Exception("CPF duplicado!");
		}  else if (tbForcaVendas.get(0).getAtivo().equals("N")) {
			return new TokenEsqueciSenhaResponse(204, "Usuario inativo");			 
		}
						
		LocalDateTime dataHoraAtual = LocalDateTime.now();
		
		log.info("[addTokenEsqueciSenha - gerando token]");
		String token = this.gerarToken(tokenEsqueciSenha.getCpf().toString()+dataHoraAtual.toString().replace("=", ""));
		
		log.info("[addTokenEsqueciSenha - insert]");
		
		try {
			TbodTokenResetSenha resetSenha = new TbodTokenResetSenha();
			resetSenha.setCpf(tokenEsqueciSenha.getCpf());
			resetSenha.setEmailDestinatario(tbForcaVendas.get(0).getEmail());
			resetSenha.setToken(token.replace("=", ""));
			resetSenha.setDataEnvioEmail(new Date());
			resetSenha = tokenEsqueciSenhaDAO.save(resetSenha);			
			
			SendMailEsqueciSenha sendEmail = new SendMailEsqueciSenha();
			sendEmail.sendMail(tbForcaVendas.get(0).getEmail(), resetSenha.getToken() );
			
		} catch (Exception e) {
			log.error(e);
			log.error("\"Erro ao cadastrar token de esqueci minha senha :: Detalhe: [" + e.getMessage() + "]");
			return new TokenEsqueciSenhaResponse(0, "Erro ao cadastrar token de esqueci minha Osenha. Detalhe: [" + e.getMessage() + "]");
		}
				
		return new TokenEsqueciSenhaResponse(200, "Token de reset de senha gerado com sucesso. Enviado para o e-mail: " + tbForcaVendas.get(0).getEmail());
	}

	@Override
	public TbodTokenResetSenha buscarTokenEsqueciSenha(String token) {
		return tokenEsqueciSenhaDAO.findCPFporToken(token);
	}

	@Override
	public TokenEsqueciSenhaResponse updateTokenEsqueciSenha(TokenEsqueciSenha tokenAceite) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String gerarToken(String chave) {
		return GerarTokenUtils.gerarHashToken(chave);
	}

}

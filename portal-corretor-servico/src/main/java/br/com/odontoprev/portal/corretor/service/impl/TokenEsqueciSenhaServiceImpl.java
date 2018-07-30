package br.com.odontoprev.portal.corretor.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.business.SendMailEsqueciSenha;
import br.com.odontoprev.portal.corretor.dao.CorretoraDAO;
import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dao.TokenEsqueciSenhaDAO;
import br.com.odontoprev.portal.corretor.dto.TokenEsqueciSenha;
import br.com.odontoprev.portal.corretor.dto.TokenEsqueciSenhaResponse;
import br.com.odontoprev.portal.corretor.model.TbodCorretora;
import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
import br.com.odontoprev.portal.corretor.model.TbodTokenResetSenha;
import br.com.odontoprev.portal.corretor.service.TokenEsqueciSenhaService;
import br.com.odontoprev.portal.corretor.util.Constantes;
import br.com.odontoprev.portal.corretor.util.GerarTokenUtils;

@Service
public class TokenEsqueciSenhaServiceImpl implements TokenEsqueciSenhaService{
	
	private static final Log log = LogFactory.getLog(TokenEsqueciSenhaServiceImpl.class);
	
	@Autowired
	ForcaVendaDAO forcaVendaDAO;
	
	@Autowired
	CorretoraDAO corretoraDAO; //201807171752 - esert - COR-317
	
	@Autowired
	TokenEsqueciSenhaDAO tokenEsqueciSenhaDAO;

	@Override
	public TokenEsqueciSenhaResponse addTokenEsqueciSenha(TokenEsqueciSenha tokenEsqueciSenha) throws Exception {

		log.info("[addTokenEsqueciSenha - ini]");
		log.info("tokenEsqueciSenha.getCpf():[" + tokenEsqueciSenha.getCpf() + "]");
		log.info("tokenEsqueciSenha.getCnpj():[" + tokenEsqueciSenha.getCnpj() + "]"); //201807201712 - esert - COR-317
		
		List<TbodForcaVenda> tbForcaVendas = new ArrayList<TbodForcaVenda>();
		TbodCorretora tbodCorretora = null; //201807171752 - esert - COR-317
		String cpfCnpj = null; //201807201712 - esert - COR-317
		String emailDestinatarioToken = null;
		
		if(
			tokenEsqueciSenha.getCpf() == null
			&&
			tokenEsqueciSenha.getCnpj() == null //201807201712 - esert - COR-317
		) {
			throw new Exception("Cpf e Cnpj estao nuloz.");
		} else { 
//			if(
//			tokenEsqueciSenha.getCpf().trim().isEmpty()
//			&&
//			tokenEsqueciSenha.getCnpj().trim().isEmpty() //201807201712 - esert - COR-317
//		) {
//			throw new Exception("Cpf e Cnpj estao vazioz.");
//		} else {
			if( //201807201712 - esert - COR-317
				tokenEsqueciSenha.getCpf()!=null
				&&
				!tokenEsqueciSenha.getCpf().isEmpty()
			) {
				if(tokenEsqueciSenha.getCpf().length() != Constantes.TAMANHO_CPF) {				
					throw new Exception("Cpf tamanho invalido [" + tokenEsqueciSenha.getCpf().length() + "].");						
				} else {
				
					log.info("[addTokenEsqueciSenha - detectado cpf]");
					/***O retorno eh uma lista, mas nao deve existir mais de uma forca de venda na base com o mesmo cpf***/
					tbForcaVendas = forcaVendaDAO.findByCpf(tokenEsqueciSenha.getCpf());
					if (tbForcaVendas.isEmpty()) {
						throw new Exception("ForcaVenda nao encontrada!");
					} else if (tbForcaVendas.size() > 1) {
						throw new Exception("CPF duplicado!");
					}  else if (tbForcaVendas.get(0).getAtivo().equals(Constantes.INATIVO)) {
						return new TokenEsqueciSenhaResponse(204, "Usuario Forca Venda inativo");			 
					}
					emailDestinatarioToken = tbForcaVendas.get(0).getEmail();
					cpfCnpj = tbForcaVendas.get(0).getCpf(); //201807201712 - esert - COR-317
				}
			} else if( //201807201712 - esert - COR-317
				tokenEsqueciSenha.getCnpj()!=null
				&&
				!tokenEsqueciSenha.getCnpj().isEmpty()
			) {
				if(tokenEsqueciSenha.getCnpj().length() != Constantes.TAMANHO_CNPJ) { //201807171752 - esert - COR-317
					throw new Exception("Cnpj tamanho invalido [" + tokenEsqueciSenha.getCnpj().length() + "].");						
				} else {
				
					log.info("[addTokenEsqueciSenha - detectado cnpj]");
					tbodCorretora = corretoraDAO.findByCnpj(tokenEsqueciSenha.getCnpj()); //201807171752 - esert - COR-317
					if (tbodCorretora == null) {
						throw new Exception("Corretora nao encontrada!");
		//			} else if (tbForcaVendas.size() > 1) {
		//				throw new Exception("CPF duplicado!");
					}  else if (tbodCorretora.getAtivo().equals(Constantes.INATIVO)) {
						return new TokenEsqueciSenhaResponse(204, "Usuario Corretora inativo");			 
					}
					emailDestinatarioToken = tbodCorretora.getEmail();
					cpfCnpj = tbodCorretora.getCnpj(); //201807201712 - esert - COR-317

				}
			}
		}
						
		LocalDateTime dataHoraAtual = LocalDateTime.now();
		
		log.info("[addTokenEsqueciSenha - gerando token]");
		String token = this.gerarToken(cpfCnpj.concat(dataHoraAtual.toString().replace("=", "")));
		
		log.info("[addTokenEsqueciSenha - insert TbodTokenResetSenha]");
		
		try {
			TbodTokenResetSenha tbodTokenResetSenha = new TbodTokenResetSenha();
			tbodTokenResetSenha.setCpf(cpfCnpj);
			//resetSenha.setEmailDestinatario(tbForcaVendas.get(0).getEmail());
			tbodTokenResetSenha.setEmailDestinatario(emailDestinatarioToken);
			tbodTokenResetSenha.setToken(token.replace("=", ""));
			tbodTokenResetSenha.setDataEnvioEmail(new Date());
			tbodTokenResetSenha = tokenEsqueciSenhaDAO.save(tbodTokenResetSenha);			
			
			log.info("[addTokenEsqueciSenha - sendMailEsqueciSenha]");
			SendMailEsqueciSenha sendMailEsqueciSenha = new SendMailEsqueciSenha();
			//sendEmail.sendMail(tbForcaVendas.get(0).getEmail(), tbodTokenResetSenha.getToken() );
			sendMailEsqueciSenha.sendMail(tbodTokenResetSenha.getEmailDestinatario(), tbodTokenResetSenha.getToken());
			
		} catch (Exception e) {
			log.error(e);
			String msgErro = "Erro ao cadastrar token de esqueci minha senha para Cpf/Cnpj:[" + cpfCnpj + "],Detalhe:[" + e.getMessage() + "]"; 
			log.error(msgErro);
			return new TokenEsqueciSenhaResponse(0, msgErro);
		}
				
		log.info("[addTokenEsqueciSenha - fim]");
		return new TokenEsqueciSenhaResponse(200, "Token de reset de senha gerado com sucesso para Cpf/Cnpj[" + cpfCnpj + "]. Enviado para o e-mail: " + emailDestinatarioToken + "");
	}

	@Override
	public TbodTokenResetSenha buscarTokenEsqueciSenha(String token) {
		return tokenEsqueciSenhaDAO.findToken(token);
	}

	@Override
	public TokenEsqueciSenhaResponse updateDataResetSenha(String token) {
		
		log.info("updateTokenEsqueciSenha");
		
		try {
			
			TbodTokenResetSenha tbodTokenResetSenha = new TbodTokenResetSenha();
			tbodTokenResetSenha = tokenEsqueciSenhaDAO.findToken(token);
			
			if (tbodTokenResetSenha == null) {
				return new TokenEsqueciSenhaResponse(404, HttpStatus.NOT_FOUND.toString());
			} 			
					
			tbodTokenResetSenha.setDataResetSenha(new Date());						
			tbodTokenResetSenha = tokenEsqueciSenhaDAO.save(tbodTokenResetSenha);
			
			return new TokenEsqueciSenhaResponse(200, HttpStatus.OK.toString());
			
		} catch (Exception e) {
			log.error(e);
			log.error("Erro ao confirmar data de aceite :: Detalhe: [" + e.getMessage() + "]");
			return new TokenEsqueciSenhaResponse(0, "Erro ao confirmar data de reset de senha. Detalhe: [" + e.getMessage() + "]");
		}
	}

	@Override
	public String gerarToken(String chave) {
		return GerarTokenUtils.gerarHashToken(chave);
	}

}

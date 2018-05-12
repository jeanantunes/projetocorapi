package br.com.odontoprev.portal.corretor.service.impl;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.business.SendMailAceite;
import br.com.odontoprev.portal.corretor.dao.TokenAceiteDAO;
import br.com.odontoprev.portal.corretor.dto.EmailAceite;
import br.com.odontoprev.portal.corretor.dto.Plano;
import br.com.odontoprev.portal.corretor.dto.TokenAceite;
import br.com.odontoprev.portal.corretor.dto.TokenAceiteResponse;
import br.com.odontoprev.portal.corretor.model.TbodTokenAceite;
import br.com.odontoprev.portal.corretor.model.TbodTokenAceitePK;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.service.PlanoService;
import br.com.odontoprev.portal.corretor.service.TokenAceiteService;
import br.com.odontoprev.portal.corretor.service.VendaService;
import br.com.odontoprev.portal.corretor.util.GerarTokenUtils;

@Service
public class TokenAceiteServiceImpl implements TokenAceiteService {	
	
	private static final Log log = LogFactory.getLog(ForcaVendaServiceImpl.class);
	
	@Autowired
	TokenAceiteDAO tokenAceiteDAO;
	
	@Autowired
	VendaService vendaService;
	
	@Autowired
	PlanoService planoService;
	
	@Value("${EXPIRACAO_TOKEN_ACEITE_EMAIL}")
	private String EXPIRACAO_TOKEN_ACEITE_EMAIL;
		
	@Override
	public TokenAceiteResponse addTokenAceite(TokenAceite tokenAceite) {
		
		//TODO: validaçoes
		
		log.info("[addTokenAceite - validacao venda existe]");
		
		TbodVenda venda = vendaService.buscarVendaPorCodigo(tokenAceite.getCdVenda());
		
		if(venda == null) {
			return new TokenAceiteResponse(204, "Código de venda não encontrado");
		}

		log.info("[addTokenAceite - insert]");
		
		LocalDateTime dataHoraAtual = LocalDateTime.now();
		
		TbodTokenAceitePK tokenAceitePK = new TbodTokenAceitePK();
		TbodTokenAceite tbodTokenAceite = new TbodTokenAceite();
		
		try {
			tokenAceitePK.setCdVenda(tokenAceite.getCdVenda());
			String token = this.gerarToken(tokenAceite.getCdVenda().toString()+dataHoraAtual.toString().replace("=", ""));
			tokenAceitePK.setCdToken(token.replace("=", ""));		
			System.out.println(tokenAceitePK.getCdToken());
			tbodTokenAceite.setId(tokenAceitePK);
			tbodTokenAceite.setEmailEnvio(venda.getTbodEmpresa().getEmail());
			tbodTokenAceite.setDtEnvio(new Date());
			
			Calendar c = Calendar.getInstance();
			Long expiracao = Long.valueOf(EXPIRACAO_TOKEN_ACEITE_EMAIL);
		    c.add(Calendar.DAY_OF_MONTH, expiracao.intValue());
		    tbodTokenAceite.setDtExpiracao(c.getTime());
			
			tbodTokenAceite = tokenAceiteDAO.save(tbodTokenAceite);
			
			EmailAceite emailAceite = new EmailAceite();
			emailAceite.setNomeCorretor(venda.getTbodForcaVenda().getNome());
			emailAceite.setNomeCorretora(venda.getTbodForcaVenda().getTbodCorretora().getNome());
			emailAceite.setNomeEmpresa(venda.getTbodEmpresa().getRazaoSocial());
			emailAceite.setEmailEnvio(venda.getTbodEmpresa().getEmail());
			emailAceite.setToken(tokenAceitePK.getCdToken());
			
			List<Plano> planos = planoService.findPlanosByEmpresa(venda.getTbodEmpresa().getCdEmpresa());
										
			emailAceite.setPlanos(planos);
			
			SendMailAceite sendMailAceite = new SendMailAceite();
			sendMailAceite.sendMail(emailAceite);
			
		} catch (Exception e) {
			log.error(e);
			log.error("Erro ao cadastrar token de aceite :: Detalhe: [" + e.getMessage() + "]");
			return new TokenAceiteResponse(0, "Erro ao cadastrar token de aceite. Detalhe: [" + e.getMessage() + "]");
		}
		
		return new TokenAceiteResponse(200, HttpStatus.OK.toString());
	}

	@Override
	public TokenAceite buscarTokenAceitePorChave(String chave) {

		log.info("buscarTokenAceitePorChave");
		
		TokenAceite tokenAceite = new TokenAceite();		
		
		try {
			
			TbodTokenAceite tbTokenAceite = tokenAceiteDAO.findByIdCdToken(chave);
			
			if(tbTokenAceite != null) {
				tokenAceite.setToken(tbTokenAceite.getId().getCdToken());
				tokenAceite.setCdVenda(tbTokenAceite.getId().getCdVenda());
				tokenAceite.setEmail(tbTokenAceite.getEmailEnvio());
			}
			
		} catch (Exception e) {
			log.error(e);
			log.error("Erro ao buscar tokenAceite por chave. Detalhe: [" + e.getMessage() + "]");
		}
		
		return tokenAceite;
		
	}

	@Override
	public String gerarToken(String chave) {
		return GerarTokenUtils.gerarHashToken(chave);
	}

	@Override
	public TokenAceiteResponse updateTokenAceite(TokenAceite tokenAceite) {
		
		log.info("updateTokenAceite");
		
		try {
			
			TbodTokenAceite tbodTokenAceite = new TbodTokenAceite();
			tbodTokenAceite = tokenAceiteDAO.findTokenPorVendaToken(tokenAceite.getCdVenda(), tokenAceite.getToken(), tokenAceite.getEmail());
			
			if (tbodTokenAceite == null) {
				return new TokenAceiteResponse(404, HttpStatus.NOT_FOUND.toString());
			} 			
			
			//TODO: validaçoes
			
			tbodTokenAceite.setIp(tokenAceite.getIp());
			tbodTokenAceite.setDtAceite(new Date());
			
			tbodTokenAceite = tokenAceiteDAO.save(tbodTokenAceite);
			
			return new TokenAceiteResponse(200, HttpStatus.OK.toString());
			
		} catch (Exception e) {
			log.error(e);
			log.error("Erro ao confirmar data de aceite :: Detalhe: [" + e.getMessage() + "]");
			return new TokenAceiteResponse(0, "Erro ao confirmar data de aceite. Detalhe: [" + e.getMessage() + "]");
		}
	
	}
}

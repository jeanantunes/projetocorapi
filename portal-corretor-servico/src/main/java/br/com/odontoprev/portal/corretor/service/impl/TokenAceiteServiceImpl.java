package br.com.odontoprev.portal.corretor.service.impl;

import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.TokenAceiteDAO;
import br.com.odontoprev.portal.corretor.dto.TokenAceite;
import br.com.odontoprev.portal.corretor.dto.TokenAceiteResponse;
import br.com.odontoprev.portal.corretor.model.TbodTokenAceite;
import br.com.odontoprev.portal.corretor.model.TbodTokenAceitePK;
import br.com.odontoprev.portal.corretor.service.TokenAceiteService;
import br.com.odontoprev.portal.corretor.util.GerarTokenUtils;

@Service
public class TokenAceiteServiceImpl implements TokenAceiteService {	
	
	private static final Log log = LogFactory.getLog(ForcaVendaServiceImpl.class);
	
	@Autowired
	TokenAceiteDAO tokenAceiteDAO;
		
	@Override
	public TokenAceiteResponse addTokenAceite(TokenAceite tokenAceite) {

		log.info("[addTokenAceite]");
		
		LocalDateTime dataHoraAtual = LocalDateTime.now();
		
		TbodTokenAceitePK tokenAceitePK = new TbodTokenAceitePK();
		TbodTokenAceite tbodTokenAceite = new TbodTokenAceite();
		
		try {
			tokenAceitePK.setCdVenda(tokenAceite.getCdVenda());
			tokenAceitePK.setCdToken(this.gerarToken(tokenAceite.getCdVenda().toString()+dataHoraAtual.toString()));		
			
			tbodTokenAceite.setId(tokenAceitePK);
			tbodTokenAceite.setEmailEnvio(tokenAceite.getEmail());
			tbodTokenAceite.setDtEnvio(new Date());
			tbodTokenAceite.setDtExpiracao(new Date());
			
			tbodTokenAceite = tokenAceiteDAO.save(tbodTokenAceite);
			
		} catch (Exception e) {
			log.error(e);
			log.error("Erro ao cadastrar token de aceite :: Detalhe: [" + e.getMessage() + "]");
			return new TokenAceiteResponse(0, "Erro ao cadastrar token de aceite. Detalhe: [" + e.getMessage() + "]");
		}
		
		return new TokenAceiteResponse(200, "ok");
	}

	@Override
	public TokenAceite buscarTokenAceitePorChave(String chave) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String gerarToken(String chave) {
		return GerarTokenUtils.gerarHashToken(chave);
	}
}

package br.com.odontoprev.portal.corretor.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.business.SendMailAceite;
import br.com.odontoprev.portal.corretor.dao.StatusVendaDAO;
import br.com.odontoprev.portal.corretor.dao.TokenAceiteDAO;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.dto.ArquivoContratacao;
import br.com.odontoprev.portal.corretor.dto.EmailAceite;
import br.com.odontoprev.portal.corretor.dto.Plano;
import br.com.odontoprev.portal.corretor.dto.TokenAceite;
import br.com.odontoprev.portal.corretor.dto.TokenAceiteResponse;
import br.com.odontoprev.portal.corretor.model.TbodStatusVenda;
import br.com.odontoprev.portal.corretor.model.TbodTokenAceite;
import br.com.odontoprev.portal.corretor.model.TbodTokenAceitePK;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.service.ArquivoContratacaoService;
import br.com.odontoprev.portal.corretor.service.PlanoService;
import br.com.odontoprev.portal.corretor.service.TokenAceiteService;
import br.com.odontoprev.portal.corretor.service.VendaService;
import br.com.odontoprev.portal.corretor.util.Constantes;
import br.com.odontoprev.portal.corretor.util.DataUtil;
import br.com.odontoprev.portal.corretor.util.GerarTokenUtils;
import br.com.odontoprev.portal.corretor.util.XlsEmpresa;

@Service
@Transactional(rollbackFor = {Exception.class}) //201806281838 - esert - COR-348
public class TokenAceiteServiceImpl implements TokenAceiteService {

    private static final Log log = LogFactory.getLog(TokenAceiteServiceImpl.class);

    @Autowired
    TokenAceiteDAO tokenAceiteDAO;

    @Autowired
    VendaService vendaService;

    @Autowired
    PlanoService planoService;

    @Autowired
    SendMailAceite sendMailAceite; //201806282008 - esert - COR-348

    @Autowired
    XlsEmpresa xlsEmpresa; //20180 - jant - COR-

    @Autowired
    VendaDAO vendaDAO;

    @Autowired
    StatusVendaDAO statusVendaDAO;

    @Autowired
    DataUtil dataUtil;

    @Autowired
    ArquivoContratacaoService arquivoContratacaoService; //201808232000 - esert - COR-617 servico gerar pdf detalhe contratacao pme

    @Value("${EXPIRACAO_TOKEN_ACEITE_EMAIL}")
    private String EXPIRACAO_TOKEN_ACEITE_EMAIL;

    @Override
    @Transactional(rollbackFor = {Exception.class}) //201806281838 - esert - COR-348
    public TokenAceiteResponse addTokenAceite(TokenAceite tokenAceite) {

        //TODO: validaçoes

        log.info("[addTokenAceite - validacao venda existe]");

        TbodVenda venda = vendaService.buscarVendaPorCodigo(tokenAceite.getCdVenda());

        if (venda == null) {
            return new TokenAceiteResponse(HttpStatus.NO_CONTENT.value(), "Código de venda não encontrado [" + tokenAceite.getCdVenda() + "]"); //201805181925 - esert - COR-171 - evolucao msg erro
        }

        log.info("[addTokenAceite - insert]");

        LocalDateTime dataHoraAtual = LocalDateTime.now();

        TbodTokenAceitePK tokenAceitePK = new TbodTokenAceitePK();
        TbodTokenAceite tbodTokenAceite = new TbodTokenAceite();

        try {
            tokenAceitePK.setCdVenda(tokenAceite.getCdVenda());
            String token = this.gerarToken(tokenAceite.getCdVenda().toString() + dataHoraAtual.toString().replace("=", ""));
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
            emailAceite.setDataMovimentacao(DataUtil.dateToStringParse(venda.getDtMovimentacao()));
            emailAceite.setDataVigencia(DataUtil.dateToStringParse(venda.getDtVigencia()));

            List<Plano> planos = planoService.findPlanosByEmpresa(venda.getTbodEmpresa().getCdEmpresa());

            emailAceite.setPlanos(planos);
            
            //201808232000 - esert - COR-617 servico gerar pdf detalhe contratacao pme
            //ArquivoContratacao arquivoContratacao = arquivoContratacaoService.getByCdEmpresa(venda.getTbodEmpresa().getCdEmpresa(),true);
            ArquivoContratacao arquivoContratacao = arquivoContratacaoService.createPdfPmePorEmpresa(venda.getTbodEmpresa().getCdEmpresa()); //201809251858 - esert - COR-820 - BUG: precisa regerar o pdf apos save do (novo) email
            //nem preciso falar que ...
            if(arquivoContratacao!=null) {
            	if(
            		arquivoContratacao.getArquivoBase64()!=null 
            		&& 
            		!arquivoContratacao.getArquivoBase64().trim().isEmpty()
            	) {
            		//... o arquivo pdf deve ter sido gerado antes =]
            		//emailAceite.setArquivoBase64(arquivoContratacao.getArquivoBase64());
            		emailAceite.setArquivoContratacao(arquivoContratacao); //201808240105 - esert
            	} else {
            		log.info("addTokenAceite - arquivoContratacao.getArquivoBase64()== null || isEmpty() !");
            	}
            } else {
        		log.info("addTokenAceite - arquivoContratacao==null");            	
            }
            
            sendMailAceite.sendMail(emailAceite);

        } catch (Exception e) {
            log.error(e);
            log.error("Erro ao cadastrar token de aceite :: Detalhe: [" + e.getMessage() + "]");
            return new TokenAceiteResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro ao cadastrar token de aceite. Detalhe: [" + e.getMessage() + "]"); //201805181937 - esert - evolucao status erro
        }

        TokenAceiteResponse tokenAceiteResponse = new TokenAceiteResponse(HttpStatus.OK.value(), HttpStatus.OK.toString()); //201805181937 - esert - evolucao status erro
        tokenAceiteResponse.setCdVenda(tbodTokenAceite.getId().getCdVenda()); //201808082019
        tokenAceiteResponse.setCdToken(tbodTokenAceite.getId().getCdToken()); //201808082019
        return tokenAceiteResponse;
    }


    //TODO: Debito tecnico trocar response por ResponseEntity 201808031615
    @Override
    public TokenAceite buscarTokenAceitePorChave(String chave) {

        log.info("buscarTokenAceitePorChave");

        TokenAceite tokenAceite = new TokenAceite();

        try {

            TbodTokenAceite tbTokenAceite = tokenAceiteDAO.findByIdCdToken(chave);

            if (tbTokenAceite != null) {

                SimpleDateFormat sdfDDMMYYYY = new SimpleDateFormat("dd/MM/yyyy");


                if (tbTokenAceite.getDtAceite() != null) {

                    tokenAceite.setId(HttpStatus.ACCEPTED.value());
                    tokenAceite.setMensagem("Aceite ja realizado em " + sdfDDMMYYYY.format(tbTokenAceite.getDtAceite()) + ".");
                    tokenAceite.setDataAceite(sdfDDMMYYYY.format(tbTokenAceite.getDtAceite()));
                    return tokenAceite;//new TokenAceite(HttpStatus.ACCEPTED.value(), "Aceite ja realizado em " + sdfDDMMYYYY.format(tbTokenAceite.getDtAceite()) + ".");

                }

                Date dateHoje = new Date();
                if (tbTokenAceite.getDtExpiracao().getTime() < dateHoje.getTime()) {

                    tokenAceite.setId(HttpStatus.ACCEPTED.value());
                    tokenAceite.setMensagem("Token expirado em '" + sdfDDMMYYYY.format(tbTokenAceite.getDtExpiracao()) + "'.");
                    tokenAceite.setDataExpiracao(sdfDDMMYYYY.format(tbTokenAceite.getDtExpiracao()));
                    return tokenAceite;//new TokenAceite(HttpStatus.ACCEPTED.value(), "Token expirado em '" + sdfDDMMYYYY.format(tbTokenAceite.getDtExpiracao()) + "'.");

                }

                tokenAceite.setToken(tbTokenAceite.getId().getCdToken());
                tokenAceite.setCdVenda(tbTokenAceite.getId().getCdVenda());
                tokenAceite.setEmail(tbTokenAceite.getEmailEnvio());
                tokenAceite.setMensagem("Data de expiracao '" + sdfDDMMYYYY.format(tbTokenAceite.getDtExpiracao()) + "'.");
                tokenAceite.setId(HttpStatus.OK.value());
            }

        } catch (Exception e) {
            log.error(e);
            log.error("Erro ao buscar tokenAceite por chave. Detalhe: [" + e.getMessage() + "]");
        }

        return tokenAceite;

    }

    @Override
    @Transactional(rollbackFor = {Exception.class}) //201806281838 - esert - COR-348
    public String gerarToken(String chave) {
        return GerarTokenUtils.gerarHashToken(chave);
    }

    @Override
    public TokenAceiteResponse updateTokenAceite(TokenAceite tokenAceite) { // COR-5

        log.info("updateTokenAceite");

        try {
            if (tokenAceite == null) {
                log.error("tokenAceite == null");
                return new TokenAceiteResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString()); //201809141505 - esert - COR-734 - equalizar status todas vendas mesma pme empresa
            }
            if (tokenAceite.getCdVenda() == null) {
                log.error("tokenAceite.getCdVenda() == null");
                return new TokenAceiteResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString()); //201809141505 - esert - COR-734 - equalizar status todas vendas mesma pme empresa
            }
            if (tokenAceite.getToken() == null) {
            	log.error("tokenAceite.getToken() == null");
            	return new TokenAceiteResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString()); //201809141505 - esert - COR-734 - equalizar status todas vendas mesma pme empresa
            }
            if (tokenAceite.getEmail() == null) {
            	log.error("tokenAceite.getEmail() == null");
            	return new TokenAceiteResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString()); //201809141505 - esert - COR-734 - equalizar status todas vendas mesma pme empresa
            }

            TbodTokenAceite tbodTokenAceite = tokenAceiteDAO.findTokenPorVendaToken(
            		tokenAceite.getCdVenda(), 
            		tokenAceite.getToken(), 
            		tokenAceite.getEmail()
            		);

            if (tbodTokenAceite == null) {
            	log.error("tbodTokenAceite == null");
                return new TokenAceiteResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString()); //TODO: Trocar para HttpStatus.no_cotent 201808031156 (pesquisar impacto no front 201809141232 esert)
            }

            if (tbodTokenAceite.getDtAceite() != null) {
                SimpleDateFormat sdfDDMMYYYY = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String msg = "Aceite já realizado em [" + sdfDDMMYYYY.format(tbodTokenAceite.getDtAceite()) + "].";
            	log.info("tbodTokenAceite.getDtAceite() != null => [" + msg + "]");
                return new TokenAceiteResponse(HttpStatus.FORBIDDEN.value(), msg); //TODO: Trocar para HttpStatus.no_cotent 201808031156
            }

            Date dateHoje = new Date();

            if (tbodTokenAceite.getDtExpiracao().getTime() < dateHoje.getTime()) {
                SimpleDateFormat sdfDDMMYYYY = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String msg = "Token expirado em [" + sdfDDMMYYYY.format(tbodTokenAceite.getDtExpiracao()) + "].";
            	log.info("tbodTokenAceite.getDtExpiracao().getTime() < dateHoje.getTime() => [" + msg + "]");
                return new TokenAceiteResponse(HttpStatus.FORBIDDEN.value(), msg); //TODO: Trocar para HttpStatus.no_cotent 201808031156
            }

            List<TbodTokenAceite> listTbodTokenAceite = tokenAceiteDAO.findTokenPorVenda(tokenAceite.getCdVenda());

            if (listTbodTokenAceite != null) {

                for (TbodTokenAceite itemTbodTokenAceite : listTbodTokenAceite) {

                    itemTbodTokenAceite.setIp(tokenAceite.getIp());
                    itemTbodTokenAceite.setDtAceite(new Date());

                    tbodTokenAceite = tokenAceiteDAO.save(itemTbodTokenAceite);

                    TbodVenda tbodVendaAceite = vendaDAO.findOne(tokenAceite.getCdVenda());
                    
                    if(tbodVendaAceite.getTbodEmpresa()==null) {
                    	log.error("tbodVenda.getTbodEmpresa()==null para tokenAceite.getCdVenda():[" + tokenAceite.getCdVenda() + "]");
                    	continue; //vai para proximo token                    	
                    }
                    
                    if(tbodVendaAceite.getTbodEmpresa().getTbodVendas()==null) {
                    	log.error("tbodVendaAceite.getTbodEmpresa().getTbodVendas()==null para ");
                    	continue; //vai para proximo token                    	
                    }
                    
                    for(TbodVenda tbodVendaItem : tbodVendaAceite.getTbodEmpresa().getTbodVendas()) {
	
	                    tbodVendaItem.setDtVigencia(dataUtil.isEffectiveDate(
	                    		tbodVendaItem.getFaturaVencimento(), 
	                    		tbodTokenAceite.getDtAceite()
	                    		)
	                    	);
	
	                    Calendar calMovimentacao = new GregorianCalendar();
	                    calMovimentacao.setTime(tbodVendaItem.getDtVigencia());
	                    calMovimentacao.set(
	                            calMovimentacao.get(Calendar.YEAR),
	                            calMovimentacao.get(Calendar.MONTH),
	                            calMovimentacao.get(Calendar.DAY_OF_MONTH),
	                            0,
	                            0,
	                            0
	                    );
	                    calMovimentacao.add(Calendar.DATE, -11);
	                    tbodVendaItem.setDtMovimentacao(calMovimentacao.getTime());
	                    
	                    TbodStatusVenda tbodStatusVenda = statusVendaDAO.findOne(Constantes.STATUS_VENDA_ENVIADO);
	                    if (tbodStatusVenda == null){
	                        throw new Exception("Status Venda não encontrado " + "[" + Constantes.STATUS_VENDA_ENVIADO + "]");
	                    }
	                    tbodVendaItem.setTbodStatusVenda(tbodStatusVenda);
	                    
	                    tbodVendaItem.setDtAceite(tbodTokenAceite.getDtAceite());
	                    
	                    tbodVendaItem = vendaDAO.save(tbodVendaItem);
                    
                    } //for(TbodVenda tbodVenda : tbodVenda.getTbodEmpresa().getTbodVendas())

                    xlsEmpresa.GerarEmpresaXLS(tbodVendaAceite);
                    
                    //201808281701 - esert - COR-656 atualizar pdf pme no click aceite.
                    ArquivoContratacao arquivoContratacao = arquivoContratacaoService.createPdfPmePorEmpresa(tbodVendaAceite.getTbodEmpresa().getCdEmpresa()); //201808281701 - esert - COR-656 atualizar pdf pme no click aceite.
                    if(arquivoContratacao==null) {
                    	log.info("arquivoContratacao==null para tbodVenda.getTbodEmpresa().getCdEmpresa():[" + tbodVendaAceite.getTbodEmpresa().getCdEmpresa() + "]");
                    	throw new Exception("Erro atualização pdf pme detalhes contratação para CdEmpresa:[" + tbodVendaAceite.getTbodEmpresa().getCdEmpresa() + "]");
                    }
                    
                }

            } else {
            	log.info("listTbodTokenAceite == null");
                return new TokenAceiteResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString()); //TODO: Trocar para HttpStatus.no_cotent 201808031156
            }

            return new TokenAceiteResponse(HttpStatus.OK.value(), HttpStatus.OK.toString());

        } catch (Exception e) {
            log.error(e);
            log.error("Erro ao confirmar data de aceite :: Detalhe: [" + e.getMessage() + "]");
            return new TokenAceiteResponse(0, "Erro ao confirmar data de aceite. Detalhe: [" + e.getMessage() + "]");
        }
        
    }
    
}

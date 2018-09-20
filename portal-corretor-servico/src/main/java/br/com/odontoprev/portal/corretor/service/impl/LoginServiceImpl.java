package br.com.odontoprev.portal.corretor.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.bytecode.buildtime.spi.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.odontoprev.portal.corretor.dao.ContratoCorretoraDAO;
import br.com.odontoprev.portal.corretor.dao.LoginDAO;
import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.dto.Login;
import br.com.odontoprev.portal.corretor.dto.LoginResponse;
import br.com.odontoprev.portal.corretor.dto.LoginRetorno;
import br.com.odontoprev.portal.corretor.exceptions.ApiTokenException;
import br.com.odontoprev.portal.corretor.model.TbodContratoCorretora;
import br.com.odontoprev.portal.corretor.model.TbodLogin;
import br.com.odontoprev.portal.corretor.service.BloqueioService;
import br.com.odontoprev.portal.corretor.service.CorretoraService;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;
import br.com.odontoprev.portal.corretor.service.LoginService;
import br.com.odontoprev.portal.corretor.util.Constantes;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Log log = LogFactory.getLog(LoginServiceImpl.class);

    @Autowired
    private LoginDAO loginDAO;
    
    @Autowired
    private ForcaVendaService forcaVendaService;
    
    @Autowired
    private CorretoraService corretoraService;
    
    @Autowired
    private ContratoCorretoraDAO contratoCorretoraDAO;
    
    @Autowired
    private ApiManagerTokenServiceImpl apiManagerTokenService;
    //201809201646 - esert - aqui so funciona com ApiManagerTokenServiceImpl
    //201809201646 - esert - Field apiManagerTokenService in br.com.odontoprev.portal.corretor.service.impl.LoginServiceImpl required a bean of type 'br.com.odontoprev.portal.corretor.service.ApiManagerTokenService' that could not be found.

    @Value("${DCSS_URL}")
    private String dcssUrl;

    private final LoginResponse responseNotFound = new LoginResponse();

    @Autowired
	private BloqueioService bloqueioService; //201809181600 - esert - COR-730 : Novo servico (processar bloqueio)

    @Override
    public LoginResponse login(Login login) {

        log.info("[login]");

        String perfil = "Corretora";
        if (login.getUsuario() == null && login.getUsuario().isEmpty()) {
            return null;
        }

        if (login.getUsuario().length() == 11) {
            try {
	            perfil = "Corretor";
	            
	            if(!bloqueioService.doBloqueioForcaVenda(login.getUsuario())) { //201809181600 - esert - COR-730 - protecao
	        		throw new Exception("ERRO doBloqueioForcaVenda(login.getUsuario(" + login.getUsuario() + "))"); //201809201050 - esert - COR-730 : Novo servico (processar bloqueio)
	        	}
	
	            final ForcaVenda forcaVenda = forcaVendaService.findForcaVendaByCpf(login.getUsuario());
	
	            if (forcaVenda == null) {
	                return responseNotFound;
	            }
	
	            final RestTemplate restTemplate = new RestTemplate();
	            final Map<String, String> loginMap = new HashMap<>();
	            loginMap.put("login", login.getUsuario());
	            loginMap.put("senha", login.getSenha());

                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", "Bearer " + apiManagerTokenService.getToken());

                HttpEntity<?> request = new HttpEntity<Map<String, String>>(loginMap, headers);

                final ResponseEntity<LoginRetorno> loginRetorno = restTemplate
                        .postForEntity(
                                (dcssUrl + "/login/1.0/"),
                                //loginMap,
                                request,
                                LoginRetorno.class
                        );
                
                //201809201027 - esert - COR-730 - desligar resposta dcss fake p teste unitario
                //final ResponseEntity<LoginRetorno> loginRetorno = ResponseEntity.ok(new LoginRetorno("nome fake teste", "doc fake teste", 999)); 

                if (loginRetorno != null && loginRetorno.getBody().getCodigo() == 0) {
                    return null;
                }

                LoginResponse loginResponse = new LoginResponse(
                        loginRetorno.getBody().getCodigo(),  //codigoDcss
                        forcaVenda.getCdForcaVenda(),         //codigoUsuario
                        loginRetorno.getBody().getNomeUsuario(),
                        loginRetorno.getBody().getDocumento(),
                        forcaVenda.getCorretora().getCdCorretora(),
                        forcaVenda.getCorretora().getRazaoSocial(),
                        perfil,
                        forcaVenda.getLogin().getTemBloqueio(),
                        forcaVenda.getLogin().getCodigoTipoBloqueio(),
                        forcaVenda.getLogin().getDescricaoTipoBloqueio());

                log.info("loginResponse:[" + loginResponse + "]");
                return loginResponse;

            } catch (final ApiTokenException e) {
                //throw e;
                log.error("ApiTokenException:[" + e.getMessage() + "]");
                return null;

            } catch (final Exception e) {
                //throw e;
                log.error("Exception:[" + e.getMessage() + "]");
                return null;
            }
        } else {
            try {

                if(!bloqueioService.doBloqueioCorretora(login.getUsuario())) { //201809181600 - esert - COR-730 : protecao
                	throw new Exception("ERRO doBloqueioCorretora(login.getUsuario(" + login.getUsuario() + "))"); //201809201050 - esert - COR-730 : Novo servico (processar bloqueio)
                }

            	final TbodLogin loginCorretora = loginDAO
                        .findByTbodCorretoras(login.getUsuario());
                if (loginCorretora != null && login.getSenha() != null
                        && loginCorretora.getSenha().equals(login.getSenha())) {

                    final Corretora corretora = corretoraService
                            .buscaCorretoraPorCnpj(login.getUsuario());

                    if (corretora.getCdCorretora() == 0) {
                        throw new ExecutionException(
                                "Dono de corretora sem corretora atrelada.");
                    }

                    String dtAceiteContrato = null;
                    List<TbodContratoCorretora> listTbodContratoCorretora = 
                    		//contratoCorretoraDAO.findByTbodCorretoraCdCorretora(corretora.getCdCorretora());
                    		contratoCorretoraDAO.findByTbodCorretoraCdCorretoraAndTbodContratoModeloCdContratoModeloOrTbodContratoModeloCdContratoModelo(
                    				corretora.getCdCorretora(), 
                    				Constantes.CONTRATO_CORRETAGEM_V1, 
                    				Constantes.CONTRATO_INTERMEDIACAO_V1);
                    //System.out.println(cdCorretoraContrato);
                    if (listTbodContratoCorretora != null && listTbodContratoCorretora.size()>0) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        dtAceiteContrato = sdf.format(listTbodContratoCorretora.get(0).getDtAceiteContrato());
                    }

                    return new LoginResponse(
                            loginCorretora.getCdLogin(),
                            corretora.getRazaoSocial(),
                            login.getUsuario(),
                            corretora.getCdCorretora(),
                            corretora.getRazaoSocial(),
                            perfil,
                            dtAceiteContrato,
                            corretora.getLogin().getTemBloqueio(),
                            corretora.getLogin().getCodigoTipoBloqueio(),
                            corretora.getLogin().getDescricaoTipoBloqueio());
                } else {
                    return responseNotFound;
                }
            } catch (final Exception e) {
                log.error("Exception:[" + e.getMessage() + "]");
                return null;
            }
        }
    }
}

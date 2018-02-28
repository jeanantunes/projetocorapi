package br.com.odontoprev.portal.corretor.service.impl;

import br.com.odontoprev.portal.corretor.dao.LoginDAO;
import br.com.odontoprev.portal.corretor.dto.*;
import br.com.odontoprev.portal.corretor.model.TbodLogin;
import br.com.odontoprev.portal.corretor.service.LoginService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.bytecode.buildtime.spi.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Log log = LogFactory.getLog(LoginServiceImpl.class);

    @Autowired
    LoginDAO loginDAO;

    @Value("${DCSS_URL}")
    private String dcssUrl;

    private LoginResponse responseNotFound = new LoginResponse();

    @Override
    public LoginResponse login(Login login) {

        String perfil = "Corretora";
        if (login.getUsuario() != null && !login.getUsuario().isEmpty()) {
            return null;
        }

        if (login.getUsuario().length() == 11) {
            perfil = "Corretor";
            ForcaVendaServiceImpl serviceFV = new ForcaVendaServiceImpl();
            ForcaVenda forcaVenda = serviceFV.findForcaVendaByCpf(login.getUsuario());

            if (forcaVenda == null) {
                return responseNotFound;
            }

            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> loginMap = new HashMap<>();
            loginMap.put("login", login.getUsuario());
            loginMap.put("senha", login.getSenha());
            try {

                ResponseEntity<LoginRetorno> loginRetorno = restTemplate.postForEntity((dcssUrl + "/login/1.0/?"), loginMap, LoginRetorno.class);

                return new LoginResponse(loginRetorno.getBody().getCodigoUsuario(),
                        loginRetorno.getBody().getNomeUsuario(),
                        loginRetorno.getBody().getDocumento(),
                        forcaVenda.getCorretora().getCdCorretora(),
                        forcaVenda.getCorretora().getRazaoSocial(),
                        perfil);

            } catch (Exception e) {
                throw e;
            }
        } else {
            TbodLogin loginCorretora = loginDAO.findByTbodCorretoras(login.getUsuario());
            if (loginCorretora != null && login.getSenha() != null && loginCorretora.getSenha().equals(login.getSenha())) {

                CorretoraServiceImpl serviceCorretora = new CorretoraServiceImpl();
                Corretora corretora = serviceCorretora.buscaCorretoraPorCnpj(login.getUsuario());

                if (corretora.getCdCorretora() == 0) {
                    throw new ExecutionException("Dono de corretora sem corretora atrelada.");
                }

                return new LoginResponse(loginCorretora.getCdLogin(),
                        corretora.getRazaoSocial(),
                        login.getUsuario(),
                        corretora.getCdCorretora(),
                        corretora.getRazaoSocial(),
                        perfil);
            } else {
                return responseNotFound;
            }
        }
    }
}

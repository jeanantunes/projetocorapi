package br.com.odontoprev.portal.corretor.service.impl;

import br.com.odontoprev.portal.corretor.dao.LoginDAO;
import br.com.odontoprev.portal.corretor.dto.Login;
import br.com.odontoprev.portal.corretor.dto.LoginResponse;
import br.com.odontoprev.portal.corretor.dto.LoginRetorno;
import br.com.odontoprev.portal.corretor.model.TbodLogin;
import br.com.odontoprev.portal.corretor.service.LoginService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Override
    public LoginResponse login(Login login) {

        if (login.getUsuario() != null && login.getSenha() != null) {
            if (login.getUsuario().length() == 11) {
                RestTemplate restTemplate = new RestTemplate();

                Map<String, String> loginMap = new HashMap<>();
                loginMap.put("login", login.getUsuario());
                loginMap.put("senha", login.getSenha());
                try {

                    ResponseEntity<LoginRetorno> loginRetorno = restTemplate.postForEntity(("https://api-it1.odontoprev.com.br:8243/dcss/login/1.0/?"), loginMap, LoginRetorno.class);
                    return new LoginResponse(login.getUsuario(), true, Long.parseLong(loginRetorno.getBody().getCodigo()));
                } catch (Exception e) {
                    return new LoginResponse(null, false, null);
                }
            } else {
                TbodLogin corretora = loginDAO.findByTbodCorretora(login.getUsuario());
                if (corretora != null && login.getSenha() != null && corretora.getSenha().equals(login.getSenha())) {
                    return new LoginResponse(login.getUsuario(), true, corretora.getCdLogin());
                } else {
                    return new LoginResponse(null, false, null);
                }
            }
        } else {
            return new LoginResponse(null, false, null);
        }


    }
}

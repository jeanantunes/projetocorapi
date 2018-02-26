package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.Login;
import br.com.odontoprev.portal.corretor.dto.LoginResponse;

public interface LoginService {

    LoginResponse login(Login login);

}



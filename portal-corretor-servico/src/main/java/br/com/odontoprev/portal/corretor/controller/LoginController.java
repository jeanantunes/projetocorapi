package br.com.odontoprev.portal.corretor.controller;

import br.com.odontoprev.portal.corretor.dto.Login;
import br.com.odontoprev.portal.corretor.dto.LoginResponse;
import br.com.odontoprev.portal.corretor.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid Login login) {
        LoginResponse res = loginService.login(login);

        return res.getSucesso() ? ResponseEntity.ok(res) : ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
    }


}

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

        try {

            if (login.getUsuario() == null || login.getUsuario() == "" ||
                    login.getSenha() == null || login.getSenha() == "") {
                return ResponseEntity.badRequest().build();
            }

            LoginResponse res = loginService.login(login);

            if (res != null && res.getCodigoUsuario() == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (res == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return ResponseEntity.ok(res);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
}

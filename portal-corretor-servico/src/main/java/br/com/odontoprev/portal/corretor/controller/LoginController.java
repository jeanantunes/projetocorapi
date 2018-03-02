package br.com.odontoprev.portal.corretor.controller;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.Login;
import br.com.odontoprev.portal.corretor.dto.LoginResponse;
import br.com.odontoprev.portal.corretor.service.LoginService;

@RestController
public class LoginController {
	private static final Log log = LogFactory.getLog(LoginController.class);


	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/login", method = {RequestMethod.POST})
	public ResponseEntity<LoginResponse> login(@RequestBody @Valid Login login) {
		log.info(login);
		try {

			if (login.getUsuario() == null || login.getUsuario() == "" ||
					login.getSenha() == null || login.getSenha() == "") {
				return ResponseEntity.badRequest().build();
			}

			final LoginResponse res = loginService.login(login);

			if (res != null && res.getCodigoUsuario() == 0) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			if (res == null) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}

			return ResponseEntity.ok(res);

		} catch (final Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}
}

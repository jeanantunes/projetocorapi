package br.com.odontoprev.portal.corretor.service.impl;

import java.util.HashMap;
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

import br.com.odontoprev.portal.corretor.dao.LoginDAO;
import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.dto.Login;
import br.com.odontoprev.portal.corretor.dto.LoginResponse;
import br.com.odontoprev.portal.corretor.dto.LoginRetorno;
import br.com.odontoprev.portal.corretor.exceptions.ApiTokenException;
import br.com.odontoprev.portal.corretor.model.TbodLogin;
import br.com.odontoprev.portal.corretor.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	private static final Log log = LogFactory.getLog(LoginServiceImpl.class);

	@Autowired
	private LoginDAO loginDAO;
	@Autowired
	private ForcaVendaServiceImpl serviceFV;
	@Autowired
	private CorretoraServiceImpl serviceCorretora;
	@Autowired
	private ApiManagerTokenServiceImpl apiManagerTokenService;

	@Value("${DCSS_URL}")
	private String dcssUrl;

	private final LoginResponse responseNotFound = new LoginResponse();

	@Override
	public LoginResponse login(Login login) {

		log.info("[login]");

		String perfil = "Corretora";
		if (login.getUsuario() == null && login.getUsuario().isEmpty()) {
			return null;
		}

		if (login.getUsuario().length() == 11) {
			perfil = "Corretor";
			final ForcaVenda forcaVenda = serviceFV.findForcaVendaByCpf(login.getUsuario());

			if (forcaVenda == null) {
				return responseNotFound;
			}

			final RestTemplate restTemplate = new RestTemplate();
			final Map<String, String> loginMap = new HashMap<>();
			loginMap.put("login", login.getUsuario());
			loginMap.put("senha", login.getSenha());
			try {

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

				LoginResponse loginResponse = new LoginResponse(
						loginRetorno.getBody().getCodigo(),  //codigoDcss
						forcaVenda.getCdForcaVenda(),		 //codigoUsuario
						loginRetorno.getBody().getNomeUsuario(),
						loginRetorno.getBody().getDocumento(),
						forcaVenda.getCorretora().getCdCorretora(),
						forcaVenda.getCorretora().getRazaoSocial(), perfil);
				
				log.info("loginResponse:["+ loginResponse +"]");
				return loginResponse;

			} catch (final ApiTokenException e) {
				//throw e;
				log.error("ApiTokenException:["+ e.getMessage() +"]");
				return null;

			} catch (final Exception e) {
				//throw e;
				log.error("Exception:["+ e.getMessage() +"]");
				return null;
			}
		} else {
			final TbodLogin loginCorretora = loginDAO
					.findByTbodCorretoras(login.getUsuario());
			if (loginCorretora != null && login.getSenha() != null
					&& loginCorretora.getSenha().equals(login.getSenha())) {

				final Corretora corretora = serviceCorretora
						.buscaCorretoraPorCnpj(login.getUsuario());

				if (corretora.getCdCorretora() == 0) {
					throw new ExecutionException(
							"Dono de corretora sem corretora atrelada.");
				}

				return new LoginResponse(
						loginCorretora.getCdLogin(),
						corretora.getRazaoSocial(), login.getUsuario(),
						corretora.getCdCorretora(), corretora.getRazaoSocial(),
						perfil);
			} else {
				return responseNotFound;
			}
		}
	}
}

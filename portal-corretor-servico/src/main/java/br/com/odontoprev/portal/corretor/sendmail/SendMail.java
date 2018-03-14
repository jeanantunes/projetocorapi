package br.com.odontoprev.portal.corretor.sendmail;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.odontoprev.api.manager.client.token.ApiManagerToken;
import br.com.odontoprev.api.manager.client.token.ApiManagerTokenFactory;
import br.com.odontoprev.api.manager.client.token.ApiToken;
import br.com.odontoprev.api.manager.client.token.enumerator.ApiManagerTokenEnum;
import br.com.odontoprev.api.manager.client.token.util.ConfigurationUtils;
import br.com.odontoprev.portal.corretor.serviceEmail.ApiException;
import br.com.odontoprev.portal.corretor.serviceEmail.api.DefaultApi;
import br.com.odontoprev.portal.corretor.serviceEmail.model.RequestEmail;


@Component
public class SendMail {
	
	private static final Log log = LogFactory.getLog(SendMail.class);

	@Value("${SENDMAIL_ENDPOINT_URL}")
	private String SENDMAIL_ENDPOINT_URL;
	
	public void sendMail() {
		
		try {
			
			ApiManagerToken apiManager = ApiManagerTokenFactory.create(ApiManagerTokenEnum.WSO2, "PORTAL_CORRETOR_SERVICO");
			ApiToken apiToken = apiManager.generateToken();
			
			DefaultApi apiInstance = new DefaultApi();
			//String SENDMAIL_ENDPOINT_URL = "sendemail/1.0";
			apiInstance.getApiClient().setBasePath(ConfigurationUtils.getURLGetToken().replaceAll("/token","/"+ SENDMAIL_ENDPOINT_URL ));
			apiInstance.getApiClient().setAccessToken(apiToken.getAccessToken());
			apiInstance.getApiClient().addDefaultHeader("Authorization", "Bearer "+apiToken.getAccessToken());
			//String ab = "Authorization: Bearer "+apiToken.getAccessToken() //exemplo roberto
			RequestEmail body = new RequestEmail(); // RequestEmail
			String msg = "TESTE";
//			String msg = faleConoscoForcaVendasModel.getNomeCorretor() + ";";
//			msg += faleConoscoForcaVendasModel.getNomeCorretora() + ";";
//			msg += faleConoscoForcaVendasModel.getTipoMensagem() + ";";
//			msg += faleConoscoForcaVendasModel.getMensagem() + ".";
			body.setBody("Email Aceite: "+ msg );
			body.setRecepientName("Prezados");
			body.setRecepients(Arrays.asList(new String [] {"jalves@vectoritcgroup.com","jaqueline.alves@outlook.com"}));
			body.setSender("arquitetura.ti@odontoprev.com.br");
			body.setSenderName("Arquitetura");
			body.setType("text/html");
			body.setSubject("ASSUNTO2");

			apiInstance.sendEmail(body);
		} catch (ApiException e) {
			log.error(e.getResponseBody());
//			System.out.println(e.getResponseBody());
			log.error("Exception when calling DefaultApi#sendEmail");
//			System.out.println("Exception when calling DefaultApi#sendEmail");
			e.printStackTrace();
		} catch(Exception ex) {
			log.error(ex.getLocalizedMessage());
//			System.out.println(ex.getLocalizedMessage());
		}
	}
}

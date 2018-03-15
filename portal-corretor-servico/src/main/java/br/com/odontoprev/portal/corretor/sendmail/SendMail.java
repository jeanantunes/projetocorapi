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
import br.com.odontoprev.portal.corretor.dto.EmailAceite;
import br.com.odontoprev.portal.corretor.serviceEmail.ApiException;
import br.com.odontoprev.portal.corretor.serviceEmail.api.DefaultApi;
import br.com.odontoprev.portal.corretor.serviceEmail.model.RequestEmail;
import br.com.odontoprev.portal.corretor.util.FileReaderUtil;


@Component
public class SendMail {
	
	private static final Log log = LogFactory.getLog(SendMail.class);

	@Value("${SENDMAIL_ENDPOINT_URL}")
	private String SENDMAIL_ENDPOINT_URL;
	
	public void sendMail(EmailAceite email) {
		
		try {
			
			ApiManagerToken apiManager = ApiManagerTokenFactory.create(ApiManagerTokenEnum.WSO2, "PORTAL_CORRETOR_SERVICO");
			ApiToken apiToken = apiManager.generateToken();
			
			DefaultApi apiInstance = new DefaultApi();
			apiInstance.getApiClient().setBasePath(ConfigurationUtils.getURLGetToken().replaceAll("/token","/"+ SENDMAIL_ENDPOINT_URL ));
			apiInstance.getApiClient().setAccessToken(apiToken.getAccessToken());
			apiInstance.getApiClient().addDefaultHeader("Authorization", "Bearer "+apiToken.getAccessToken());
			RequestEmail body = new RequestEmail(); // RequestEmail
			
			String msg = this.montarBodyMsg(email);
			
			body.setBody(msg);
//			body.setRecepientName("Prezados");
			//TODO
			body.setRecepients(Arrays.asList(new String [] {"jalves@vectoritcgroup.com","jaqueline.alves@outlook.com"}));
			body.setSender("arquitetura.ti@odontoprev.com.br"); //TODO
			body.setSenderName("Arquitetura"); //TODO
			body.setType("text/html");
			body.setSubject("ASSUNTO2"); //TODO

			apiInstance.sendEmail(body);
		} catch (ApiException e) {
			log.error(e.getResponseBody());
			log.error("Exception when calling DefaultApi#sendEmail");
			e.printStackTrace();
		} catch(Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
	}

	private String montarBodyMsg(EmailAceite email) {
		
		FileReaderUtil fileReader = new FileReaderUtil();
		String htmlStr = fileReader.readHtmlFile();
		
		htmlStr.replace("@NOMEDOCORRETOR", email.getNomeCorretor());
		htmlStr.replace("@CORRETORA", email.getNomeCorretora());
		htmlStr.replace("@EMPRESA", email.getNomeEmpresa());
		
		return htmlStr;
		
	}
}

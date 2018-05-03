package br.com.odontoprev.portal.corretor.business;

import java.util.Arrays;

import javax.annotation.ManagedBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.odontoprev.api.manager.client.token.ApiManagerToken;
import br.com.odontoprev.api.manager.client.token.ApiManagerTokenFactory;
import br.com.odontoprev.api.manager.client.token.ApiToken;
import br.com.odontoprev.api.manager.client.token.enumerator.ApiManagerTokenEnum;
import br.com.odontoprev.api.manager.client.token.util.ConfigurationUtils;
import br.com.odontoprev.portal.corretor.serviceEmail.ApiException;
import br.com.odontoprev.portal.corretor.serviceEmail.api.DefaultApi;
import br.com.odontoprev.portal.corretor.serviceEmail.model.RequestEmail;
import br.com.odontoprev.portal.corretor.util.FileReaderUtil;
import br.com.odontoprev.portal.corretor.util.PropertiesUtils;


@ManagedBean
public class SendMailForcaStatus {
	
	private static final Log log = LogFactory.getLog(SendMailForcaStatus.class);
	
	public void sendMail(String email, String nomeCorretora, String opcaoStatus) {
		
		log.info("sendMail");
		
		try {
			
			String sendMailEndpointUrl = PropertiesUtils.getProperty(PropertiesUtils.SENDMAIL_ENDPOINT_URL);
			String recepientName = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_RECEPIENTNAME_FORCASTATUS);
			String sender = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SENDER_FORCASTATUS);			
			String senderName = (opcaoStatus == "REPROVAR" ? PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SENDERNAME_FORCASTATUS_REPROVADO) : PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SENDERNAME_FORCASTATUS_APROVADO));			
			String type = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_TYPE_FORCASTATUS);			
			String subject = (opcaoStatus == "REPROVAR" ? PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SUBJECT_FORCASTATUS_REPROVADO) : PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SUBJECT_FORCASTATUS_APROVADO));
			
			ApiManagerToken apiManager = ApiManagerTokenFactory.create(ApiManagerTokenEnum.WSO2, "PORTAL_CORRETOR_SERVICO");
			ApiToken apiToken = apiManager.generateToken();
						
			DefaultApi apiInstance = new DefaultApi();
			apiInstance.getApiClient().setBasePath(ConfigurationUtils.getURLGetToken().replaceAll("/token","/"+ sendMailEndpointUrl));				
			apiInstance.getApiClient().setAccessToken(apiToken.getAccessToken());		
			apiInstance.getApiClient().addDefaultHeader("Authorization", "Bearer "+apiToken.getAccessToken());			
			
			RequestEmail body = new RequestEmail(); 
			
			String msg = this.montarBodyMsg(nomeCorretora, opcaoStatus);
			
			body.setBody(msg);
			body.setRecepientName(recepientName);			
			body.setRecepients(Arrays.asList(new String [] {email}));
			body.setSender(sender);
			body.setSenderName(senderName);
			body.setType(type);
			body.setSubject(subject);

			apiInstance.sendEmail(body);
		} catch (ApiException e) {
			log.error(e.getResponseBody());
			log.error("Exception when calling DefaultApi#sendEmail");
			e.printStackTrace();
		} catch(Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
	}

	private String montarBodyMsg(String nomeCorretora, String opcaoStatus) {
		
		log.info("montarBodyMsg");
		
		String htmlStr = "";
		
		try {
			
			FileReaderUtil fileReader = new FileReaderUtil();
			
			if (opcaoStatus == "REPROVAR") {
				htmlStr = fileReader.readHtmlForcaStatus("REPROVAR");
			} else {
				htmlStr = fileReader.readHtmlForcaStatus("APROVAR");
			}
			
			
			if(htmlStr == null || "".equals(htmlStr)) {
				throw new Exception(" Html força status vazio!");
			}
			else {			
				
				String imgEmailBase = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_BASE);
				String imgEmailHeader = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_HEADER_FORCA_STATUS);
				String imgEmailFb = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_FB);
				String imgEmailLb = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_LD);
				String imgEmailYt = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_YT);
				String imgEmailAsn = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_ASN);
							
				htmlStr = htmlStr.replace("@HEADER", imgEmailBase + imgEmailHeader)
						.replace("@FB", imgEmailBase + imgEmailFb)
						.replace("@LD", imgEmailBase + imgEmailLb)
						.replace("@YT", imgEmailBase + imgEmailYt)
						.replace("@ANS", imgEmailBase + imgEmailAsn)
						.replace("@CORRETORA", nomeCorretora);
				}
			
		} catch (Exception e) {
			log.error("Erro ao ler arquivo email status! Detalhe: [" + e.getMessage() + "]");
			return "";
		}
		
		return htmlStr;
		
	}
}

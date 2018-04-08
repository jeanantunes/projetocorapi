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
import br.com.odontoprev.portal.corretor.dto.EmailAceite;
import br.com.odontoprev.portal.corretor.dto.Plano;
import br.com.odontoprev.portal.corretor.serviceEmail.ApiException;
import br.com.odontoprev.portal.corretor.serviceEmail.api.DefaultApi;
import br.com.odontoprev.portal.corretor.serviceEmail.model.RequestEmail;
import br.com.odontoprev.portal.corretor.util.FileReaderUtil;
import br.com.odontoprev.portal.corretor.util.PropertiesUtils;


@ManagedBean
public class SendMailEsqueciSenha {
	
	private static final Log log = LogFactory.getLog(SendMailEsqueciSenha.class);

//	@Value("${SENDMAIL_ENDPOINT_URL}")
//	private String SENDMAIL_ENDPOINT_URL;
//	
//	@Value("${requestmail.body.recepientname}")
//	private String REQUESTMAIL_RECEPIENTNAME;
//	
//	@Value("${requestmail.body.sender}")
//	private String REQUESTMAIL_SENDER;
//	
//	@Value("${requestmail.body.sendername}")
//	private String REQUESTMAIL_SENDERNAME;
//	
//	@Value("${requestmail.body.type}")
//	private String REQUESTMAIL_TYPE;
//	
//	@Value("${requestmail.body.subject}")
//	private String REQUESTMAIL_SUBJECT;
//	
//	@Value("${img.email.base}")
//	private String IMG_EMAIL_BASE;
//	
//	@Value("${img.email.header}")
//	private String IMG_EMAIL_HEADER;
//	
//	@Value("${img.email.btn}")
//	private String IMG_EMAIL_BTN;
//	
//	@Value("${img.email.ans}")
//	private String IMG_EMAIL_ASN;
//	
//	@Value("${img.email.fb}")
//	private String IMG_EMAIL_FB;
//	
//	@Value("${img.email.ld}")
//	private String IMG_EMAIL_LD;
//	
//	@Value("${img.email.yt}")
//	private String IMG_EMAIL_YT;
//	
//	@Value("${api.portalcorretor.token}")
//	private String API_CORRETOR_TOKEN;
	
	
	public void sendMail() {
		
		log.info("sendMail");
		
		try {
			
			String sendMailEndpointUrl = "sendemail/1.0/send"; //PropertiesUtils.getProperty(PropertiesUtils.SENDMAIL_ENDPOINT_URL);
			String recepientName = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_RECEPIENTNAME);
			String sender = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SENDER);
			String senderName = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SENDERNAME);
			String type = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_TYPE);
			String subject = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SUBJECT);
			
			ApiManagerToken apiManager = ApiManagerTokenFactory.create(ApiManagerTokenEnum.WSO2, "PORTAL_CORRETOR_SERVICO");
			ApiToken apiToken = apiManager.generateToken();
			
			//String teste = "d59c1a8e-6a55-3971-88b3-2f4056a3f3f2";
			
			DefaultApi apiInstance = new DefaultApi();
			//apiInstance.getApiClient().setBasePath(ConfigurationUtils.getURLGetToken().replaceAll("/token","/"+ sendMailEndpointUrl));
			String teste = "https://api.odontoprev.com.br:8243/sendemail/1.0/send";
			apiInstance.getApiClient().setBasePath(ConfigurationUtils.getURLGetToken().replaceAll("/tokenEsqueciSenha","/"+ teste));
			apiInstance.getApiClient().setAccessToken(apiToken.getAccessToken());
			//apiInstance.getApiClient().setAccessToken(teste);
			apiInstance.getApiClient().addDefaultHeader("Authorization", "Bearer "+apiToken.getAccessToken());
			//apiInstance.getApiClient().addDefaultHeader("Authorization", "Bearer "+teste);
			RequestEmail body = new RequestEmail(); // RequestEmail
			String msg = this.montarBodyMsg();
			
			body.setBody(msg);
			body.setRecepientName(recepientName);			
			//body.setRecepients(Arrays.asList(new String [] {email.getEmailEnvio()}));
			body.setRecepients(Arrays.asList(new String [] {"izaura.fsilva@gmail.com"}));
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

	private String montarBodyMsg() {
		
		log.info("montarBodyMsg");
		
		String htmlStr = "";
		
		try {
			
			FileReaderUtil fileReader = new FileReaderUtil();
			htmlStr = fileReader.readHtmlFile();
			
			if(htmlStr == null || "".equals(htmlStr)) {
				throw new Exception(" Html aceite email esqueci minha senha vazio!");
			}
			else {			
				
				String imgEmailBase = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_BASE);
				String imgEmailHeader = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_HEADER);
				String imgEmailFb = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_FB);
				String imgEmailLb = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_LD);
				String imgEmailYt = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_YT);
				String imgEmailAsn = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_ASN);
				String imgEmailBtn = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_BTN);
				String apiCorretorToken = PropertiesUtils.getProperty(PropertiesUtils.API_CORRETOR_TOKEN);
				
				htmlStr = htmlStr.replace("@HEADER", imgEmailBase + imgEmailHeader)
						.replace("@FB", imgEmailBase + imgEmailFb)
						.replace("@LD", imgEmailBase + imgEmailLb)
						.replace("@YT", imgEmailBase + imgEmailYt)
						.replace("@ANS", imgEmailBase + imgEmailAsn)
						.replace("@BTN", imgEmailBase + imgEmailBtn)
						.replace("@TOKEN", imgEmailBase + apiCorretorToken + "KHHURUTUEYTU2343HDCAGSFGSFNZCBJJBJBJBJGH");
	//					.replace("@TOKEN", IMG_EMAIL_BASE + API_CORRETOR_TOKEN + "KHHURUTUEYTU2343HDCAGSFGSFNZCBJJBJBJBJGH");	
			}
			
		} catch (Exception e) {
			log.error("Erro ao ler arquivo email esqueci minha senha! Detalhe: [" + e.getMessage() + "]");
			return "";
		}
		
		return htmlStr;
		
	}
}

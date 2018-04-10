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
	
	
	public void sendMail(String email, String token) {
		
		log.info("sendMail");
		
		try {
			
			String sendMailEndpointUrl = PropertiesUtils.getProperty(PropertiesUtils.SENDMAIL_ENDPOINT_URL);
			String recepientName = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_RECEPIENTNAME_ESQUECISENHA);
			String sender = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SENDER_ESQUECISENHA);
			String senderName = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SENDERNAME_ESQUECISENHA);
			String type = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_TYPE_ESQUECISENHA);
			String subject = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SUBJECT_ESQUECISENHA);
			
			ApiManagerToken apiManager = ApiManagerTokenFactory.create(ApiManagerTokenEnum.WSO2, "PORTAL_CORRETOR_SERVICO");
			ApiToken apiToken = apiManager.generateToken();
						
			DefaultApi apiInstance = new DefaultApi();
			apiInstance.getApiClient().setBasePath(ConfigurationUtils.getURLGetToken().replaceAll("/token","/"+ sendMailEndpointUrl));			
			apiInstance.getApiClient().setAccessToken(apiToken.getAccessToken());		
			apiInstance.getApiClient().addDefaultHeader("Authorization", "Bearer "+apiToken.getAccessToken());			
			RequestEmail body = new RequestEmail(); 
			String msg = this.montarBodyMsg(token);
			
			body.setBody(msg);
			body.setRecepientName(recepientName);			
			body.setRecepients(Arrays.asList(new String [] {email}));
			//body.setRecepients(Arrays.asList(new String [] {"almeida_yago@hotmail.com"}));
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

	private String montarBodyMsg(String token) {
		
		log.info("montarBodyMsg");
		
		String htmlStr = "";
		
		try {
			
			FileReaderUtil fileReader = new FileReaderUtil();
			htmlStr = fileReader.readHtmlFile("EsqueciMinhaSenha");
			
			if(htmlStr == null || "".equals(htmlStr)) {
				throw new Exception(" Html aceite email esqueci minha senha vazio!");
			}
			else {			
				
				//String imgEmailBase = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_BASE);
				String imgEmailBase = "www.parceirosodontoprev.com.br";
				String imgEmailHeader = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_HEADER_ESQUECI_SENHA);
				String imgEmailFb = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_FB);
				String imgEmailLb = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_LD);
				String imgEmailYt = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_YT);
				String imgEmailAsn = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_ASN);
				//String imgEmailBtn = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_BTN);
				//String apiCorretorToken = PropertiesUtils.getProperty(PropertiesUtils.API_CORRETOR_TOKEN);
				//String apiCorretorToken = "esqueciMinhaSenha/";
				
				htmlStr = htmlStr.replace("@HEADER", imgEmailBase + imgEmailHeader)
						.replace("@FB", imgEmailBase + imgEmailFb)
						.replace("@LD", imgEmailBase + imgEmailLb)
						.replace("@YT", imgEmailBase + imgEmailYt)
						.replace("@ANS", imgEmailBase + imgEmailAsn)
						.replace("@TOKEN", imgEmailBase + "/"+token);
						//.replace("@TOKEN", imgEmailBase + apiCorretorToken + token);
						//.replace("@BTN", imgEmailBase + imgEmailBtn);
						//.replace("@TOKEN", imgEmailBase + apiCorretorToken + token);
	//					.replace("@TOKEN", IMG_EMAIL_BASE + API_CORRETOR_TOKEN + "KHHURUTUEYTU2343HDCAGSFGSFNZCBJJBJBJBJGH");	
			}
			
		} catch (Exception e) {
			log.error("Erro ao ler arquivo email esqueci minha senha! Detalhe: [" + e.getMessage() + "]");
			return "";
		}
		
		return htmlStr;
		
	}
}

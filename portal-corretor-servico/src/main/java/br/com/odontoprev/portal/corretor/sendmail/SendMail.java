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
import br.com.odontoprev.portal.corretor.dto.Plano;
import br.com.odontoprev.portal.corretor.serviceEmail.ApiException;
import br.com.odontoprev.portal.corretor.serviceEmail.api.DefaultApi;
import br.com.odontoprev.portal.corretor.serviceEmail.model.RequestEmail;
import br.com.odontoprev.portal.corretor.util.FileReaderUtil;


@Component
public class SendMail {
	
	private static final Log log = LogFactory.getLog(SendMail.class);

	@Value("${SENDMAIL_ENDPOINT_URL}")
	private String SENDMAIL_ENDPOINT_URL;
	
	@Value("${requestmail.body.recepientname}")
	private String REQUESTMAIL_RECEPIENTNAME;
	
	@Value("${requestmail.body.sender}")
	private String REQUESTMAIL_SENDER;
	
	@Value("${requestmail.body.sendername}")
	private String REQUESTMAIL_SENDERNAME;
	
	@Value("${requestmail.body.type}")
	private String REQUESTMAIL_TYPE;
	
	@Value("${requestmail.body.subject}")
	private String REQUESTMAIL_SUBJECT;
	
	@Value("${img.email.base}")
	private String IMG_EMAIL_BASE;
	
	@Value("${img.email.header}")
	private String IMG_EMAIL_HEADER;
	
	@Value("${img.email.btn}")
	private String IMG_EMAIL_BTN;
	
	@Value("${img.email.ans}")
	private String IMG_EMAIL_ASN;
	
	@Value("${img.email.fb}")
	private String IMG_EMAIL_FB;
	
	@Value("${img.email.ld}")
	private String IMG_EMAIL_LD;
	
	@Value("${img.email.yt}")
	private String IMG_EMAIL_YT;
	
	@Value("${api.portalcorretor.token}")
	private String API_CORRETOR_TOKEN;
	
	
	public void sendMail(EmailAceite email) {
		
		log.info("sendMail");
		
		try {
			
			ApiManagerToken apiManager = ApiManagerTokenFactory.create(ApiManagerTokenEnum.WSO2, "PORTAL_CORRETOR_SERVICO");
			ApiToken apiToken = apiManager.generateToken();
			
			DefaultApi apiInstance = new DefaultApi();
			apiInstance.getApiClient().setBasePath(ConfigurationUtils.getURLGetToken().replaceAll("/token","/"+ SENDMAIL_ENDPOINT_URL ));
			apiInstance.getApiClient().setAccessToken(apiToken.getAccessToken());
			apiInstance.getApiClient().addDefaultHeader("Authorization", "Bearer "+apiToken.getAccessToken());
			RequestEmail body = new RequestEmail(); // RequestEmail
//			EmailAceite email = new EmailAceite(); //teste
			String msg = this.montarBodyMsg(email);
			
			body.setBody(msg);
			body.setRecepientName(REQUESTMAIL_RECEPIENTNAME);
//			body.setRecepients(Arrays.asList(new String [] {"jalves@vectoritcgroup.com"})); //teste
			body.setRecepients(Arrays.asList(new String [] {email.getEmailEnvio()}));
			body.setSender(REQUESTMAIL_SENDER);
			body.setSenderName(REQUESTMAIL_SENDERNAME);
			body.setType(REQUESTMAIL_TYPE);
			body.setSubject(REQUESTMAIL_SUBJECT);

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
		
		log.info("montarBodyMsg");
		
		String htmlStr = "";
		
		try {
			
			FileReaderUtil fileReader = new FileReaderUtil();
			htmlStr = fileReader.readHtmlFile();
			
			if(htmlStr == null || "".equals(htmlStr)) {
				throw new Exception(" Html aceite email vazio!");
			}
			else {
				StringBuilder planos = new StringBuilder("");
				StringBuilder precos = new StringBuilder("");
				if(email.getPlanos() != null) {
					for(Plano plano : email.getPlanos()) {
						planos.append(plano.getDescricao());
						precos.append(plano.getValor());
					}
				}
				
				htmlStr = htmlStr.replace("@NOMEDOCORRETOR", email.getNomeCorretor())
						.replace("@CORRETORA", email.getNomeCorretora())
						.replace("@EMPRESA", email.getNomeEmpresa())
						.replace("@PLANO", planos.toString())
						.replace("@PREÇOPLANO", precos.toString());
				
	//			htmlStr = htmlStr.replace("@NOMEDOCORRETOR", "Joao Silva")
	//					.replace("@CORRETORA", "Corretora e Cia")
	//					.replace("@EMPRESA", "Empresa e Cia")
	//					.replace("@PLANO", "Plano 1, Plano 2")
	//					.replace("@PREÇOPLANO", "14,90, 49,90");
				
				htmlStr = htmlStr.replace("@HEADER", IMG_EMAIL_BASE + IMG_EMAIL_HEADER)
						.replace("@FB", IMG_EMAIL_BASE + IMG_EMAIL_FB)
						.replace("@LD", IMG_EMAIL_BASE + IMG_EMAIL_LD)
						.replace("@YT", IMG_EMAIL_BASE + IMG_EMAIL_YT)
						.replace("@ANS", IMG_EMAIL_BASE + IMG_EMAIL_ASN)
						.replace("@BTN", IMG_EMAIL_BASE + IMG_EMAIL_BTN)
						.replace("@TOKEN", IMG_EMAIL_BASE + API_CORRETOR_TOKEN + email.getToken());
	//					.replace("@TOKEN", IMG_EMAIL_BASE + API_CORRETOR_TOKEN + "KHHURUTUEYTU2343HDCAGSFGSFNZCBJJBJBJBJGH");	
			}
			
		} catch (Exception e) {
			log.error("Erro ao ler arquivo email aceite! Detalhe: [" + e.getMessage() + "]");
			return "";
		}
		
		return htmlStr;
		
	}
}

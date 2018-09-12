package br.com.odontoprev.portal.corretor.business;

import java.io.File;
import java.util.Arrays;

import javax.annotation.ManagedBean;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.api.manager.client.token.ApiManagerToken;
import br.com.odontoprev.api.manager.client.token.ApiManagerTokenFactory;
import br.com.odontoprev.api.manager.client.token.ApiToken;
import br.com.odontoprev.api.manager.client.token.enumerator.ApiManagerTokenEnum;
import br.com.odontoprev.api.manager.client.token.util.ConfigurationUtils;
import br.com.odontoprev.portal.corretor.dto.EmailContratoCorretora;
import br.com.odontoprev.portal.corretor.serviceEmail.ApiException;
import br.com.odontoprev.portal.corretor.serviceEmail.api.DefaultApi;
import br.com.odontoprev.portal.corretor.serviceEmail.model.Attachment;
import br.com.odontoprev.portal.corretor.serviceEmail.model.RequestEmail;
import br.com.odontoprev.portal.corretor.util.FileReaderUtil;
import br.com.odontoprev.portal.corretor.util.PropertiesUtils;

//201809121716 - esert - COR-714 - Serviço - Novo serviço gerar enviar contrato corretora
@ManagedBean
@Transactional(rollbackFor={Exception.class})
public class SendMailContratoCorretora {

	private static final Log log = LogFactory.getLog(SendMailContratoCorretora.class);

	@Value("SENDMAIL_ENDPOINT_URL")
	private String sendMailEndpointUrl; //= PropertiesUtils.getProperty(PropertiesUtils.SENDMAIL_ENDPOINT_URL);

	@Value("requestmailContratoCorretora.body.recepientname")
	private String recepientName; //= PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_RECEPIENTNAME);
	
	@Value("requestmailContratoCorretora.body.sender")
	private String sender; //= PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SENDER);
	
	@Value("requestmailContratoCorretora.body.sendername")
	private String senderName; //= PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SENDERNAME);

	@Value("requestmailContratoCorretora.body.type")
	private String type; //= PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_TYPE);
	
	@Value("requestmailContratoCorretora.body.subject")
	private String subject; //= PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SUBJECT);

	@Transactional(rollbackFor={Exception.class})
	public boolean sendMail(EmailContratoCorretora email) {
		log.info("sendMail - ini");
		boolean retorno = false;
		try {
			
			ApiManagerToken apiManager = ApiManagerTokenFactory.create(ApiManagerTokenEnum.WSO2, "PORTAL_CORRETOR_SERVICO");
			ApiToken apiToken = apiManager.generateToken();
			
			DefaultApi apiInstance = new DefaultApi();
			apiInstance.getApiClient().setBasePath(ConfigurationUtils.getURLGetToken().replaceAll("/token","/"+ sendMailEndpointUrl));
			apiInstance.getApiClient().setAccessToken(apiToken.getAccessToken());
			apiInstance.getApiClient().addDefaultHeader("Authorization", "Bearer "+apiToken.getAccessToken());
			RequestEmail body = new RequestEmail(); // RequestEmail
			String msg = this.montarBodyMsg(email);
			
			body.setBody(msg);
			body.setRecepientName(recepientName);			
			body.setRecepients(Arrays.asList(new String [] {email.getEmailEnvio()}));
			body.setSender(sender);
			body.setSenderName(senderName);
			body.setType(type);
			body.setSubject(subject.replace("__RazaoSocial__", email.getNomeCorretora()));
			
			//208109121718 - esert - COR-714 - Serviço - Novo serviço gerar enviar contrato corretora
			if(email.getContratoCorretora()!=null) {
				String arquivoBase64 = 
						Base64.encodeBase64String(
								FileReaderUtil.readContentIntoByteArray(
										new File(
												email.
												getContratoCorretora().
												getCaminhoCarga().
												concat(email.getContratoCorretora().getNomeArquivo())
										)
								)
						);
				
				Attachment attachment = new Attachment();
				attachment.setContentAttachmentBase64(arquivoBase64);
				attachment.setFileNameAttachment(email.getContratoCorretora().getNomeArquivo());
				body.setAttachment(attachment);
			}

			apiInstance.sendEmail(body);
			retorno = true;
			
		} catch (ApiException e) {
			log.error("SendMailAceite.sendMail(); getMessage:[" + e.getMessage() + "]"); 
			log.error("SendMailAceite.sendMail(); getResponseBody:[" + e.getResponseBody() + "]");
			e.printStackTrace();
			retorno = false;
		} catch(Exception ex) {
			log.error(ex.getLocalizedMessage());
			retorno = false;
		}
		
		log.info("sendMail - fim");
		return retorno;
	}

	private String montarBodyMsg(EmailContratoCorretora email) {
		
		log.info("montarBodyMsg");
		
		String htmlStr = "";
		
		try {
			
			FileReaderUtil fileReader = new FileReaderUtil();
			htmlStr = fileReader.readHtmlFile("EmailContratoCorretora");
			
			if(htmlStr == null || "".equals(htmlStr)) {
				throw new Exception(" Html Contrato Corretora email vazio!");
			}
			else {
				
				htmlStr = htmlStr
						.replace("@RAZAOSOCIAL", email.getNomeCorretora())
						;
				
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
						.replace("@TOKEN", imgEmailBase + apiCorretorToken + email.getToken());
	//					.replace("@TOKEN", IMG_EMAIL_BASE + API_CORRETOR_TOKEN + "KHHURUTUEYTU2343HDCAGSFGSFNZCBJJBJBJBJGH");	
			}
			
		} catch (Exception e) {
			log.error("Erro ao ler arquivo email aceite! Detalhe: [" + e.getMessage() + "]");
			return "";
		}
		
		return htmlStr;
		
	}
}

package br.com.odontoprev.portal.corretor.business;

import java.util.Arrays;

import javax.annotation.ManagedBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.api.manager.client.token.ApiManagerToken;
import br.com.odontoprev.api.manager.client.token.ApiManagerTokenFactory;
import br.com.odontoprev.api.manager.client.token.ApiToken;
import br.com.odontoprev.api.manager.client.token.enumerator.ApiManagerTokenEnum;
import br.com.odontoprev.api.manager.client.token.util.ConfigurationUtils;
import br.com.odontoprev.portal.corretor.dto.EmailAceite;
import br.com.odontoprev.portal.corretor.dto.Plano;
import br.com.odontoprev.portal.corretor.serviceEmail.ApiException;
import br.com.odontoprev.portal.corretor.serviceEmail.api.DefaultApi;
import br.com.odontoprev.portal.corretor.serviceEmail.model.Attachment;
import br.com.odontoprev.portal.corretor.serviceEmail.model.RequestEmail;
import br.com.odontoprev.portal.corretor.util.FileReaderUtil;
import br.com.odontoprev.portal.corretor.util.PropertiesUtils;


@ManagedBean
@Transactional(rollbackFor={Exception.class}) //201806281838 - esert - COR-348 //201806291448 - esert - robertinho acertou token em desenv
public class SendMailAceite {
	
	private static final Log log = LogFactory.getLog(SendMailAceite.class);

	@Transactional(rollbackFor={Exception.class}) //201806281838 - esert - COR-348 //aqui nao mas so em teste //201806291448 - esert - robertinho acertou token em desenv
	public void sendMail(EmailAceite email) {
		
		log.info("sendMail - ini");
		
		try {
			
			String sendMailEndpointUrl = PropertiesUtils.getProperty(PropertiesUtils.SENDMAIL_ENDPOINT_URL);
			String recepientName = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_RECEPIENTNAME);
			String sender = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SENDER);
			String senderName = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SENDERNAME);
			String type = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_TYPE);
			String subject = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SUBJECT);
			
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
			body.setSubject(subject);
			
			//208108231952 - esert - COR-617 serviço gerar pdf detalhe contratação pme
			//if(email.getArquivoBase64()!=null) {
			if(email.getArquivoContratacao().getArquivoBase64()!=null) { //208108240106
				Attachment attachment = new Attachment();
				attachment.setContentAttachmentBase64(email.getArquivoContratacao().getArquivoBase64()); //208108231952
				attachment.setFileNameAttachment(email.getArquivoContratacao().getNomeArquivo()); //208108240106
				body.setAttachment(attachment);
			}

			apiInstance.sendEmail(body); //TODO 201808221825 - esert - DESCOMENTAR
			
		} catch (ApiException e) {
			log.error("SendMailAceite.sendMail(); getMessage:[" + e.getMessage() + "]"); 
			log.error("SendMailAceite.sendMail(); getResponseBody:[" + e.getResponseBody() + "]");
			e.printStackTrace();
		} catch(Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
		
		log.info("sendMail - fim");
	}

	private String montarBodyMsg(EmailAceite email) {
		
		log.info("montarBodyMsg");
		
		String htmlStr = "";
		
		try {
			
			FileReaderUtil fileReader = new FileReaderUtil();
			htmlStr = fileReader.readHtmlFile("EmailAceite");
			
			if(htmlStr == null || "".equals(htmlStr)) {
				throw new Exception(" Html aceite email vazio!");
			}
			else {
				StringBuilder planosSb = new StringBuilder("");
				StringBuilder precosSb = new StringBuilder("");
				
				for(Plano plano : email.getPlanos()) {
					planosSb.append(plano.getDescricao() + ",");
					precosSb.append(plano.getValor() + ",");
				}
				
				htmlStr = htmlStr.replace("@NOMEDOCORRETOR", email.getNomeCorretor())
						.replace("@CORRETORA", email.getNomeCorretora())
						.replace("@EMPRESA", email.getNomeEmpresa())
						.replace("@PLANO", planosSb.toString())
						.replace("@PREÇOPLANO", precosSb.toString());
				
	//			htmlStr = htmlStr.replace("@NOMEDOCORRETOR", "Joao Silva")
	//					.replace("@CORRETORA", "Corretora e Cia")
	//					.replace("@EMPRESA", "Empresa e Cia")
	//					.replace("@PLANO", "Plano 1, Plano 2")
	//					.replace("@PREÇOPLANO", "14,90, 49,90");
				
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

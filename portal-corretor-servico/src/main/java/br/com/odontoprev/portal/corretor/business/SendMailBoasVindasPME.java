package br.com.odontoprev.portal.corretor.business;

import java.util.List;

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

//201805091500 - esert - COR-160
@ManagedBean
public class SendMailBoasVindasPME {
	
	private static final Log log = LogFactory.getLog(SendMailBoasVindasPME.class);
	
	public void sendMail(
			//String email,
			List<String> listEmail,
			String nomeEmpresa, 
			String login, 
			String senha, 
			String linkPortal, 
			String dataVigencia, 
			String diaVencimentoFatura
			) {
		
		//201805091825 - esert
		log.info("sendMail(email:["+ listEmail +"])");
		log.info("sendMail(nomeEmpresa:["+ nomeEmpresa +"])");
		log.info("sendMail(login:["+ login +"])");
		log.info("sendMail(linkPortal:["+ linkPortal +"])");
		log.info("sendMail(dataVigencia:["+ dataVigencia +"])");
		log.info("sendMail(diaVencimentoFatura:["+ diaVencimentoFatura +"])");
		
		try {
			
			String sendMailEndpointUrl = PropertiesUtils.getProperty(PropertiesUtils.SENDMAIL_ENDPOINT_URL);
			String recepientName = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_RECEPIENTNAME_BOASVINDASPME);
			String sender = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SENDER_BOASVINDASPME);			
			String senderName = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SENDERNAME_BOASVINDASPME);			
			String type = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_TYPE_BOASVINDASPME);			
			String subject = PropertiesUtils.getProperty(PropertiesUtils.REQUESTMAIL_SUBJECT_BOASVINDASPME);
			
			ApiManagerToken apiManager = ApiManagerTokenFactory.create(ApiManagerTokenEnum.WSO2, "PORTAL_CORRETOR_SERVICO");
			ApiToken apiToken = apiManager.generateToken();
						
			DefaultApi apiInstance = new DefaultApi();
			apiInstance.getApiClient().setBasePath(ConfigurationUtils.getURLGetToken().replaceAll("/token","/"+ sendMailEndpointUrl));				
			apiInstance.getApiClient().setAccessToken(apiToken.getAccessToken());		
			apiInstance.getApiClient().addDefaultHeader("Authorization", "Bearer "+apiToken.getAccessToken());			
			
			RequestEmail body = new RequestEmail(); 
			
			String msg = this.montarBodyMsg(
					nomeEmpresa, 
					login, 
					senha, 
					linkPortal, 
					dataVigencia, 
					diaVencimentoFatura
					);
			
			body.setBody(msg);
			body.setRecepientName(recepientName);			
			body.setRecepients(listEmail);
			body.setSender(sender);
			body.setSenderName(senderName);
			body.setType(type);
			body.setSubject(subject);

			//201806212034 - esert - ensaios de log do email completo
			//String stringBody = new Gson().toJson(body, RequestEmail.class); //201806201026 - esert - RequestEmail em json para log. tam > 20.000 bytes devido utf8 - max oracle varchar 4000
			//log.info("SendMailBoasVindasPME.sendMail; msg:[" + msg + "]"); //201806212034 - esert - apenas msg body em json para log. tam > 10.000 - max oracle varchar 4000
			
			apiInstance.sendEmail(body);
		} catch (ApiException e) {
			log.error(e.getResponseBody());
			log.error("Exception when calling DefaultApi#sendEmail");
			e.printStackTrace();
		} catch(Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
	}

	//201805091610 - esert - COR-160
	private String montarBodyMsg(
			String nomeEmpresa, 
			String login, 
			String senha, 
			String linkPortal, 
			String dataVigencia, 
			String diaVencimentoFatura
	) {
		
		log.info("montarBodyMsg");
		
		String htmlStr = "";
		
		try {
			
			FileReaderUtil fileReader = new FileReaderUtil();
			
			htmlStr = fileReader.readHtmlFile("BoasVindasPME");
						
			if(htmlStr == null || "".equals(htmlStr)) {
				throw new Exception(" Html boas vindas pme vazio!");
			}
			else {			
				
				String imgEmailBase = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_BASE);
				String imgEmailHeader = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_HEADER_BOAS_VINDAS_PME);
				String imgEmailFb = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_FB);
				String imgEmailLb = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_LD);
				String imgEmailYt = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_YT);
				String imgEmailAsn = PropertiesUtils.getProperty(PropertiesUtils.IMG_EMAIL_ASN);
							
				htmlStr = htmlStr.replace("@HEADER", imgEmailBase + imgEmailHeader)
						.replace("@FB", imgEmailBase + imgEmailFb)
						.replace("@LD", imgEmailBase + imgEmailLb)
						.replace("@YT", imgEmailBase + imgEmailYt)
						.replace("@ANS", imgEmailBase + imgEmailAsn)
						.replace("@NOMEEMPRESA", nomeEmpresa)
						.replace("@LOGIN", login)
						.replace("@SENHA", senha)
						.replace("@LINKPORTAL", linkPortal)
						.replace("@DATAVIGENCIA", dataVigencia)
						.replace("@DIAVENCTOFATURA", diaVencimentoFatura)
						;
				}
			
		} catch (Exception e) {
			log.error("Erro ao ler arquivo email status! Detalhe: [" + e.getMessage() + "]");
			return "";
		}
		
		return htmlStr;
		
	}
}

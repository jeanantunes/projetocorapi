package br.com.odontoprev.portal.corretor.util;

import java.util.ResourceBundle;

public class PropertiesUtils {
	
	private static final ResourceBundle PROPERTY;
	
	public static final String REQUESTMAIL_RECEPIENTNAME_ESQUECISENHA = "requestmailEsqueciSenha.body.recepientname";
	public static final String REQUESTMAIL_SENDER_ESQUECISENHA = "requestmailEsqueciSenha.body.sender";
	public static final String REQUESTMAIL_SENDERNAME_ESQUECISENHA = "requestmailEsqueciSenha.body.sendername";
	public static final String REQUESTMAIL_TYPE_ESQUECISENHA = "requestmailEsqueciSenha.body.type";
	public static final String REQUESTMAIL_SUBJECT_ESQUECISENHA = "requestmailEsqueciSenha.body.subject";
	
	public static final String REQUESTMAIL_RECEPIENTNAME_FORCASTATUS = "requestmailForcaStatus.body.recepientname";
	public static final String REQUESTMAIL_SENDER_FORCASTATUS = "requestmailForcaStatus.body.sender";
	public static final String REQUESTMAIL_SENDERNAME_FORCASTATUS_REPROVADO = "requestmailForcaStatusReprovado.body.sendername";
	public static final String REQUESTMAIL_TYPE_FORCASTATUS = "requestmailForcaStatus.body.type";
	public static final String REQUESTMAIL_SUBJECT_FORCASTATUS_REPROVADO = "requestmailForcaStatusReprovado.body.subject";

	
	public static final String PATH_XLS_EMPRESA = "server.path.empresa";
	
	public static final String PATH_XLS_VIDAS = "server.path.vidas";
	
	public static final String SENDMAIL_ENDPOINT_URL = "SENDMAIL_ENDPOINT_URL";
	
	public static final String REQUESTMAIL_RECEPIENTNAME = "requestmail.body.recepientname";
	
	public static final String REQUESTMAIL_SENDER = "requestmail.body.sender";
	
	public static final String REQUESTMAIL_SENDERNAME = "requestmail.body.sendername";
	
	public static final String REQUESTMAIL_TYPE = "requestmail.body.type";
	
	public static final String REQUESTMAIL_SUBJECT = "requestmail.body.subject";
	
	public static final String IMG_EMAIL_BASE = "img.email.base";
	
	public static final String IMG_EMAIL_HEADER = "img.email.header";
	
	public static final String IMG_EMAIL_HEADER_ESQUECI_SENHA = "img.email.header.esqueciSenha";
	
	public static final String IMG_EMAIL_HEADER_FORCA_STATUS = "img.email.header.forcaStatus";
	
	public static final String IMG_EMAIL_BTN = "img.email.btn";
	
	public static final String IMG_EMAIL_ASN = "img.email.ans";
	
	public static final String IMG_EMAIL_FB = "img.email.fb";
	
	public static final String IMG_EMAIL_LD = "img.email.ld";
	
	public static final String IMG_EMAIL_YT = "img.email.yt";
	
	public static final String API_CORRETOR_TOKEN = "api.portalcorretor.token";
	

	static {
		PROPERTY = ResourceBundle.getBundle("application");
	}

	private PropertiesUtils() {}
	
	public static String getProperty(final String nome){
		return PROPERTY.getString(nome);
	}
	
}

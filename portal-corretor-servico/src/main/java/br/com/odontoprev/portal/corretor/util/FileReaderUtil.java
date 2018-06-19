package br.com.odontoprev.portal.corretor.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileReaderUtil {

	private static final Log log = LogFactory.getLog(FileReaderUtil.class);

	public String readHtmlFile(String tipoEmail) {
		log.info("readHtmlFile(tipoEmail:[" + tipoEmail + "])"); //201805091625 - esert - inc
		
		String html = "";
		
		System.out.println("tipoEmail:[" + tipoEmail + "]");
		
//		String emkt = (tipoEmail.equals("EmailAceite") ? "emkt.html" : "emktRecuperarSenha.html"); //201805091620 - esert - exc
		
		//201805091620 - esert - alt
		String emkt = "";
		switch(tipoEmail) {
			case "EmailAceite":
				emkt = "emkt.html";
				break; //201806012119 - esert - inc
			case "EsqueciMinhaSenha":
				emkt = "emktRecuperarSenha.html";
				break; //201806012119 - esert - inc
			case "BoasVindasPME":
				emkt = "emkt_boas_vindas_pme.html"; //201805091620 - esert - inc
				break; //201806012119 - esert - inc
		}

		log.info("readHtmlFile(tipoEmail:[" + tipoEmail + "]) -> emkt:[" + emkt + "]"); //201805091625 - esert - inc

		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("templates" + File.separatorChar + emkt);
			BufferedInputStream bufferInput = new BufferedInputStream(inputStream);
			BufferedReader br = new BufferedReader(new InputStreamReader(bufferInput));
			
			StringBuffer inputLine = new StringBuffer();
            String tmp; 
            while ((tmp = br.readLine()) != null) {
                inputLine.append(tmp);
                System.out.println(tmp);
            }
            
            html = inputLine.toString();
            
			bufferInput.close();
			br.close();

		} catch (Exception e) {
			log.error("Erro ao ler nome arq html email ! tipoEmail:[" + tipoEmail + "]; emkt:[" + emkt + "]; Detalhe: [" + e.getMessage() + "]"); //201805091620 - esert - alt
			return "";
		}

		return html;

	}
	
	public String readHtmlForcaStatus(String opcao) {
		
		String html = "";
		
		String emkt = null;
		
		switch (opcao) {
		case "REPROVAR":
			emkt = "emkt_statusReprovadoForca.html";
			log.info("readHtmlForcaStatusReprovado");
			break;
		case "APROVAR":
			emkt = "emkt_statusAprovadoForca.html";
			log.info("readHtmlForcaStatusAprovado");
			break;
		}
	
		html = readHTML(html, emkt);

		return html;
	}

	private String readHTML(String html, String emkt) {
		
		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("templates" + File.separatorChar + emkt);
			BufferedInputStream bufferInput = new BufferedInputStream(inputStream);
			BufferedReader br = new BufferedReader(new InputStreamReader(bufferInput));
			
			StringBuffer inputLine = new StringBuffer();
            String tmp; 
            while ((tmp = br.readLine()) != null) {
                inputLine.append(tmp);
            }
            
            html = inputLine.toString();
            
			bufferInput.close();
			br.close();

		} catch (Exception e) {
			log.error("Erro ao ler html! Detalhe: [" + e.getMessage() + "]");
			return "";
		}
		return html;
	}

}

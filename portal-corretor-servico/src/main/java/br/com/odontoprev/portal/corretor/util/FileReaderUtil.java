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

	public String readHtmlFile() {
		
		String html = "";

		log.info("readHtmlFile");

		try {

			InputStream inputStream = this.getClass().getClassLoader()
					.getResourceAsStream("templates" + File.separatorChar + "emkt.html");
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
			log.error("Erro ao ler html email aceite! Detalhe: [" + e.getMessage() + "]");
			return "";
		}

		return html;

	}

}

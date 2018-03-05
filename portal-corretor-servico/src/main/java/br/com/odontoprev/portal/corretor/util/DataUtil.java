package br.com.odontoprev.portal.corretor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.odontoprev.portal.corretor.service.impl.BeneficiarioServiceImpl;

public class DataUtil {
	
	private static final Log log = LogFactory.getLog(BeneficiarioServiceImpl.class);

	public static final Date dateParse(String strData) throws Exception {

		Date data = null;

		if (strData != null) {

			//SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy"); //201803050237 - esertorio - formato acordado com Yago
			
			try {
				data = df.parse(strData);
			} catch (ParseException e) {
				//201803050237 - esertorio - formato acordado com Yago
//				try {
//					data = df.parse("01-01-1900");
//				} catch (ParseException e1) {
//					log.error("Erro ao converter data [01-01-1900]. Detalhe: [" + e.getMessage() + "]");
//				}
				log.error("Erro ao converter data [" + strData + "]. Detalhe: [" + e.getMessage() + "]");
				throw new Exception("Erro ao converter data [" + strData + "]. Detalhe: [" + e.getMessage() + "]");
			}
		}

		return data;

	}

}

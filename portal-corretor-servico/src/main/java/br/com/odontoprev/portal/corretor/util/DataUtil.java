package br.com.odontoprev.portal.corretor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataUtil {
	
	private static final Log log = LogFactory.getLog(DataUtil.class);

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
	
	public static final String dateToStringParse(Date data) throws Exception {
		
		
		String dataStr = "00/00/0000";
		
		if(data != null) {
			final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				dataStr = sdf.format(data);
			} catch (final Exception e) {
				log.error("Erro ao converter data [" + dataStr + "]. Detalhe: [" + e.getMessage() + "]");
				throw new Exception("Erro ao converter data [" + dataStr + "]. Detalhe: [" + e.getMessage() + "]");
			}
		}

		return dataStr;

	}

	//201805092100 - esert - inicial
	//201805101525 - esert - calcula DataVigencia a partir do DiaVencimento
	//201805101739 - esert - funcao isEffectiveDate copíada do App na versao abaixo 
	//201805101739 - esert - porem usa DataVenda ao inves de CurrentDate vide Camila@ODPV
	//h t t p : //git .odontoprev .com .br /esteira-digital/est-portalcorretor-app/blob/sprint6/VendasOdontoPrev/app/src/main/assets/app/pmeFaturaController.js
	//201805101900 - esert - ultimo teste unitario ok
	public static final String isEffectiveDate(long dayDueDate, Date dateDataVenda) {
		String strDataVigencia = "dd/MM/yyyy";
		SimpleDateFormat sdf_ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
//	function isEffectiveDate(dayDueDate) {

		log.info("dayDueDate:[" + dayDueDate + "]");
		log.info("dateDataVenda:[" + sdf_ddMMyyyy.format(dateDataVenda) + "]");

		
//		var currentTime = moment();	
//		currentTime.set({ hour: 0, minute: 0, second: 0, millisecond: 0 });
//		currentTime.toISOString();
//		currentTime.format();

		//201805101000 - esert
		Date dateAgoraComHora = new Date();
		
		Calendar dataHoje = new GregorianCalendar();
		Calendar dataVenda = new GregorianCalendar();
		Calendar olderDate_VencMenos11Dias = new GregorianCalendar();
		Calendar dataVencimento = new GregorianCalendar();

		dataHoje.setTime(dateAgoraComHora);
		log.info("dataHoje:[" + sdf_ddMMyyyy.format(dataHoje.getTime()) + "]");

		
		dataVenda.setTime(dateDataVenda); //201805101754 - esert - vinda da TBOD_VENDA
		dataVenda = new GregorianCalendar(
			dataVenda.get(Calendar.YEAR), 
			dataVenda.get(Calendar.MONTH), 
			dataVenda.get(Calendar.DATE)
		);
		log.info("dataVenda:[" + sdf_ddMMyyyy.format(dataVenda.getTime()) + "]");
		
//		var month = currentTime.format('MM');
//		var day = currentTime.format('DD');
//		var year = currentTime.format('YYYY');
		//201805101030 - esert
		int month = dataHoje.get(Calendar.MONTH);
//		int day = calendarHoje.get(Calendar.DATE);
		int year = dataHoje.get(Calendar.YEAR);
//
//		$("#divProximoMes").addClass('hide');
//
//		switch (dayDueDate) {
		int intDayDueDate = (int)dayDueDate;
	    switch (intDayDueDate) {
//
//			case "05":
			case 5:
			case 15: //201805101537 - esert|yalme - exceto dia 05, os demais usam mesma regra
			case 25: //201805101537 - esert|yalme - exceto dia 05, os demais usam mesma regra
//
//	            var vencimento;
//	            var dataVencimento = moment("05-" + month.toString() + "-" + year, "DD-MM-YYYY");
				dataVencimento = new GregorianCalendar(year, month, intDayDueDate);
				log.info("dataVencimento:[" + sdf_ddMMyyyy.format(dataVencimento.getTime()) + "]");
				
				//201805101537 - esert|yalme - so qdo dia 05 cinco que incrementa um mes de inicio
				if(intDayDueDate==5) { 
//		            var dataVencimento = dataVencimento.add(1, 'M');
					dataVencimento.add(Calendar.MONTH, 1); //incrementa um mes de inicio
				}
				
//	            var olderDate = moment(dataVencimento).add(-11, "days");
				olderDate_VencMenos11Dias.setTime(dataVencimento.getTime()); //inicializa com DataVencimento 
				olderDate_VencMenos11Dias.add(Calendar.DATE, -11); //subtrai 11 dias = atrasa 11 dias
				log.info("olderDate_VencMenos11Dias:[" + sdf_ddMMyyyy.format(olderDate_VencMenos11Dias.getTime()) + "]");

//	            if (currentTime.isAfter(olderDate)) 
				if(dataVenda.getTimeInMillis() > olderDate_VencMenos11Dias.getTimeInMillis()) {
//					vencimento = dataVencimento.add(1, 'M');
					dataVencimento.add(Calendar.MONTH, 1);
//	            else 
				} else {
//					vencimento = dataVencimento;
					//dataVencimento = dataVencimento;
				}

//	            var dataDeCorteDeMovimentacao = moment(dataVencimento).add(-11, "days");
//	            $("#corte").html('Data de corte de movimentação:<br>' + dataDeCorteDeMovimentacao.format("DD/MM/YYYY"));
//	            $("#vencimento").html('Data de vencimento:<br>' + vencimento.format("DD/MM/YYYY"));
//	            $("#vigencia").html('Data de vigência:<br>' + vencimento.format("DD/MM/YYYY"));

				//201805101530 - esert
			    strDataVigencia = sdf_ddMMyyyy.format(dataVencimento.getTime());
				log.info("dataVigencia:[" + sdf_ddMMyyyy.format(dataVencimento.getTime()) + "]");

	            break;
		} //switch (dayDueDate)
	    
		return strDataVigencia;
	}

}

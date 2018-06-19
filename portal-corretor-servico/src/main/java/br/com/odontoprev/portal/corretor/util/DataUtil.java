package br.com.odontoprev.portal.corretor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

public class DataUtil {
	
	private static final Log log = LogFactory.getLog(DataUtil.class);
	
	@Value("${datautil.prazoDeImplantacaoEmDias}") //201806151614 - esert - parametro para prazo de implantacao vide camila@odpv 
	private static int prazoDeImplantacaoEmDias; //201806151614 - esert - parametro para prazo de implantacao vide camila@odpv

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
	//h t t p : //git .odontoprev .com .br /esteira-digital/est-portalcorretor-app/blob/sprint6/VendasOdontoPrev/app/src/main/assets/app/pmeFaturaController.js
	//201805101739 - esert - porem usa DataVenda ao inves de CurrentDate vide Camila@ODPV
	//201805101900 - esert - ultimo teste unitario ok
	//201806141632 - esert - alterar retorno de String para Date para deixar formatacao para o uso final
	//public static final String isEffectiveDate(long dayDueDate, Date dateDataVenda) {
	public static final Date isEffectiveDate(long dayDueDate, Date dateDataVenda) {
		String strDataVigencia = "dd/MM/yyyy"; //valor default de saida - nao eh um formato
		SimpleDateFormat sdf_ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
		//	function isEffectiveDate(dayDueDate) {

		log.info("isEffectiveDate - ini"); //201806141315
		log.info("dayDueDate:[" + dayDueDate + "]");
		log.info("dateDataVenda:[" + sdf_ddMMyyyy.format(dateDataVenda) + "]");

		log.info("prazoDeImplantacaoEmDias:[" + prazoDeImplantacaoEmDias + "] no applicaiton.properties");
		if(prazoDeImplantacaoEmDias==0) {
			log.info("prazoDeImplantacaoEmDias==0 -> nao encontrado no applicaiton.properties");
			prazoDeImplantacaoEmDias = 12;
			log.info("prazoDeImplantacaoEmDias:[" + prazoDeImplantacaoEmDias + "] ajustado valor default 12");
		}
		prazoDeImplantacaoEmDias = Math.abs(prazoDeImplantacaoEmDias) * -1; 
		
//		var currentTime = moment();	
//		currentTime.set({ hour: 0, minute: 0, second: 0, millisecond: 0 });
//		currentTime.toISOString();
//		currentTime.format();

		//201805101000 - esert
		Date dateAgoraComHora = new Date();
		
		Calendar dataHoje = new GregorianCalendar();
		Calendar dataVenda = new GregorianCalendar();
		Calendar olderDate_LimiteVenda_VencMenosXdias = new GregorianCalendar();
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
		//int month = dataHoje.get(Calendar.MONTH); //201806151614 - esert - exc - deve usar data da venda AQUI TBM vide camila@odpv desde 201805101739
		int month = dataVenda.get(Calendar.MONTH); //201806151614 - esert - inc - deve usar data da venda AQUI TBM vide camila@odpv desde 201805101739
//		int day = calendarHoje.get(Calendar.DATE);
		//int year = dataHoje.get(Calendar.YEAR); //201806151614 - esert - exc - deve usar data da venda AQUI TBM vide camila@odpv desde 201805101739
		int year = dataVenda.get(Calendar.YEAR); //201806151614 - esert - inc - deve usar data da venda AQUI TBM vide camila@odpv desde 201805101739

//		$("#divProximoMes").addClass('hide');

//        var vencimento;
//        var dataVencimento = moment("05-" + month.toString() + "-" + year, "DD-MM-YYYY");
		int intDayDueDate = (int)dayDueDate;
		dataVencimento = new GregorianCalendar(year, month, intDayDueDate);
		log.info("dataVencimento:[" + sdf_ddMMyyyy.format(dataVencimento.getTime()) + "]");
		
//		switch (dayDueDate) {
	    switch (intDayDueDate) {
//
//			case "05":
			case 5:

			case 15: //201805101537 - esert|yalme - exceto dia 05, os demais usam mesma regra
			case 25: //201805101537 - esert|yalme - exceto dia 05, os demais usam mesma regra

				log.info("escolhido dia do Vencimento:[" + dayDueDate + "]");
				//201805101537 - esert|yalme - so qdo dia 05 cinco que incrementa um mes de inicio
				if(intDayDueDate==5) { 
//		            var dataVencimento = dataVencimento.add(1, 'M');
					dataVencimento.add(Calendar.MONTH, 1); //incrementa um mes de inicio
					log.info("dataVencimento:[" + sdf_ddMMyyyy.format(dataVencimento.getTime()) + "](qdo dia 05 ja adiciona +um mes)"); //201806141233 - esert - nova linha log
				}				
				
//	            var olderDate = moment(dataVencimento).add(prazoDeImplantacaoEmDias, "days");
				olderDate_LimiteVenda_VencMenosXdias.setTime(dataVencimento.getTime()); //inicializa com DataVencimento 
				olderDate_LimiteVenda_VencMenosXdias.add(Calendar.DATE, prazoDeImplantacaoEmDias); //subtrai X dias = atrasa X dias
				log.info("olderDate_LimiteVenda_VencMenosXdias:[" + sdf_ddMMyyyy.format(olderDate_LimiteVenda_VencMenosXdias.getTime()) + "]");

//	            if (currentTime.isAfter(olderDate)) 
				//201805101739 - esert - porem para WEB usara DataVenda ao inves de CurrentDate vide Camila@ODPV
				if(dataVenda.getTimeInMillis() > olderDate_LimiteVenda_VencMenosXdias.getTimeInMillis()) {
					log.info("dataVenda eh > maior que olderDate_LimiteVenda_VencMenosXdias, entao adiciona mais+ um mesa data de vencimento."); //201806151831 - esert - nova linha log
//					vencimento = dataVencimento.add(1, 'M');
					dataVencimento.add(Calendar.MONTH, 1);
					log.info("dataVencimento:[" + sdf_ddMMyyyy.format(dataVencimento.getTime()) + "]"); //201806151831 - esert - nova linha log
//	            else 
				} else {
					log.info("dataVenda NAO eh > maior que olderDate_LimiteVenda_VencMenosXdias, entao data de vencimento fica como esta."); //201806151831 - esert - nova linha log
//					vencimento = dataVencimento;
					//dataVencimento = dataVencimento;
				}

//	            var dataDeCorteDeMovimentacao = moment(dataVencimento).add(prazoDeImplantacaoEmDias, "days");
//	            $("#corte").html('Data de corte de movimentação:<br>' + dataDeCorteDeMovimentacao.format("DD/MM/YYYY"));
//	            $("#vencimento").html('Data de vencimento:<br>' + vencimento.format("DD/MM/YYYY"));
//	            $("#vigencia").html('Data de vigência:<br>' + vencimento.format("DD/MM/YYYY"));

				//201805101530 - esert
			    strDataVigencia = sdf_ddMMyyyy.format(dataVencimento.getTime());
				log.info("dataVigencia:[" + sdf_ddMMyyyy.format(dataVencimento.getTime()) + "]");

	            break;
	            
//	        default: //201806151654 - esert - tratar excecao dia diferente dos esperados(05,15,25)
//				log.info("escolhido dia do Vencimento: invalido [" + sdf_ddMMyyyy.format(dataVencimento.getTime()) + "]");
//				Date dataVencimentoInvalida = new GregorianCalendar(1,0,1).getTime();
//				log.info("retorno de erro [" + sdf_ddMMyyyy.format(dataVencimentoInvalida) + "]");
//				return dataVencimentoInvalida;

		} //switch (dayDueDate)
	    
		log.info("isEffectiveDate - fim"); //201806141315
		
		//201806141632 - esert - alterar retorno de String para Date a fim de poupar dupla conversao
		//return strDataVigencia;  
		return dataVencimento.getTime();
	}

}

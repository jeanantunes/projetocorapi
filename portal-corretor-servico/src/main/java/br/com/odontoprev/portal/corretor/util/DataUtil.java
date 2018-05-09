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

	public static final String isEffectiveDate(long dayDueDate) {
		String strDataVigencia = "dd/MM/yyyy";
//		function isEffectiveDate(dayDueDate) {
//
//		    var currentTime = moment();
//		    currentTime.set({ hour: 0, minute: 0, second: 0, millisecond: 0 });
//		    currentTime.toISOString();
//		    currentTime.format();
//
//		    var month = currentTime.format('MM');
//		    var day = currentTime.format('DD');
//		    var year = currentTime.format('YYYY');
//
//		    $("#divProximoMes").addClass('hide');
//
//		    switch (dayDueDate) {
//
//		        case "05":
//
//		            var vencimento;
//		            var dataVencimento = moment("05-" + month.toString() + "-" + year, "DD-MM-YYYY");
//		            var dataVencimento = dataVencimento.add(1, 'M');
//
//		            var olderDate = moment(dataVencimento).add(-11, "days");
//
//		            if (currentTime.isAfter(olderDate)) vencimento = dataVencimento.add(1, 'M');
//		            else vencimento = dataVencimento;
//
//		            var dataDeCorteDeMovimentacao = moment(dataVencimento).add(-11, "days");
//
//		            $("#corte").html('Data de corte de movimentação:<br>' + dataDeCorteDeMovimentacao.format("DD/MM/YYYY"));
//		            $("#vencimento").html('Data de vencimento:<br>' + vencimento.format("DD/MM/YYYY"));
//		            $("#vigencia").html('Data de vigência:<br>' + vencimento.format("DD/MM/YYYY"));
//
//		            break;
//
//		        case "15": 
//
//		            var vencimento;
//		            var dataVencimento = moment("15-" + month.toString() + "-" + year, "DD-MM-YYYY");
//		            var olderDate = moment(dataVencimento).add(-11, "days");
//
//		            if (currentTime.isAfter(olderDate)) vencimento = dataVencimento.add(1, 'M');
//		            else vencimento = dataVencimento;
//
//		            var dataDeCorteDeMovimentacao = moment(dataVencimento).add(-11, "days");
//
//		            $("#corte").html('Data de corte de movimentação:<br>' + dataDeCorteDeMovimentacao.format("DD/MM/YYYY"));
//		            $("#vencimento").html('Data de vencimento:<br>' + vencimento.format("DD/MM/YYYY"));
//		            $("#vigencia").html('Data de vigência:<br>' + vencimento.format("DD/MM/YYYY"));
//
//		            break;
//
//		        case "25":
//
//		            var vencimento;
//		            var dataVencimento = moment("25-" + month.toString() + "-" + year, "DD-MM-YYYY");
//		            var olderDate = moment(dataVencimento).add(-11, "days");
//
//		            if (currentTime.isAfter(olderDate)) vencimento = dataVencimento.add(1, 'M');
//		            else vencimento = dataVencimento;
//
//		            var dataDeCorteDeMovimentacao = moment(dataVencimento).add(-11, "days");
//
//		            $("#corte").html('Data de corte de movimentação:<br>' + dataDeCorteDeMovimentacao.format("DD/MM/YYYY"));
//		            $("#vencimento").html('Data de vencimento:<br>' + vencimento.format("DD/MM/YYYY"));
//		            $("#vigencia").html('Data de vigência:<br>' + vencimento.format("DD/MM/YYYY"));
//
//		            break;
//		    }
//		}
		return strDataVigencia;
	}
}

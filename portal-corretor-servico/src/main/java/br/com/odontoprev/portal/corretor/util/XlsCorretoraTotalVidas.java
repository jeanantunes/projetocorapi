package br.com.odontoprev.portal.corretor.util;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import br.com.odontoprev.portal.corretor.model.VwodCorretoraTotalVidasPME;
import br.com.odontoprev.portal.corretor.model.VwodCorretoraTotalVidasPF;

//201806111500 - esert
@Component
public class XlsCorretoraTotalVidas {

	private static final Log log = LogFactory.getLog(XlsCorretoraTotalVidas.class);
	
	public String formataData_yyyyMMddHHmmss() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String stringDate = sdf.format(new Date());
		return stringDate;
	}

	//201806111930 - rmarques/esert - byte[]
	public byte[] gerarCorretoraTotalVidasPMEXLS(List<VwodCorretoraTotalVidasPME> vwodCorretoraTotalVidas) throws Exception { 

		log.info("gerarCorretoraTotalVidasPMEXLS - ini");
		
		String stringDataVenda = "";
		String stringDataVendaDefault = "00/00/0000";
		SimpleDateFormat sdfDataNascimento = new SimpleDateFormat("dd/MM/yyyy");

		try {

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("CorretoraTotalVidasPME");

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("DT_VENDA");//01
			rowhead.createCell(1).setCellValue("CNPJ_CORRETORA");//02
			rowhead.createCell(2).setCellValue("CORRETORA");//03
			rowhead.createCell(3).setCellValue("CPF");//04
			rowhead.createCell(4).setCellValue("VENDEDOR");//05
			rowhead.createCell(5).setCellValue("CNPJ_CLIENTE");//06
			rowhead.createCell(6).setCellValue("RAZAO_SOCIAL_CLIENTE");//07
			rowhead.createCell(7).setCellValue("LOGIN_DCMS");//08
			rowhead.createCell(8).setCellValue("LOGRADOURO");//09
			rowhead.createCell(9).setCellValue("NUMERO");//10
			rowhead.createCell(10).setCellValue("COMPLEMENTO");//11
			rowhead.createCell(11).setCellValue("BAIRRO");//12
			rowhead.createCell(12).setCellValue("CIDADE");//13
			rowhead.createCell(13).setCellValue("UF");//14
			rowhead.createCell(14).setCellValue("CEP");//15
			rowhead.createCell(15).setCellValue("REPRESENTANTE_LEGAL");//16
			rowhead.createCell(16).setCellValue("CELULAR");//17
			rowhead.createCell(17).setCellValue("EMAIL");//18
			rowhead.createCell(18).setCellValue("PLANO");//19
			rowhead.createCell(19).setCellValue("TOTAL_VIDAS");//20
			rowhead.createCell(20).setCellValue("DIA_FATURA");//21

			int rowCount = 0;

			for (VwodCorretoraTotalVidasPME item : vwodCorretoraTotalVidas) {
				
				rowCount++;
				Row row = sheet.createRow(rowCount);

				stringDataVenda = stringDataVendaDefault;
				try {
					stringDataVenda = sdfDataNascimento.format(item.getDtVenda());
				} catch (Exception e) {
					log.error("gerarCorretoraTotalVidasPMEXLS :: Erro ao formatar data venda. Detalhe: [" + e.getMessage() + "]");
				}
				row.createCell(0).setCellValue(stringDataVenda);//01
				row.createCell(1).setCellValue(item.getCnpj_corretora());//02
				row.createCell(2).setCellValue(item.getCorretora());//03
				row.createCell(3).setCellValue(item.getCpf());//04
				row.createCell(4).setCellValue(item.getVendedor());//05
				row.createCell(5).setCellValue(item.getCnpj_cliente());//06
				row.createCell(6).setCellValue(item.getRazao_social_cliente());//07
				row.createCell(7).setCellValue(item.getLogin_dcms());//08
				row.createCell(8).setCellValue(item.getLogradouro());//09
				row.createCell(9).setCellValue(item.getNumero());//10
				row.createCell(10).setCellValue(item.getComplemento());//11
				row.createCell(11).setCellValue(item.getBairro());//12
				row.createCell(12).setCellValue(item.getCidade());//13
				row.createCell(13).setCellValue(item.getUf());//14
				row.createCell(14).setCellValue(item.getCep());//15
				row.createCell(15).setCellValue(item.getRepresentante_legal());//16
				row.createCell(16).setCellValue(item.getCelular());//17
				row.createCell(17).setCellValue(item.getEmail());//18
				row.createCell(18).setCellValue(item.getPlano());//19
				
				String strTotal_vidas = "(n/a)";
				if(item.getTotal_vidas() != null) {
					strTotal_vidas = item.getTotal_vidas().toString();
				}
				row.createCell(19).setCellValue(strTotal_vidas);//20
				
				String strDia_fatura = "(n/a)";
				if(item.getDia_fatura() != null) {
					strDia_fatura = item.getDia_fatura().toString();
				}
				row.createCell(20).setCellValue(strDia_fatura);//21

			}
			
			log.info("for (... item : vwodCorretoraTotalVidas); rowCount=[" + rowCount + "]");
			
			ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
			workbook.write(fileOut);
			//fileOut.close();
			workbook.close();

			log.info("gerarCorretoraTotalVidasPMEXLS - fim");
			return fileOut.toByteArray();

		} catch (Exception e) {
			String msgErro = "gerarCorretoraTotalVidasPMEXLS; Erro; Message:["+ e.getMessage() +"]; Cause:["+ e.getCause() +"]";
			throw new Exception(msgErro, e);
		}
		
	}

	//201806121211 - esert - byte[]
	public byte[] gerarCorretoraTotalVidasPFXLS(List<VwodCorretoraTotalVidasPF> vwodCorretoraTotalVidasPF) throws Exception { 

		log.info("gerarCorretoraTotalVidasPFXLS - ini");
		
		String stringDT_VENDA = "";
		String stringPRIMEIRO_VENCIMENTO = "";
		String stringDataDefault = "00/00/0000";
		SimpleDateFormat sdf_ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");

		try {

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("CorretoraTotalVidasPF");

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("NUM_PROPOSTA");
			rowhead.createCell(1).setCellValue("DT_VENDA");
			rowhead.createCell(2).setCellValue("PRIMEIRO_VENCIMENTO");
			rowhead.createCell(3).setCellValue("CORRETORA");
			rowhead.createCell(4).setCellValue("CNPJ_CORRETORA");
			rowhead.createCell(5).setCellValue("NOME_FORCA");
			rowhead.createCell(6).setCellValue("CPF_FORCA");
			rowhead.createCell(7).setCellValue("PLANO_PF");
			rowhead.createCell(8).setCellValue("TIPO_PLANO");
			rowhead.createCell(9).setCellValue("VIDAS");

			rowhead.createCell(10).setCellValue("VALOR_VENDA");
			rowhead.createCell(11).setCellValue("STATUS_PROPOSTA");

			rowhead.createCell(12).setCellValue("CPF_TITULAR");
			rowhead.createCell(13).setCellValue("NOME_TITULAR");
			rowhead.createCell(14).setCellValue("LOGRADOURO");
			rowhead.createCell(15).setCellValue("NUMERO");
			rowhead.createCell(16).setCellValue("COMPLEMENTO");
			rowhead.createCell(17).setCellValue("BAIRRO");
			rowhead.createCell(18).setCellValue("CIDADE");
			rowhead.createCell(19).setCellValue("UF");
			rowhead.createCell(20).setCellValue("CEP");
			rowhead.createCell(21).setCellValue("EMAIL");

			int rowCount = 0;

			for (VwodCorretoraTotalVidasPF item : vwodCorretoraTotalVidasPF) {
				
				rowCount++;
				Row row = sheet.createRow(rowCount);

				row.createCell(0).setCellValue(item.getNum_proposta());//01

				stringDT_VENDA = stringDataDefault;
				try {
					stringDT_VENDA = sdf_ddMMyyyy.format(item.getDt_venda());
				} catch (Exception e) {
					log.error("gerarCorretoraTotalVidasPFXLS :: Erro ao formatar data venda. Detalhe: [" + e.getMessage() + "]");
				}
				row.createCell(1).setCellValue(stringDT_VENDA);//02
				

				stringPRIMEIRO_VENCIMENTO = stringDataDefault;
				try {
					stringPRIMEIRO_VENCIMENTO = sdf_ddMMyyyy.format(item.getPrimeiro_vencimento());
				} catch (Exception e) {
					log.error("gerarCorretoraTotalVidasPFXLS :: Erro ao formatar data PRIMEIRO_VENCIMENTO. Detalhe: [" + e.getMessage() + "]");
				}
				row.createCell(2).setCellValue(stringPRIMEIRO_VENCIMENTO);//03		
				
				row.createCell(3).setCellValue(item.getCorretora());//04
				row.createCell(4).setCellValue(item.getCnpj_corretora());//05
				row.createCell(5).setCellValue(item.getNome_forca());//06
				row.createCell(6).setCellValue(item.getCpf_forca());//07
				row.createCell(7).setCellValue(item.getPlano_pf());//08
				row.createCell(8).setCellValue(item.getTipo_plano());//09

				String strTotal_vidas = "(n/a)";
				if(item.getVidas() != null) {
					strTotal_vidas = item.getVidas().toString();
				}
				row.createCell(9).setCellValue(strTotal_vidas);//10

				row.createCell(10).setCellValue(item.getValor_venda());
				row.createCell(11).setCellValue(item.getStatus_proposta());

				row.createCell(12).setCellValue(item.getCpf_titular());
				row.createCell(13).setCellValue(item.getNome_titular());
				row.createCell(14).setCellValue(item.getLogradouro());
				row.createCell(15).setCellValue(item.getNumero());
				row.createCell(16).setCellValue(item.getComplemento());
				row.createCell(17).setCellValue(item.getBairro());
				row.createCell(18).setCellValue(item.getCidade());
				row.createCell(19).setCellValue(item.getUf());
				row.createCell(20).setCellValue(item.getCep());
				row.createCell(21).setCellValue(item.getEmail());
	
			}
			
			log.info("for (... item : vwodCorretoraTotalVidasPF); rowCount=[" + rowCount + "]");
			
			ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
			workbook.write(fileOut);
			//fileOut.close();
			workbook.close();

			log.info("gerarCorretoraTotalVidasPFXLS - fim");
			return fileOut.toByteArray();

		} catch (Exception e) {
			String msgErro = "gerarCorretoraTotalVidasPFXLS; Erro; Message:["+ e.getMessage() +"]; Cause:["+ e.getCause() +"]";
			throw new Exception(msgErro, e);
		}
		
	}

}

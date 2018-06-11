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

import br.com.odontoprev.portal.corretor.model.VwodCorretoraTotalVidas;

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
	public byte[] gerarCorretoraTotalVidasXLS(List<VwodCorretoraTotalVidas> vwodCorretoraTotalVidas) throws Exception { 

		log.info("gerarCorretoraTotalVidasXLS - ini");
		
		String stringDataVenda = "";
		String stringDataVendaDefault = "00/00/0000";
		SimpleDateFormat sdfDataNascimento = new SimpleDateFormat("dd/MM/yyyy");

		//String pathVidas = PropertiesUtils.getProperty(PropertiesUtils.PATH_XLS_VIDAS); //201803050304 2kill
		//String pathVidas = PATH_XLS_VIDAS;

		try {

			//String filename = pathVidas + tbEmpresa.getEmpDcms() + "_" + Data() + ".xls";
			//String filename = pathVidas + vwodCorretoraTotalVidas.get(0).getLogin_dcms() + "_" + formataData_yyyyMMddHHmmss() + ".xls";
//			String filename = "C:\\Users\\Vm8.1\\Desktop\\ArquivosTestes\\" + tbEmpresa.getEmpDcms() + "_" + Data() + ".xls";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("CorretoraTotalVidas");

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

			for (VwodCorretoraTotalVidas item : vwodCorretoraTotalVidas) {
				
				rowCount++;
				Row row = sheet.createRow(rowCount);

				stringDataVenda = stringDataVendaDefault;
				try {
					stringDataVenda = sdfDataNascimento.format(item.getDtVenda());
				} catch (Exception e) {
					log.error("gerarCorretoraTotalVidasXLS :: Erro ao formatar data venda. Detalhe: [" + e.getMessage() + "]");
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
			
			//FileOutputStream fileOut = new FileOutputStream(filename);
			ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
			workbook.write(fileOut);
			//fileOut.close();
			workbook.close();

			//String msgOk = "gerarCorretoraTotalVidasXLS; OK; filename:["+ filename +"]";
			//log.info(msgOk);

			log.info("gerarCorretoraTotalVidasXLS - fim");
			//System.out.println("Sucesso!");
			//return filename;
			return fileOut.toByteArray();

		} catch (Exception e) {
			//System.out.println("Fracasso!");
			String msgErro = "GerarEmpresaXLS; Erro; Message:["+ e.getMessage() +"]; Cause:["+ e.getCause() +"]";
			throw new Exception(msgErro, e);
		}
		
	}

}

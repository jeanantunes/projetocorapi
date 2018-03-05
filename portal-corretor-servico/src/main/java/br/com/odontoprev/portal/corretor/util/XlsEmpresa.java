package br.com.odontoprev.portal.corretor.util;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;

import br.com.odontoprev.portal.corretor.business.VendaPFBusiness;
import br.com.odontoprev.portal.corretor.model.TbodEmpresa;
import br.com.odontoprev.portal.corretor.model.TbodEndereco;

public class XlsEmpresa {

//	@Value("${server.path.empresa}")
//	private String PATH_XLS_EMPRESA;
	
	private static final Log log = LogFactory.getLog(VendaPFBusiness.class);

	public String montaData_ddMMyyyy_HHmm() {

		Date data = new Date();
		SimpleDateFormat formatador = new SimpleDateFormat("ddMMyyyy");
		String dia = formatador.format(data);

		SimpleDateFormat ftd = new SimpleDateFormat("HHmm");
		String hora = ftd.format(data.getTime());

		String total = dia + "_" + hora;

		return total;
	}

	public void GerarEmpresaXLS(TbodEmpresa emp, long dataFatura) throws Exception {

		try {

			String[] empresa = new String[20];
			empresa[0] = emp.getCnpj();
			empresa[1] = emp.getIncEstadual();
			empresa[2] = emp.getRamoAtividade();
			empresa[3] = emp.getRazaoSocial();
			empresa[4] = emp.getNomeFantasia();
			empresa[5] = emp.getRepresentanteLegal();
			empresa[6] = String.valueOf(emp.getContatoEmpresa());
			empresa[7] = emp.getTelefone();
			empresa[8] = emp.getCelular();
			empresa[9] = emp.getEmail();
			TbodEndereco tbEndereco = emp.getTbodEndereco();
			empresa[10] = tbEndereco.getCep();
			empresa[11] = tbEndereco.getLogradouro();
			empresa[12] = tbEndereco.getNumero();
			empresa[13] = tbEndereco.getComplemento();
			empresa[14] = tbEndereco.getBairro();
			empresa[15] = tbEndereco.getCidade();
			empresa[16] = tbEndereco.getUf();
			// empresa[17] = String.valueOf(emp.isMesmo_endereco_correspondencia());
			empresa[17] = "";
			empresa[18] = String.valueOf(dataFatura);
			empresa[19] = emp.getCnae();

			// Tratamento de CNPJ
			String newcnpj = empresa[0].replaceAll("[.]", "").replaceAll("/", "");
			
			String pathEmpresa = PropertiesUtils.getProperty(PropertiesUtils.PATH_XLS_EMPRESA); //201803050304 2kill
			//String pathEmpresa = PATH_XLS_EMPRESA;

//			String filename = "C:\\Users\\Vm8.1\\Desktop\\Arquivos\\" + empresa[16] + "_" + newcnpj + "_" + Data() + ".xls";
			
			String filename = pathEmpresa + empresa[16] + "_" + newcnpj + "_" + montaData_ddMMyyyy_HHmm() + ".xls";

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Empresa");

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("CNPJ");
			rowhead.createCell(1).setCellValue("INSCRICAO ESTADUAL");
			rowhead.createCell(2).setCellValue("RAMO DE ATIVIDADE");
			rowhead.createCell(3).setCellValue("RAZAO SOCIAL ");
			rowhead.createCell(4).setCellValue("NOME FANTASIA");
			rowhead.createCell(5).setCellValue("REPRESENTANTE LEGAL");
			rowhead.createCell(6).setCellValue("REPRESENTANTE CONTATO NA EMPRESA?");
			rowhead.createCell(7).setCellValue("TELEFONE");
			rowhead.createCell(8).setCellValue("CELULAR");
			rowhead.createCell(9).setCellValue("E-MAIL");
			rowhead.createCell(10).setCellValue("CEP");
			rowhead.createCell(11).setCellValue("ENDERECO");
			rowhead.createCell(12).setCellValue("NUMERO");
			rowhead.createCell(13).setCellValue("COMPLEMENTO");
			rowhead.createCell(14).setCellValue("BAIRRO");
			rowhead.createCell(15).setCellValue("CIDADE");
			rowhead.createCell(16).setCellValue("ESTADO");
			rowhead.createCell(17).setCellValue("MESMO ENDERECO CORRESPONDENCIA?");
			rowhead.createCell(18).setCellValue("VENCIMENTO DA FATURA");
			rowhead.createCell(19).setCellValue("CNAE");

			HSSFRow row = sheet.createRow((short) 1);
			for (int i = 0; i < 20; i++) {
				row.createCell(i).setCellValue(empresa[i]);
			}

			FileOutputStream fileOut = new FileOutputStream(filename);
			workbook.write(fileOut);
			fileOut.close();
//			workbook.close();
			
			String msgOk = "GerarEmpresaXLS; OK; filename:["+ filename +"]";
			log.info(msgOk);

			//System.out.println("Sucesso!");

		} catch (Exception e) {
			//System.out.println("Fracasso!");
			String msgErro = "GerarEmpresaXLS; Erro; Message:["+ e.getMessage() +"]; Cause:["+ e.getCause() +"]";
			throw new Exception(msgErro, e);
		}
	}
}

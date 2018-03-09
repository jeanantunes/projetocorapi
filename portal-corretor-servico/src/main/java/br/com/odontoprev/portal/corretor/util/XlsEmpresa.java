package br.com.odontoprev.portal.corretor.util;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.odontoprev.portal.corretor.business.VendaPFBusiness;
import br.com.odontoprev.portal.corretor.dto.Empresa;
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

	public void GerarEmpresaXLS(TbodEmpresa tbEmpresa, Empresa empresaDto) throws Exception {

		try {

			String[] empresaArr = new String[22];
			empresaArr[0] = tbEmpresa.getCnpj();
			empresaArr[1] = tbEmpresa.getIncEstadual();
			empresaArr[2] = tbEmpresa.getRamoAtividade();
			empresaArr[3] = tbEmpresa.getRazaoSocial();
			empresaArr[4] = tbEmpresa.getNomeFantasia();
			empresaArr[5] = tbEmpresa.getRepresentanteLegal();
			empresaArr[6] = String.valueOf(tbEmpresa.getContatoEmpresa());
			empresaArr[7] = tbEmpresa.getTelefone();
			empresaArr[8] = tbEmpresa.getCelular();
			empresaArr[9] = tbEmpresa.getEmail();
			TbodEndereco tbEndereco = tbEmpresa.getTbodEndereco();
			empresaArr[10] = tbEndereco.getCep();
			empresaArr[11] = tbEndereco.getLogradouro();
			empresaArr[12] = tbEndereco.getNumero();
			empresaArr[13] = tbEndereco.getComplemento();
			empresaArr[14] = tbEndereco.getBairro();
			empresaArr[15] = tbEndereco.getCidade();
			empresaArr[16] = tbEndereco.getUf();
			// empresa[17] = String.valueOf(emp.isMesmo_endereco_correspondencia());
			empresaArr[17] = "";
			empresaArr[18] = String.valueOf(empresaDto.getVencimentoFatura());
			empresaArr[19] = tbEmpresa.getCnae();
			empresaArr[20] = empresaDto.getCnpjCorretora();
			empresaArr[21] = empresaDto.getNomeCorretora();

			// Tratamento de CNPJ
			String newcnpj = empresaArr[0].replaceAll("[.]", "").replaceAll("/", "");
			
			String pathEmpresa = PropertiesUtils.getProperty(PropertiesUtils.PATH_XLS_EMPRESA); //201803050304 2kill
			//String pathEmpresa = PATH_XLS_EMPRESA;

//			String filename = "C:\\Users\\Vm8.1\\Desktop\\ArquivosTestes\\" + empresaArr[16] + "_" + newcnpj + "_" + montaData_ddMMyyyy_HHmm() + ".xls";
			
			String filename = pathEmpresa + empresaArr[16] + "_" + newcnpj + "_" + montaData_ddMMyyyy_HHmm() + ".xls";

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
			rowhead.createCell(20).setCellValue("CNPJ CORRETORA");
			rowhead.createCell(21).setCellValue("NOME CORRETORA");

			HSSFRow row = sheet.createRow((short) 1);
			for (int i = 0; i < 22; i++) {
				row.createCell(i).setCellValue(empresaArr[i]);
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

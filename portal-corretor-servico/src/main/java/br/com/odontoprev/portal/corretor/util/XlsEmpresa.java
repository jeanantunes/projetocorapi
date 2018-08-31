package br.com.odontoprev.portal.corretor.util;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.ManagedBean;
import javax.transaction.RollbackException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.business.VendaPFBusiness;
import br.com.odontoprev.portal.corretor.model.TbodEmpresa;
import br.com.odontoprev.portal.corretor.model.TbodEndereco;
import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
import br.com.odontoprev.portal.corretor.model.TbodVenda;

@ManagedBean //201806281739 - esert - COR-348 rollback vendapme
@Transactional(rollbackFor={Exception.class}) //201806281719 - esert - rollback vendapme COR-348
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

	@Transactional(rollbackFor={Exception.class}) //201806281719 - esert - rollback vendapme COR-348
	public void GerarEmpresaXLS(TbodVenda tbodVenda) throws Exception {
		log.info("GerarEmpresaXLS(); ini");

		try {

			TbodEmpresa tbodEmpresa = tbodVenda.getTbodEmpresa();
			TbodForcaVenda tbodForcaVenda = tbodVenda.getTbodForcaVenda();

			String[] empresaArr = new String[27];
			empresaArr[0] = tbodEmpresa.getCnpj();
			empresaArr[1] = tbodEmpresa.getIncEstadual();
			empresaArr[2] = tbodEmpresa.getRamoAtividade();
			empresaArr[3] = tbodEmpresa.getRazaoSocial();
			empresaArr[4] = tbodEmpresa.getNomeFantasia();
			empresaArr[5] = tbodEmpresa.getRepresentanteLegal();
			empresaArr[6] = String.valueOf(tbodEmpresa.getContatoEmpresa());
			empresaArr[7] = tbodEmpresa.getTelefone();
			empresaArr[8] = tbodEmpresa.getCelular();
			empresaArr[9] = tbodEmpresa.getEmail();
			TbodEndereco tbodEndereco = tbodEmpresa.getTbodEndereco();
			empresaArr[10] = tbodEndereco.getCep();
			empresaArr[11] = tbodEndereco.getLogradouro();
			empresaArr[12] = tbodEndereco.getNumero();
			empresaArr[13] = tbodEndereco.getComplemento();
			empresaArr[14] = tbodEndereco.getBairro();
			empresaArr[15] = tbodEndereco.getCidade();
			empresaArr[16] = tbodEndereco.getUf();
			// empresa[17] = String.valueOf(emp.isMesmo_endereco_correspondencia());
			empresaArr[17] = "";
			empresaArr[18] = String.valueOf(tbodVenda.getFaturaVencimento());
			empresaArr[19] = tbodEmpresa.getCnae();
			empresaArr[20] = tbodVenda.getTbodCorretora().getCnpj();
			empresaArr[21] = tbodVenda.getTbodCorretora().getRazaoSocial();
			empresaArr[22] = (tbodForcaVenda != null ? tbodForcaVenda.getNome() : " "); 
			empresaArr[23] = (tbodForcaVenda != null ? tbodForcaVenda.getEmail() : " "); 
			empresaArr[24] = (tbodForcaVenda != null ? tbodForcaVenda.getCelular() : " ");

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			empresaArr[25] = sdf.format(tbodVenda.getDtVigencia()); //201806141829 - esert - (COR-314 acrescentar os dois novos campos no arquivo empresa XLS)
			empresaArr[26] = sdf.format(tbodVenda.getDtMovimentacao()); //201806141829 - esert - (COR-314 acrescentar os dois novos campos no arquivo empresa XLS)

			// Tratamento de CNPJ
			String newcnpj = empresaArr[0].replaceAll("[.]", "").replaceAll("/", "");
			
			String pathEmpresa = PropertiesUtils.getProperty(PropertiesUtils.PATH_XLS_EMPRESA);
			//String pathEmpresa = PATH_XLS_EMPRESA;
			log.info("pathEmpresa:[" + pathEmpresa + "]");

//			String filename = "C:\\Users\\Vm8.1\\Desktop\\ArquivosTestes\\" + empresaArr[16] + "_" + newcnpj + "_" + empresaDto.getNomeCorretora() + ".xls";
			
			//String filename = "C:\\planilhaUploadOdpv\\" + empresaArr[16] + "_" + newcnpj + "_" + empresaDto.getNomeCorretora() + ".xls";
			String filename = pathEmpresa + empresaArr[16] + "_" + newcnpj + "_" + tbodVenda.getTbodCorretora().getRazaoSocial() + ".xls";
			log.info("filename:[" + filename + "]");

			@SuppressWarnings("resource")
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
			rowhead.createCell(22).setCellValue("NOME FORCA");
			rowhead.createCell(23).setCellValue("EMAIL FORCA");
			rowhead.createCell(24).setCellValue("TELEFONE FORCA");
			rowhead.createCell(25).setCellValue("DATA VIGENCIA"); //201806141829 - esert - (COR-314 acrescentar os dois novos campos no arquivo empresa XLS)
			rowhead.createCell(26).setCellValue("DATA MOVIMENTACAO"); //201806141829 - esert - (COR-314 acrescentar os dois novos campos no arquivo empresa XLS)

			HSSFRow row = sheet.createRow((short) 1);
//			for (int i = 0; i < 27; i++) {
			for (int i = 0; i < rowhead.getPhysicalNumberOfCells(); i++) { //201806141855 - esert - teste com rowhead.getPhysicalNumberOfCells()
				row.createCell(i).setCellValue(empresaArr[i]);
			}

			FileOutputStream fileOut = new FileOutputStream(filename);
			workbook.write(fileOut);
			fileOut.close();
//			workbook.close();
			
			String msgOk = "GerarEmpresaXLS; OK; filename:["+ filename +"]";
			log.info(msgOk);

			//System.out.println("Sucesso!");
			log.info("GerarEmpresaXLS(); fim");
			

		} catch (Exception e) {
			//System.out.println("Fracasso!");
			String msgErro = "GerarEmpresaXLS; Erro; Message:["+ e.getMessage() +"]";
			log.error(msgErro);
//			throw new Exception(msgErro, e);
			throw new RollbackException(msgErro); //201805281522 - esert - forcar erro transacao para causar rollback - teste 
		}
	}
}

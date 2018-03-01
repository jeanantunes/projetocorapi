package br.com.odontoprev.portal.corretor.util;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import br.com.odontoprev.portal.corretor.model.TbodEmpresa;
import br.com.odontoprev.portal.corretor.model.TbodVida;

public class XlsVidas {

	private static final Log log = LogFactory.getLog(XlsVidas.class);

	public String Data() {

		Date data = new Date();
		SimpleDateFormat formatador = new SimpleDateFormat("ddMMyyyy");
		String dia = formatador.format(data);

		SimpleDateFormat ftd = new SimpleDateFormat("HHmmss");
		String hora = ftd.format(data.getTime());

		String total = dia + "_" + hora;

		return total;
	}

	public void gerarVidasXLS(List<TbodVida> vidas, TbodEmpresa tbEmpresa) {

		String dataStr = "00/00/0000";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String pathVidas = PropertiesUtils.getProperty(PropertiesUtils.PATH_XLS_VIDAS);

		try {

//			String filename = pathVidas + Data() + ".xls";
			String filename = "C:\\Users\\Vm8.1\\Desktop\\Arquivos\\" + Data() + ".xls";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Vidas");

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("NOME DO BENEFICIARIO");
			rowhead.createCell(1).setCellValue("INDENTIFICACAO DO BENIFICIARIO");
			rowhead.createCell(2).setCellValue("PLANO");
			rowhead.createCell(3).setCellValue("DATA DE NASCIMENTO");
			rowhead.createCell(4).setCellValue("TIPO DE ENDERECO");
			rowhead.createCell(5).setCellValue("ENDERECO");
			rowhead.createCell(6).setCellValue("NUM. DO LOGRADOURO");
			rowhead.createCell(7).setCellValue("BAIRRO");
			rowhead.createCell(8).setCellValue("COMPLEMENTO DO ENDERECO");
			rowhead.createCell(9).setCellValue("CEP");
			rowhead.createCell(10).setCellValue("ESTADO/UF");
			rowhead.createCell(11).setCellValue("CIDADE");
			rowhead.createCell(12).setCellValue("CIDADE BENEFICIARIO");
			rowhead.createCell(13).setCellValue("TELEFONE RESINDECIAL");
			rowhead.createCell(14).setCellValue("CARTEIRA DE IDENTIDADE");
			rowhead.createCell(15).setCellValue("ORGAO EMISSOR");
			rowhead.createCell(16).setCellValue("ESTADO CIVIL");
			rowhead.createCell(17).setCellValue("DEPARTAMENTO");
			rowhead.createCell(18).setCellValue("PARENTESCO DOS DEPENDENTES");
			rowhead.createCell(19).setCellValue("NUM. DA CARTERINHA");
			rowhead.createCell(20).setCellValue("ACAO");
			rowhead.createCell(21).setCellValue("NUM. DE REGISTRO DO FUNCIONARIO NA EMPRESA");
			rowhead.createCell(22).setCellValue("CPF");
			rowhead.createCell(23).setCellValue("SEXO");
			rowhead.createCell(24).setCellValue("NOME DA MAE");
			rowhead.createCell(25).setCellValue("PIS");
			rowhead.createCell(26).setCellValue("NUM. DECLARACAO DE NASCIDO VIVO");
			rowhead.createCell(27).setCellValue("CODIGO DA EMPRESA");
			rowhead.createCell(28).setCellValue("E-MAIL");
			rowhead.createCell(29).setCellValue("CNS");
			rowhead.createCell(30).setCellValue("MOTIVOS DE EXCLUSAO");
			rowhead.createCell(31).setCellValue("HOUVE CONTRIBUICAO?");
			rowhead.createCell(32).setCellValue("PERIODO DE CONTRIBUICAO");
			rowhead.createCell(33).setCellValue("FOI INFORMADO DO DIREITO DE PERMANENCIA");
			rowhead.createCell(34).setCellValue("EMPRESA NOVA");
			rowhead.createCell(35).setCellValue("NUMERO DO BANCO");
			rowhead.createCell(36).setCellValue("NUMERO DA AGENCIA");
			rowhead.createCell(37).setCellValue("NUMERO DA CONTA CORRENTE");
			rowhead.createCell(38).setCellValue("DIGITO CONTA CORRENTE");
			rowhead.createCell(39).setCellValue("DIGITO AGENCIA");
			rowhead.createCell(40).setCellValue("DATA DE ASSOCIACAO");
			rowhead.createCell(41).setCellValue("DATA DE INATIVACAO");

			int rowCount = 1;

			for (TbodVida tbVida : vidas) {
				boolean isTitular = tbVida.getTbodVida() == null ? true : false;

				String[] vidasArr = new String[42];

				// Obrigatorio 70 carac
				vidasArr[0] = tbVida.getNome().length() > 70 ? tbVida.getNome().substring(0, 70) : tbVida.getNome();
				// T titular D Dependente Obrigatorio
				vidasArr[1] = isTitular ? "T" : "D";
				// Obrigatorio TBOD_VENDA_VIDA.CD_PLANO

				vidasArr[2] = tbVida.getSiglaPlano();
				// Obrigatorio dd/MM/yyyy
				try {
					dataStr = sdf.format(tbVida.getDataNascimento());
				} catch (Exception e) {
					log.error("XlsVidas :: Erro ao formatar data de nascimento. Detalhe: [" + e.getMessage() + "]");
				}
				vidasArr[3] = dataStr;
				// Sempre 1
				vidasArr[4] = "1";
				// Logradouro (Endereco da empresa) Obrigatorio
				if (tbEmpresa.getTbodEndereco() != null) {
					vidasArr[5] = tbEmpresa.getTbodEndereco().getLogradouro();
					// Obrigatorio
					vidasArr[6] = tbEmpresa.getTbodEndereco().getNumero();
					// 20 caract
					vidasArr[7] = tbEmpresa.getTbodEndereco().getBairro().length() > 20
							? tbEmpresa.getTbodEndereco().getBairro().substring(0, 20)
							: "";
					// 20 caract
					vidasArr[8] = tbEmpresa.getTbodEndereco().getComplemento() != null
							? tbEmpresa.getTbodEndereco().getComplemento().length() > 20
									? tbEmpresa.getTbodEndereco().getComplemento().substring(0, 20)
									: tbEmpresa.getTbodEndereco().getComplemento()
							: "";
					// Obrigatorio
					vidasArr[9] = tbEmpresa.getTbodEndereco().getCep();
					// Obrig 2 caract
					vidasArr[10] = tbEmpresa.getTbodEndereco().getUf();
					// (Ate aqui eh empresa
					vidasArr[11] = tbEmpresa.getTbodEndereco().getCidade();
					// mesmo q cidade
					vidasArr[12] = tbEmpresa.getTbodEndereco().getCidade();
				}
				vidasArr[13] = ""; // nada
				vidasArr[14] = ""; // nada
				vidasArr[15] = ""; // nada
				vidasArr[16] = ""; // nada
				vidasArr[17] = ""; // nada
				vidasArr[18] = "Outros"; // Fixo "Outros" Obrigat
				vidasArr[19] = ""; // nada
				vidasArr[20] = "I"; // Fixo "I" (Inclusao) Obrigatorio
				// cpf do titular (para titular e seus dependentes) Obrig
				vidasArr[21] = isTitular ? tbVida.getCpf() : tbVida.getTbodVida().getCpf();
				// cpf do titular Obriga
				vidasArr[22] = isTitular ? tbVida.getCpf() : tbVida.getTbodVida().getCpf();
				vidasArr[23] = tbVida.getSexo(); // Obrig
				vidasArr[24] = tbVida.getNomeMae(); // Obrig 70 carac
				vidasArr[25] = ""; // nada
				vidasArr[26] = "";// nada
				vidasArr[27] = tbEmpresa.getEmpDcms();// gerado pelo dcms obrig TBOD_EMPRESA.EMP_DCMS
				vidasArr[28] = ""; // nada
				vidasArr[29] = ""; // nada
				vidasArr[30] = ""; // nda
				vidasArr[31] = ""; // nada
				vidasArr[32] = ""; // nada
				vidasArr[33] = ""; // nada
				vidasArr[34] = ""; // nada
				vidasArr[35] = ""; // nada
				vidasArr[36] = ""; // nada
				vidasArr[37] = ""; // nada
				vidasArr[38] = ""; // nada
				vidasArr[39] = ""; // nada
				vidasArr[40] = ""; // nada
				vidasArr[41] = ""; // nada

				Row row = sheet.createRow(rowCount);

				for (int i = 0; i < 42; i++) {
					row.createCell(i).setCellValue(vidasArr[i]);
				}

				rowCount++;

			}
			FileOutputStream fileOut = new FileOutputStream(filename);
			workbook.write(fileOut);
			fileOut.close();
			// workbook.close();

			log.info("Sucesso!");
		} catch (Exception e) {
			log.error("Fracasso!");

		}
		log.info("Escreveu Planilha Vidas");
	}
}

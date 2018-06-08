package br.com.odontoprev.portal.corretor.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import br.com.odontoprev.portal.corretor.dto.RelatorioGestaoVenda;
import br.com.odontoprev.portal.corretor.model.VwodCorretoraTotalVidas;

public class CsvUtil {

		private static final Log log = LogFactory.getLog(CsvUtil.class);

		public static void gerarCsvRelatorioGestaoVendas(HttpServletResponse response, List<RelatorioGestaoVenda> gestaoVendas) {

			log.info("gerarCsvRelatorioGestaoVendas - ini"); //201806081902 - esert

			try {

				String csvFileName = "relatorio-gestao-vendas.csv";

				response.setContentType(String.valueOf(MediaType.TEXT_PLAIN_VALUE));

				String headerKey = "Content-Disposition";
		        String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
		        response.setHeader(headerKey, headerValue);

				ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				        CsvPreference.STANDARD_PREFERENCE);

				String[] header = {
						"DataVenda",
						"Nome",
						"Cpf",
						"DataNascimento",
						"NomeMae",
						"Celular",
						"Email",
						"Cep",
						"Logradouro",
						"Numero",
						"Bairro",
						"Complemento",
						"Cidade",
						"UF",
						"NomePlano",
						"TipoPlano",
						"ValorMensal",
						"ValorAnual",
						"ForcaNome" //201805301905 - esert/jack - COR-8 - ajuste
					};

				csvWriter.writeHeader(header);

				for (RelatorioGestaoVenda venda : gestaoVendas) {
					csvWriter.write(venda, header);
			    }

			    csvWriter.close();

			    log.info("CSV criado com sucesso!");
				log.info("gerarCsvRelatorioGestaoVendas - fim"); //201806081902 - esert
			}
			catch (IOException e) {
				log.error("Erro ao tentar realizar download Relatorio Gestao Vendas. Detalhe: [" + e.getMessage() + "]");
			} catch (Exception e) {
				log.error("Erro ao tentar realizar download Relatorio Gestao Vendas. Detalhe: [" + e.getMessage() + "]");
			}
		}

		//201806081903 - esert - relatorio corretora total vidas pme deve retornar XLS
		public static void gerarCsvRelatorioCorretoraTotalVidasPME(HttpServletResponse response, List<VwodCorretoraTotalVidas> corretoraTotalVidas) {
			
			log.info("gerarCsvRelatorioCorretoraTotalVidasPME - ini");
			
			try {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String stringDataHoraAgorayyyyMMddHHmmss = sdf.format(new Date());
				String csvFileName = "relatorio-corretora-total-vidas-" + stringDataHoraAgorayyyyMMddHHmmss + ".csv";
				
				response.setContentType(String.valueOf(MediaType.TEXT_PLAIN_VALUE));
				
				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
				response.setHeader(headerKey, headerValue);
				
				ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
						CsvPreference.STANDARD_PREFERENCE);
				
				String[] header = {
						"dtVenda",
						"cnpj_corretora",
						"corretora",
						"cpf",
						"vendedor",
						"cnpj_cliente",
						"razao_social_cliente",
						"login_dcms",
						"logradouro",
						"numero",
						"complemento",
						"bairro",
						"cidade",
						"uf",
						"cep",
						"representante_legal",
						"celular",
						"email",
						"plano",
						"total_vidas",
						"dia_fatura"
				};
				
				csvWriter.writeHeader(header);
				
				for (VwodCorretoraTotalVidas venda : corretoraTotalVidas) {
					csvWriter.write(venda, header);
				}
				
				csvWriter.close();
				
				log.info("CSV criado com sucesso!");
				log.info("gerarCsvRelatorioCorretoraTotalVidasPME - fim");
			}
			catch (IOException e) {
				log.error("Erro ao tentar realizar download Relatorio Corretora Total Vidas PME. Detalhe: [" + e.getMessage() + "]");
			} catch (Exception e) {
				log.error("Erro ao tentar realizar download Relatorio Corretora Total Vidas PME. Detalhe: [" + e.getMessage() + "]");
			}
		}
	}
package br.com.odontoprev.portal.corretor.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import br.com.odontoprev.portal.corretor.dto.RelatorioGestaoVenda;

public class CsvUtil {

		private static final Log log = LogFactory.getLog(CsvUtil.class);

		public static void gerarCsv(HttpServletResponse response, List<RelatorioGestaoVenda> gestaoVendas) {

			log.info("gerarCsv");

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
			}
			catch (IOException e) {
				log.error("Erro ao tentar realizar download Relatorio Gestao Vendas. Detalhe: [" + e.getMessage() + "]");
			} catch (Exception e) {
				log.error("Erro ao tentar realizar download Relatorio Gestao Vendas. Detalhe: [" + e.getMessage() + "]");
			}
		}
	}
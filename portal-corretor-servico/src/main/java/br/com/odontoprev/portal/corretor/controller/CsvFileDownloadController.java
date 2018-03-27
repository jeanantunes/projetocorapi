package br.com.odontoprev.portal.corretor.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import br.com.odontoprev.portal.corretor.dto.RelatorioGestaoVenda;
import br.com.odontoprev.portal.corretor.service.RelatorioGestaoVendaService;

@RestController
public class CsvFileDownloadController {
	
	private static final Log log = LogFactory.getLog(CsvFileDownloadController.class);
	
	@Autowired
	RelatorioGestaoVendaService relatorioGestaoVendaService;
	
	@RequestMapping(value = "/downloadCSV/{cnpj}", method = { RequestMethod.GET })
	public void downloadCSV(@PathVariable String cnpj, HttpServletResponse response) {
		
		log.info("downloadCSV");
		
		try {
			
			List<RelatorioGestaoVenda> gestaoVendas = relatorioGestaoVendaService.findVendasByCorretora(cnpj);
			
			String csvFileName = "relatorio-gestao-vendas.csv";
			
			response.setContentType("text/csv");
			
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
					"ValorAnual"
				};
			
			csvWriter.writeHeader(header);
			
			for (RelatorioGestaoVenda venda : gestaoVendas) {
				csvWriter.write(venda, header);
		    }
			
		    csvWriter.close();

		} 
		catch (IOException e) {
			log.error("Erro ao tentar realizar download Relatorio Gestao Vendas. Detalhe: [" + e.getMessage() + "]");
		} catch (Exception e) {
			log.error("Erro ao tentar realizar download Relatorio Gestao Vendas. Detalhe: [" + e.getMessage() + "]");
		} 
		
	}

}

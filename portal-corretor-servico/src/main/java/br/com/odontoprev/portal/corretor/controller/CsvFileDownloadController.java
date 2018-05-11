package br.com.odontoprev.portal.corretor.controller;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.RelatorioGestaoVenda;
import br.com.odontoprev.portal.corretor.service.RelatorioGestaoVendaService;
import br.com.odontoprev.portal.corretor.util.CsvUtil;

@RestController
public class CsvFileDownloadController {
	
	private static final Log log = LogFactory.getLog(CsvFileDownloadController.class);
	
	@Autowired
	RelatorioGestaoVendaService relatorioGestaoVendaService;
	
	@RequestMapping(value = "downloadCSV/{cnpj}", method = { RequestMethod.GET })
	public void downloadCSV(@PathVariable String cnpj, HttpServletResponse response) {
		
		log.info("downloadCSV");
		
		List<RelatorioGestaoVenda> gestaoVendas = relatorioGestaoVendaService.findVendasByCorretora(cnpj);
		
		if(gestaoVendas != null && !gestaoVendas.isEmpty()) {
			CsvUtil.gerarCsv(response, gestaoVendas);
		}
		
	}

	

}

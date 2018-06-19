package br.com.odontoprev.portal.corretor.controller;


import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.CorretoraTotalVidasPME;
import br.com.odontoprev.portal.corretor.dto.RelatorioGestaoVenda;
import br.com.odontoprev.portal.corretor.model.VwodCorretoraTotalVidasPME;
import br.com.odontoprev.portal.corretor.service.PropostaService;
import br.com.odontoprev.portal.corretor.service.RelatorioGestaoVendaService;
import br.com.odontoprev.portal.corretor.util.CsvUtil;

@RestController
public class CsvFileDownloadController {
	
	private static final Log log = LogFactory.getLog(CsvFileDownloadController.class);
	
	@Autowired
	RelatorioGestaoVendaService relatorioGestaoVendaService;
	
	@Autowired
	PropostaService propostaService;
	
	@RequestMapping(value = "downloadCSV/{cnpj}", method = { RequestMethod.GET })
	public void downloadCSV(@PathVariable String cnpj, HttpServletResponse response) {
		
		log.info("downloadCSV");
		
		List<RelatorioGestaoVenda> gestaoVendas = relatorioGestaoVendaService.findVendasByCorretora(cnpj);
		
		if(gestaoVendas != null && !gestaoVendas.isEmpty()) {
			CsvUtil.gerarCsvRelatorioGestaoVendas(response, gestaoVendas);
		}
		
	}

	//201806081840 - esert - relatorio venda pme deve retornar XLS
	@RequestMapping(value = "downloadcsv/corretoratotalvidaspme/{dtVendaInicio}/{dtVendaFim}/{cnpjCorretora}", method = { RequestMethod.GET })
	public void corretoratotalvidaspmeCSV(
			@PathVariable String dtVendaInicio, //yyyy-MM-dd
			@PathVariable String dtVendaFim, //yyyy-MM-dd
			@PathVariable String cnpjCorretora,  //12345678901234 //ex.:64154543000146 Teste Corretora	38330982874	FERNANDO SETAI
			HttpServletResponse response
			) throws ParseException {
		
		log.info("corretoratotalvidaspmeCSV - ini");
		
		CorretoraTotalVidasPME corretoraTotalVidasPME = new CorretoraTotalVidasPME();
		corretoraTotalVidasPME.setDtVendaInicio(dtVendaInicio);
		corretoraTotalVidasPME.setDtVendaFim(dtVendaFim);
		corretoraTotalVidasPME.setCnpjCorretora(cnpjCorretora);
		
		log.info(corretoraTotalVidasPME);

		List<VwodCorretoraTotalVidasPME> corretoraTotalVidas = propostaService.findVwodCorretoraTotalVidasByFiltro(corretoraTotalVidasPME);

		if(corretoraTotalVidas != null && !corretoraTotalVidas.isEmpty()) {
			log.info("corretoraTotalVidas.size():[" + corretoraTotalVidas.size() + "]");
			CsvUtil.gerarCsvRelatorioCorretoraTotalVidasPME(response, corretoraTotalVidas);
		} else {
			log.info("corretoraTotalVidas == null ou corretoraTotalVidas.isEmpty");			
		}
		
		log.info("corretoratotalvidaspmeCSV - fim");

	}
}

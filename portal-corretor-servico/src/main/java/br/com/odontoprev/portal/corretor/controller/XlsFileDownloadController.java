package br.com.odontoprev.portal.corretor.controller;


import br.com.odontoprev.portal.corretor.dto.CorretoraTotalVidasPF;
import br.com.odontoprev.portal.corretor.dto.CorretoraTotalVidasPME;
import br.com.odontoprev.portal.corretor.model.VwodCorretoraTotalVidasPF;
import br.com.odontoprev.portal.corretor.model.VwodCorretoraTotalVidasPME;
import br.com.odontoprev.portal.corretor.service.PropostaService;
import br.com.odontoprev.portal.corretor.util.XlsCorretoraTotalVidas;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

@RestController
public class XlsFileDownloadController {

    private static final Log log = LogFactory.getLog(XlsFileDownloadController.class);

    @Autowired
    PropostaService propostaService;

    @Autowired
    XlsCorretoraTotalVidas xlsCorretoraTotalVidas;

    //201806081840 - esert - relatorio venda pme deve retornar XLS
    @RequestMapping(value = "downloadxls/corretoratotalvidaspme/{dtVendaInicio}/{dtVendaFim}/{cnpjCorretora}", method = {RequestMethod.GET})
    public void corretoraTotalVidasPMEXLS(
            @PathVariable String dtVendaInicio, //yyyy-MM-dd
            @PathVariable String dtVendaFim, //yyyy-MM-dd
            @PathVariable String cnpjCorretora,  //12345678901234 //ex.:64154543000146 Teste Corretora	38330982874	FERNANDO SETAI
            HttpServletResponse response
    ) throws ParseException {

        log.info("corretoraTotalVidasPMEXLS - ini");

        CorretoraTotalVidasPME corretoraTotalVidasPME = new CorretoraTotalVidasPME();
        corretoraTotalVidasPME.setDtVendaInicio(dtVendaInicio);
        corretoraTotalVidasPME.setDtVendaFim(dtVendaFim);
        corretoraTotalVidasPME.setCnpjCorretora(cnpjCorretora);

        log.info(corretoraTotalVidasPME);

        List<VwodCorretoraTotalVidasPME> corretoraTotalVidas = propostaService.findVwodCorretoraTotalVidasByFiltro(corretoraTotalVidasPME);

        if (corretoraTotalVidas != null && !corretoraTotalVidas.isEmpty()) {
            log.info("corretoraTotalVidas.size():[" + corretoraTotalVidas.size() + "]");
            //CsvUtil.gerarCsvRelatorioCorretoraTotalVidasPME(response, corretoraTotalVidas);
            //String filename;
            byte[] fileOut = null;
            try {
                //filename = (new XlsCorretoraTotalVidas()).gerarCorretoraTotalVidasXLS(corretoraTotalVidas);
                fileOut = xlsCorretoraTotalVidas.gerarCorretoraTotalVidasPMEXLS(corretoraTotalVidas);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //response.setContentType(String.valueOf(MediaType.TEXT_PLAIN_VALUE));
            response.setContentType("application/vnd.ms-excel");

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", "relatorio-pme.xls");
            response.setHeader(headerKey, headerValue);
            try {
                response.getOutputStream().write(fileOut); //201806111930 - rmarques/esert
                response.getOutputStream().flush(); //201806111930 - rmarques/esert
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            log.info("corretoraTotalVidas == null ou corretoraTotalVidas.isEmpty");
        }

        log.info("corretoraTotalVidasPMEXLS - fim");

    }

    //201806121218 - esert - relatorio venda pf deve retornar XLS
    @RequestMapping(value = "downloadxls/corretoratotalvidaspf/{dtVendaInicio}/{dtVendaFim}/{cnpjCorretora}", method = {RequestMethod.GET})
    public void corretoraTotalVidasPFXLS(
            @PathVariable String dtVendaInicio,
            @PathVariable String dtVendaFim,
            @PathVariable String cnpjCorretora,
            HttpServletResponse response
    ) throws Exception {

        log.info("corretoraTotalVidasPFXLS - ini");

        CorretoraTotalVidasPF corretoraTotalVidasPF = new CorretoraTotalVidasPF();
        corretoraTotalVidasPF.setDtVendaInicio(dtVendaInicio);
        corretoraTotalVidasPF.setDtVendaFim(dtVendaFim);
        corretoraTotalVidasPF.setCnpjCorretora(cnpjCorretora);

        log.info(corretoraTotalVidasPF);

        List<VwodCorretoraTotalVidasPF> vwodCorretoraTotalVidasPF = propostaService.findVwodCorretoraTotalVidasPFByFiltro(corretoraTotalVidasPF);

        if (vwodCorretoraTotalVidasPF != null && !vwodCorretoraTotalVidasPF.isEmpty()) {
            log.info("vwodCorretoraTotalVidasPF.size():[" + vwodCorretoraTotalVidasPF.size() + "]");
            byte[] fileOut = null;
            fileOut = xlsCorretoraTotalVidas.gerarCorretoraTotalVidasPFXLS(vwodCorretoraTotalVidasPF);

            response.setContentType("application/vnd.ms-excel");

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", "relatorio-pf.xls");
            response.setHeader(headerKey, headerValue);

            response.getOutputStream().write(fileOut);
            response.getOutputStream().flush();

        } else {
            log.info("vwodCorretoraTotalVidasPF==null ou vwodCorretoraTotalVidasPF.isEmpty");
        }

        log.info("corretoraTotalVidasPFXLS - fim");

    }
}

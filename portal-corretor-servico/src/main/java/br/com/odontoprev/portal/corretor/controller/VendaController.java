package br.com.odontoprev.portal.corretor.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

//import com.itextpdf.text.DocumentException;

import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaPME;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;
import br.com.odontoprev.portal.corretor.service.ConvertObjectService;
import br.com.odontoprev.portal.corretor.service.VendaPFService;
import br.com.odontoprev.portal.corretor.util.FileReaderUtil;
import br.com.odontoprev.portal.corretor.util.Html2Pdf;
//import br.com.odontoprev.portal.corretor.util.Html2Pdf;
//import br.com.odontoprev.portal.corretor.util.Html2Pdf.Base64ImageProvider;

@RestController
public class VendaController {
	
	private static final Log log = LogFactory.getLog(VendaController.class);
	
	@Autowired
	VendaPFService vendaPFService;
	
	@Autowired
	ConvertObjectService convertObjectToJson;
	
	@RequestMapping(value = "/vendapf", method = { RequestMethod.POST })
	@Transactional(rollbackFor= {Exception.class}) //201806291652 - esert/rmarques - COR-358 rollback pf
	public VendaResponse addVendaPF(@RequestBody Venda venda, @RequestHeader(value="User-Agent") String userAgent) throws Exception {
		
		log.info(venda);
		
		//convertObjectToJson.addJsonInTable(venda, null, userAgent); //201806282101 - esert - desligamento total do log json antigo
		
		return vendaPFService.addVenda(venda);
	}
	
	@RequestMapping(value = "/vendapme", method = { RequestMethod.POST })
	public VendaResponse addVendaPME(@RequestBody VendaPME vendaPME, @RequestHeader(value="User-Agent") String userAgent) {
		
		log.info(vendaPME);
		
		//convertObjectToJson.addJsonInTable(null, vendaPME, userAgent); //201806282101 - esert - desligamento total do log json antigo
		
		return vendaPFService.addVendaPME(vendaPME);
	}
	
	@RequestMapping(value = "/itextpdftest", method = { RequestMethod.POST })
	public VendaResponse iTextPdfTest(@RequestBody VendaPME vendaPME, @RequestHeader(value="User-Agent") String userAgent) {
		
		log.info(vendaPME);
		
		//convertObjectToJson.addJsonInTable(null, vendaPME, userAgent); //201806282101 - esert - desligamento total do log json antigo
		StringBuffer html = new StringBuffer();
		html.append("<html>");
		html.append("<title>");
		html.append("Teste Title HTML2PDF");
		html.append("</title>");
		html.append("<body>");
		html.append("<div>");
		html.append("<p>");
		html.append("Teste Body HTML2PDF");
		html.append("</p>");
		html.append("</div>");
		html.append("</body>");
		html.append("</html>");
		
		try {
			File fileOut = new File("c://arquivos_gerados//pme_pdf//out.pdf");
			Html2Pdf html2Pdf = new Html2Pdf(html.toString());
			html2Pdf.setImageProvider(new br.com.odontoprev.portal.corretor.util.Html2Pdf.Base64ImageProvider());
			try {
				html2Pdf.convert(fileOut);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		
		//return vendaPFService.addVendaPME(vendaPME);
		return new VendaResponse(HttpStatus.OK.value(), HttpStatus.OK.name());
	}

	
	@RequestMapping(value = "/itextpdftest2", method = { RequestMethod.POST })
	public VendaResponse iTextPdfTest2(@RequestBody VendaPME vendaPME, @RequestHeader(value="User-Agent") String userAgent) {
		log.info("iTextPdfTest2 - ini");
		try{
			//String htmlPathFileName = "emktSemCSS.html"; 
			//String htmlPathFileName = "emktSemCSSSimples1.html"; 
			String htmlPathFileName = "c:\\vector\\workspaceEdu\\est-portalcorretor-api\\portal-corretor-servico\\src\\main\\resources\\templates\\emktSemCSSSimples1.html"; 
			log.info("htmlPathFileName:[" + htmlPathFileName + "]");
			//String html = (new FileReaderUtil()).readHTML("",htmlPathFileName);
			String html = (new FileReaderUtil()).readFile(htmlPathFileName, Charset.defaultCharset());
			log.info("html:[" + html + "]");
			
			Html2Pdf html2pdf = new Html2Pdf(html);
			
			//String cssPathFileName = "email.css"; 
			String cssPathFileName = "c:\\vector\\workspaceEdu\\est-portalcorretor-api\\portal-corretor-servico\\src\\main\\resources\\templates\\email.css"; 
			log.info("cssPathFileName:[" + cssPathFileName + "]");
			//String css = (new FileReaderUtil()).readHTML("",cssPathFileName);
			String css = (new FileReaderUtil()).readFile(cssPathFileName, Charset.defaultCharset());
			//html2pdf.addCss(css);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
			String pdfPathFileName = "c:\\arquivos_gerados\\pme_pdf\\out." + vendaPME.getEmpresas().get(0).getNomeFantasia() + "." + sdf.format(new Date()) + ".pdf";
			log.info("pdfPathFileName:[" + pdfPathFileName + "]");
			html2pdf.gerarPdf(pdfPathFileName);
		}catch (Exception e) {
			log.info("iTextPdfTest2 - erro");
			log.error(e.getMessage());
			return new VendaResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name());
		}
		log.info("iTextPdfTest2 - fim");
		return new VendaResponse(HttpStatus.OK.value(), HttpStatus.OK.name());
	}
	
	@RequestMapping(value = "/itextpdftest3", method = { RequestMethod.POST })
	public VendaResponse iTextPdfTest3(@RequestBody VendaPME vendaPME, @RequestHeader(value="User-Agent") String userAgent) {
		log.info("iTextPdfTest3 - ini");
		String html = "";
		Html2Pdf html2pdf;
		try {
			html2pdf = new Html2Pdf(html);
		
			String htmlPathFileName = "c:\\vector\\workspaceEdu\\est-portalcorretor-api\\portal-corretor-servico\\src\\main\\resources\\templates\\emktSemCSSSimples1.html"; 
			log.info("htmlPathFileName:[" + htmlPathFileName + "]");
	
			String cssPathFileName = "c:\\vector\\workspaceEdu\\est-portalcorretor-api\\portal-corretor-servico\\src\\main\\resources\\templates\\email.css"; 
			log.info("cssPathFileName:[" + cssPathFileName + "]");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
			String pdfPathFileName = "c:\\arquivos_gerados\\pme_pdf\\out." + vendaPME.getEmpresas().get(0).getNomeFantasia() + "." + sdf.format(new Date()) + ".pdf";
			log.info("pdfPathFileName:[" + pdfPathFileName + "]");
			
			html2pdf.html2pdf2(htmlPathFileName, cssPathFileName, pdfPathFileName);
		} catch (TransformerException e) {
			log.info("iTextPdfTest3 - erro");
			log.error(e.getMessage());
			return new VendaResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name());
		}
		log.info("iTextPdfTest3 - fim");
		return new VendaResponse(HttpStatus.OK.value(), HttpStatus.OK.name());
	}
}

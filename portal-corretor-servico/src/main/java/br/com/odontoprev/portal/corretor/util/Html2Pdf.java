package br.com.odontoprev.portal.corretor.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import com.itextpdf.text.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import com.ederbaum.io.IOUtil;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.exceptions.CssResolverException;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.itextpdf.tool.xml.pipeline.html.ImageProvider;

/**
 *  *  * @author Éder  
 */
public class Html2Pdf {

	private static final Log log = LogFactory.getLog(Html2Pdf.class);

	private final InputStream is;
	private ImageProvider imProvider;
	private CSSResolver cssResolver;

	public Html2Pdf(InputStream is) throws TransformerConfigurationException, TransformerException {
		this.is = is;
		cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
		this.imProvider = new Base64ImageProvider();
		log.info("Html2Pdf(InputStream is) - fim");
	}

	public Html2Pdf(String html) throws TransformerException {
		this(new ByteArrayInputStream(html.getBytes()));
		log.info("Html2Pdf(String html) - fim");
	}

	public void setImageProvider(ImageProvider imProvider) {
		this.imProvider = imProvider;
	}

	public void addCss(String css) throws CssResolverException {
		cssResolver.addCss(css, Boolean.TRUE);
	}

	public void convert(OutputStream file) throws DocumentException, IOException {
		log.info("convert(OutputStream file) - ini");
		Document document = new Document();
		// step 2
		log.info("step 2 ...");
		PdfWriter writer = PdfWriter.getInstance(document, file);
		// step 3
		log.info("step 3 ...");
		document.open();
		// step 4 
		log.info("step 4 ...");

		// HTML
		log.info("HTML ...");
		HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
		htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
		if (imProvider != null) {
			htmlContext.setImageProvider(imProvider);
		}

		// Pipelines
		log.info("Pipelines ...");
		PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
		HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
		CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

		// XML Worker
		log.info("XML Worker ...");
		XMLWorker worker = new XMLWorker(css, true);
		XMLParser p = new XMLParser(worker);
		log.info("p.parse(is) ...");
		p.parse(is);

		// step 5
		log.info("step 5 ...");
		document.close();
		log.info("convert(OutputStream file) - fim");
	}

	public void convert(File os) throws FileNotFoundException, IOException, DocumentException {
		log.info("convert(File os) - ini");
		try (OutputStream out = new BufferedOutputStream(new FileOutputStream(os))) {
			convert(out);
			// } catch (Exception e) {
			// // TODO: handle exception
			// log.error(e);
		}
		log.info("convert(File os) - fim");
	}

	// public static void main(String[] args)
	// throws IOException, FileNotFoundException, DocumentException,
	// TransformerException, CssResolverException {
	// // Html2Pdf pdf = new Html2Pdf(new File("C:/tmp/private/boleto_tmp.html"));
	// Html2Pdf pdf = new Html2Pdf("<h1 style=\"color:red\">Tchau Querida</h1>");
	// pdf.setImageProvider(new Base64ImageProvider());
	// pdf.convert(new File("/out.pdf"));
	// }

	public boolean gerarPdf(String pdfPathFileName) {
		log.info("gerarPdf - ini");
		try {
			// Html2Pdf html2Pdf = new Html2Pdf(html);
			this.setImageProvider(new Base64ImageProvider());
			// File fileOut = new File("c://arquivos_gerados//pme_pdf//out.pdf");
			File fileOut = new File(pdfPathFileName);
			this.convert(fileOut);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.info("gerarPdf - erro");
			log.error(e);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.info("gerarPdf - erro");
			log.error(e);
			return false;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.info("gerarPdf - erro");
			log.error(e);
			return false;
		}
		log.info("gerarPdf - fim");
		return true;
	}

	public static class Base64ImageProvider extends AbstractImageProvider {

		@Override
		public Image retrieve(String src) {
			int pos = src.indexOf("base64,");
			try {
				if (src.startsWith("data") && pos > 0) {
					byte[] img = Base64.decode(src.substring(pos + 7));
					return Image.getInstance(img);
				} else {
					return Image.getInstance(src);
				}
			} catch (BadElementException | IOException ex) {
				return null;
			}
		}

		@Override
		public String getImageRootPath() {
			return null;
		}
	}

	public boolean html2pdf2(String htmlSource, String cssSource, String pdfTarget) {
		log.info("html2pdf2 - ini");
		
		ByteArrayInputStream html = null;
		// html = getHtmlByteArrayStream(); //this is only for my picture not neccessary
		if(htmlSource!=null && !htmlSource.trim().isEmpty()) {
			log.info(String.format("htmlSource from [%s]", htmlSource));
			//html = new ByteArrayInputStream(FileReaderUtil.readContentIntoByteArray(new File(htmlSource)));
			html = new ByteArrayInputStream(htmlSource.getBytes());
		} else {
			log.error(String.format("htmlSource inválido [%s]", htmlSource));
			return false;
		}
		
		ByteArrayInputStream css = null;
		if(cssSource!=null && !cssSource.trim().isEmpty()) {
			log.info(String.format("cssSource from [%s]", cssSource));
			css = new ByteArrayInputStream(FileReaderUtil.readContentIntoByteArray(new File(cssSource)));
		}
		
		// step 1
		log.info("step 1 ...");
		Document document = new Document(PageSize.A4);

		// step 2
		log.info("step 2 ...");
		PdfWriter writer = null;
		try {
			if(pdfTarget!=null && !pdfTarget.trim().isEmpty()) {
				log.info(String.format("pdfTarget to [%s]", pdfTarget));
				writer = PdfWriter.getInstance(document, new FileOutputStream(pdfTarget));
			} else {
				log.error(String.format("pdfTarget inválido [%s]", pdfTarget));
				return false;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
			return false;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
			return false;
		}
		
		try {
			writer.setInitialLeading(12);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			log.error(e1);
			return false;
		}
		
		// step 3
		log.info("step 3 ...");
		document.open();

		// step 4
		log.info("step 4 ...");
		try {
			if(css==null) {
				log.info("XMLWorkerHelper.getInstance().parseXHtml(writer, document, html) ...");
				XMLWorkerHelper.getInstance().parseXHtml(writer, document, html);
			} else {
				log.info("XMLWorkerHelper.getInstance().parseXHtml(writer, document, html, css) ...");
				XMLWorkerHelper.getInstance().parseXHtml(writer, document, html, css);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
			return false;
		}

		// step 5
		log.info("step 5 ...");
		document.close();
		
		return true;
	}
}

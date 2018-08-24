package br.com.odontoprev.portal.corretor.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.dao.ArquivoContratacaoDAO;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.dto.ArquivoContratacao;
import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioPaginacao;
import br.com.odontoprev.portal.corretor.dto.Beneficiarios;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.model.TbodArquivoContratacao;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.service.ArquivoContratacaoService;
import br.com.odontoprev.portal.corretor.service.BeneficiarioService;
import br.com.odontoprev.portal.corretor.service.EmpresaService;
import br.com.odontoprev.portal.corretor.util.FileReaderUtil;
import br.com.odontoprev.portal.corretor.util.Html2Pdf;

//201808231715 - esert - COR-617 - novo servico para nova tabela TBOD_ARQUIVO_CONTRATACAO
@Service
@Transactional(rollbackFor={Exception.class})
public class ArquivoContratacaoServiceImpl implements ArquivoContratacaoService {
	
	private static final Log log = LogFactory.getLog(ArquivoContratacaoServiceImpl.class);
	
	@Autowired
	ArquivoContratacaoDAO arquivoContratacaoDAO;

	@Autowired
	private VendaDAO vendaDAO;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private BeneficiarioService beneficiarioService;

	@Override
	public ArquivoContratacao get(Long codigoEmpresa, boolean isArquivo) {		
		log.info("get - ini");		
		
		TbodArquivoContratacao entity = arquivoContratacaoDAO.findOne(codigoEmpresa);
						
		ArquivoContratacao dto = null;

		dto = adaptEntityToDto(entity, isArquivo);
		
		log.info("get - fim");
		return dto;
	}
	
	@Override
	public ArquivoContratacao save(ArquivoContratacao dto, boolean isArquivo) {
		log.info("save - ini");	
		TbodArquivoContratacao tbod = null;
		
		if(dto.getCodigoEmpresa() != null) {
			tbod = arquivoContratacaoDAO.findOne(dto.getCodigoEmpresa());
		}
		
		if(tbod==null) {
			tbod = new TbodArquivoContratacao(); //se nao existe deve criar
		}
		
		tbod = adaptDtoToEntity(dto, false);
		
		tbod = arquivoContratacaoDAO.save(tbod);
		
		dto = adaptEntityToDto(tbod, false);
		
		log.info("save - fim");	
		return dto;
	}

	@Override
	public ArquivoContratacao saveArquivo(ArquivoContratacao dto) {
		log.info("saveArquivo - ini");	
		TbodArquivoContratacao tbod = null;
		
		if(dto.getCodigoEmpresa() != null) {
			tbod = arquivoContratacaoDAO.findOne(dto.getCodigoEmpresa());
		}
		
		tbod = adaptDtoToEntity(dto, true);
		
		tbod = arquivoContratacaoDAO.save(tbod);
		
		dto = adaptEntityToDto(tbod, true);
		
		log.info("saveArquivo - fim");	
		return dto;
	}

	private TbodArquivoContratacao adaptDtoToEntity(ArquivoContratacao dto, boolean isArquivo) {
		log.info("TbodArquivoContratacao adaptDtoToEntity(ArquivoContratacao dto, boolean isArquivo) - ini");
		TbodArquivoContratacao tbod = null;
		if(dto != null) {
			tbod = new TbodArquivoContratacao();
			tbod.setCodigoEmpresa(dto.getCodigoEmpresa());
			tbod.setDataCriacao(dto.getDataCriacao());
			tbod.setNomeArquivo(dto.getNomeArquivo());
			tbod.setTamanhoArquivo(dto.getTamanhoArquivo());
			tbod.setTipoConteudo(dto.getTipoConteudo());
			
			if(dto.getCaminhoCarga()!=null && !dto.getCaminhoCarga().isEmpty()) {
				File file = new File(dto.getCaminhoCarga().concat(dto.getNomeArquivo()));
				
				if(tbod.getTamanhoArquivo()==null) {
					tbod.setTamanhoArquivo(file.length());
				}
				
				byte[] pdfInBytes = new byte[(int)file.length()];
				FileInputStream fis;
				try {
					fis = new FileInputStream(file);
					fis.read(pdfInBytes);
					fis.close();
					
					if(isArquivo) {
						tbod.setArquivo(pdfInBytes);
					}
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					log.error(e);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error(e);
				}
			} else {
				tbod.setArquivo(Base64.decodeBase64(dto.getArquivoBase64())); //201808231947 - esert
			}
		}
		log.info("TbodArquivoContratacao adaptDtoToEntity(ArquivoContratacao dto, boolean isArquivo) - fim");
		return tbod;
	}

	private ArquivoContratacao adaptEntityToDto(TbodArquivoContratacao entity, boolean isArquivo) {
		log.info("ArquivoContratacao adaptEntityToDto(TbodArquivoContratacao entity, boolean isArquivo) - ini");
		ArquivoContratacao dto = null;
		if(entity != null) {
			dto = new ArquivoContratacao();
			
			dto.setCodigoEmpresa(entity.getCodigoEmpresa());
			dto.setDataCriacao(entity.getDataCriacao());
			dto.setNomeArquivo(entity.getNomeArquivo());
			dto.setTamanhoArquivo(entity.getTamanhoArquivo());
			dto.setTipoConteudo(entity.getTipoConteudo());
			
			dto.setTamanhoArquivo( entity.getArquivo()!=null ? (long)entity.getArquivo().length : -1L );
			if(isArquivo) {
				dto.setArquivoBase64(Base64.encodeBase64String(entity.getArquivo())); //201807131230
				dto.setTamanhoArquivo( dto.getArquivoBase64()!=null ? (long)dto.getArquivoBase64().length() : -1L );
			}
		}		
		log.info("ArquivoContratacao adaptEntityToDto(TbodArquivoContratacao entity, boolean isArquivo) - fim");
		return dto;
	}

	@Override
	public ArquivoContratacao gerarSalvarPdfPmePelaVenda(Long cdVenda) {
		log.info(String.format("gerarSalvarPdfPmePelaVenda(%d) - ini", cdVenda));
		ArquivoContratacao arquivoContratacao = null;
		try {
			//obter empresa da venda
			TbodVenda tbodVenda = vendaDAO.findOne(cdVenda);
			
			if(tbodVenda==null) {
				log.error(String.format("tbodVenda==null para cdVenda:[%d]", cdVenda));
				return arquivoContratacao;
			}
			
			if(tbodVenda.getTbodEmpresa()==null) {
				log.error(String.format("tbodVenda.getTbodEmpresa()==null para cdVenda:[%d]", cdVenda));
				return arquivoContratacao;
			}
						
			//obter template html/css e destino pdf 
			String htmlEmpresaPathFileName = "c:\\vector\\workspaceEdu\\est-portalcorretor-api\\portal-corretor-servico\\src\\main\\resources\\templates\\pdfPMEEmpresa.html";
			log.info("htmlEmpresaPathFileName:[" + htmlEmpresaPathFileName + "]");
			String htmlVidaPathFileName = "c:\\vector\\workspaceEdu\\est-portalcorretor-api\\portal-corretor-servico\\src\\main\\resources\\templates\\pdfPMEVida.html";
			log.info("htmlVidaPathFileName:[" + htmlVidaPathFileName+ "]");
	
			String cssPathFileName = null;
			log.info("cssPathFileName:[" + cssPathFileName + "]");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
			String pdfPathName = "c:\\arquivos_gerados\\pme_pdf\\";
			log.info("pdfPathName:[" + pdfPathName + "]");
			String pdfFileName = "out." + tbodVenda.getTbodEmpresa().getNomeFantasia().replaceAll(" ", "_") + "." + sdf.format(new Date()) + ".pdf";
			log.info("pdfFileName:[" + pdfFileName + "]");
			String pdfPathFileName = pdfPathName + pdfFileName; 
			log.info("pdfPathFileName:[" + pdfPathFileName + "]");

			//gerar html
			String html = montarHtmlContratacaoPME(htmlEmpresaPathFileName, htmlVidaPathFileName, cssPathFileName, tbodVenda.getTbodEmpresa().getCdEmpresa());
			
			//gerar pdf com html
			Html2Pdf html2Pdf = new Html2Pdf(html);
			if(!html2Pdf.html2pdf2(html, null, pdfPathFileName)) {
				log.error(String.format("Falha ao gerar pdf com html para pdfPathFileName:[%s]", pdfPathFileName));
				return arquivoContratacao;
			}

			arquivoContratacao = new ArquivoContratacao();
			
			//salvar pdf na base
			try {
				arquivoContratacao.setCodigoEmpresa(tbodVenda.getTbodEmpresa().getCdEmpresa());
				arquivoContratacao.setDataCriacao(new Date());;
				arquivoContratacao.setNomeArquivo(pdfFileName);
				arquivoContratacao.setCaminhoCarga(pdfPathName);
				arquivoContratacao.setTipoConteudo("application/pdf");
				//arquivoContratacao.setTamanhoArquivo(tamanhoArquivo); //sera atribuido no adaptDtoToEntity()
				//arquivoContratacao.setArquivoBase64(arquivoBase64); //sera atribuido no adaptDtoToEntity()
				
				TbodArquivoContratacao tbodArquivoContratacao = adaptDtoToEntity(arquivoContratacao, true);
				
				tbodArquivoContratacao = arquivoContratacaoDAO.save(tbodArquivoContratacao);

				arquivoContratacao = adaptEntityToDto(tbodArquivoContratacao, true);

			}catch (Exception e) {
				// TODO: handle exception
				log.error(e);
				return arquivoContratacao;
			}
			
		} catch (Exception e) {
			log.info(String.format("gerarSalvarPdfPmePelaVenda() - erro"));
			log.error(e);
			return arquivoContratacao;
		}
		
		log.info(String.format("gerarSalvarPdfPmePelaVenda(%d) - fim", cdVenda));
		return arquivoContratacao;
	}

	private String montarHtmlContratacaoPME(
			String htmlEmpresaPathFileName, 
			String htmlVidasPathFileName,
			String cssPathFileName, 
			Long cdEmpresa
	) {
		log.info("montarHtmlContratacaoPME - ini");
		StringBuilder sbHtmlRet = new StringBuilder("");
		String htmlEmpresaTempalate = null;
		String htmlVidaTemplate = null;
		try {
			//htmlEmpresaTempalate = (new FileReaderUtil()).readHTML("", htmlEmpresaPathFileName);
			htmlEmpresaTempalate = (new FileReaderUtil()).readFile(htmlEmpresaPathFileName, Charset.defaultCharset());
			//htmlVidaTemplate = (new FileReaderUtil()).readHTML("", htmlVidasPathFileName);
			htmlVidaTemplate = (new FileReaderUtil()).readFile(htmlVidasPathFileName, Charset.defaultCharset());
			
			Empresa empresa = empresaService.findByCdEmpresa(cdEmpresa);
					
			String htmlEmpresa = htmlEmpresaTempalate
			.replaceAll("_RazaoSocial_", empresa.getRazaoSocial())
			.replaceAll("_NomeFantasia_", empresa.getNomeFantasia())
			;
			
			sbHtmlRet.append(htmlEmpresa);
			
//			Beneficiarios beneficiarios = beneficiarioService.getPage(cdEmpresa, 999L, 1L);
//			
//			for(BeneficiarioPaginacao titularPaginacao : beneficiarios.getTitulares()){
//				
//				String htmlVidaTit = htmlVidaTemplate
//				.replaceAll("_Nome_", titularPaginacao.getNome())
//				;
//				
//				sbHtmlRet.append(htmlVidaTit);
//				
//				if(titularPaginacao.getDependentes()!=null) {
//					for(Beneficiario beneficiario : titularPaginacao.getDependentes()){
//						
//						String htmlVidaBen = htmlVidaTemplate
//						.replaceAll("_Nome_", beneficiario.getNome())
//						;
//						
//						sbHtmlRet.append(htmlVidaBen);
//						
//					} //for(Beneficiario beneficiario : titularPaginacao.getDependentes())
//				} //if(titularPaginacao.getDependentes()!=null)
//				
//			} //for(BeneficiarioPaginacao titularPaginacao : beneficiarios.getTitulares())
			
		} catch (Exception e) {
			// TODO: handle exception
			log.info("montarHtmlContratacaoPME - erro");
			return null;
		}
		
		log.info("montarHtmlContratacaoPME - fim");
		return sbHtmlRet.toString();
	}

}


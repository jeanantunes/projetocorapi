package br.com.odontoprev.portal.corretor.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.dao.ArquivoContratacaoDAO;
import br.com.odontoprev.portal.corretor.dao.EmpresaDAO;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.dto.ArquivoContratacao;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.model.TbodArquivoContratacao;
import br.com.odontoprev.portal.corretor.model.TbodEmpresa;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.model.TbodVendaVida;
import br.com.odontoprev.portal.corretor.service.ArquivoContratacaoService;
import br.com.odontoprev.portal.corretor.service.BeneficiarioService;
import br.com.odontoprev.portal.corretor.service.EmpresaService;
import br.com.odontoprev.portal.corretor.util.FileReaderUtil;
import br.com.odontoprev.portal.corretor.util.Html2Pdf;
import br.com.odontoprev.portal.corretor.util.StringsUtil;

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
	private EmpresaDAO empresaDAO;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private BeneficiarioService beneficiarioService;

	@Override
	public ArquivoContratacao getByCdEmpresa(Long cdEmpresa, boolean isArquivo) {		
		log.info("getByCdEmpresa - ini");		
		ArquivoContratacao dto = null;
		try {
			TbodArquivoContratacao entity = arquivoContratacaoDAO.findOne(cdEmpresa);
							
			dto = adaptEntityToDto(entity, isArquivo);
		} catch (Exception e) {
			log.error("getByCdEmpresa - erro", e);
			return null;
		}
		log.info("getByCdEmpresa - fim");
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
		TbodArquivoContratacao entity = null;
		try {
			if(dto != null) {
				entity = new TbodArquivoContratacao();
				entity.setCodigoEmpresa(dto.getCodigoEmpresa());
				try {
					entity.setDataCriacao((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(dto.getDataCriacao())); //201808271556 - esert
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					log.error("Erro em parse: new SimpleDateFormat(yyyy-MM-dd HH:mm:ss)).parse(" + dto.getDataCriacao() + "), aplicado (new Date())", e1); //201808271556 - esert
					entity.setDataCriacao(new Date());
				}
				entity.setNomeArquivo(dto.getNomeArquivo());
				entity.setTamanhoArquivo(dto.getTamanhoArquivo());
				entity.setTipoConteudo(dto.getTipoConteudo());
				
				if(dto.getCaminhoCarga()!=null && !dto.getCaminhoCarga().isEmpty()) {
					File file = new File(dto.getCaminhoCarga().concat(dto.getNomeArquivo()));
					
					if(entity.getTamanhoArquivo()==null) {
						entity.setTamanhoArquivo(file.length());
					}
					
					byte[] pdfInBytes = new byte[(int)file.length()];
					FileInputStream fis;
					try {
						fis = new FileInputStream(file);
						fis.read(pdfInBytes);
						fis.close();
						
						if(isArquivo) {
							entity.setArquivo(pdfInBytes);
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
					entity.setArquivo(Base64.decodeBase64(dto.getArquivoBase64())); //201808231947 - esert
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error("TbodArquivoContratacao adaptDtoToEntity(ArquivoContratacao dto, boolean isArquivo) - erro", e);
			return null;
		}
		log.info("TbodArquivoContratacao adaptDtoToEntity(ArquivoContratacao dto, boolean isArquivo) - fim");
		return entity;
	}

	private ArquivoContratacao adaptEntityToDto(TbodArquivoContratacao entity, boolean isArquivo) {
		log.info("ArquivoContratacao adaptEntityToDto(TbodArquivoContratacao entity, boolean isArquivo) - ini");
		ArquivoContratacao dto = null;
		try {
			if(entity != null) {
				dto = new ArquivoContratacao();
				
				dto.setCodigoEmpresa(entity.getCodigoEmpresa());
				
				try {
					//dto.setDataCriacao(entity.getDataCriacao());
					dto.setDataCriacao((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(entity.getDataCriacao()).replace(" ", "T").concat("-0300")); //201808271556 - esert
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					log.error("Erro formatando data ...format(entity.getDataCriacao())", e);
				}

				dto.setNomeArquivo(entity.getNomeArquivo());
				dto.setTamanhoArquivo(entity.getTamanhoArquivo());
				dto.setTipoConteudo(entity.getTipoConteudo());
				
				dto.setTamanhoArquivo( entity.getArquivo()!=null ? (long)entity.getArquivo().length : -1L );
				if(isArquivo) {
					dto.setArquivoBase64(Base64.encodeBase64String(entity.getArquivo())); //201807131230
					dto.setTamanhoArquivo( dto.getArquivoBase64()!=null ? (long)dto.getArquivoBase64().length() : -1L );
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error("ArquivoContratacao adaptEntityToDto(TbodArquivoContratacao entity, boolean isArquivo) - erro", e);
			return dto;
		}		
		log.info("ArquivoContratacao adaptEntityToDto(TbodArquivoContratacao entity, boolean isArquivo) - fim");
		return dto;
	}

	@Override
	public ArquivoContratacao createPdfPmePorVenda(Long cdVenda) {
		log.info(String.format("createPdfPmePorVenda(%d) - ini", cdVenda));
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
			
			arquivoContratacao = this.createPdfPmePorEmpresa(tbodVenda.getTbodEmpresa().getCdEmpresa());
			
		} catch (Exception e) {
			log.info(String.format("createPdfPmePorVenda() - erro"));
			log.error(e);
			return arquivoContratacao;
		}
		
		log.info(String.format("createPdfPmePorVenda(%d) - fim", cdVenda));
		return arquivoContratacao;
	}

	@Override
	public ArquivoContratacao createPdfPmePorEmpresa(Long cdEmpresa) {
		log.info(String.format("createPdfPmePorEmpresa(%d) - ini", cdEmpresa));
		ArquivoContratacao arquivoContratacao = null;
		try {
			//obter empresa da venda
			TbodEmpresa tbodEmpresa = empresaDAO.findOne(cdEmpresa);
			
			if(tbodEmpresa==null) {
				log.error(String.format("tbodEmpresa==null para cdEmpresa:[%d]", cdEmpresa));
				return arquivoContratacao;
			}
			
			String pdfPathName = "c:\\arquivos_gerados\\pme_pdf\\";
			log.info("pdfPathName:[" + pdfPathName + "]");
			
			Date agoraDate = new Date();
			String dataCriacaoString = (new SimpleDateFormat("yyyyMMddHHmmss")).format(agoraDate);
			
			String pdfFileName = "contratacao"
					.concat(".")
					.concat(StringsUtil.stripAccents(tbodEmpresa.getNomeFantasia().replaceAll(" ", "_")))
					.concat(".")
					.concat(dataCriacaoString)
					.concat(".")
					.concat("pdf")
					;
			log.info("pdfFileName:[" + pdfFileName + "]");
			
			String pdfPathFileName = pdfPathName + pdfFileName; 
			log.info("pdfPathFileName:[" + pdfPathFileName + "]");

			//gerar html
			String html = montarHtmlContratacaoPME(
					tbodEmpresa.getCdEmpresa()
					);
			
			if(html==null) {
				log.error(String.format("Falha ao gerar pdf com html==null para pdfPathFileName:[%s]", pdfPathFileName));
				return arquivoContratacao;
			}
			
			//gerar pdf com html
			Html2Pdf html2Pdf = new Html2Pdf(html);
			if(!html2Pdf.html2pdf2(html, null, pdfPathFileName)) {
				log.error(String.format("Falha ao gerar pdf com html para pdfPathFileName:[%s]", pdfPathFileName));
				return arquivoContratacao;
			}

			arquivoContratacao = new ArquivoContratacao();
			
			//salvar pdf na base
			String dataCriacaoStringDTO = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(agoraDate);
			try {
				arquivoContratacao.setCodigoEmpresa(tbodEmpresa.getCdEmpresa());
				arquivoContratacao.setDataCriacao(dataCriacaoStringDTO);;
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
			log.info(String.format("createPdfPmePorEmpresa() - erro"));
			log.error(e);
			return arquivoContratacao;
		}
		
		log.info(String.format("createPdfPmePorEmpresa(%d) - fim", cdEmpresa));
		return arquivoContratacao;
	}

	private String montarHtmlContratacaoPME(
			Long cdEmpresa
	) {
		log.info("montarHtmlContratacaoPME - ini");
		
		//obter template html/css e destino pdf
		String rootPath = "c:\\vector\\workspaceEdu\\est-portalcorretor-api\\portal-corretor-servico\\src\\main\\resources\\templates\\";

		String htmlCabecalhoPathFileName = rootPath.concat("pdfPMECabecalho.html");
		log.info("htmlCabecalhoPathFileName:[" + htmlCabecalhoPathFileName + "]");
		
		String htmlEmpresaPathFileName = rootPath.concat("pdfPMECorpoEmpresa.html");
		log.info("htmlEmpresaPathFileName:[" + htmlEmpresaPathFileName + "]");
		
		String htmlVidaPathFileName = rootPath.concat("pdfPMECorpoVida.html");
		log.info("htmlVidaPathFileName:[" + htmlVidaPathFileName+ "]");
		
		String htmlRodapePathFileName = rootPath.concat("pdfPMERodape.html");
		log.info("htmlRodapePathFileName:[" + htmlRodapePathFileName+ "]");
		
		String cssPathFileName = null;
		log.info("cssPathFileName:[" + cssPathFileName + "]");

		
		StringBuilder sbHtmlRet = new StringBuilder("");
		String htmlCabecalhoValues = null;
		String htmlCabecalhoTemplate = null;
		String htmlEmpresaValues = null;
		String htmlEmpresaTemplate = null;
		String htmlVidaValues = null;
		String htmlVidaTemplate = null;
		String htmlRodapeValues = null;
		String htmlRodapeTemplate = null;
		try {
			//htmlEmpresaTempalate = (new FileReaderUtil()).readHTML("", htmlEmpresaPathFileName);
			htmlCabecalhoTemplate = (new FileReaderUtil()).readFile(htmlCabecalhoPathFileName, Charset.defaultCharset());
			//htmlEmpresaTempalate = (new FileReaderUtil()).readHTML("", htmlEmpresaPathFileName);
			htmlEmpresaTemplate = (new FileReaderUtil()).readFile(htmlEmpresaPathFileName, Charset.defaultCharset());
			//htmlVidaTemplate = (new FileReaderUtil()).readHTML("", htmlVidasPathFileName);
			htmlVidaTemplate = (new FileReaderUtil()).readFile(htmlVidaPathFileName, Charset.defaultCharset());
			//htmlRodapeTemplate = (new FileReaderUtil()).readHTML("", htmlVidasPathFileName);
			htmlRodapeTemplate = (new FileReaderUtil()).readFile(htmlRodapePathFileName, Charset.defaultCharset());
			
			Empresa empresa = empresaService.findByCdEmpresa(cdEmpresa);

			if(empresa!=null) {
				
				htmlCabecalhoValues = htmlCabecalhoTemplate
				.replace("__RazaoSocial__", Objects.toString(empresa.getRazaoSocial(),""))
				.replace("__NomeFantasia__", Objects.toString(empresa.getNomeFantasia(),""))
				;

				List<TbodVenda> listVenda = vendaDAO.findByTbodEmpresaCdEmpresa(empresa.getCdEmpresa());
				if(listVenda!=null && listVenda.size()>0) {
					htmlEmpresaValues = htmlEmpresaTemplate
					.replace("__NomeCorretor__", Objects.toString(listVenda.get(0).getTbodForcaVenda().getNome(), ""))
					.replace("__NomeCorretora__", Objects.toString(listVenda.get(0).getTbodCorretora().getNome(), ""))
					
					.replace("__CNPJ__", Objects.toString(empresa.getCnpj(), ""))
					.replace("__RazaoSocial__", Objects.toString(empresa.getRazaoSocial(), ""))
					.replace("__NomeFantasia__", Objects.toString(empresa.getNomeFantasia(), ""))
					
					.replace("__RamoDeAtividade__", Objects.toString(empresa.getRamoAtividade(), ""))
					.replace("__RepresentanteLegal__", Objects.toString(empresa.getRepresentanteLegal(), ""))
					.replace("__CPF__", Objects.toString(empresa.getCpfRepresentante(), ""))
					
					.replace("__Telefone__", Objects.toString(empresa.getTelefone(), ""))
					.replace("__Celular__", Objects.toString(empresa.getCelular(), ""))
					.replace("__Email__", Objects.toString(empresa.getEmail(), ""))
					
					;
				}
				
				if(empresa.getEnderecoEmpresa()!=null) {
					htmlEmpresaValues = htmlEmpresaValues
					.replace("__CEP__", Objects.toString(empresa.getEnderecoEmpresa().getCep(), ""))
					
					.replace("__Endereco__", Objects.toString(empresa.getEnderecoEmpresa().getLogradouro(), ""))
					.replace("__Numero__", Objects.toString(empresa.getEnderecoEmpresa().getNumero(), ""))
					.replace("__Complemento__", Objects.toString(empresa.getEnderecoEmpresa().getComplemento(), ""))
					
					.replace("__Bairro__", Objects.toString(empresa.getEnderecoEmpresa().getBairro(), ""))
					.replace("__Cidade__", Objects.toString(empresa.getEnderecoEmpresa().getCidade(), ""))
					.replace("__Estado__", Objects.toString(empresa.getEnderecoEmpresa().getEstado(), ""))

					;
				}

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

				String diaVencimentoFatura = null;
				if(listVenda.get(0).getFaturaVencimento()!=null) {
					diaVencimentoFatura = listVenda.get(0).getFaturaVencimento().toString(); 
				}

				String dataCorteMovimentacao = null;
				if(listVenda.get(0).getDtMovimentacao()!=null) {
					dataCorteMovimentacao = sdf.format(listVenda.get(0).getDtMovimentacao()); 
				}

				String dataVigencia = null;
				if(listVenda.get(0).getDtVigencia()!=null) {
					dataVigencia = sdf.format(listVenda.get(0).getDtVigencia()); 
				}
				
				htmlEmpresaValues = htmlEmpresaValues
					.replace("__DiaVencimentoFatura__", Objects.toString(diaVencimentoFatura, ""))
					.replace("__DataCorteMovimentacao__", Objects.toString(dataCorteMovimentacao, ""))
					.replace("__DataVigencia__", Objects.toString(dataVigencia, ""))
					;

			}
			
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

			htmlRodapeValues = htmlRodapeTemplate;
			
			sbHtmlRet.append(htmlCabecalhoValues);
			sbHtmlRet.append(htmlEmpresaValues);
			sbHtmlRet.append(htmlRodapeValues);

		} catch (Exception e) {
			// TODO: handle exception
			log.info("montarHtmlContratacaoPME - erro");
			return null;
		}
		
		log.info("montarHtmlContratacaoPME - fim");
		return sbHtmlRet.toString();
	}

}


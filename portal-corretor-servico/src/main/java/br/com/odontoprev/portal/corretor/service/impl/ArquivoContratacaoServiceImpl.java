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
import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioPaginacao;
import br.com.odontoprev.portal.corretor.dto.Beneficiarios;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.model.TbodArquivoContratacao;
import br.com.odontoprev.portal.corretor.model.TbodEmpresa;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.service.ArquivoContratacaoService;
import br.com.odontoprev.portal.corretor.service.BeneficiarioService;
import br.com.odontoprev.portal.corretor.service.EmpresaService;
import br.com.odontoprev.portal.corretor.util.Constantes;
import br.com.odontoprev.portal.corretor.util.FileReaderUtil;
import br.com.odontoprev.portal.corretor.util.Html2Pdf;
import br.com.odontoprev.portal.corretor.util.StringsUtil;

import javax.annotation.Resource;

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
					.concat(tbodEmpresa.getCdEmpresa().toString())
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
		
		StringBuilder sbHtmlRet = new StringBuilder("");

		try {

			//obter template html/css e destino pdf
			//String rootPath = "C:\\Users\\almei\\dev\\APPS\\portal-corretor-api\\est-portalcorretor-api\\portal-corretor-servico\\src\\main\\resources\\templates\\";
			//String rootPath = this.getClass().getClassLoader().getResourceAsStream("templates\\").toString();
			String rootPath = ""; //nao precisa montar path quando usar readHTML("", fileName) pq ele ja faz isso internamente //201808282026 - esert 

			String htmlCabecalhoPathFileName = rootPath.concat("pdfPMECabecalho.html");
			log.info("htmlCabecalhoPathFileName:[" + htmlCabecalhoPathFileName + "]");
			
			String htmlEmpresaPathFileName = rootPath.concat("pdfPMECorpoEmpresa.html");
			log.info("htmlEmpresaPathFileName:[" + htmlEmpresaPathFileName + "]");
			
			String htmlCorpoAbreBeneficiarioPathFileName = rootPath.concat("pdfPMECorpoAbreBeneficiarios.html");
			log.info("htmlCorpoAbreBeneficiarioPathFileName:[" + htmlCorpoAbreBeneficiarioPathFileName + "]");
			
			String htmlCorpoTituloBeneficiarioPathFileName = rootPath.concat("pdfPMECorpoTituloBeneficiario.html");
			log.info("htmlCorpoTituloBeneficiarioPathFileName:[" + htmlCorpoTituloBeneficiarioPathFileName + "]");
			
			String htmlCorpoVidaBeneficiarioPathFileName = rootPath.concat("pdfPMECorpoVidaBeneficiario.html");
			log.info("htmlCorpoVidaBeneficiarioPathFileName:[" + htmlCorpoVidaBeneficiarioPathFileName + "]");
			
			String htmlCorpoTituloDependentePathFileName = rootPath.concat("pdfPMECorpoTituloDependente.html");
			log.info("htmlCorpoTituloDependentePathFileName:[" + htmlCorpoTituloDependentePathFileName + "]");
			
			String htmlCorpoVidaDependentePathFileName = rootPath.concat("pdfPMECorpoVidaDependente.html");
			log.info("htmlCorpoVidaDependentePathFileName:[" + htmlCorpoVidaDependentePathFileName + "]");
			
			String htmlRodapePathFileName = rootPath.concat("pdfPMERodape.html");
			log.info("htmlRodapePathFileName:[" + htmlRodapePathFileName + "]");

			String htmlCorpoFechaVidaPathFileName = rootPath.concat("pdfPMEFechaVida.html");
			log.info("htmlCorpoFechaVidaPathFileName:[" + htmlCorpoFechaVidaPathFileName + "]");

			String htmlCorpoFechaVidaDependentePathFileName = rootPath.concat("pdfPMEFechaDependenteVida.html");
			log.info("pdfPMEFechaDependenteVida:[" + htmlCorpoFechaVidaPathFileName + "]");

			String cssPathFileName = null;
			log.info("cssPathFileName:[" + cssPathFileName + "]");
				
			
			String htmlCabecalhoTemplate = (new FileReaderUtil()).readHTML("", htmlCabecalhoPathFileName);
			//String htmlCabecalhoTemplate = (new FileReaderUtil()).readFile(htmlCabecalhoPathFileName, Charset.defaultCharset());
				
			String htmlEmpresaTemplate = (new FileReaderUtil()).readHTML("", htmlEmpresaPathFileName);
			//String htmlEmpresaTemplate = (new FileReaderUtil()).readFile(htmlEmpresaPathFileName, Charset.defaultCharset());
			String htmlEmpresaValues = null;

			//String htmlCorpoAbreBeneficiarioTemplate = (new FileReaderUtil()).readHTML("", htmlCorpoAbreBeneficiarioPathFileName);
			String htmlCorpoAbreBeneficiarioTemplate = (new FileReaderUtil()).readFile(htmlCorpoAbreBeneficiarioPathFileName, Charset.defaultCharset());

			String htmlCorpoFechaVidaTemplate = (new FileReaderUtil()).readHTML("",htmlCorpoFechaVidaPathFileName);
			//String htmlCorpoFechaVidaTemplate = (new FileReaderUtil()).readFile(htmlCorpoFechaVidaPathFileName, Charset.defaultCharset());

			String htmlCorpoFechaVidaDependenteTemplate = (new FileReaderUtil()).readHTML("",htmlCorpoFechaVidaDependentePathFileName);
			//String htmlCorpoFechaVidaDependenteTemplate = (new FileReaderUtil()).readFile(htmlCorpoFechaVidaDependentePathFileName, Charset.defaultCharset());

			//String htmlCorpoTituloBeneficiarioTemplate = (new FileReaderUtil()).readHTML("", htmlCorpoTituloBeneficiarioPathFileName);
			String htmlCorpoTituloBeneficiarioTemplate = (new FileReaderUtil()).readFile(htmlCorpoTituloBeneficiarioPathFileName, Charset.defaultCharset());
				
			String htmlCorpoVidaBeneficiarioTemplate = (new FileReaderUtil()).readHTML("", htmlCorpoVidaBeneficiarioPathFileName);
			//String htmlCorpoVidaBeneficiarioTemplate = (new FileReaderUtil()).readFile(htmlCorpoVidaBeneficiarioPathFileName, Charset.defaultCharset());
			String htmlCorpoVidaBeneficiarioValues = null;
			
			//String htmlCorpoTituloBeneficiarioTemplate = (new FileReaderUtil()).readHTML("", htmlCorpoTituloBeneficiarioPathFileName);
			String htmlCorpoTituloDependenteTemplate = (new FileReaderUtil()).readFile(htmlCorpoTituloDependentePathFileName, Charset.defaultCharset());
				
			String htmlCorpoVidaDependenteTemplate = (new FileReaderUtil()).readHTML("", htmlCorpoVidaDependentePathFileName);
			//String htmlCorpoVidaDependenteTemplate = (new FileReaderUtil()).readFile(htmlCorpoVidaDependentePathFileName, Charset.defaultCharset());
			String htmlCorpoVidaDependenteValues = null;
				
			String htmlRodapeTemplate = (new FileReaderUtil()).readHTML("", htmlRodapePathFileName);
			//String htmlRodapeTemplate = (new FileReaderUtil()).readFile(htmlRodapePathFileName, Charset.defaultCharset());
			String htmlRodapeValues = null;
			
			Empresa empresa = empresaService.findByCdEmpresa(cdEmpresa);

			if(empresa!=null) {
				
				String htmlCabecalhoValues = htmlCabecalhoTemplate
				.replace("__CdEmpresa__", Objects.toString(empresa.getCdEmpresa(),""))
				.replace("__RazaoSocial__", Objects.toString(empresa.getRazaoSocial(),""))
				.replace("__NomeFantasia__", Objects.toString(empresa.getNomeFantasia(),""))
				;
				
				sbHtmlRet.append(htmlCabecalhoValues);

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
				
				String prevista = "";
				if(listVenda.get(0).getDtAceite()==null) { //se ainda nao tem data de aceite
					prevista = " " + "PREVISTA"; //entao as datas de movimentacao e vigencia sao PREVISTAS
					//deixar despacito pra não relar
				}				
				
				htmlEmpresaValues = htmlEmpresaValues
				.replace("__DiaVencimentoFatura__", Objects.toString(diaVencimentoFatura, ""))
				.replace("__DataCorteMovimentacao__", Objects.toString(dataCorteMovimentacao, ""))
				.replace("__DataVigencia__", Objects.toString(dataVigencia, ""))
				.replace("__Prevista__", prevista)
				;
				
				sbHtmlRet.append(htmlEmpresaValues);
			}
			
			Beneficiarios beneficiarios = beneficiarioService.getPage(cdEmpresa, 999L, 1L);
			
			if(beneficiarios.getTitulares()!=null && beneficiarios.getTitulares().size()>0) {
				
				sbHtmlRet.append(htmlCorpoAbreBeneficiarioTemplate);
				
				for(BeneficiarioPaginacao titularPaginacao : beneficiarios.getTitulares()){
	
					sbHtmlRet.append(htmlCorpoTituloBeneficiarioTemplate);

					String descPlanoTitular = Objects.toString(titularPaginacao.getDescPlano(), "");
					String sexoTitular = SexoPorExtenso(Objects.toString(titularPaginacao.getSexo(), ""));

					htmlCorpoVidaBeneficiarioValues = ""; //limpa cada ciclo de titular
					htmlCorpoVidaBeneficiarioValues = htmlCorpoVidaBeneficiarioTemplate
					.replace("__Nome__", Objects.toString(titularPaginacao.getNome(), ""))
					.replace("__CPF__", Objects.toString(titularPaginacao.getCpf(), ""))
					.replace("__Sexo__", sexoTitular)
					.replace("__DataNascimento__", Objects.toString(titularPaginacao.getDataNascimento(), ""))
					.replace("__NomeMae__", Objects.toString(titularPaginacao.getNomeMae(), ""))
					.replace("__Plano__", descPlanoTitular)
					;
					
					sbHtmlRet.append(htmlCorpoVidaBeneficiarioValues);
					
					if(titularPaginacao.getDependentes()!=null && titularPaginacao.getDependentes().size()>0) {
						
						sbHtmlRet.append(htmlCorpoTituloDependenteTemplate);

						int contadorDeDependentes = 0;

						for(Beneficiario beneficiario : titularPaginacao.getDependentes()){

							contadorDeDependentes++;

							String sexoBeneficiario = SexoPorExtenso(Objects.toString(beneficiario.getSexo(), ""));

							htmlCorpoVidaDependenteValues = htmlCorpoVidaDependenteTemplate
							.replace("__Nome__", Objects.toString(beneficiario.getNome(), ""))
							.replace("__CPF__", Objects.toString(beneficiario.getCpf(), ""))
							.replace("__Sexo__", sexoBeneficiario)
							.replace("__DataNascimento__", Objects.toString(beneficiario.getDataNascimento(), ""))
							.replace("__NomeMae__", Objects.toString(beneficiario.getNomeMae(), ""))
							.replace("__Plano__", descPlanoTitular)
							;

							sbHtmlRet.append(htmlCorpoVidaDependenteValues);

							if(!(contadorDeDependentes == titularPaginacao.getDependentes().size())){
								sbHtmlRet.append(htmlCorpoFechaVidaDependenteTemplate);
							}
							
						} //for(Beneficiario beneficiario : titularPaginacao.getDependentes())
					} //if(titularPaginacao.getDependentes()!=null)

					sbHtmlRet.append(htmlCorpoFechaVidaTemplate);
					
				}

				//for(BeneficiarioPaginacao titularPaginacao : beneficiarios.getTitulares())
			}
			

			htmlRodapeValues = htmlRodapeTemplate;			
			sbHtmlRet.append(htmlRodapeValues);

		} catch (Exception e) {
			// TODO: handle exception
			log.info("montarHtmlContratacaoPME - erro");
			return null;
		}
		
		log.info("montarHtmlContratacaoPME - fim");
		return sbHtmlRet.toString();
	}

	//201808272009 - esert
	private String SexoPorExtenso(String siglaSexo) {
		switch (siglaSexo.toUpperCase()) {
			case Constantes.FEMININO:
				return "Feminino";
			case Constantes.MASCULINO:
				return "Masculino";
			default:
				return "n/a";
		}
	}
}


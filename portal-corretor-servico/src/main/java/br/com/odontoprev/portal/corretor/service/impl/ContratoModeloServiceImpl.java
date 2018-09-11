package br.com.odontoprev.portal.corretor.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.pdf.codec.Base64;

import br.com.odontoprev.portal.corretor.dao.ContratoModeloDAO;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioPaginacao;
import br.com.odontoprev.portal.corretor.dto.Beneficiarios;
import br.com.odontoprev.portal.corretor.dto.ContratoModelo;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.model.TbodContratoModelo;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.service.BeneficiarioService;
import br.com.odontoprev.portal.corretor.service.ContratoModeloService;
import br.com.odontoprev.portal.corretor.util.Constantes;
import br.com.odontoprev.portal.corretor.util.FileReaderUtil;

//201808231715 - esert - COR-617 - novo servico para nova tabela TBOD_ARQUIVO_CONTRATACAO
@Service
@Transactional(rollbackFor={Exception.class})
public class ContratoModeloServiceImpl implements ContratoModeloService {
	
	private static final Log log = LogFactory.getLog(ContratoModeloServiceImpl.class);
	
	@Autowired
	VendaDAO vendaDAO;
	
	@Autowired
	ContratoModeloDAO contratoModeloDAO;
	
	@Autowired
	BeneficiarioService beneficiarioService;
		
//	@Value("${server.path.pdfpme}") //201808311529 - esert - COR-617 gerar pdf pme
//	private String pdfPMEPathName; //201808311529 - esert - COR-617 gerar pdf pme

	@Override
	public ContratoModelo getByCdContratoModelo(Long cdContratoModelo) {		
		log.info("getByCdContratoModelo - ini");		
		ContratoModelo dto = null;
		try {
			TbodContratoModelo entity = contratoModeloDAO.findOne(cdContratoModelo);
							
			dto = adaptEntityToDto(entity);
		} catch (Exception e) {
			log.error("getByCdContratoModelo - erro", e);
			return null;
		}
		log.info("getByCdContratoModelo - fim");
		return dto;
	}

	@Override
	public ContratoModelo saveArquivo(ContratoModelo dto) {
		log.info("saveArquivo - ini");	
		TbodContratoModelo tbod = null;
		
		if(dto.getCdContratoModelo() != null) {
			tbod = contratoModeloDAO.findOne(dto.getCdContratoModelo());
		}
		
		tbod = adaptDtoToEntity(dto);
		
		tbod = contratoModeloDAO.save(tbod);
		
		dto = adaptEntityToDto(tbod);
		
		log.info("saveArquivo - fim");	
		return dto;
	}

	private TbodContratoModelo adaptDtoToEntity(ContratoModelo dto) {
		log.info("TbodContratoModelo adaptDtoToEntity(ContratoModelo dto) - ini");
		TbodContratoModelo entity = null;
		try {
			if(dto != null) {
				entity = new TbodContratoModelo();
				entity.setCdContratoModelo(dto.getCdContratoModelo());
				try {
					entity.setDtCriacao((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(dto.getDtCriacao()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					log.error("Erro em parse: new SimpleDateFormat(yyyy-MM-dd HH:mm:ss)).parse(" + dto.getDtCriacao() + "), aplicado (new Date())", e1); //201808271556 - esert
					entity.setDtCriacao(new Date());
				}
				entity.setNomeArquivo(dto.getNomeArquivo());
				entity.setTamanhoArquivo(dto.getTamanhoArquivo());
				entity.setTipoConteudo(dto.getTipoConteudo());
				
				if(dto.getCaminhoCarga()!=null && !dto.getCaminhoCarga().isEmpty()) {
					File file = new File(dto.getCaminhoCarga().concat(dto.getNomeArquivo()));
					
					if(entity.getTamanhoArquivo()==null) {
						entity.setTamanhoArquivo(file.length());
					}
					
					byte[] fileInBytes = new byte[(int)file.length()];
					FileInputStream fis;
					try {
						fis = new FileInputStream(file);
						fis.read(fileInBytes);
						fis.close();
						
						entity.setArquivoBytes(fileInBytes);
						
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
					entity.setArquivoBytes(dto.getArquivoBase64().getBytes());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error("TbodContratoModelo adaptDtoToEntity(ContratoModelo dto) - erro", e);
			return null;
		}
		log.info("TbodContratoModelo adaptDtoToEntity(ContratoModelo dto) - fim");
		return entity;
	}

	private ContratoModelo adaptEntityToDto(TbodContratoModelo entity) {
		log.info("ContratoModelo adaptEntityToDto(TbodContratoModelo entity, boolean isArquivo) - ini");
		ContratoModelo dto = null;
		try {
			if(entity != null) {
				dto = new ContratoModelo();
				
				dto.setCdContratoModelo(entity.getCdContratoModelo());
				
				try {
					//dto.setDataCriacao(entity.getDataCriacao());
					dto.setDtCriacao((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(entity.getDtCriacao())/*.replace(" ", "T").concat("-0300")*/); //201809111132 - esert
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					log.error("Erro formatando data ...format(entity.getDataCriacao())", e);
				}

				dto.setNomeArquivo(entity.getNomeArquivo());
				dto.setTamanhoArquivo(entity.getTamanhoArquivo());
				dto.setTipoConteudo(entity.getTipoConteudo());
				
				dto.setArquivoBase64(Base64.encodeBytes(entity.getArquivoBytes()));
				dto.setArquivoString(new String(entity.getArquivoBytes(), Charset.defaultCharset())); //201809112014
					
				//dto.setTamanhoArquivo( dto.getArquivoBase64()!=null ? (long)dto.getArquivoBase64().length() : -1L );
				//dto.setTamanhoArquivo( dto.getArquivoString()!=null ? (long)dto.getArquivoString().length() : -1L );
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error("ContratoModelo adaptEntityToDto(TbodContratoModelo entity) - erro", e);
			return dto;
		}		
		log.info("ContratoModelo adaptEntityToDto(TbodContratoModelo entity) - fim");
		return dto;
	}

	private String montarHtmlContratacaoPME(Long cdContratoModelo) {
		log.info("montarHtmlContratacaoPME - ini");
		
		StringBuilder sbHtmlRet = new StringBuilder("");

		try {

			//obter template html/css e destino pdf
			//String rootPath = "C:\\Users\\almei\\dev\\APPS\\portal-corretor-api\\est-portalcorretor-api\\portal-corretor-servico\\src\\main\\resources\\templates\\";
			//String rootPath = "C:\\Users\\vector\\workspaceEdu\\est-portalcorretor-api\\portal-corretor-servico\\src\\main\\resources\\templates\\";
			String rootPath = ""; //nao precisa montar path rootPath para usar readHTML("", fileName) pq ja o faz internamente //201808282026 - esert 
			log.info("rootPath:[" + rootPath + "]");

			//201808291120 - esert - 2KILL - css externo sem uso
			String cssPathFileName = null;
			log.info("cssPathFileName:[" + cssPathFileName + "]");
				
			String htmlCabecalhoPathFileName = rootPath.concat("pdfPMECabecalho.html");
			log.info("htmlCabecalhoPathFileName:[" + htmlCabecalhoPathFileName + "]");
			String htmlCabecalhoTemplate = (new FileReaderUtil()).readHTML("", htmlCabecalhoPathFileName);
			//String htmlCabecalhoTemplate = (new FileReaderUtil()).readFile(htmlCabecalhoPathFileName, Charset.defaultCharset());
				
			String htmlEmpresaPathFileName = rootPath.concat("pdfPMECorpoEmpresa.html");
			log.info("htmlEmpresaPathFileName:[" + htmlEmpresaPathFileName + "]");
			String htmlEmpresaTemplate = (new FileReaderUtil()).readHTML("", htmlEmpresaPathFileName);
			//String htmlEmpresaTemplate = (new FileReaderUtil()).readFile(htmlEmpresaPathFileName, Charset.defaultCharset());
			String htmlEmpresaValues = null;

			String htmlCorpoAbreBeneficiarioPathFileName = rootPath.concat("pdfPMECorpoAbreBeneficiarios.html");
			log.info("htmlCorpoAbreBeneficiarioPathFileName:[" + htmlCorpoAbreBeneficiarioPathFileName + "]");
			String htmlCorpoAbreBeneficiarioTemplate = (new FileReaderUtil()).readHTML("", htmlCorpoAbreBeneficiarioPathFileName);
			//String htmlCorpoAbreBeneficiarioTemplate = (new FileReaderUtil()).readFile(htmlCorpoAbreBeneficiarioPathFileName, Charset.defaultCharset());

				String htmlCorpoTituloBeneficiarioPathFileName = rootPath.concat("pdfPMECorpoTituloBeneficiario.html");
				log.info("htmlCorpoTituloBeneficiarioPathFileName:[" + htmlCorpoTituloBeneficiarioPathFileName + "]");
				String htmlCorpoTituloBeneficiarioTemplate = (new FileReaderUtil()).readHTML("", htmlCorpoTituloBeneficiarioPathFileName);
				//String htmlCorpoTituloBeneficiarioTemplate = (new FileReaderUtil()).readFile(htmlCorpoTituloBeneficiarioPathFileName, Charset.defaultCharset());
			
				String htmlCorpoVidaBeneficiarioPathFileName = rootPath.concat("pdfPMECorpoVidaBeneficiario.html");
				log.info("htmlCorpoVidaBeneficiarioPathFileName:[" + htmlCorpoVidaBeneficiarioPathFileName + "]");
				String htmlCorpoVidaBeneficiarioTemplate = (new FileReaderUtil()).readHTML("", htmlCorpoVidaBeneficiarioPathFileName);
				//String htmlCorpoVidaBeneficiarioTemplate = (new FileReaderUtil()).readFile(htmlCorpoVidaBeneficiarioPathFileName, Charset.defaultCharset());
				String htmlCorpoVidaBeneficiarioValues = null;
				
					String htmlCorpoTituloDependentePathFileName = rootPath.concat("pdfPMECorpoTituloDependente.html");
					log.info("htmlCorpoTituloDependentePathFileName:[" + htmlCorpoTituloDependentePathFileName + "]");
					String htmlCorpoTituloDependenteTemplate = (new FileReaderUtil()).readHTML("", htmlCorpoTituloDependentePathFileName);
					//String htmlCorpoTituloDependenteTemplate = (new FileReaderUtil()).readFile(htmlCorpoTituloDependentePathFileName, Charset.defaultCharset());
					
					String htmlCorpoVidaDependentePathFileName = rootPath.concat("pdfPMECorpoVidaDependente.html");
					log.info("htmlCorpoVidaDependentePathFileName:[" + htmlCorpoVidaDependentePathFileName + "]");
					String htmlCorpoVidaDependenteTemplate = (new FileReaderUtil()).readHTML("", htmlCorpoVidaDependentePathFileName);
					//String htmlCorpoVidaDependenteTemplate = (new FileReaderUtil()).readFile(htmlCorpoVidaDependentePathFileName, Charset.defaultCharset());
					String htmlCorpoVidaDependenteValues = null;
	
						String htmlCorpoFechaDependenteVidaPathFileName = rootPath.concat("pdfPMEFechaDependenteVida.html");
						log.info("pdfPMEFechaDependenteVida:[" + htmlCorpoFechaDependenteVidaPathFileName + "]");
						String htmlCorpoFechaVidaDependenteTemplate = (new FileReaderUtil()).readHTML("",htmlCorpoFechaDependenteVidaPathFileName);
						//String htmlCorpoFechaVidaDependenteTemplate = (new FileReaderUtil()).readFile(htmlCorpoFechaVidaDependentePathFileName, Charset.defaultCharset());

				String htmlCorpoFechaVidaPathFileName = rootPath.concat("pdfPMEFechaVida.html");
				log.info("htmlCorpoFechaVidaPathFileName:[" + htmlCorpoFechaVidaPathFileName + "]");
				String htmlCorpoFechaVidaTemplate = (new FileReaderUtil()).readHTML("",htmlCorpoFechaVidaPathFileName);
				//String htmlCorpoFechaVidaTemplate = (new FileReaderUtil()).readFile(htmlCorpoFechaVidaPathFileName, Charset.defaultCharset());
				
			String htmlRodapePathFileName = rootPath.concat("pdfPMERodape.html");
			log.info("htmlRodapePathFileName:[" + htmlRodapePathFileName + "]");
			String htmlRodapeTemplate = (new FileReaderUtil()).readHTML("", htmlRodapePathFileName);
			//String htmlRodapeTemplate = (new FileReaderUtil()).readFile(htmlRodapePathFileName, Charset.defaultCharset());
			String htmlRodapeValues = null;
			
			Empresa empresa = new Empresa(); //empresaService.findByCdEmpresa(cdEmpresa);

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
					prevista = " " + "prevista"; //entao as datas de movimentacao e vigencia sao PREVISTAS //201808311638 - minÚsculo vide Pedro@Vector
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
			
			Beneficiarios beneficiarios = beneficiarioService.getPage(cdContratoModelo, 999L, 1L);
			
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


package br.com.odontoprev.portal.corretor.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.business.SendMailContratoCorretora;
import br.com.odontoprev.portal.corretor.dao.ContratoCorretoraDAO;
import br.com.odontoprev.portal.corretor.dao.ContratoModeloDAO;
import br.com.odontoprev.portal.corretor.dao.CorretoraDAO;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretora;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraDataAceite;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraPreenchido;
import br.com.odontoprev.portal.corretor.dto.ContratoModelo;
import br.com.odontoprev.portal.corretor.dto.EmailContratoCorretora;
import br.com.odontoprev.portal.corretor.model.TbodContratoCorretora;
import br.com.odontoprev.portal.corretor.model.TbodContratoModelo;
import br.com.odontoprev.portal.corretor.model.TbodCorretora;
import br.com.odontoprev.portal.corretor.model.TbodEndereco;
import br.com.odontoprev.portal.corretor.service.ContratoCorretoraService;
import br.com.odontoprev.portal.corretor.service.ContratoModeloService;
import br.com.odontoprev.portal.corretor.util.Constantes;
import br.com.odontoprev.portal.corretor.util.DataUtil;
import br.com.odontoprev.portal.corretor.util.Html2Pdf;
import br.com.odontoprev.portal.corretor.util.StringsUtil;

@Service
@Transactional
public class ContratoCorretoraServiceImpl implements ContratoCorretoraService {

	private static final Log log = LogFactory.getLog(ContratoCorretoraServiceImpl.class);

	@Autowired
	private ContratoCorretoraDAO contratoCorretoraDAO;

    @Autowired
    private CorretoraDAO corretoraDAO;

    @Autowired
    private ContratoModeloDAO contratoModeloDAO;

    @Autowired
	private ContratoModeloService contratoModeloService;

    @Autowired
	private SendMailContratoCorretora sendEmailContratoCorretora;

	@Value("${server.path.pdfcontratocorretora}") //201809121533 - esert - COR-714 gerar enviar pdf contrato corretora
	private String pdfContratoCorretoraPath; //201809121533 - esert - COR-714 gerar enviar pdf contrato corretora

	@Override
	public ContratoCorretoraDataAceite getDataAceiteContratoByCdCorretora(long cdCorretora) throws Exception {
		log.info("getDataAceiteContratoByCdCorretora(" + cdCorretora + ") - ini");
		
		ContratoCorretoraDataAceite contratoCorretora = new ContratoCorretoraDataAceite();

		try {

			List<TbodContratoCorretora> listTbodContratoCorretora = contratoCorretoraDAO.findByTbodCorretoraCdCorretora(cdCorretora);

			if(listTbodContratoCorretora == null){
				log.info("getDataAceiteContratoByCdCorretora(" + cdCorretora + ") - fim - listTbodContratoCorretora == null");
				return null;
			}

			contratoCorretora.setCdCorretora(listTbodContratoCorretora.get(0).getTbodCorretora().getCdCorretora());
			contratoCorretora.setDtAceiteContrato(
					(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
							.format(listTbodContratoCorretora.get(0).getDtAceiteContrato())); //201808271556 - esert


		}catch (Exception e){
			log.info("getDataAceiteContratoByCdCorretora(" + cdCorretora + ") - erro");
			log.error(e);;
			throw new Exception(e);
		}

		log.info("getDataAceiteContratoByCdCorretora(" + cdCorretora + ") - fim");
		return contratoCorretora;
	}

	//201809101646 - esert - COR-709 - Serviço - Novo serviço GET/contratocorretora/cdCor/tipo/cdTipo
	//201809101700 - esert - COR-710 - Serviço - TDD Novo serviço GET/contratocorretora/cdCor/tipo/cdTipo
	//201809112040 - esert - COR-710 - Serviço - TDD Novo serviço GET/contratocorretora/cdCor/tipo/cdTipo
	@Override
	public ContratoCorretoraPreenchido getContratoPreenchido(Long cdCorretora, Long cdContratoModelo, String cdSusep) throws Exception {
		log.info("getContratoPreenchido - ini");
		log.info(String.format("cdCorretora:[%d]", cdCorretora));
		log.info(String.format("cdContratoModelo:[%d]", cdContratoModelo));
		log.info(String.format("cdSusep:[%s]", cdSusep));
		ContratoCorretoraPreenchido contratoCorretoraPreenchido = new ContratoCorretoraPreenchido();

		String dataAceite = DataUtil.dateToStringParse(new Date());
		
		contratoCorretoraPreenchido.setContratoPreenchido(
				montarHtmlContratoCorretagemIntermediacao(
						cdCorretora, 
						cdContratoModelo, 
						cdSusep, 
						dataAceite,
						true //apenasMiolo=true para tela //201809121522 - esert - COR-714
				)
		);
		
		ContratoModelo contratoModelo = contratoModeloService.getByCdContratoModelo(cdContratoModelo);
		contratoCorretoraPreenchido.setNomeArquivo(contratoModelo.getNomeArquivo()); //201809112249 - esert
		contratoCorretoraPreenchido.setTipoConteudo(contratoModelo.getTipoConteudo()); //201809112249 - esert
		
		log.info("getContratoPreenchido - fim");
		return  contratoCorretoraPreenchido;
	}

	@Override
	public ContratoCorretoraPreenchido getContratoPreenchidoDummy(Long cdCorretora, Long cdContratoModelo) throws Exception {
		log.info("getContratoPreenchido(" + cdCorretora + ", " + cdContratoModelo + ") - ini");
		
		ContratoCorretoraPreenchido contratoCorretora = new ContratoCorretoraPreenchido();

		try {

			//List<TbodContratoCorretora> listTbodContratoCorretora = contratoCorretoraDAO.findByTbodCorretoraCdCorretora(cdCorretora);
			List<TbodContratoCorretora> listTbodContratoCorretora = 
					contratoCorretoraDAO.findByTbodCorretoraCdCorretoraAndTbodContratoModeloCdContratoModeloOrTbodContratoModeloCdContratoModelo(
							cdCorretora,
							Constantes.CONTRATO_CORRETAGEM_V1,
							Constantes.CONTRATO_INTERMEDIACAO_V1
					);
			//TbodCorretora tbodCorretora = corretoraDAO.findOne(cdCorretora);

			if(listTbodContratoCorretora == null || listTbodContratoCorretora.size()==0){
				log.info("getContratoPreenchido(" + cdCorretora + ", " + cdContratoModelo + ") - null - listTbodContratoCorretora == null");
				return null;
			}

			contratoCorretora.setCdCorretora(listTbodContratoCorretora.get(0).getTbodCorretora().getCdCorretora());
			contratoCorretora.setCdContratoModelo(cdContratoModelo);
			String contratoPreenchido = null;
			//contratoPreenchido = tbodContratoCorretora.toString();
			//contratoPreenchido = contratoPreenchido.substring(contratoPreenchido.indexOf("{"), contratoPreenchido.length()) ;
			//contratoPreenchido = tbodCorretora.toString();
			contratoPreenchido = listTbodContratoCorretora.get(0).getTbodCorretora().toString(); //201809111752 - esert
			contratoPreenchido = contratoPreenchido.substring(contratoPreenchido.indexOf("["), contratoPreenchido.length()) ;
			contratoPreenchido = contratoPreenchido.replace("{", "<p>");
			contratoPreenchido = contratoPreenchido.replace("[", "<p>");
			contratoPreenchido = contratoPreenchido.replaceAll(",", "</p><p>");
			contratoPreenchido = contratoPreenchido.replace("}", "</p>");
			contratoPreenchido = contratoPreenchido.replace("]", "</p>");
			contratoCorretora.setContratoPreenchido(contratoPreenchido);


		}catch (Exception e){
			log.info("getContratoPreenchido(" + cdCorretora + ", " + cdContratoModelo + ") - erro");
			log.error(e);;
			throw new Exception(e);
		}

		log.info("getContratoPreenchido(" + cdCorretora + ", " + cdContratoModelo + ") - fim");
		return contratoCorretora;
	}

	//201809111900 - esert - COR-711 - mover regras do controller para service para resolver erro de dependencia no teste
	@Override
	public ContratoCorretora postContratoCorretora(ContratoCorretora contratoCorretora) {
		log.info("postContratoCorretora - ini");

        if (contratoCorretora == null) {
            log.error("postContratoCorretora - BAD_REQUEST - tbodCorretora null");
            //ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            return null;
        }

		log.info("contratoCorretora[" + contratoCorretora.toString() + "]");

        TbodCorretora tbodCorretora = corretoraDAO.findOne(contratoCorretora.getCdCorretora());

        if (tbodCorretora == null) {
            log.error("postContratoCorretora - NO_CONTENT - tbodCorretora == null");
            //ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            return null;
        }

        if (contratoCorretora.getCdSusep() != null) {
            tbodCorretora.setTemSusep(Constantes.SIM);
            tbodCorretora.setCodigoSusep(contratoCorretora.getCdSusep());
            contratoCorretora.setCdContratoModelo(Constantes.CONTRATO_CORRETAGEM_V1); // 1 - Contrato Corretagem
        } else if (contratoCorretora.getCdSusep() == null) {
            tbodCorretora.setTemSusep(Constantes.NAO);
            tbodCorretora.setCodigoSusep(null);
            contratoCorretora.setCdContratoModelo(Constantes.CONTRATO_INTERMEDIACAO_V1); // 2 - Contrato Intermediação
        }

        tbodCorretora = corretoraDAO.save(tbodCorretora);

        TbodContratoCorretora tbodContratoCorretora = new TbodContratoCorretora();

        tbodContratoCorretora.setTbodCorretora(tbodCorretora);

        TbodContratoModelo tbodContratoModelo = contratoModeloDAO.findOne(contratoCorretora.getCdContratoModelo());

        if (tbodContratoModelo == null) {
            log.error("ERROR: tbodContratoModelo == null para " + contratoCorretora.getCdContratoModelo());
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        tbodContratoCorretora.setTbodContratoModelo(tbodContratoModelo);
        tbodContratoCorretora.setDtAceiteContrato(new Date());

        tbodContratoCorretora = contratoCorretoraDAO.save(tbodContratoCorretora);
        
        contratoCorretora.setCdContratoCorretora(tbodContratoCorretora.getCdContratoCorretora());
        contratoCorretora.setDtAceiteContrato(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tbodContratoCorretora.getDtAceiteContrato()));

//        ContratoCorretora contratoCorretoraRet = this.enviarEmailContratoCorretagemIntermediacao(tbodContratoCorretora.getCdContratoCorretora());
//        log.info(contratoCorretoraRet);
        
		log.info("postContratoCorretora - fim");
        return contratoCorretora;
	}

	//201809121519 - esert - COR-714 - Serviço - Novo serviço gerar enviar contrato corretora - (apenasMiolo) define se html deve ser =(true=>para tela) ou (false>=para pdf)
	@Override
	public String montarHtmlContratoCorretagemIntermediacao(Long cdCorretora, Long cdContratoModelo, String cdSusep, String dataAceite, boolean apenasMiolo) {
		log.info("montarHtmlContratoCorretagemIntermediacao - ini");
		
		StringBuilder sbHtmlRet = new StringBuilder("");

		try {

			//obter template html/css e destino pdf
			//String rootPath = "C:\\Users\\almei\\dev\\APPS\\portal-corretor-api\\est-portalcorretor-api\\portal-corretor-servico\\src\\main\\resources\\templates\\";
			//String rootPath = "C:\\Users\\vector\\workspaceEdu\\est-portalcorretor-api\\portal-corretor-servico\\src\\main\\resources\\templates\\";
			String rootPath = ""; //nao precisa montar path rootPath para usar readHTML("", fileName) pq ja o faz internamente //201808282026 - esert 
			log.info("rootPath:[" + rootPath + "]");
				
			ContratoModelo contratoModelo = contratoModeloService.getByCdContratoModelo(cdContratoModelo);
			if(contratoModelo==null) {
				throw new Exception("contratoModelo==null para cdContratoModelo:[" + cdContratoModelo + "]");
			}
			
			String htmlCorretoraTemplate = contratoModelo.getArquivoString();
			String htmlCorretoraValues = null;
			
			TbodCorretora tbodCorretora = corretoraDAO.findOne(cdCorretora);
			if(tbodCorretora==null) {
				throw new Exception("tbodCorretora==null para cdCorretora:[" + cdCorretora + "]");
			}
			
			TbodEndereco tbodEndereco = tbodCorretora.getTbodEndereco();
			if(tbodEndereco==null) {
				log.error("tbodEndereco==null para CdCorretora:[" + tbodCorretora.getCdCorretora() + "] - endereco ficara em branco");
				tbodEndereco = new TbodEndereco();
			}

			htmlCorretoraValues = htmlCorretoraTemplate

			.replace("__DataAceite__", Objects.toString(dataAceite, ""))

			.replace("__RazaoSocial__", Objects.toString(tbodCorretora.getRazaoSocial(), ""))
			.replace("__CNPJ__", Objects.toString(tbodCorretora.getCnpj(), ""))
			
			.replace("__SUSEP__", Objects.toString(cdSusep, "(n/a)"))
			
			.replace("__CEP__", Objects.toString(tbodEndereco.getCep(), ""))
			
			.replace("__Estado__", Objects.toString(tbodEndereco.getUf(), ""))
			.replace("__Cidade__", Objects.toString(tbodEndereco.getCidade(), ""))
			.replace("__Bairro__", Objects.toString(tbodEndereco.getBairro(), ""))
			
			.replace("__Endereco__", Objects.toString(tbodEndereco.getLogradouro(), ""))
			.replace("__Numero__", Objects.toString(tbodEndereco.getNumero(), ""))
			.replace("__Complemento__", Objects.toString(tbodEndereco.getComplemento(), ""))
			;
				
			sbHtmlRet.append(htmlCorretoraValues);

		} catch (Exception e) {
			// TODO: handle exception
			log.info("montarHtmlContratoCorretagemIntermediacao - erro");
			return null;
		}
		
		log.info("montarHtmlContratoCorretagemIntermediacao - fim");
		return sbHtmlRet.toString();
	}

	//201809121519 - esert - COR-714 - Serviço - Novo serviço gerar enviar contrato corretora - (apenasMiolo) define se html deve ser =(true=>para tela) ou (false>=para pdf)
	@Override
	public ContratoCorretora createPdfContratoCorretora(TbodContratoCorretora tbodContratoCorretora) {
		log.info(String.format("createPdfContratoCorretora - ini"));
		ContratoCorretora contratoCorretora = null;
		try {
			if(tbodContratoCorretora==null) { //201809131208 - esert - protecao
				log.error(String.format("tbodContratoCorretora==null"));
				return null;
			}
			//log.info(String.format("createPdfContratoCorretora(%s)", tbodContratoCorretora));
			
			//obter empresa da venda
			TbodCorretora tbodCorretora = tbodContratoCorretora.getTbodCorretora();
			
			if(tbodCorretora==null) {
				log.error(String.format("tbodCorretora==null para CdContratoCorretora:[%d]", tbodContratoCorretora.getCdContratoCorretora()));
				return null;
			}
			
			log.info("pdfContratoCorretora:[" + pdfContratoCorretoraPath + "]");
			
			//String dataCriacaoString = (new SimpleDateFormat("yyyyMMddHHmmss")).format(agoraDate);
			String stringDataAceiteCorretora_YYYY_MM_DD = (new SimpleDateFormat("yyyy_MM_dd")).format(tbodContratoCorretora.getDtAceiteContrato());
			
			String pdfContratoCorretoraFileName = 
					"Contrato Corretora_OdontoPrev" //201809121608 - esert - ajustado nome arquivo conforme COR-80 que redefine a COR-667
					.concat("_").concat(StringsUtil.stripAccents(tbodCorretora.getRazaoSocial())) //201808311629 - esert - COR-617 - ajustado conf historia
					.concat("_").concat(stringDataAceiteCorretora_YYYY_MM_DD)
					.concat(".").concat("pdf")
					;
			log.info("pdfContratoCorretoraFileName:[" + pdfContratoCorretoraFileName + "]");
			
			String pdfContratoCorretoraPathFileName = pdfContratoCorretoraPath + pdfContratoCorretoraFileName; 
			log.info("pdfContratoCorretoraPathFileName:[" + pdfContratoCorretoraPathFileName + "]");
	
			//gerar html preenchido
			String stringHtml = montarHtmlContratoCorretagemIntermediacao(
					tbodContratoCorretora.getTbodCorretora().getCdCorretora(), 
					tbodContratoCorretora.getTbodContratoModelo().getCdContratoModelo(), 
					tbodContratoCorretora.getTbodCorretora().getCodigoSusep(), 
					DataUtil.dateToStringParse(tbodContratoCorretora.getDtAceiteContrato()),
					false //apenasMiolo=false para pdf //201809121522 - esert - COR-714
					);

			if(stringHtml==null) {
				log.error(String.format("Falha ao gerar pdf com html==null para pdfContratoCorretoraPathFileName:[%s]", pdfContratoCorretoraPathFileName));
				return null;
			}
			
			//gerar pdf com html
			Html2Pdf html2Pdf = new Html2Pdf(stringHtml);
			if(!html2Pdf.html2pdf2(stringHtml, null, pdfContratoCorretoraPathFileName)) {
				log.error(String.format("Falha ao gerar pdf com html para pdfPMEPathFileName:[%s]", pdfContratoCorretoraPathFileName));
				return null;
			}
	
			contratoCorretora = new ContratoCorretora();
			
			//salvar pdf na base
			String stringDataAceiteCorretora_yyyyMMddHHmmss = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(tbodContratoCorretora.getDtAceiteContrato());
			try {
				contratoCorretora.setCdContratoCorretora(tbodContratoCorretora.getCdContratoCorretora());
				contratoCorretora.setCdCorretora(tbodContratoCorretora.getTbodCorretora().getCdCorretora());
				contratoCorretora.setCdContratoModelo(tbodContratoCorretora.getTbodContratoModelo().getCdContratoModelo());
				contratoCorretora.setDtAceiteContrato(stringDataAceiteCorretora_yyyyMMddHHmmss);;
				contratoCorretora.setCdSusep(tbodContratoCorretora.getTbodCorretora().getCodigoSusep());;
				contratoCorretora.setNomeArquivo(pdfContratoCorretoraFileName);
				contratoCorretora.setCaminhoCarga(pdfContratoCorretoraPath);
				contratoCorretora.setTipoConteudo(MediaType.APPLICATION_PDF_VALUE); //201809122210 - esert
				try {
					Long tamanhoArquivo = new File(pdfContratoCorretoraPathFileName).length();
					contratoCorretora.setTamanhoArquivo(tamanhoArquivo);
				} catch(Exception e) {
					//#dexaketo //201809121637 - esert
				}
				
				//2kill //nao se aplica para contrato corretora pq nao salva pdf na base //mas deixa aqui por enquanto //201809121637 - esert
				//arquivoContratacao.setArquivoBase64(arquivoBase64); //sera atribuido no adaptDtoToEntity()
				//TbodArquivoContratacao tbodArquivoContratacao = adaptDtoToEntity(contratoCorretora, true);
				//tbodArquivoContratacao = arquivoContratacaoDAO.save(tbodArquivoContratacao);
				//contratoCorretora = adaptEntityToDto(tbodArquivoContratacao, true);
	
			}catch (Exception e) {
				// TODO: handle exception
				log.error(e);
				return null;
			}
			
		} catch (Exception e) {
			log.info(String.format("createPdfContratoCorretora() - erro"));
			log.error(e);
			return null;
		}
		
		log.info(String.format("createPdfContratoCorretora(%d) - fim", tbodContratoCorretora.getTbodCorretora().getCdCorretora()));
		return contratoCorretora;
	}

	//201809121519 - esert - COR-714 - Serviço - Novo serviço gerar enviar contrato corretora - (apenasMiolo) define se html deve ser =(true=>para tela) ou (false>=para pdf)
	//201809122223 - esert - COR-714 - Serviço - Novo serviço gerar enviar contrato corretora - novo parm Long cdContratoCorretora - refazer assinatura do teste
	@Override
	public ContratoCorretora enviarEmailContratoCorretagemIntermediacao(Long cdCorretora, Long cdContratoCorretora) {
		log.info("enviarEmailContratoCorretagemIntermediacao(" + cdCorretora + ") - ini");
		ContratoCorretora contratoCorretora = null;
		TbodContratoCorretora tbodContratoCorretora = null;
		try {
			List<TbodContratoCorretora> listTbodContratoCorretora = contratoCorretoraDAO.
//				findByTbodCorretoraCdCorretoraAndTbodContratoModeloCdContratoModeloOrTbodContratoModeloCdContratoModelo(
//					cdCorretora, 
//					Constantes.CONTRATO_CORRETAGEM_V1, 
//					Constantes.CONTRATO_INTERMEDIACAO_V1 
//				);
				findByCdContratoCorretoraAndTbodCorretoraCdCorretora(
					cdContratoCorretora,
					cdCorretora
				); //201809122223 - esert

			if(
				listTbodContratoCorretora==null 
				|| 
				listTbodContratoCorretora.size()==0
			) {
				log.error("listTbodContratoCorretora==null || size()==0 para cdCorretora:[" + cdCorretora + "]");
				return null;
			} else {
				tbodContratoCorretora = listTbodContratoCorretora.get(0); //201809122014 - esert
			}
			
			//gerar html preenchido
			//e
			//gerar arq pdf em dir temp
			contratoCorretora = createPdfContratoCorretora(tbodContratoCorretora);
			if(contratoCorretora==null) {
				log.error("erro em createPdfContratoCorretora para tbodContratoCorretora.getCdContratoCorretora():[" + tbodContratoCorretora.getCdContratoCorretora() + "]");
				return null;
			}
						
			//montar/enviar email passando pdf anexo
			EmailContratoCorretora emailContratoCorretora = new EmailContratoCorretora();
			emailContratoCorretora.setContratoCorretora(contratoCorretora);
			emailContratoCorretora.setCdCorretora(tbodContratoCorretora.getTbodCorretora().getCdCorretora());
			emailContratoCorretora.setNomeCorretora(tbodContratoCorretora.getTbodCorretora().getRazaoSocial());
			emailContratoCorretora.setEmailEnvio(tbodContratoCorretora.getTbodCorretora().getEmail());
			
			if(!sendEmailContratoCorretora.sendMail(emailContratoCorretora)) {
				log.error("erro em sendEmailContratoCorretora.sendMail(" + emailContratoCorretora + ")");
			};
			
		}catch(Exception e) {
			log.error("enviarEmailContratoCorretagemIntermediacao(" + cdCorretora + ") - erro");
			log.error(e);
			return null;
		}
		
		log.info("enviarEmailContratoCorretagemIntermediacao(" + cdCorretora + ") - fim");
		return contratoCorretora;
	}

	//201809121030 - esert - COR-718 - Serviço - Novo serviço GET/contratocorretora/cdCorretora/arquivo retorna PDF
	@Override
	public ContratoCorretora getContratoCorretoraPreenchidoByteArray(Long cdCorretora) {
		log.info("getContratoCorretoraPreenchidoByteArray(" + cdCorretora + ") - ini");
		try {
			log.info("getContratoCorretoraPreenchidoByteArray(" + cdCorretora + ") - fim");
			return createPdfContratoCorretora(
				contratoCorretoraDAO
				.findByTbodCorretoraCdCorretoraAndTbodContratoModeloCdContratoModeloOrTbodContratoModeloCdContratoModelo(
						cdCorretora, 
						Constantes.CONTRATO_CORRETAGEM_V1, 
						Constantes.CONTRATO_INTERMEDIACAO_V1
				).get(0)
			);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//a mensagem de erro peculiar deve_se ao fato que uma excecao pode representar mais de uma situacao
			//havendo registro(s), o erro pode ser um mal funcionamento qualquer
			//NAO havendo registro, o erro sera java.lang.IndexOutOfBoundsException
			//mas em ambos os casos vai retornar NULL que representa NOCONTENT no CONTROLLER
			log.info("getContratoCorretoraPreenchidoByteArray(" + cdCorretora + ") - erro de lista vazia/nula ou outro pior:( ");
			log.error(e);
			return null;
		}
	}

}

package br.com.odontoprev.portal.corretor.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.ContratoCorretoraDAO;
import br.com.odontoprev.portal.corretor.dao.ContratoModeloDAO;
import br.com.odontoprev.portal.corretor.dao.CorretoraDAO;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretora;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraDataAceite;
import br.com.odontoprev.portal.corretor.dto.ContratoCorretoraPreenchido;
import br.com.odontoprev.portal.corretor.dto.ContratoModelo;
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
		log.info("contratoCorretora[" + contratoCorretora.toString() + "]");
		
        TbodCorretora tbodCorretora = corretoraDAO.findOne(contratoCorretora.getCdCorretora());

        if (tbodCorretora == null) {
            log.error("postContratoCorretora - BAD_REQUEST - tbodCorretora null");
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (tbodCorretora == null) {
            log.error("postContratoCorretora - BAD_REQUEST - tbodCorretora null");
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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

        this.enviarEmailContratoCorretagemIntermediacao(contratoCorretora.getCdCorretora());
        
        contratoCorretora.setCdContratoCorretora(tbodContratoCorretora.getCdContratoCorretora());
        contratoCorretora.setDtAceiteContrato(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tbodContratoCorretora.getDtAceiteContrato()));
        
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
				return null;
			}
			
			if(tbodCorretora.getCodigoSusep()!=null) {
				cdSusep = tbodCorretora.getCodigoSusep(); //201809112302 - esert
			}
			
			TbodEndereco tbodEndereco = tbodCorretora.getTbodEndereco();
			if(tbodEndereco==null) {
				tbodEndereco = new TbodEndereco();
			}
			
			if(
				tbodCorretora.getTbodContratoCorretoras()!=null 
				&& 
				tbodCorretora.getTbodContratoCorretoras().size()>0
			) {
				Optional<TbodContratoCorretora> optionalTbodContratoCorretora = 
						tbodCorretora.getTbodContratoCorretoras()
						.stream()
						.filter(item -> item.getTbodContratoModelo().getCdContratoModelo() == cdContratoModelo)
						.findFirst();
				
				if(optionalTbodContratoCorretora.isPresent()) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					dataAceite = sdf.format(optionalTbodContratoCorretora.get().getDtAceiteContrato());
				}
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
	public ContratoCorretora createPdfContratoCorretoraPorCorretora(Long cdCorretora) {
		log.info(String.format("createPdfContratoCorretoraPorCorretora(%d) - ini", cdCorretora));
		ContratoCorretora contratoCorretora = null;
		try {
			//obter empresa da venda
			TbodCorretora tbodCorretora = corretoraDAO.findOne(cdCorretora);
			
			if(tbodCorretora==null) {
				log.error(String.format("tbodCorretora==null para cdCorretora:[%d]", cdCorretora));
				return null;
			}
			
			log.info("pdfContratoCorretora:[" + pdfContratoCorretoraPath + "]");
			
			Date agoraDate = new Date();
			//String dataCriacaoString = (new SimpleDateFormat("yyyyMMddHHmmss")).format(agoraDate);
			String dataCriacaoString = (new SimpleDateFormat("yyyy_MM_dd")).format(agoraDate);
			
			String pdfContratoCorretoraFileName = 
					"Contrato Corretora_OdontoPrev" //201809121608 - esert - ajustado nome arquivo conforme COR-80 que redefine a COR-667
					.concat("_").concat(StringsUtil.stripAccents(tbodCorretora.getRazaoSocial())) //201808311629 - esert - COR-617 - ajustado conf historia
					.concat("_").concat(dataCriacaoString)
					.concat(".").concat("pdf")
					;
			log.info("pdfContratoCorretoraFileName:[" + pdfContratoCorretoraFileName + "]");
			
			String pdfContratoCorretoraPathFileName = pdfContratoCorretoraPath + pdfContratoCorretoraFileName; 
			log.info("pdfContratoCorretoraPathFileName:[" + pdfContratoCorretoraPathFileName + "]");
	
			//gerar html
			List<TbodContratoCorretora> listTbodContratoCorretora = contratoCorretoraDAO.findByTbodCorretoraCdCorretoraAndTbodContratoModeloCdContratoModeloOrTbodContratoModeloCdContratoModelo(cdCorretora, Constantes.CONTRATO_CORRETAGEM_V1, Constantes.CONTRATO_INTERMEDIACAO_V1);
			
			if(listTbodContratoCorretora==null || listTbodContratoCorretora.size()==0) {
				log.error("enviarEmailContratoCorretagemIntermediacao - erro - listTbodContratoCorretora==null || listTbodContratoCorretora.size()==0");
				return null;
			}
			
			//gerar html preenchido
			String html = montarHtmlContratoCorretagemIntermediacao(
					listTbodContratoCorretora.get(0).getTbodCorretora().getCdCorretora(), 
					listTbodContratoCorretora.get(0).getTbodContratoModelo().getCdContratoModelo(), 
					listTbodContratoCorretora.get(0).getTbodCorretora().getCodigoSusep(), 
					DataUtil.dateToStringParse(listTbodContratoCorretora.get(0).getDtAceiteContrato()),
					false //apenasMiolo=false para pdf //201809121522 - esert - COR-714
					);

			if(html==null) {
				log.error(String.format("Falha ao gerar pdf com html==null para pdfContratoCorretoraPathFileName:[%s]", pdfContratoCorretoraPathFileName));
				return null;
			}
			
			//gerar pdf com html
			Html2Pdf html2Pdf = new Html2Pdf(html);
			if(!html2Pdf.html2pdf2(html, null, pdfContratoCorretoraPathFileName)) {
				log.error(String.format("Falha ao gerar pdf com html para pdfPMEPathFileName:[%s]", pdfContratoCorretoraPathFileName));
				return null;
			}
	
			contratoCorretora = new ContratoCorretora();
			
			//salvar pdf na base
			String dataCriacaoStringDTO = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(agoraDate);
			try {
				contratoCorretora.setCdCorretora(tbodCorretora.getCdCorretora());
				contratoCorretora.setDtAceiteContrato(dataCriacaoStringDTO);;
				contratoCorretora.setNomeArquivo(pdfContratoCorretoraFileName);
				contratoCorretora.setCaminhoCarga(pdfContratoCorretoraPath);
				contratoCorretora.setTipoConteudo(MediaType.TEXT_HTML_VALUE);
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
			log.info(String.format("createPdfContratoCorretoraPorCorretora() - erro"));
			log.error(e);
			return null;
		}
		
		log.info(String.format("createPdfContratoCorretoraPorCorretora(%d) - fim", cdCorretora));
		return contratoCorretora;
	}

	//201809121519 - esert - COR-714 - Serviço - Novo serviço gerar enviar contrato corretora - (apenasMiolo) define se html deve ser =(true=>para tela) ou (false>=para pdf)
	@Override
	public ContratoCorretora enviarEmailContratoCorretagemIntermediacao(Long cdCorretora) {
		log.info("enviarEmailContratoCorretagemIntermediacao(" + cdCorretora + ") - ini");
		ContratoCorretora contratoCorretora = null;
		try {
			List<TbodContratoCorretora> listTbodContratoCorretora = 
					contratoCorretoraDAO.findByTbodCorretoraCdCorretoraAndTbodContratoModeloCdContratoModeloOrTbodContratoModeloCdContratoModelo(
							cdCorretora, 
							Constantes.CONTRATO_CORRETAGEM_V1, 
							Constantes.CONTRATO_INTERMEDIACAO_V1);
			
			if(listTbodContratoCorretora==null || listTbodContratoCorretora.size()==0) {
				log.error("enviarEmailContratoCorretagemIntermediacao - erro - listTbodContratoCorretora==null || listTbodContratoCorretora.size()==0");
				return null;
			}
			
			//gerar html preenchido
			//e
			//gerar arq pdf em dir temp
			contratoCorretora  = createPdfContratoCorretoraPorCorretora(cdCorretora);
						
			//montar/enviar email passando pdf anexo
			
		}catch(Exception e) {
			log.info("enviarEmailContratoCorretagemIntermediacao(" + cdCorretora + ") - erro");
			log.error(e);
			return null;
		}
		
		log.info("enviarEmailContratoCorretagemIntermediacao(" + cdCorretora + ") - fim");
		return contratoCorretora;
	}

}

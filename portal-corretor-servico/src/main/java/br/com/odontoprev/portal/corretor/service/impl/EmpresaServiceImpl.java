package br.com.odontoprev.portal.corretor.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.text.MaskFormatter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.business.BeneficiarioBusiness;
import br.com.odontoprev.portal.corretor.business.EmpresaBusiness;
import br.com.odontoprev.portal.corretor.business.SendMailAceite;
import br.com.odontoprev.portal.corretor.business.SendMailBoasVindasPME;
import br.com.odontoprev.portal.corretor.dao.EmpresaDAO;
import br.com.odontoprev.portal.corretor.dao.LogEmailBoasVindasPMEDAO;
import br.com.odontoprev.portal.corretor.dao.TokenAceiteDAO;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.dto.CnpjDados;
import br.com.odontoprev.portal.corretor.dto.CnpjDadosAceite;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.dto.EmpresaDcms;
import br.com.odontoprev.portal.corretor.dto.EmpresaEmailAceite;
import br.com.odontoprev.portal.corretor.dto.EmpresaResponse;
import br.com.odontoprev.portal.corretor.dto.TokenAceite;
import br.com.odontoprev.portal.corretor.dto.TokenAceiteResponse;
import br.com.odontoprev.portal.corretor.model.TbodEmpresa;
import br.com.odontoprev.portal.corretor.model.TbodLogEmailBoasVindasPME;
import br.com.odontoprev.portal.corretor.model.TbodTokenAceite;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.model.TbodVida;
import br.com.odontoprev.portal.corretor.service.EmpresaService;
import br.com.odontoprev.portal.corretor.service.PlanoService;
import br.com.odontoprev.portal.corretor.service.TokenAceiteService;
import br.com.odontoprev.portal.corretor.util.ConvertObjectUtil;
import br.com.odontoprev.portal.corretor.util.DataUtil;
import br.com.odontoprev.portal.corretor.util.PropertiesUtils;
import br.com.odontoprev.portal.corretor.util.XlsVidas;

@Service
public class EmpresaServiceImpl implements EmpresaService {

	private static final Log log = LogFactory.getLog(EmpresaServiceImpl.class);

	private List<String> listaEmails = new ArrayList<>();

	private String emailForcaVenda = "";

	@Autowired
	EmpresaDAO empresaDAO;

	@Autowired
	VendaDAO vendaDAO;
	
	@Autowired
	TokenAceiteDAO tokenAceiteDAO;

	@Autowired
	LogEmailBoasVindasPMEDAO logEmailBoasVindasPMEDAO; //201805221245 - esert - COR-225 - Serviço - LOG Envio e-mail de Boas Vindas PME

	@Autowired
	EmpresaBusiness empresaBusiness;

	@Autowired
	BeneficiarioBusiness beneficiarioBusiness;

	@Autowired
	SendMailAceite sendMailAceite;
	
	@Autowired
	SendMailBoasVindasPME sendMailBoasVindasPME;

	@Autowired
	PlanoService planoService;

    @Value("${mensagem.empresa.atualizada.dcms}")
	private String empresaAtualizadaDCMS; //201805181310 - esert - COR-160

    @Value("${mensagem.empresa.atualizada.aceite}")
	private String empresaAtualizadaAceite; //201805181310 - esert - COR-171

    @Autowired
	private TokenAceiteService tokenAceiteService; //201805181856 - esert - COR-171

	@Override
	@Transactional
	public EmpresaResponse add(Empresa empresa) {

		log.info("[EmpresaServiceImpl::add]");

		return empresaBusiness.salvarEmpresaEnderecoVenda(empresa);
	}

	@Override
	@Transactional
	public EmpresaResponse updateEmpresa(EmpresaDcms empresaDcms) {

		log.info("[EmpresaServiceImpl::updateEmpresa]");
		
		TbodEmpresa tbEmpresa = new TbodEmpresa();

		try {
			
			if(
				(empresaDcms.getCdEmpresa() == null)
				|| 
				(empresaDcms.getCnpj() == null || empresaDcms.getCnpj().isEmpty())
				|| 
				(empresaDcms.getEmpDcms() == null || empresaDcms.getEmpDcms().isEmpty())
			) {
				throw new Exception("Os parametros sao obrigatorios!");
			}

			
			MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
			mask.setValueContainsLiteralCharacters(false);
			
			String cnpj = empresaDcms.getCnpj().replace(".", "").replace("/", "").replace("-", "").replace(" ", ""); //201805171917 - esert - desformata se [eventualmente] vier formatado
			cnpj = mask.valueToString(cnpj); //201805171913 - esert - inc mask.valueToString() - reformata
			tbEmpresa = empresaDAO.findBycdEmpresaAndCnpj(empresaDcms.getCdEmpresa(), cnpj); 

			if (tbEmpresa != null) {
				tbEmpresa.setEmpDcms(empresaDcms.getEmpDcms());
				empresaDAO.save(tbEmpresa);
				
				List<TbodVida> vidas = beneficiarioBusiness.buscarVidasPorEmpresa(tbEmpresa.getCdEmpresa());
				
				if(vidas != null && !vidas.isEmpty()) {
					XlsVidas xlsVidas = new XlsVidas();
					xlsVidas.gerarVidasXLS(vidas, tbEmpresa);
				}
				
				//201805091745 - esert
				//201805101609 - esert - criar servico independente para Email Boas Vindas PME vide Fernando@ODPV em 20180510
				//201805101941 - esert - excluido param cnpj
				ResponseEntity<EmpresaDcms> res = this.sendMailBoasVindasPME(empresaDcms.getCdEmpresa());
				//log.info("res:[" + res.toString() + "]");
			}
			else {
				throw new Exception("CdEmpresa nao relacionado com CNPJ!");
			}

		} catch (Exception e) {
			log.error("EmpresaServiceImpl :: Erro ao atualizar empresaDcms. Detalhe: [" + e.getMessage() + "]");
			return new EmpresaResponse(0, "Erro ao cadastrar empresaDcms. Favor, entre em contato com o suporte. Detalhe: [" + e.getMessage() + "]");
		}

		return new EmpresaResponse(tbEmpresa.getCdEmpresa(), empresaAtualizadaDCMS);

	}

	//201805091745 - esert
	//201805101941 - esert - excluido param cnpj
	//201805101947 - esert - service para reenvio de emails vide Fernando@ODPV
	//201805221057 - esert - refactor sendEmailBoasVindasPME para sendMailBoasVindasPME mantendo padrao
	@Override
	public ResponseEntity<EmpresaDcms> sendMailBoasVindasPME(Long cdEmpresa) {
		Long longDiaVencimentoFatura = 0L;
		Date dateDataVenda = null;
		
		try {
			log.info("cdEmpresa:[" + cdEmpresa + "]");
			
			TbodEmpresa tbodEmpresa = empresaDAO.findOne(cdEmpresa);
			listaEmails.add(tbodEmpresa.getEmail());

			if(tbodEmpresa==null) {
				log.info("tbodEmpresa==null");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			
			if(tbodEmpresa.getEmail()==null) {
				log.info("tbodEmpresa.getEmail()==null");
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
			}
			
			log.info("tbodEmpresa.getEmpDcms():[" + tbodEmpresa.getEmpDcms() + "]");
			
			List<TbodVenda> tbodVendas = vendaDAO.findByTbodEmpresaCdEmpresa(cdEmpresa);

			if(tbodVendas!=null) {
				log.info("tbodVendas.size():[" + tbodVendas.size() + "]"); //201805221233 - esert				
				if(tbodVendas.size()>0) { //201805221233 - esert - protecao

					for (TbodVenda tbodVenda : tbodVendas) {
						//201805101616 - esert - arbitragem : fica com a ultima venda suposta mais recente
						longDiaVencimentoFatura = tbodVenda.getFaturaVencimento();
						log.info("longDiaVencimentoFatura:[" + longDiaVencimentoFatura + "]");				
						dateDataVenda = tbodVenda.getDtVenda();
						log.info("dateDataVenda:[" + (new SimpleDateFormat("dd/MM/yyyy")).format(dateDataVenda) + "]");

						if(emailForcaVenda.isEmpty()){
							emailForcaVenda = tbodVenda.getTbodForcaVenda().getEmail();
							listaEmails.add(emailForcaVenda);
						}

					}
				} else {
					log.info("tbodVendas.size == 0 ZERO para cdEmpresa:[" + cdEmpresa + "]!!!"); //201805221233 - esert - protecao
					throw new Exception("tbodVendas.size == 0 ZERO para cdEmpresa:[" + cdEmpresa + "]!!!"); //201805221233 - esert - protecao
				}
			} else {
				log.info("tbodVendas == null para cdEmpresa:[" + cdEmpresa + "]"); //201805221233 - esert - protecao
				throw new Exception("tbodVendas == null para cdEmpresa:[" + cdEmpresa + "]!!!"); //201805221233 - esert - protecao
			}

			log.info("longDiaVencimentoFatura:[" + longDiaVencimentoFatura + "]");				
			log.info("dateDataVenda:[" + (new SimpleDateFormat("dd/MM/yyyy")).format(dateDataVenda) + "]");				

			String strDiaVencimentoFatura = String.valueOf(100L + longDiaVencimentoFatura).substring(1,3);
			log.info("strDiaVencimentoFatura:[" + strDiaVencimentoFatura + "]");				

			//201805101739 - esert - funcao isEffectiveDate copíada do App na versao abaixo porem usa DataVenda ao inves de CurrentDate vide Camila@ODPV
			//http://git.odontoprev.com.br/esteira-digital/est-portalcorretor-app/blob/sprint6/VendasOdontoPrev/app/src/main/assets/app/pmeFaturaController.js
			String strDataVigencia = DataUtil.isEffectiveDate(longDiaVencimentoFatura, dateDataVenda);
			log.info("strDataVigencia:[" + strDataVigencia + "]");				

			// TESTE VICTOR
			sendMailBoasVindasPME.sendMail(
				//tbodEmpresa.getEmail(), //email,
				listaEmails, //lista de emails,
				tbodEmpresa.getNomeFantasia(), //nomeCorretora, 
				tbodEmpresa.getEmpDcms(), //login, 
				PropertiesUtils.getProperty(PropertiesUtils.SENHA_INICIAL_PORTAL_PME), //senha, 
				PropertiesUtils.getProperty(PropertiesUtils.LINK_PORTAL_PME_URL), //linkPortal, 
				strDataVigencia, //dataVigencia, 
				strDiaVencimentoFatura //diaVencimentoFatura
				);
			
			//201805221245 - esert - COR-225 - Serviço - LOG Envio e-mail de Boas Vindas PME
			TbodLogEmailBoasVindasPME tbodLogEmailBoasVindasPME = new TbodLogEmailBoasVindasPME();
			tbodLogEmailBoasVindasPME.setCdEmpresa(tbodEmpresa.getCdEmpresa());
			tbodLogEmailBoasVindasPME.setRazaoSocial(tbodEmpresa.getRazaoSocial());
			tbodLogEmailBoasVindasPME.setEmail(tbodEmpresa.getEmail());
			tbodLogEmailBoasVindasPME.setDtEnvio(new Date());
			logEmailBoasVindasPMEDAO.save(tbodLogEmailBoasVindasPME);
			
			EmpresaDcms empresaDcms = new EmpresaDcms();
			empresaDcms.setCdEmpresa(tbodEmpresa.getCdEmpresa());
			empresaDcms.setEmpDcms(tbodEmpresa.getEmpDcms());
			empresaDcms.setCnpj(tbodEmpresa.getCnpj()); //201805221106 - esert - COR-160 - refactor - semm qg no cnpj
			empresaDcms.setEmail(tbodEmpresa.getEmail()); //201805221106 - esert - COR-160 - refactor - inc campo especifico para email
			return ResponseEntity.ok(empresaDcms);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}		
		
	}

	//201805102039 - esert - COR-169 - obter tbodEmpresa.getEmpDcms()); 				
	@Override
	public CnpjDados findDadosEmpresaByCnpj(String cnpj) throws ParseException {
		
		log.info("#### findDadosEmpresaByCnpj ####");
		
		MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
		mask.setValueContainsLiteralCharacters(false);
		
		TbodEmpresa tbodEmpresa = null;		
		CnpjDados cnpjDados = new CnpjDados();		
		
		try {
			
			if ( cnpj.length() == 14) {
				
				tbodEmpresa = empresaDAO.findByCnpj(mask.valueToString(cnpj));
								
				if(tbodEmpresa != null) {
					cnpjDados.setCdEmpresa(tbodEmpresa.getCdEmpresa());
					cnpjDados.setRazaoSocial(tbodEmpresa.getRazaoSocial());
					cnpjDados.setCnpj(cnpj);				
					cnpjDados.setEmpDcms(tbodEmpresa.getEmpDcms()); //201805102039 - esert - COR-169				
				} else {				
					tbodEmpresa = empresaDAO.findByCnpj(cnpj);
					if(tbodEmpresa == null) {
						cnpjDados.setObservacao("Cnpj [" + cnpj + "] não encontrado na base !!! [" + (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date().getTime())) + "]");
					} else {
						cnpjDados.setCdEmpresa(tbodEmpresa.getCdEmpresa());
						cnpjDados.setRazaoSocial(tbodEmpresa.getRazaoSocial());
						cnpjDados.setCnpj(cnpj);
						cnpjDados.setEmpDcms(tbodEmpresa.getEmpDcms()); //201805102039 - esert - COR-169				
					}						
				}
			} else {
				cnpjDados.setObservacao("Cnpj é obrigatório informar 14 digitos!!!");
			}
			
		} catch (Exception e) {
			cnpjDados.setObservacao("Encontrado +1 cnpj na base!!!");
			return cnpjDados;
		}
		
		return cnpjDados;	
	}
	
	//201805111131 - esert - COR-172 - Serviço - Consultar dados empresa PME 				
	@Override
	public CnpjDadosAceite findDadosEmpresaAceiteByCnpj(String cnpj) throws ParseException { //201805111131 - esert - COR-172
		CnpjDadosAceite cnpjDadosAceite = new CnpjDadosAceite();		
		
		log.info("findDadosEmpresaAceiteByCnpj - ini");
		
		MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
		mask.setValueContainsLiteralCharacters(false);
		
		TbodEmpresa tbodEmpresa = null;		
		TbodVenda tbodVenda = null;		
		TbodTokenAceite tbodTokenAceite = null;		
		
		try {
			
			if ( cnpj.length() == 14) {
				
				tbodEmpresa = empresaDAO.findByCnpj(mask.valueToString(cnpj));
				
				if(tbodEmpresa == null) {
					tbodEmpresa = empresaDAO.findByCnpj(cnpj);
				}
				
				if(tbodEmpresa != null) {
					cnpjDadosAceite = translateTbodEmpresaToCnpjDadosAceite(cnpjDadosAceite, tbodEmpresa);
					
					List<TbodVenda> listTbodVenda = vendaDAO.findByTbodEmpresaCdEmpresa(tbodEmpresa.getCdEmpresa());
					
					if(listTbodVenda!=null) {
						Date maiorDataVenda = null;
						Long maiorCdVenda = null;
						for (TbodVenda tbodVendaItem : listTbodVenda) {
							if(
								maiorDataVenda==null
								||
								maiorCdVenda==null
								||
								tbodVendaItem.getDtVenda().getTime() > maiorDataVenda.getTime() //201805111201 - esert - COR-172 - fica com maiorDataVenda para ter o mais recente
								||
								tbodVendaItem.getCdVenda() > maiorCdVenda //201805111201 - esert - COR-172 - fica com maiorDataVenda para ter o mais recente
							) {
								tbodVenda = tbodVendaItem;
								maiorDataVenda = tbodVenda.getDtVenda(); //201805111201 - esert - COR-172 - fica com maiorDataVenda para ter o mais recente
								maiorCdVenda = tbodVenda.getCdVenda(); //201805111517 - esert - COR-172 - fica com maiorDataVenda para ter o mais recente
							}
						}
					}
					
					if(tbodVenda != null) {
						
						cnpjDadosAceite.setCdVenda(tbodVenda.getCdVenda());
						cnpjDadosAceite.setDtVenda(tbodVenda.getDtVenda()); //201805111519 - esert - informativo de desempate
						
//						tbodTokenAceite = tokenAceiteDAO.findByTbodVendaCdVenda(tbodVenda.getCdVenda());
						List<TbodTokenAceite> listTbodTokenAceite = tokenAceiteDAO.findTokenPorVenda(tbodVenda.getCdVenda()); //201805211602 - esert - pega ultimo token da venda
						if(listTbodTokenAceite!=null) {
							if(listTbodTokenAceite.size()>0) {
								if(listTbodTokenAceite.size()==1) {
									tbodTokenAceite = listTbodTokenAceite.get(listTbodTokenAceite.size()-1); //pega unico 									
								} else {
									Date maiorDtEnvio = (new GregorianCalendar(1900,1,1).getTime());
									for(TbodTokenAceite itemTokenAceite : listTbodTokenAceite) {
										if(itemTokenAceite.getDtEnvio().getTime() > maiorDtEnvio.getTime()) { //fica 											
											maiorDtEnvio = itemTokenAceite.getDtEnvio();
											tbodTokenAceite = itemTokenAceite; //pega maior data envio
										}
									}
								} //else //if(listTbodTokenAceite.size()==1)
						
								if(tbodTokenAceite!=null) {
									cnpjDadosAceite = translateTbodTokenAceiteToCnpjDadosAceite(cnpjDadosAceite, tbodTokenAceite);
								} else {
									cnpjDadosAceite.setObservacao("TokenAceite não definido !!!");							
								} //if(tbodTokenAceite!=null)
							} else {
								cnpjDadosAceite.setObservacao("TokenAceite não encontrado !!!");														
							} //if(listTbodTokenAceite.size()>0)
						} else {
							cnpjDadosAceite.setObservacao("TokenAceite não encontrado !!!");														
						} //if(listTbodTokenAceite==null)
					} else {
						cnpjDadosAceite.setObservacao("Venda não encontrado !!!");
					} //if(tbodVenda != null)
				} else {
					cnpjDadosAceite.setObservacao("CNPJ não encontrado!!!");
				} //if(tbodEmpresa != null)				
			} else {
				cnpjDadosAceite.setObservacao("CNPJ obrigatório informar 14 digitos!!!");
			} //if ( cnpj.length() == 14)
			
		} catch (Exception e) {
			log.error("Exception e:["+ e.getMessage() +"]");
			cnpjDadosAceite.setObservacao("Encontrado +1 cnpj na base!!!");
		}
		
		log.info("findDadosEmpresaAceiteByCnpj - fim");

		return cnpjDadosAceite;	
	}

	//201805111210 - esert - COR-172
	private CnpjDadosAceite translateTbodTokenAceiteToCnpjDadosAceite( //201805111210 - esert - COR-172
			CnpjDadosAceite cnpjDadosAceite,
			TbodTokenAceite tbodTokenAceite
	) {
		log.info("translateTbodTokenAceiteToCnpjDadosAceite - ini");

		if(tbodTokenAceite!=null) {
			
			if(cnpjDadosAceite==null) {
				cnpjDadosAceite = new CnpjDadosAceite();
			}
			
			cnpjDadosAceite.setTokenAceite(
					ConvertObjectUtil.translateTbodTokenAceiteToTokenAceite(
							tbodTokenAceite
					)
			);
		}
		log.info("translateTbodTokenAceiteToCnpjDadosAceite - fim");
		return cnpjDadosAceite;
	}

	//201805111153 - esert - COR-172
	private CnpjDadosAceite translateTbodEmpresaToCnpjDadosAceite( //201805111153 - esert - COR-172
			CnpjDadosAceite cnpjDadosAceite, 
			TbodEmpresa tbodEmpresa
	) {
		log.info("translateTbodEmpresaToCnpjDadosAceite - ini");

		if(tbodEmpresa!=null) {
			
			if(cnpjDadosAceite==null) {
				cnpjDadosAceite = new CnpjDadosAceite();
			}
			
			cnpjDadosAceite.setCdEmpresa(tbodEmpresa.getCdEmpresa());
			cnpjDadosAceite.setRazaoSocial(tbodEmpresa.getRazaoSocial());
			cnpjDadosAceite.setCnpj(tbodEmpresa.getCnpj());				
			cnpjDadosAceite.setEmpDcms(tbodEmpresa.getEmpDcms()); //201805102039 - esert - COR-169
			cnpjDadosAceite.setEmail(tbodEmpresa.getEmail()); //201805181249 - esert - COR-169
		}
		log.info("translateTbodEmpresaToCnpjDadosAceite - fim");
		return cnpjDadosAceite;
	}

	//201805111544 - esert - COR-171 - Serviço - Atualizar email cadastrado empresa
	@Override
	@Transactional
	public EmpresaResponse updateEmpresaEmailAceite(EmpresaEmailAceite empresaEmail) { //201805111544 - esert - COR-171 - Serviço - Atualizar email cadastrado empresa

		log.info("updateEmpresaEmailAceite - ini");
		
		TbodEmpresa tbEmpresa = new TbodEmpresa();

		try {
			
			if(
				empresaEmail.getCdEmpresa() == null
				|| 
				empresaEmail.getCdEmpresa() == 0
			){
				return (new EmpresaResponse(empresaEmail.getCdEmpresa(), "parametro CdEmpresa obrigatorio!"));
			}
			
			if(
				empresaEmail.getCdVenda() == null
				|| 
				empresaEmail.getCdVenda() == 0
			){
				return (new EmpresaResponse(empresaEmail.getCdEmpresa(), "parametro CdVenda obrigatorio!")); //201805171124 - esert
			}
			
			if(
				empresaEmail.getEmail() == null
				||
				empresaEmail.getEmail().isEmpty()
			){
				return (new EmpresaResponse(0, "parametro Email obrigatorio !"));
			}

			tbEmpresa = empresaDAO.findOne(empresaEmail.getCdEmpresa());

			if (tbEmpresa != null) {
				tbEmpresa.setEmail(empresaEmail.getEmail());
				empresaDAO.save(tbEmpresa);
								
				TbodVenda tbodVenda = vendaDAO.findOne(empresaEmail.getCdVenda());
				if(tbodVenda==null) {
					return (new EmpresaResponse(empresaEmail.getCdVenda(), "TbodVenda nao encontrado para empresaEmail.getCdVenda("+ empresaEmail.getCdVenda() +")!"));
				}
				
//				LocalDateTime dataHoraAtual = LocalDateTime.now();
//				TbodTokenAceitePK tokenAceitePK = new TbodTokenAceitePK();
//				tokenAceitePK.setCdVenda(tbodVenda.getCdVenda());
//				String token = this.gerarToken(tbodVenda.getCdVenda().toString()+dataHoraAtual.toString().replace("=", ""));
//				tokenAceitePK.setCdToken(token.replace("=", ""));		
//				System.out.println(tokenAceitePK.getCdToken());
//				
//				TbodTokenAceite tbodTokenAceite = new TbodTokenAceite();
//				tbodTokenAceite.setId(tokenAceitePK);
//				tbodTokenAceite.setEmailEnvio(tbodVenda.getTbodEmpresa().getEmail());
//				tbodTokenAceite.setDtEnvio(new Date());
//				
//				Calendar c = Calendar.getInstance();
//				Long expiracao = Long.valueOf(EXPIRACAO_TOKEN_ACEITE_EMAIL);
//			    c.add(Calendar.DAY_OF_MONTH, expiracao.intValue());
//			    tbodTokenAceite.setDtExpiracao(c.getTime());
//				
//				tbodTokenAceite = tokenAceiteDAO.save(tbodTokenAceite);
//				
//				EmailAceite emailAceite = new EmailAceite();
//				emailAceite.setNomeCorretor(tbodVenda.getTbodForcaVenda().getNome());
//				emailAceite.setNomeCorretora(tbodVenda.getTbodForcaVenda().getTbodCorretora().getNome());
//				emailAceite.setNomeEmpresa(tbodVenda.getTbodEmpresa().getRazaoSocial());
//				emailAceite.setEmailEnvio(tbodVenda.getTbodEmpresa().getEmail());
//				emailAceite.setToken(tbodTokenAceite.getId().getCdToken());
//				
//				List<Plano> planos = planoService.findPlanosByEmpresa(tbodVenda.getTbodEmpresa().getCdEmpresa());
//											
//				emailAceite.setPlanos(planos);
//				sendMailAceite.sendMail(emailAceite);
				
				TokenAceite tokenAceite = new TokenAceite(); //201805181904 - esert - COR-171
				tokenAceite.setCdTokenAceite(null); //sera atribuido dentro de addTokenAceite() //201805181904 - esert - COR-171
				tokenAceite.setCdVenda(tbodVenda.getCdVenda());
				tokenAceite.setDataAceite(null); //sera atribuido dentro de addTokenAceite() //201805181904 - esert - COR-171
				tokenAceite.setDataEnvio(null); //sera atribuido dentro de addTokenAceite() //201805181904 - esert - COR-171
				tokenAceite.setDataExpiracao(null); //sera atribuido dentro de addTokenAceite() //201805181904 - esert - COR-171
				tokenAceite.setEmail(null); //sera atribuido dentro de addTokenAceite() //201805181904 - esert - COR-171
				tokenAceite.setIp(null); //sera atribuido dentro de addTokenAceite() //201805181904 - esert - COR-171
				tokenAceite.setToken(null); //sera atribuido dentro de addTokenAceite() //201805181904 - esert - COR-171
				
				TokenAceiteResponse tokenAceiteResponse = tokenAceiteService.addTokenAceite(tokenAceite); //201805181904 - esert - COR-171
				
				if(tokenAceiteResponse!= null) {
					if(tokenAceiteResponse.getId()==0L) {
						throw new Exception("updateEmpresaEmailAceite :: Erro ao gerar token ou enviar email aceite ! na chamada de addTokenAceite( CdVenda([" + tbodVenda.getCdVenda() + "])); tokenAceiteResponse.getMensagem([" + tokenAceiteResponse.getMensagem() + "]); tokenAceiteResponse.getId([" + tokenAceiteResponse.getId() + "])"); //201805181916 - esert - COR-171
					}
				}
				
			} else {
				throw new Exception("CdEmpresa [" + empresaEmail.getCdEmpresa() + "] nao encontrado !"); //201705172015 - esert
			}

		} catch (Exception e) {
			log.error("EmpresaServiceImpl : updateEmpresaEmailAceite : Erro ao atualizar empresaDcms. Detalhe: [" + e.getMessage() + "]");
			return new EmpresaResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro ao salvar email da empresa. Favor, entre em contato com o suporte. Detalhe: [" + e.getMessage() + "][" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + "]");
		}
		
		log.info("updateEmpresaEmailAceite - fim");

		return new EmpresaResponse(HttpStatus.OK.value(), empresaAtualizadaAceite);  //201805181310 - esert - COR-171
	}

	public List<String> getListaEmails() {
		return listaEmails;
	}

	public void setListaEmails(List<String> listaEmails) {
		this.listaEmails = listaEmails;
	}

	public String getEmailForcaVenda() {
		return emailForcaVenda;
	}

	public void setEmailForcaVenda(String emailForcaVenda) {
		this.emailForcaVenda = emailForcaVenda;
	}
}

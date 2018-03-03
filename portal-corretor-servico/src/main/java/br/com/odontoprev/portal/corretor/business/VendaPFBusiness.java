package br.com.odontoprev.portal.corretor.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import br.com.odontoprev.api.manager.client.token.ApiManagerToken;
import br.com.odontoprev.api.manager.client.token.ApiManagerTokenFactory;
import br.com.odontoprev.api.manager.client.token.ApiToken;
import br.com.odontoprev.api.manager.client.token.enumerator.ApiManagerTokenEnum;
import br.com.odontoprev.api.manager.client.token.exception.ConnectionApiException;
import br.com.odontoprev.api.manager.client.token.exception.CredentialsInvalidException;
import br.com.odontoprev.api.manager.client.token.exception.URLEndpointNotFound;
import br.com.odontoprev.portal.corretor.dao.EmpresaDAO;
import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dao.PlanoDAO;
import br.com.odontoprev.portal.corretor.dao.StatusVendaDAO;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioPropostaDCMS;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioResponse;
import br.com.odontoprev.portal.corretor.dto.CorretoraPropostaDCMS;
import br.com.odontoprev.portal.corretor.dto.DadosBancariosPropostaDCMS;
import br.com.odontoprev.portal.corretor.dto.DadosBancariosVenda;
import br.com.odontoprev.portal.corretor.dto.EnderecoProposta;
import br.com.odontoprev.portal.corretor.dto.PlanoDCMS;
import br.com.odontoprev.portal.corretor.dto.PropostaDCMS;
import br.com.odontoprev.portal.corretor.dto.PropostaDCMSResponse;
import br.com.odontoprev.portal.corretor.dto.TipoCobrancaPropostaDCMS;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;
import br.com.odontoprev.portal.corretor.model.TbodEmpresa;
import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
import br.com.odontoprev.portal.corretor.model.TbodPlano;
import br.com.odontoprev.portal.corretor.model.TbodStatusVenda;
import br.com.odontoprev.portal.corretor.model.TbodVenda;

@ManagedBean
public class VendaPFBusiness {

	private static final Log log = LogFactory.getLog(VendaPFBusiness.class);

	private RestTemplate restTemplate = null;
	
	@PostConstruct
	private void init() {		
		if(restTemplate  == null)
			restTemplate = new RestTemplate();
	}
	
	@Autowired
	VendaDAO vendaDao;
	
	@Autowired
	EmpresaDAO empresaDao;
	
	@Autowired
	PlanoDAO planoDao;
	
	@Autowired
	ForcaVendaDAO forcaVendaDao;

	@Autowired
	StatusVendaDAO statusVendaDao;
	
	@Autowired
	BeneficiarioBusiness beneficiarioBusiness;

	@Value("${DCSS_URL}")
	private String dcssUrl;

	@Value("${DCSS_VENDAS_PROPOSTA_PATH}")
	private String dcss_venda_propostaPath;
	
	@Value("${DCSS_CODIGO_CANAL_VENDAS}")
	private String dcss_codigo_canal_vendas; //201803021328 esertorio para moliveira
	
	@Value("${DCSS_CODIGO_EMPRESA_DCMS}")
	private String dcss_codigo_empresa_dcms; //201803021538 esertorio para moliveira
	
	public VendaResponse salvarVendaComTitularesComDependentes(Venda venda) {

		log.info("[salvarVendaPFComTitularesComDependentes]");

		TbodVenda tbVenda = new TbodVenda();
		PropostaDCMSResponse propostaDCMSResponse = new PropostaDCMSResponse();
		
		try {		
			
			if(venda != null) {
				if(venda.getCdVenda() != null) {
					tbVenda = vendaDao.findOne(venda.getCdVenda());					
				}
			}

			if(tbVenda == null) {
				throw new Exception("Venda CdVenda:[" + venda.getCdVenda() + "] não existe!");
			}
			
			tbVenda.setCdVenda(venda.getCdVenda());
						
			if(venda.getCdEmpresa() != null) {
				TbodEmpresa tbodEmpresa = empresaDao.findOne(venda.getCdEmpresa());
				tbVenda.setTbodEmpresa(tbodEmpresa);
			}

//			if(venda.getPlanos() != null && !venda.getPlanos().isEmpty()) {
//				TbodPlano tbodPlano = planoDao.findByCdPlano(venda.getPlanos().get(0).getCdPlano());
//				tbVenda.setTbodPlano(tbodPlano);
//			}
			TbodPlano tbodPlano = null;
			if(venda.getCdPlano() != null) {
				tbodPlano = planoDao.findByCdPlano(venda.getCdPlano());
				tbVenda.setTbodPlano(tbodPlano);
			}

			if(venda.getCdForcaVenda() != null) {
				TbodForcaVenda tbodForcaVenda = forcaVendaDao.findOne(venda.getCdForcaVenda()); 
				tbVenda.setTbodForcaVenda(tbodForcaVenda);
			}
			
			if(venda.getDataVenda() != null) {
				tbVenda.setDtVenda(venda.getDataVenda());
			} else {
				tbVenda.setDtVenda(new Date());
			}
			
			//TbodVendaVida tbodVendaVida = null;
			//tbVenda.getTbodVendaVida().setCdVendaVida((Long) null);
			
			if(venda.getCdStatusVenda() != null) {
				TbodStatusVenda tbodStatusVenda = statusVendaDao.findOne(venda.getCdStatusVenda());
				tbVenda.setTbodStatusVenda(tbodStatusVenda);
			}
			
			if(venda.getFaturaVencimento() != null) {
				tbVenda.setFaturaVencimento(venda.getFaturaVencimento());
			} else {
				tbVenda.setFaturaVencimento((long)0);				
			}
			
			if(venda.getTitulares() != null 
				&& !venda.getTitulares().isEmpty()
				&& venda.getTitulares().get(0) != null
				&& venda.getTitulares().get(0).getDadosBancarios() != null
			) {
					
				DadosBancariosVenda dadosBancariosVenda = venda.getTitulares().get(0).getDadosBancarios();
				
				//Dados Bancarios
				if(dadosBancariosVenda.getTipoConta() != null) {
					tbVenda.setTipoConta(dadosBancariosVenda.getTipoConta());
				}
				
				if(dadosBancariosVenda.getCodigoBanco() != null) {
					tbVenda.setBanco(dadosBancariosVenda.getCodigoBanco());
				}
				
				if(dadosBancariosVenda.getAgencia() != null) {
					dadosBancariosVenda.setAgencia(dadosBancariosVenda.getAgencia().replace("-", ""));
					if(!dadosBancariosVenda.getAgencia().isEmpty()) {
						String ag = dadosBancariosVenda.getAgencia().substring(0,dadosBancariosVenda.getAgencia().length()-1);
						String agDV = dadosBancariosVenda.getAgencia().substring(dadosBancariosVenda.getAgencia().length()-1);
						tbVenda.setAgencia(ag);
						tbVenda.setAgenciaDv(agDV);
					}
				} //if(dadosBancariosVenda.getAgencia() != null)
				
				if(dadosBancariosVenda.getConta() != null) {
					dadosBancariosVenda.setConta(dadosBancariosVenda.getConta().replace("-", ""));
					if(!dadosBancariosVenda.getConta().isEmpty()) {
						String cc = dadosBancariosVenda.getConta().substring(0,dadosBancariosVenda.getConta().length()-1);
						String ccDv = dadosBancariosVenda.getConta().substring(dadosBancariosVenda.getConta().length()-1);
						tbVenda.setConta(cc);
						tbVenda.setContaDv(ccDv);
					}
				} //if(dadosBancariosVenda.getConta() != null)
			} //if(beneficiarioTitular.getDadosBancarios() != null)
			
			if(venda.getTipoPagamento() != null) {
				tbVenda.setTipoPagamento(venda.getTipoPagamento());
			}
			
			tbVenda = vendaDao.save(tbVenda);
			
			if(venda.getTitulares() != null) {
				for (Beneficiario titular : venda.getTitulares()) {
					titular.setCdVenda(tbVenda.getCdVenda());
				}
			}

			BeneficiarioResponse beneficiarioResponse = beneficiarioBusiness.salvarTitularComDependentes(venda.getTitulares());
			
			if(beneficiarioResponse.getId() == 0) {
				throw new Exception(beneficiarioResponse.getMensagem());
			}
			
			//QG TESTE APP SYNC 201802282300
			String flagNumero = "";
			if(venda.getTitulares() != null
				&& !venda.getTitulares().isEmpty()
				&& venda.getTitulares().get(0) != null
				&& venda.getTitulares().get(0).getEndereco() != null
				&& venda.getTitulares().get(0).getEndereco().getNumero() != null
				&& !venda.getTitulares().get(0).getEndereco().getNumero().isEmpty()
			) {
				flagNumero = venda.getTitulares().get(0).getEndereco().getNumero();
			}
			
			//QG TESTE APP SYNC 201802282300
			if(!flagNumero.equals("0") && !flagNumero.equals("1")) {
				
				propostaDCMSResponse = chamarWsDcssLegado(venda, tbodPlano);
				
			} else {
				Integer cod = Integer.parseInt(flagNumero);
				String msg = "Venda cadastrada CdVenda:[" + tbVenda.getCdVenda() + "]; NumeroProposta:[" + cod + "].";
				return new VendaResponse(cod, msg);
			}
			
			atualizarNumeroPropostaVenda(venda, tbVenda, propostaDCMSResponse);

		} catch (Exception e) {
			log.error("salvarVendaPFComTitularesComDependentes :: Erro ao cadastrar venda CdVenda:[" + venda.getCdVenda() + "], Detalhe: [" + e.getMessage() + "], Causa: [" + e.getCause() != null ? e.getCause().getMessage() : "null" + "]");
			
			String msg = "Erro ao cadastrar venda ";
			if(venda.getCdVenda() != null) {
				msg += ", CdVenda:["+ venda.getCdVenda() +"]";
			} else {
				msg += ", CdVenda:[null]";
			}
			if(venda.getCdEmpresa() != null) {
				msg += ", CdEmpresa:["+ venda.getCdEmpresa() +"]";
			} else {
				msg += ", CdEmpresa:[null]";
			}
			if(venda.getCdForcaVenda() != null) {
				msg += ", CdForcaVenda:["+ venda.getCdForcaVenda() +"]";
			} else {
				msg += ", CdForcaVenda:[null]";
			}
			
//			if(venda.getPlanos() != null && !venda.getPlanos().isEmpty()) {
//				msg += ", Planos[0].CdPlano:["+ venda.getPlanos().get(0) +"].";
//			} else {
//				msg += ", CdPlano:[null].";
//			}
			
			if(venda.getCdPlano() != null) {
				msg += ", CdPlano:["+ venda.getCdPlano() +"].";
			} else {
				msg += ", CdPlano:[null].";
			}

			return new VendaResponse(0, msg);
		}

		return new VendaResponse(tbVenda.getCdVenda(), "Venda cadastrada." 
		+ " CdVenda:[" + tbVenda.getCdVenda() + "];" 
		+ " NumeroProposta:[" + propostaDCMSResponse.getNumeroProposta() + "];" 
		+ " DtVenda:[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(tbVenda.getDtVenda()) + "];"
		+ " MensagemErro:[" + propostaDCMSResponse.getMensagemErro() + "];"
		);
	}

	public void atualizarNumeroPropostaVenda(Venda venda, TbodVenda tbVenda, PropostaDCMSResponse propostaDCMSResponse) throws Exception 
	{
		TbodStatusVenda tbodStatusVenda;
		TbodVenda tbodVendaUpdate = vendaDao.findOne(tbVenda.getCdVenda());
		try {
			if(tbodVendaUpdate != null) {
				
				if(propostaDCMSResponse!=null 
				    && propostaDCMSResponse.getNumeroProposta() != null 
					&& !propostaDCMSResponse.getNumeroProposta().isEmpty()
				) {
					tbodVendaUpdate.setPropostaDcms(propostaDCMSResponse.getNumeroProposta());
					tbodStatusVenda = statusVendaDao.findOne(new Long(1)); //1=Aprovado
					if(tbodStatusVenda == null) {
						throw new Exception("atualizarNumeroPropostaVenda; Não achou tbodStatusVenda.getCdStatusVenda(1)");
					}
				} else {
					tbodVendaUpdate.setPropostaDcms(null);
					tbodStatusVenda = statusVendaDao.findOne(new Long(2)); //2=Criticado
					if(tbodStatusVenda == null) {
						throw new Exception("atualizarNumeroPropostaVenda; Não achou tbodStatusVenda.getCdStatusVenda(1)");
					}
				}
				
				tbodVendaUpdate.setTbodStatusVenda(tbodStatusVenda);
				
				tbodVendaUpdate = vendaDao.save(tbodVendaUpdate);
				
			} else {
				throw new Exception("atualizarNumeroPropostaVenda; Não achou tbVenda.getCdVenda():["+ tbVenda.getCdVenda() +"]");
			}
		} catch (Exception e) {
			throw new Exception("atualizarNumeroPropostaVenda; e.getMessage():["+ e.getMessage() +"]; e.getCause().getMessage():["+ e.getCause() != null ? e.getCause().getMessage() : "null" +"]");
		}
	}

	public PropostaDCMSResponse chamarWsDcssLegado(Venda venda, TbodPlano tbodPlano) throws Exception {
								
		PropostaDCMS propostaDCMS = atribuirVendaPFParaPropostaDCMS(venda, tbodPlano);
					
		Gson gson = new Gson();
		String propostaJson = gson.toJson(propostaDCMS);			
		log.info("chamarWSLegadoPropostaPOST; propostaJson:[" + propostaJson + "];");

		PropostaDCMSResponse propostaDCMSResponse = chamarWSLegadoPropostaPOST(propostaDCMS);
		
		if(propostaDCMSResponse != null) {
			log.info("chamarWSLegadoPropostaPOST; propostaResponse.getNumeroProposta:[" + propostaDCMSResponse.getNumeroProposta() + "]; getMensagemErro:[" + propostaDCMSResponse.getMensagemErro() + "]");
		}
			
		return propostaDCMSResponse;
	}

	private PropostaDCMS atribuirVendaPFParaPropostaDCMS(Venda venda, TbodPlano tbodPlano) throws Exception {
		
		PropostaDCMS propostaDCMS = new PropostaDCMS();

		TbodForcaVenda tbodForcaVenda = forcaVendaDao.findOne(venda.getCdForcaVenda()); 
				
		propostaDCMS.setCorretora(new CorretoraPropostaDCMS());
		
		//propostaDCMS.getCorretora().setCodigo(tbodForcaVenda.getTbodCorretora().getCdCorretora());
		Long corretoraCodigo = -1L;
		try {
			corretoraCodigo = Long.parseLong(tbodForcaVenda.getTbodCorretora().getCodigo());
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			throw new Exception("atribuirVendaPFParaPropostaDCMS(); Impossível converter tbodForcaVenda.getTbodCorretora().getCodigo():[" + tbodForcaVenda.getTbodCorretora().getCodigo() + "] para Long.");
		}
		propostaDCMS.getCorretora().setCodigo(corretoraCodigo);

		propostaDCMS.getCorretora().setCnpj(tbodForcaVenda.getTbodCorretora().getCnpj());
		propostaDCMS.getCorretora().setNome(tbodForcaVenda.getTbodCorretora().getNome());

		//propostaDCMS.setCodigoEmpresaDCMS("997692"); //codigoEmpresaDCMS: 997692
		//propostaDCMS.setCodigoEmpresaDCMS(venda.getCdEmpresaDCMS()); //201803021320 esertorio para moliveira
		propostaDCMS.setCodigoEmpresaDCMS(dcss_codigo_empresa_dcms); //201803021538 esertorio para moliveira

		//propostaDCMS.setCodigoCanalVendas((long)57); //codigoCanalVendas: 57
		//propostaDCMS.setCodigoCanalVendas("46"); //201803010449 RODRIGAO 
		//propostaDCMS.setCodigoCanalVendas("57"); //201803011730 ROBERTO
		propostaDCMS.setCodigoCanalVendas(dcss_codigo_canal_vendas); //201803021328 esertorio para moliveira
		
		propostaDCMS.setCodigoUsuario(venda.getCdForcaVenda());
		
		propostaDCMS.setTipoCobranca(new TipoCobrancaPropostaDCMS());
				
		if(venda.getTitulares() != null 
			&& venda.getTitulares().size() > 0
			&& venda.getTitulares().get(0) != null
			&& venda.getTitulares().get(0).getDadosBancarios() != null
			&& venda.getTitulares().get(0).getDadosBancarios().getCodigoBanco() != null
			&& !venda.getTitulares().get(0).getDadosBancarios().getCodigoBanco().isEmpty()
		) {
						
			Beneficiario beneficiarioTitular = venda.getTitulares().get(0);
			DadosBancariosVenda dadosBancariosVenda = beneficiarioTitular.getDadosBancarios(); 
			
			DadosBancariosPropostaDCMS dadosBancariosPropostaDCMS = new DadosBancariosPropostaDCMS(); 
			
			if(dadosBancariosVenda.getCodigoBanco() != null) {
				dadosBancariosPropostaDCMS.setCodigoBanco(dadosBancariosVenda.getCodigoBanco());
			}
			
			//54	DA	S	Débito Santander - Febraban 150 - Disque - 033
			if(dadosBancariosPropostaDCMS.getCodigoBanco().equals("033")) { //
				propostaDCMS.getTipoCobranca().setCodigo((long)54); //TODO //Informar o que vem do serviço da empresa
				propostaDCMS.getTipoCobranca().setSigla("DA"); //BO = Boleto / DA = Debito automatico
			}
			
			//61	DA	S	Débito Bradesco - CNAB400 - Disque - 237
			if(dadosBancariosPropostaDCMS.getCodigoBanco().equals("237")) { //
				propostaDCMS.getTipoCobranca().setCodigo((long)61); //TODO //Informar o que vem do serviço da empresa
				propostaDCMS.getTipoCobranca().setSigla("DA"); //BO = Boleto / DA = Debito automatico
			}
			
			//62	DA	S	Débito Itau - Febraban 150 - Disque - 341
			if(dadosBancariosPropostaDCMS.getCodigoBanco().equals("341")) { //
				propostaDCMS.getTipoCobranca().setCodigo((long)62); //TODO //Informar o que vem do serviço da empresa
				propostaDCMS.getTipoCobranca().setSigla("DA"); //BO = Boleto / DA = Debito automatico
			}

			if(dadosBancariosVenda.getAgencia() != null) {
				dadosBancariosVenda.setAgencia(dadosBancariosVenda.getAgencia().replace("-", ""));
				if(!dadosBancariosVenda.getAgencia().isEmpty()) {
					String ag = dadosBancariosVenda.getAgencia().substring(0,dadosBancariosVenda.getAgencia().length()-1);
					String agDV = dadosBancariosVenda.getAgencia().substring(dadosBancariosVenda.getAgencia().length()-1);
					dadosBancariosPropostaDCMS.setAgencia(ag); 
					dadosBancariosPropostaDCMS.setAgenciaDV(agDV); 
				}
			}
			
			if(dadosBancariosVenda.getTipoConta() != null) {
				dadosBancariosPropostaDCMS.setTipoConta(dadosBancariosVenda.getTipoConta());
			}
			
			if(dadosBancariosVenda.getConta() != null) {
				dadosBancariosVenda.setConta(dadosBancariosVenda.getConta().replace("-", ""));
				if(!dadosBancariosVenda.getConta().isEmpty()) {
					String cc = dadosBancariosVenda.getConta().substring(0,dadosBancariosVenda.getConta().length()-1);
					String ccDv = dadosBancariosVenda.getConta().substring(dadosBancariosVenda.getConta().length()-1);
					dadosBancariosPropostaDCMS.setConta(cc);
					dadosBancariosPropostaDCMS.setContaDV(ccDv);
				}
			}
			
			propostaDCMS.setDadosBancarios(dadosBancariosPropostaDCMS);
			
		} else {
			//60	BO	N	Boleto Bradesco C/ Registro - MultiEmpresa - 237
			propostaDCMS.getTipoCobranca().setCodigo((long)60); //TODO //Informar o que vem do serviço da empresa
			propostaDCMS.getTipoCobranca().setSigla("BO"); //BO = Boleto / DA = Debito automatico
		}

		propostaDCMS.setBeneficiarios(new ArrayList<>());
		
		for (Beneficiario titular : venda.getTitulares()) {
			
			BeneficiarioPropostaDCMS beneficiarioPropostaTitular = new BeneficiarioPropostaDCMS();
			beneficiarioPropostaTitular.setNome(titular.getNome());
			beneficiarioPropostaTitular.setCpf(titular.getCpf().replace(".", "").replace("-", ""));
			beneficiarioPropostaTitular.setSexo(titular.getSexo());
			
			beneficiarioPropostaTitular.setDataNascimento(titular.getDataNascimento());
			SimpleDateFormat sdfApp = new SimpleDateFormat("dd/MM/yyyy");
			Date dateDataNascimento = null;
			try {
				dateDataNascimento = sdfApp.parse(titular.getDataNascimento());
			} catch (ParseException e) {
				log.info("atribuirVendaPFParaPropostaDCMS; titular.getDataNascimento(String):[" + titular.getDataNascimento() + "]; e.getMessage():[" + e.getMessage() + "];");
			}
			SimpleDateFormat sdfJsonString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String stringDataNascimentoJSON = sdfJsonString.format(dateDataNascimento).replace(" ", "T").concat(".000Z");
			beneficiarioPropostaTitular.setDataNascimento(stringDataNascimentoJSON);
			
			beneficiarioPropostaTitular.setNomeMae(titular.getNomeMae());
			beneficiarioPropostaTitular.setCelular(titular.getCelular().replace(".", "").replace("-", "").replace(" ", "").replace("(", "").replace(")", ""));
			beneficiarioPropostaTitular.setEmail(titular.getEmail());
			
//			if(venda.getPlanos() != null && !venda.getPlanos().isEmpty()) {
//				beneficiarioPropostaTitular.setCodigoPlano(String.valueOf(venda.getPlanos().get(0).getCdPlano()));
//			}
			if(tbodPlano != null) {
				//beneficiarioPropostaTitular.setCodigoPlano(String.valueOf(venda.getCdPlano()));
				beneficiarioPropostaTitular.setPlano(new PlanoDCMS());
				beneficiarioPropostaTitular.getPlano().setCodigoPlano(Long.valueOf(tbodPlano.getCodigo()));
				beneficiarioPropostaTitular.getPlano().setTipoNegociacao(tbodPlano.getTbodTipoPlano().getDescricao());
				beneficiarioPropostaTitular.getPlano().setValorPlano(Float.parseFloat(tbodPlano.getValorMensal().toString()));
			}
			
			beneficiarioPropostaTitular.setTitular(true); //TRUE PARA DEPENDENTE
			
			beneficiarioPropostaTitular.setEndereco(new EnderecoProposta());
			beneficiarioPropostaTitular.getEndereco().setCep(titular.getEndereco().getCep().replaceAll("-", ""));
			beneficiarioPropostaTitular.getEndereco().setLogradouro(titular.getEndereco().getLogradouro());
			beneficiarioPropostaTitular.getEndereco().setNumero(titular.getEndereco().getNumero());
			beneficiarioPropostaTitular.getEndereco().setComplemento(titular.getEndereco().getComplemento());
			beneficiarioPropostaTitular.getEndereco().setBairro(titular.getEndereco().getBairro());
			beneficiarioPropostaTitular.getEndereco().setCidade(titular.getEndereco().getCidade());
			beneficiarioPropostaTitular.getEndereco().setEstado(titular.getEndereco().getEstado());
			
			propostaDCMS.getBeneficiarios().add(beneficiarioPropostaTitular);
			
			for (Beneficiario dependente : titular.getDependentes()) {

				BeneficiarioPropostaDCMS beneficiarioPropostaDependente = new BeneficiarioPropostaDCMS();
				beneficiarioPropostaDependente.setNome(dependente.getNome());
				beneficiarioPropostaDependente.setCpf(dependente.getCpf().replace(".", "").replace("-", ""));
				beneficiarioPropostaDependente.setSexo(dependente.getSexo());
				
				beneficiarioPropostaDependente.setDataNascimento(dependente.getDataNascimento());
				SimpleDateFormat sdfAppDep = new SimpleDateFormat("dd/MM/yyyy");
				Date dateDataNascimentoDep = null;
				try {
					dateDataNascimentoDep = sdfAppDep.parse(dependente.getDataNascimento());
				} catch (ParseException e) {
					log.info("atribuirVendaPFParaPropostaDCMS; dependente.getDataNascimento(String):[" + titular.getDataNascimento() + "]; e.getMessage():[" + e.getMessage() + "];");
				}
				SimpleDateFormat sdfJsonStringDep = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String stringDataNascimentoJSONDep = sdfJsonStringDep.format(dateDataNascimentoDep).replace(" ", "T").concat(".000Z");
				beneficiarioPropostaTitular.setDataNascimento(stringDataNascimentoJSONDep);
				
				beneficiarioPropostaDependente.setNomeMae(dependente.getNomeMae());
				beneficiarioPropostaDependente.setCelular(dependente.getCelular().replace(".", "").replace("-", "").replace(" ", "").replace("(", "").replace(")", ""));
				beneficiarioPropostaDependente.setEmail(dependente.getEmail());
				
//				if(venda.getPlanos() != null && !venda.getPlanos().isEmpty()) {
//					beneficiarioPropostaDependente.setCodigoPlano(String.valueOf(venda.getPlanos().get(0).getCdPlano()));
//				}
//				if(venda.getCdPlano() != null) {
//					beneficiarioPropostaDependente.setCodigoPlano(String.valueOf(venda.getCdPlano()));
//				}
				if(tbodPlano != null) {
					//beneficiarioPropostaTitular.setCodigoPlano(String.valueOf(venda.getCdPlano()));
					beneficiarioPropostaDependente.setPlano(new PlanoDCMS());
					beneficiarioPropostaDependente.getPlano().setCodigoPlano(tbodPlano.getCdPlano());
					beneficiarioPropostaDependente.getPlano().setTipoNegociacao(tbodPlano.getTbodTipoPlano().getDescricao());
					beneficiarioPropostaDependente.getPlano().setValorPlano(Float.parseFloat(tbodPlano.getValorMensal().toString()));
				}
				
				beneficiarioPropostaDependente.setTitular(false); //FALSE PARA DEPENDENTE
				
				beneficiarioPropostaDependente.setEndereco(new EnderecoProposta());
				beneficiarioPropostaDependente.getEndereco().setCep(titular.getEndereco().getCep().replaceAll("-", ""));
				beneficiarioPropostaDependente.getEndereco().setLogradouro(titular.getEndereco().getLogradouro());
				beneficiarioPropostaDependente.getEndereco().setNumero(titular.getEndereco().getNumero());
				beneficiarioPropostaDependente.getEndereco().setComplemento(titular.getEndereco().getComplemento());
				beneficiarioPropostaDependente.getEndereco().setBairro(titular.getEndereco().getBairro());
				beneficiarioPropostaDependente.getEndereco().setCidade(titular.getEndereco().getCidade());
				beneficiarioPropostaDependente.getEndereco().setEstado(titular.getEndereco().getEstado());
				
				propostaDCMS.getBeneficiarios().add(beneficiarioPropostaTitular);

			} //for (Beneficiario dependente : titular.getDependentes())
			
		} //for (Beneficiario titular : venda.getTitulares())

		return propostaDCMS;
	}

	@SuppressWarnings({ })
	private PropostaDCMSResponse chamarWSLegadoPropostaPOST(PropostaDCMS proposta){
		log.info("chamarWSLegadoPropostaPOST; ini;");
		PropostaDCMSResponse propostaDCMSResponse = new PropostaDCMSResponse(); 
		ApiManagerToken apiManager = null;
		ApiToken apiToken = null;
		ResponseEntity<PropostaDCMSResponse> response;
		RestTemplate restTemplate = new RestTemplate();
		String msgErro = "";

		try {
			apiManager = ApiManagerTokenFactory.create(ApiManagerTokenEnum.WSO2, "PORTAL_CORRETOR_SERVICO");
			apiToken = apiManager.generateToken();

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + apiToken.getAccessToken());
			headers.add("Cache-Control", "no-cache");
			//headers.add("Content-Type", "application/x-www-form-urlencoded");
			headers.add("Content-Type", "application/json");

			HttpEntity<?> request = new HttpEntity<PropostaDCMS>(proposta, headers);

			String URLAPI = dcssUrl + dcss_venda_propostaPath;

			response = restTemplate.exchange(
					URLAPI, 
					HttpMethod.POST, 
					request, 
					PropostaDCMSResponse.class);
			
//			response = restTemplate.postForEntity(
//				URLAPI, 
//				proposta, 
//				PropostaDCMSResponse.class
//			);
			
			if(response != null) {
				log.info("chamarWSLegadoPropostaPOST; propostaRet.getStatusCode():[" + response.getStatusCode() + "];");
			}
	
			if(response == null 
				|| (response.getStatusCode() == HttpStatus.FORBIDDEN)				
				|| (response.getStatusCode() == HttpStatus.BAD_REQUEST)
			) {
				msgErro = "chamarWSLegadoPropostaPOST; HTTP_STATUS "+response.getStatusCode();
				log.error(msgErro);
				propostaDCMSResponse.setMensagemErro(msgErro);
				return propostaDCMSResponse;
			}

		} catch (CredentialsInvalidException e1) {
			msgErro = "chamarWSLegadoPropostaPOST; CredentialsInvalidException.getMessage():[" + e1.getMessage() + "];";
			log.error(msgErro);
			propostaDCMSResponse.setMensagemErro(msgErro);
			return propostaDCMSResponse;
		} catch (URLEndpointNotFound e1) {
			msgErro = "chamarWSLegadoPropostaPOST; URLEndpointNotFound.getMessage():[" + e1.getMessage() + "];";
			log.error(msgErro);
			propostaDCMSResponse.setMensagemErro(msgErro);
			return propostaDCMSResponse;
		} catch (ConnectionApiException e1) {
			msgErro = "chamarWSLegadoPropostaPOST; ConnectionApiException.getMessage():[" + e1.getMessage() + "];";
			log.error(msgErro);
			propostaDCMSResponse.setMensagemErro(msgErro);
			return propostaDCMSResponse;
			
		} catch (RestClientException e) {
			msgErro = "chamarWSLegadoPropostaPOST; RestClientException.getMessage():[" + e.getMessage() + "];";
			log.error(msgErro);
			propostaDCMSResponse.setMensagemErro(msgErro);
			return propostaDCMSResponse;
			//e.printStackTrace();
		} catch (Exception e) {
			msgErro = "chamarWSLegadoPropostaPOST; Exception.getMessage():[" + e.getMessage() + "];";
			log.error(msgErro);
			//e.printStackTrace();
			propostaDCMSResponse.setMensagemErro(msgErro);
			return propostaDCMSResponse;
		}
					
		log.info("chamarWSLegadoPropostaPOST; fim;");
		return response.getBody();
			
	}

}

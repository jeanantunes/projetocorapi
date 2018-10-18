package br.com.odontoprev.portal.corretor.business;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.ManagedBean;
import javax.persistence.RollbackException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

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
import br.com.odontoprev.portal.corretor.dto.EmailForcaVendaCorretora;
import br.com.odontoprev.portal.corretor.dto.EnderecoProposta;
import br.com.odontoprev.portal.corretor.dto.PlanoDCMS;
import br.com.odontoprev.portal.corretor.dto.PropostaDCMS;
import br.com.odontoprev.portal.corretor.dto.PropostaDCMSResponse;
import br.com.odontoprev.portal.corretor.dto.ResponsavelContratual;
import br.com.odontoprev.portal.corretor.dto.TipoCobrancaPropostaDCMS;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;
import br.com.odontoprev.portal.corretor.model.TbodEmpresa;
import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
import br.com.odontoprev.portal.corretor.model.TbodPlano;
import br.com.odontoprev.portal.corretor.model.TbodResponsavelContratual;
import br.com.odontoprev.portal.corretor.model.TbodStatusVenda;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.service.DCSSService;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;
import br.com.odontoprev.portal.corretor.util.Constantes;


@ManagedBean
@Transactional(rollbackFor = {Exception.class}) //201806281838 - esert - COR-348
public class VendaPFBusiness {

    //private static final Log log = LogFactory.getLog(VendaPFBusiness.class);
    private static final Logger log = LoggerFactory.getLogger(VendaPFBusiness.class);

//    private RestTemplate restTemplate = null; //201810171900 - esert - exc - COR-763:Isolar Inserção JSON Request DCMS

//201810171900 - esert - exc - COR-763:Isolar Inserção JSON Request DCMS
//    @PostConstruct
//    private void init() {
//        if (restTemplate == null)
//            restTemplate = new RestTemplate();
//    }

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

//    @Autowired
//    ApiManagerTokenServiceImpl apiManagerTokenService; //201810171900 - esert - exc - COR-763:Isolar Inserção JSON Request DCMS

    @Autowired
    ResponsavelContratualBusiness responsavelContratualBusiness;

    @Autowired
    ForcaVendaService forcaVendaService;

//201810171900 - esert - exc - COR-763:Isolar Inserção JSON Request DCMS
//    @Autowired
//    OdpvAuditorService odpvAuditor; //201806071601 - esert - log do json enviado ao dcms - solic fsetai

//201810171900 - esert - exc - COR-763:Isolar Inserção JSON Request DCMS
//    @Value("${DCSS_VENDAS_PROPOSTA_URL}")
//    //201810031800 - esert - COR-852:Alterar Request Angariador Dados nao Obrigatorios - segregar rota de login da rota de proposta para desv e teste
//    private String dcss_venda_propostaUrl; //201810031800 - esert - COR-852:Alterar Request Angariador Dados nao Obrigatorios - segregar rota de login da rota de proposta para desv e teste
//
//    @Value("${DCSS_VENDAS_PROPOSTA_PATH}")
//    private String dcss_venda_propostaPath;

    @Value("${DCSS_CODIGO_CANAL_VENDAS}")
    private String dcss_codigo_canal_vendas; //201803021328 esertorio para moliveira

    @Value("${DCSS_CODIGO_EMPRESA_DCMS}")
    private String dcss_codigo_empresa_dcms; //201803021538 esertorio para moliveira

    @Autowired
	private DCSSService dcssService; //201810171900 - esert - inc - COR-763:Isolar Inserção JSON Request DCMS

    @Transactional(rollbackFor = {Exception.class})
    //201806120946 - gmazzi@zarp - rollback vendapme //201806261820 - esert - merge from sprint6_rollback
    public VendaResponse salvarVendaComTitularesComDependentes(Venda venda, Boolean isIntegraDCSS) throws Exception {

        log.info("salvarVendaComTitularesComDependentes - ini");

        TbodVenda tbVenda = new TbodVenda();
        PropostaDCMSResponse propostaDCMSResponse = new PropostaDCMSResponse();

        //try
        {

            if (venda != null) {
                if (venda.getCdVenda() != null) {
                    tbVenda = vendaDao.findOne(venda.getCdVenda());
                }
            }

            if (tbVenda == null) {
                throw new Exception("Venda CdVenda:[" + venda.getCdVenda() + "] não existe!");
            }

            tbVenda.setCdVenda(venda.getCdVenda());

            if (venda.getCdEmpresa() != null) {
                TbodEmpresa tbodEmpresa = empresaDao.findOne(venda.getCdEmpresa());
                tbVenda.setTbodEmpresa(tbodEmpresa);
            }

//			if(venda.getPlanos() != null && !venda.getPlanos().isEmpty()) {
//				TbodPlano tbodPlano = planoDao.findByCdPlano(venda.getPlanos().get(0).getCdPlano());
//				tbVenda.setTbodPlano(tbodPlano);
//			}
            TbodPlano tbodPlano = null;
            if (venda.getCdPlano() != null) {
                tbodPlano = planoDao.findByCdPlano(venda.getCdPlano());
                tbVenda.setValorPlano(tbodPlano.getValorMensal()); //20180802 - yalm - [COR-532]
                tbVenda.setTbodPlano(tbodPlano);
            }

            if (venda.getCdForcaVenda() != null) {
                TbodForcaVenda tbodForcaVenda = forcaVendaDao.findOne(venda.getCdForcaVenda());
                if (tbodForcaVenda == null) {
                    throw new Exception("tbodForcaVenda == null para venda.getCdForcaVenda():[" + venda.getCdForcaVenda() + "]"); //201806261245 - esert - protecao
                }
                tbVenda.setTbodForcaVenda(tbodForcaVenda);
                venda.setCdDCSSUsuario(tbodForcaVenda.getCodigoDcssUsuario());

                tbVenda.setTbodCorretora(tbodForcaVenda.getTbodCorretora()); //201807311613 - esert - COR-468:Atrelar Venda com a Corretora
            }

            if (venda.getDataVenda() != null) {
                tbVenda.setDtVenda(venda.getDataVenda());
            } else {
                tbVenda.setDtVenda(new Date());
            }

            //TbodVendaVida tbodVendaVida = null;
            //tbVenda.getTbodVendaVida().setCdVendaVida((Long) null);

            if (venda.getCdStatusVenda() != null) {
                TbodStatusVenda tbodStatusVenda = statusVendaDao.findOne(venda.getCdStatusVenda());
                tbVenda.setTbodStatusVenda(tbodStatusVenda);
            }

            if (venda.getFaturaVencimento() != null) {
                tbVenda.setFaturaVencimento(venda.getFaturaVencimento());
            } else {
                tbVenda.setFaturaVencimento((long) 0);
            }

            if (venda.getDataVigencia() != null) { //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
                tbVenda.setDtVigencia(venda.getDataVigencia()); //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
            }

            if (venda.getDataMovimentacao() != null) { //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
                tbVenda.setDtMovimentacao(venda.getDataMovimentacao()); //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
            }

            tbVenda.setPlataforma(venda.getPlataforma()); //201807201122 - esert - COR-431

            long qtVidas = 0;

            //20180802 - yalm - [COR-532]
            if (
                    venda.getTitulares() != null
                            &&
                            !venda.getTitulares().isEmpty()
            ) {

                for (Beneficiario titular : venda.getTitulares()) {

                    qtVidas++;

                    if (titular.getDependentes() != null) {

                        qtVidas += titular.getDependentes().size();

                    }
                }

            }

            //20180802 - yalm - [COR-532]
            tbVenda.setQtVidas(qtVidas);

            //20180802 - yalm - [COR-532]
            if (tbVenda.getValorPlano() != null) {

                tbVenda.setValorTotal(BigDecimal.valueOf((double) qtVidas * tbVenda.getValorPlano().doubleValue()));

            }

            if (venda.getTitulares() != null
                    && !venda.getTitulares().isEmpty()
                    && venda.getTitulares().get(0) != null
                    && venda.getTitulares().get(0).getDadosBancarios() != null
            ) {

                DadosBancariosVenda dadosBancariosVenda = venda.getTitulares().get(0).getDadosBancarios();

                //Dados Bancarios
                if (dadosBancariosVenda.getTipoConta() != null) {
                    tbVenda.setTipoConta(dadosBancariosVenda.getTipoConta());
                }

                if (dadosBancariosVenda.getCodigoBanco() != null) {
                    tbVenda.setBanco(dadosBancariosVenda.getCodigoBanco());
                }

                if (dadosBancariosVenda.getAgencia() != null) {
                    //dadosBancariosVenda.setAgencia(dadosBancariosVenda.getAgencia().replace("-", ""));

                    if (!dadosBancariosVenda.getAgencia().isEmpty()) {

                        String agDV = calcularDigitoAgencia(dadosBancariosVenda.getAgencia(), dadosBancariosVenda.getCodigoBanco());
                        String ag = dadosBancariosVenda.getAgencia();

                        tbVenda.setAgencia(ag);
                        tbVenda.setAgenciaDv(agDV);
                    }
                } //if(dadosBancariosVenda.getAgencia() != null)

                if (dadosBancariosVenda.getConta() != null) {
                    dadosBancariosVenda.setConta(dadosBancariosVenda.getConta().replace("-", ""));
                    if (!dadosBancariosVenda.getConta().isEmpty()) {
                        String cc = dadosBancariosVenda.getConta().substring(0, dadosBancariosVenda.getConta().length() - 1);
                        String ccDv = dadosBancariosVenda.getConta().substring(dadosBancariosVenda.getConta().length() - 1);
                        tbVenda.setConta(cc);
                        tbVenda.setContaDv(ccDv);
                    }
                } //if(dadosBancariosVenda.getConta() != null)
            } //if(beneficiarioTitular.getDadosBancarios() != null)

            if (venda.getTipoPagamento() != null) {
                tbVenda.setTipoPagamento(venda.getTipoPagamento());
            }

            //Responsavel Contratual para venda PF, se titular menor de idade 201803281040
            if (venda.getResponsavelContratual() != null) {

                TbodResponsavelContratual tbodResponsavelContratual = responsavelContratualBusiness
                        .salvarResponsavelContratualComEndereco(venda.getResponsavelContratual());

                if (tbodResponsavelContratual != null) {
                    tbVenda.setTbodResponsavelContratual(tbodResponsavelContratual);
                }

            }

            tbVenda = vendaDao.save(tbVenda);

            if (venda.getTitulares() != null) {
                for (Beneficiario titular : venda.getTitulares()) {
                    titular.setCdVenda(tbVenda.getCdVenda());
                }
            }

            BeneficiarioResponse beneficiarioResponse = beneficiarioBusiness.salvarTitularComDependentes(venda.getTitulares());

            if (beneficiarioResponse.getId() == 0) {
                throw new Exception(beneficiarioResponse.getMensagem());
            }


            if (isIntegraDCSS) {
                propostaDCMSResponse = chamarWsDcssLegado(venda, tbodPlano);
                atualizarNumeroPropostaVenda(venda, tbVenda, propostaDCMSResponse);
            }

            log.info("salvarVendaComTitularesComDependentes - fim");

        }

        return new VendaResponse(tbVenda.getCdVenda(), "Venda cadastrada."
                + " CdVenda:[" + tbVenda.getCdVenda() + "];"
                + " NumeroProposta:[" + propostaDCMSResponse.getNumeroProposta() + "];"
                + " DtVenda:[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(tbVenda.getDtVenda()) + "];"
                + " MensagemErro:[" + propostaDCMSResponse.getMensagemErro() + "];"
                //201808241648 - esert - COR-619 - passar cdEmpresa para App/Web
                , tbVenda.getCdVenda() //cdVenda
                , propostaDCMSResponse.getNumeroProposta() //numeroProposta
                , new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(tbVenda.getDtVenda()) //dtVenda
                , propostaDCMSResponse.getMensagemErro() //mensagemErro
                , (tbVenda.getTbodEmpresa() != null ? tbVenda.getTbodEmpresa().getCdEmpresa() : null) //cdEmpresa  //201809131651 - esert - aplicado sprint13 //201809042045 - esert - venda pf nao tem TbodEmpresa deve testar null
        );
    }

    @Transactional(rollbackFor = {Exception.class}) //201806290926 - esert - COR-352 rollback pf
    public void atualizarNumeroPropostaVenda(Venda venda, TbodVenda tbVenda, PropostaDCMSResponse propostaDCMSResponse) throws Exception {
        log.info("atualizarNumeroPropostaVenda - ini");

        TbodStatusVenda tbodStatusVenda;
        TbodVenda tbodVendaUpdate = vendaDao.findOne(tbVenda.getCdVenda());
        try {
            if (tbodVendaUpdate != null) {

                if (propostaDCMSResponse != null
                        && propostaDCMSResponse.getNumeroProposta() != null
                        && !propostaDCMSResponse.getNumeroProposta().isEmpty()
                ) {
                    tbodVendaUpdate.setPropostaDcms(propostaDCMSResponse.getNumeroProposta());
                    tbodStatusVenda = statusVendaDao.findOne(new Long(1)); //1=Aprovado
                    if (tbodStatusVenda == null) {
                        throw new Exception("atualizarNumeroPropostaVenda; Não achou tbodStatusVenda.getCdStatusVenda(1)");
                    }
                } else {
                    tbodVendaUpdate.setPropostaDcms(null);
                    tbodStatusVenda = statusVendaDao.findOne(new Long(2)); //2=Criticado
                    if (tbodStatusVenda == null) {
                        throw new Exception("atualizarNumeroPropostaVenda; Não achou tbodStatusVenda.getCdStatusVenda(2)");
                    }
                }

                tbodVendaUpdate.setTbodStatusVenda(tbodStatusVenda);

                tbodVendaUpdate = vendaDao.save(tbodVendaUpdate);

                log.info("atualizarNumeroPropostaVenda - fim");
            } else {
                String message = "atualizarNumeroPropostaVenda; Não achou tbVenda.getCdVenda():[" + tbVenda.getCdVenda() + "]";
                log.info(message);
                throw new Exception(message);
            }
        } catch (Exception e) {
            String message = "atualizarNumeroPropostaVenda; e.getMessage():[" + e.getMessage() + "]; e.getCause().getMessage():[" + e.getCause() != null ? e.getCause().getMessage() : "null" + "]";
            log.info(message);
            log.error(message);
            throw new Exception(message);
        }
    }

    @Transactional(rollbackFor = {Exception.class}) //201806290936 - esert - COR-352 rollback pf
    public PropostaDCMSResponse chamarWsDcssLegado(Venda venda, TbodPlano tbodPlano) throws Exception {
        log.info("chamarWsDcssLegado - ini");

        PropostaDCMS propostaDCMS = atribuirVendaPFParaPropostaDCMS(venda, tbodPlano);

        PropostaDCMSResponse propostaDCMSResponse = dcssService.chamarWSLegadoPropostaPOST(propostaDCMS);

        //201807201529 - esert - mock-teste
        //PropostaDCMSResponse propostaDCMSResponse = new PropostaDCMSResponse();//mock-teste
        //propostaDCMSResponse.setNumeroProposta("APP999999999999");//mock-teste
        //propostaDCMSResponse.setMensagemErro("chamarWSLegadoPropostaPOST(fake)");//mock-teste

        if (propostaDCMSResponse != null) {
            log.info("chamarWsDcssLegado; propostaDCMSResponse.getNumeroProposta:[" + propostaDCMSResponse.getNumeroProposta() + "]; getMensagemErro:[" + propostaDCMSResponse.getMensagemErro() + "]");
            if(propostaDCMSResponse.getNumeroProposta()==null) {
            	log.info("chamarWsDcssLegado; propostaDCMSResponse.getNumeroProposta()==null");
            	throw new RollbackException("chamarWsDcssLegado; propostaDCMSResponse.getNumeroProposta()==null");
            }
        } else {
            log.info("chamarWsDcssLegado; propostaDCMSResponse == null");
            throw new RollbackException("chamarWsDcssLegado; propostaDCMSResponse == null");
        }

        log.info("chamarWsDcssLegado - fim");
        return propostaDCMSResponse;
    }

    @Transactional(rollbackFor = {Exception.class}) //201806281838 - esert - COR-348
    private PropostaDCMS atribuirVendaPFParaPropostaDCMS(Venda venda, TbodPlano tbodPlano) throws Exception {

        PropostaDCMS propostaDCMS = new PropostaDCMS();

        TbodForcaVenda tbodForcaVenda = forcaVendaDao.findOne(venda.getCdForcaVenda());

        propostaDCMS.setCorretorMaster(new CorretoraPropostaDCMS());
        propostaDCMS.setAngariador(new CorretoraPropostaDCMS());

        //propostaDCMS.getCorretora().setCodigo(tbodForcaVenda.getTbodCorretora().getCdCorretora());
        Long corretoraCodigo = -1L;
        try {
            corretoraCodigo = Long.parseLong(tbodForcaVenda.getTbodCorretora().getCodigo());
        } catch (NumberFormatException e1) {
            // TODO Auto-generated catch block
            String message = "atribuirVendaPFParaPropostaDCMS(); Impossível converter tbodForcaVenda.getTbodCorretora().getCodigo():[" + tbodForcaVenda.getTbodCorretora().getCodigo() + "] para Long.";
            log.error(message);
            throw new Exception(message);
        }
        propostaDCMS.getCorretorMaster().setCodigo(corretoraCodigo);
        propostaDCMS.getCorretorMaster().setCnpj(tbodForcaVenda.getTbodCorretora().getCnpj());
        propostaDCMS.getCorretorMaster().setNome(tbodForcaVenda.getTbodCorretora().getNome());

        propostaDCMS.getAngariador().setCodigo(corretoraCodigo);
        propostaDCMS.getAngariador().setCnpj(tbodForcaVenda.getTbodCorretora().getCnpj());
        propostaDCMS.getAngariador().setNome(tbodForcaVenda.getTbodCorretora().getNome());

        //propostaDCMS.setCodigoEmpresaDCMS("997692"); //codigoEmpresaDCMS: 997692
        //propostaDCMS.setCodigoEmpresaDCMS(venda.getCdEmpresaDCMS()); //201803021320 esertorio para moliveira
        propostaDCMS.setCodigoEmpresaDCMS(dcss_codigo_empresa_dcms); //201803021538 esertorio para moliveira

        //propostaDCMS.setCodigoCanalVendas((long)57); //codigoCanalVendas: 57
        //propostaDCMS.setCodigoCanalVendas("46"); //201803010449 RODRIGAO
        //propostaDCMS.setCodigoCanalVendas("57"); //201803011730 ROBERTO
        propostaDCMS.setCodigoCanalVendas(dcss_codigo_canal_vendas); //201803021328 esertorio para moliveira

        propostaDCMS.setCodigoUsuario(venda.getCdDCSSUsuario());

        propostaDCMS.setTipoCobranca(new TipoCobrancaPropostaDCMS());

        if (venda.getTitulares() != null
                && venda.getTitulares().size() > 0
                && venda.getTitulares().get(0) != null
                && venda.getTitulares().get(0).getDadosBancarios() != null
                && venda.getTitulares().get(0).getDadosBancarios().getCodigoBanco() != null
                && !venda.getTitulares().get(0).getDadosBancarios().getCodigoBanco().isEmpty()
        ) {

            Beneficiario beneficiarioTitular = venda.getTitulares().get(0);
            DadosBancariosVenda dadosBancariosVenda = beneficiarioTitular.getDadosBancarios();

            DadosBancariosPropostaDCMS dadosBancariosPropostaDCMS = new DadosBancariosPropostaDCMS();

            if (dadosBancariosVenda.getCodigoBanco() != null) {
                dadosBancariosPropostaDCMS.setCodigoBanco(dadosBancariosVenda.getCodigoBanco());
            }

            //54	DA	S	Débito Santander - Febraban 150 - Disque - 033
            if (dadosBancariosPropostaDCMS.getCodigoBanco().equals("033")) { //
                propostaDCMS.getTipoCobranca().setCodigo((long) 54); //TODO //Informar o que vem do serviço da empresa
                propostaDCMS.getTipoCobranca().setSigla("DA"); //BO = Boleto / DA = Debito automatico
            }

            //61	DA	S	Débito Bradesco - CNAB400 - Disque - 237
            if (dadosBancariosPropostaDCMS.getCodigoBanco().equals("237")) { //
                propostaDCMS.getTipoCobranca().setCodigo((long) 61); //TODO //Informar o que vem do serviço da empresa
                propostaDCMS.getTipoCobranca().setSigla("DA"); //BO = Boleto / DA = Debito automatico
            }

            //62	DA	S	Débito Itau - Febraban 150 - Disque - 341
            if (dadosBancariosPropostaDCMS.getCodigoBanco().equals("341")) { //
                propostaDCMS.getTipoCobranca().setCodigo((long) 62); //TODO //Informar o que vem do serviço da empresa
                propostaDCMS.getTipoCobranca().setSigla("DA"); //BO = Boleto / DA = Debito automatico
            }

            if (dadosBancariosVenda.getAgencia() != null) {
                dadosBancariosVenda.setAgencia(dadosBancariosVenda.getAgencia().replace("-", ""));
                if (!dadosBancariosVenda.getAgencia().isEmpty()) {

                    String agDV = calcularDigitoAgencia(dadosBancariosVenda.getAgencia(), dadosBancariosVenda.getCodigoBanco());
                    String ag = dadosBancariosVenda.getAgencia();
                    dadosBancariosPropostaDCMS.setAgencia(ag);
                    dadosBancariosPropostaDCMS.setAgenciaDV(agDV);

                }
            }

            if (dadosBancariosVenda.getTipoConta() != null) {
                dadosBancariosPropostaDCMS.setTipoConta(dadosBancariosVenda.getTipoConta());
            }

            if (dadosBancariosVenda.getConta() != null) {
                dadosBancariosVenda.setConta(dadosBancariosVenda.getConta().replace("-", ""));
                if (!dadosBancariosVenda.getConta().isEmpty()) {
                    String cc = dadosBancariosVenda.getConta().substring(0, dadosBancariosVenda.getConta().length() - 1);
                    String ccDv = dadosBancariosVenda.getConta().substring(dadosBancariosVenda.getConta().length() - 1);
                    dadosBancariosPropostaDCMS.setConta(cc);
                    dadosBancariosPropostaDCMS.setContaDV(ccDv);
                }
            }

            propostaDCMS.setDadosBancarios(dadosBancariosPropostaDCMS);

        } else {
            //60	BO	N	Boleto Bradesco C/ Registro - MultiEmpresa - 237
            propostaDCMS.getTipoCobranca().setCodigo((long) 60); //TODO //Informar o que vem do serviço da empresa
            propostaDCMS.getTipoCobranca().setSigla("BO"); //BO = Boleto / DA = Debito automatico
        }

        //Responsavel Contratual para venda PF, se titular menor de idade 201803281502
        if (venda.getResponsavelContratual() != null) {
            ResponsavelContratual responsavelContratualDCMS = new ResponsavelContratual();
            ResponsavelContratual responsavelContratualVenda = venda.getResponsavelContratual();

            responsavelContratualDCMS.setNome(responsavelContratualVenda.getNome());

            if (responsavelContratualVenda.getCpf() != null && !responsavelContratualVenda.getCpf().isEmpty()) {
                responsavelContratualDCMS.setCpf(responsavelContratualVenda.getCpf().replace(".", "").replace("-", ""));
            }

            responsavelContratualDCMS.setDataNascimento(responsavelContratualVenda.getDataNascimento());
            SimpleDateFormat sdfApp = new SimpleDateFormat("dd/MM/yyyy");
            Date dateDataNascimento = null;
            try {
                dateDataNascimento = sdfApp.parse(responsavelContratualVenda.getDataNascimento());
            } catch (ParseException e) {
                log.info("atribuirVendaPFParaPropostaDCMS; titular.getDataNascimento(String):[" + responsavelContratualVenda.getDataNascimento() + "]; e.getMessage():[" + e.getMessage() + "];");
            }
            SimpleDateFormat sdfJsonString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //String stringDataNascimentoJSON = sdfJsonString.format(dateDataNascimento).replace(" ", "T").concat(".000Z"); //201806061853 - esert/fsetai/rmarques - excluida notacao GMT ZERO
            String stringDataNascimentoJSON = sdfJsonString.format(dateDataNascimento).replace(" ", "T").concat(".000-0300"); //201806061853 - esert/fsetai/rmarques - incluida notacao para GMT-3 Hora de Brasilia
            responsavelContratualDCMS.setDataNascimento(stringDataNascimentoJSON);

            responsavelContratualDCMS.setEmail(responsavelContratualVenda.getEmail());

            if (responsavelContratualVenda.getCelular() != null && !responsavelContratualVenda.getCelular().isEmpty()) {
                responsavelContratualDCMS.setCelular(responsavelContratualVenda.getCelular()
                        .replace(".", "").replace("-", "").replace(" ", "").replace("(", "").replace(")", ""));
            }

            responsavelContratualDCMS.setSexo(responsavelContratualVenda.getSexo());

            EnderecoProposta endereco = responsavelContratualVenda.getEndereco();

            responsavelContratualDCMS.setEndereco(new EnderecoProposta());
            if (endereco.getCep() != null && !endereco.getCep().isEmpty()) {
                responsavelContratualDCMS.getEndereco().setCep(endereco.getCep().replaceAll("-", ""));
            }
            responsavelContratualDCMS.getEndereco().setLogradouro(endereco.getLogradouro());
            responsavelContratualDCMS.getEndereco().setNumero(endereco.getNumero());
            responsavelContratualDCMS.getEndereco().setComplemento(endereco.getComplemento());
            responsavelContratualDCMS.getEndereco().setBairro(endereco.getBairro());
            responsavelContratualDCMS.getEndereco().setCidade(endereco.getCidade());
            responsavelContratualDCMS.getEndereco().setEstado(endereco.getEstado());

            propostaDCMS.setResponsavelContratual(responsavelContratualDCMS);
        }// FIM Responsavel Contratual para venda PF, se titular menor de idade

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
            //String stringDataNascimentoJSON = sdfJsonString.format(dateDataNascimento).replace(" ", "T").concat(".000Z"); //201806061853 - esert/fsetai/rmarques - excluida notacao GMT ZERO
            String stringDataNascimentoJSON = sdfJsonString.format(dateDataNascimento).replace(" ", "T").concat(".000-0300"); //201806061853 - esert/fsetai/rmarques - incluida notacao para GMT-3 Hora de Brasilia
            beneficiarioPropostaTitular.setDataNascimento(stringDataNascimentoJSON);

            beneficiarioPropostaTitular.setNomeMae(titular.getNomeMae());
            beneficiarioPropostaTitular.setCelular(titular.getCelular().replace(".", "").replace("-", "").replace(" ", "").replace("(", "").replace(")", ""));
            beneficiarioPropostaTitular.setEmail(titular.getEmail());

//			if(venda.getPlanos() != null && !venda.getPlanos().isEmpty()) {
//				beneficiarioPropostaTitular.setCodigoPlano(String.valueOf(venda.getPlanos().get(0).getCdPlano()));
//			}
            if (tbodPlano != null) {
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
                //String stringDataNascimentoJSONDep = sdfJsonStringDep.format(dateDataNascimentoDep).replace(" ", "T").concat(".000Z"); //201806061853 - esert/fsetai/rmarques - excluida notacao GMT ZERO
                String stringDataNascimentoJSONDep = sdfJsonStringDep.format(dateDataNascimentoDep).replace(" ", "T").concat(".000-0300"); //201806061853 - esert/fsetai/rmarques - incluida notacao para GMT-3 Hora de Brasilia
                beneficiarioPropostaDependente.setDataNascimento(stringDataNascimentoJSONDep);

                beneficiarioPropostaDependente.setNomeMae(dependente.getNomeMae());
                beneficiarioPropostaDependente.setCelular(dependente.getCelular().replace(".", "").replace("-", "").replace(" ", "").replace("(", "").replace(")", ""));
                beneficiarioPropostaDependente.setEmail(dependente.getEmail());

//				if(venda.getPlanos() != null && !venda.getPlanos().isEmpty()) {
//					beneficiarioPropostaDependente.setCodigoPlano(String.valueOf(venda.getPlanos().get(0).getCdPlano()));
//				}
//				if(venda.getCdPlano() != null) {
//					beneficiarioPropostaDependente.setCodigoPlano(String.valueOf(venda.getCdPlano()));
//				}
                if (tbodPlano != null) {
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

                propostaDCMS.getBeneficiarios().add(beneficiarioPropostaDependente); //201803050437

            } //for (Beneficiario dependente : titular.getDependentes())

        } //for (Beneficiario titular : venda.getTitulares())

        return propostaDCMS;
    }

//    @SuppressWarnings({})
//    @Transactional(rollbackFor = {Exception.class}) //201806290926 - esert - COR-352 rollback pf
//    private PropostaDCMSResponse chamarWSLegadoPropostaPOST(PropostaDCMS propostaDCMS) throws Exception {
//        log.info("chamarWSLegadoPropostaPOST - ini");
//        PropostaDCMSResponse propostaDCMSResponse = new PropostaDCMSResponse();
////		ApiManagerToken apiManager = null;
////		ApiToken apiToken = null;
//        ResponseEntity<PropostaDCMSResponse> response;
//        RestTemplate restTemplate = new RestTemplate();
//        String msgErro = "";
//
//        try {
//            String URLAPI = dcss_venda_propostaUrl + dcss_venda_propostaPath; //201810031800 - esert - COR-852:Alterar Request Angariador Dados nao Obrigatorios - segregar rota de login da rota de proposta para desv e teste
//
////			apiManager = ApiManagerTokenFactory.create(ApiManagerTokenEnum.WSO2, "PORTAL_CORRETOR_SERVICO");
////			apiToken = apiManager.generateToken();
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Authorization", "Bearer " + apiManagerTokenService.getToken());
//            headers.add("Cache-Control", "no-cache");
//            //headers.add("Content-Type", "application/x-www-form-urlencoded");
//            headers.add("Content-Type", "application/json");
//
//            Gson gson = new Gson();
//            String propostaJson = gson.toJson(propostaDCMS);
//            //log.info("propostaJson:[" + propostaJson + "];");
//            //odpvAuditor.audit(dcss_venda_propostaPath, propostaJson, "VendaPFBusiness.chamarWsDcssLegado()"); //201806071601 - esert - log do json enviado ao dcms - solic fsetai
//            odpvAuditor.audit(URLAPI, null, propostaJson, "VendaPFBusiness.chamarWsDcssLegado", LoggerInterceptor.getHeaders(headers), null); //201806291203 - esert - log do json com headers
//
//            HttpEntity<?> request = new HttpEntity<PropostaDCMS>(propostaDCMS, headers);
//
////            response = restTemplate.exchange(
////                    URLAPI,
////                    HttpMethod.POST,
////                    request,
////                    PropostaDCMSResponse.class);
//
//            PropostaDCMSResponse propostaDCMSResponseFAKE = new PropostaDCMSResponse(); //201810171713 - esert - COR-763 - FAKE RESPOSTA DA CHAMADA POST AO DCMS 999999
//            propostaDCMSResponseFAKE.setNumeroProposta("999999"); //201810171713 - esert - COR-763 - FAKE RESPOSTA DA CHAMADA POST AO DCMS 999999
//            propostaDCMSResponseFAKE.setMensagemErro("FAKE RESPOSTA DA CHAMADA POST AO DCMS 999999"); //201810171713 - esert - COR-763 - FAKE RESPOSTA DA CHAMADA POST AO DCMS 999999
//            response = ResponseEntity.ok(propostaDCMSResponseFAKE); //201810171713 - esert - COR-763 - FAKE RESPOSTA DA CHAMADA POST AO DCMS 999999
//            
////			response = restTemplate.postForEntity(
////				URLAPI, 
////				proposta, 
////				PropostaDCMSResponse.class
////			);
//
//            if (response != null) {
//                log.info("chamarWSLegadoPropostaPOST; propostaRet.getStatusCode():[" + response.getStatusCode() + "];");
//            }
//
//            if (response == null
//                    || (response.getStatusCode() == HttpStatus.FORBIDDEN)
//                    || (response.getStatusCode() == HttpStatus.BAD_REQUEST)
//            ) {
//                msgErro = "chamarWSLegadoPropostaPOST; HTTP_STATUS " + response.getStatusCode();
//                log.error(msgErro);
//                propostaDCMSResponse.setMensagemErro(msgErro);
//                return propostaDCMSResponse;
//            }
//
////		} catch (CredentialsInvalidException e1) {
////			msgErro = "chamarWSLegadoPropostaPOST; CredentialsInvalidException.getMessage():[" + e1.getMessage() + "];";
////			log.error(msgErro);
////			propostaDCMSResponse.setMensagemErro(msgErro);
////			return propostaDCMSResponse;
////		} catch (URLEndpointNotFound e1) {
////			msgErro = "chamarWSLegadoPropostaPOST; URLEndpointNotFound.getMessage():[" + e1.getMessage() + "];";
////			log.error(msgErro);
////			propostaDCMSResponse.setMensagemErro(msgErro);
////			return propostaDCMSResponse;
////		} catch (ConnectionApiException e) {
////			msgErro = "chamarWSLegadoPropostaPOST; ConnectionApiException.getMessage():[" + e.getMessage() + "];";
////			log.error(msgErro);
////			propostaDCMSResponse.setMensagemErro(msgErro);
////			//return propostaDCMSResponse;
////			throw e;			
////		} catch (RestClientException e) {
////			msgErro = "chamarWSLegadoPropostaPOST; RestClientException.getMessage():[" + e.getMessage() + "];";
////			log.error(msgErro);
////			propostaDCMSResponse.setMensagemErro(msgErro);
////			//return propostaDCMSResponse;
////			throw e; //201806291524 - esert - se o DCMS falhar deve fazer rollback - COR-352 rollback pf
////			//e.printStackTrace();
////		} catch (ApiTokenException e2) {
////			// TODO Auto-generated catch block
////			//e.printStackTrace();
////			throw e2;
//        } catch (Exception e) {
//            msgErro = "chamarWSLegadoPropostaPOST; Exception.getMessage():[" + e.getMessage() + "];";
//            log.error(msgErro);
//            //e.printStackTrace();
//            propostaDCMSResponse.setMensagemErro(msgErro);
//
//            throw new RollbackException(msgErro); //201806291524 - esert - se o DCMS falhar deve fazer rollback - COR-352 rollback pf
//
//            ////201808021330 - fake
//            ////201809131714 - fake - novo teste apos aplicar/merge do COR-736 no sprint13 - esert
//            //propostaDCMSResponse.setMensagemErro(propostaDCMSResponse.getMensagemErro().concat(";fake-999999"));
//            //propostaDCMSResponse.setNumeroProposta("999999");
//            //return propostaDCMSResponse;
//        }
//        propostaDCMSResponse = response.getBody();
//
//        log.info("chamarWSLegadoPropostaPOST; fim;");
//        return propostaDCMSResponse;
//
//    }

    public VendaResponse verificarErro(Venda vendaPF) {
        log.info("verificarErro - ini");
        VendaResponse vendaResponse = null; //nenhum erro de validacao
        EmailForcaVendaCorretora emailForcaVendaCorretora = forcaVendaService.findByCdForcaVendaEmail(vendaPF.getCdForcaVenda());
        if (emailForcaVendaCorretora == null) {
            log.error("emailForcaVendaCorretora == null para vendaPF.getCdForcaVenda():[{}]", vendaPF.getCdForcaVenda());
        } else {
            if (vendaPF.getTitulares() != null) {
                for (Beneficiario beneficiario : vendaPF.getTitulares()) {
                    if (beneficiario.getEmail() != null) {
                        if (beneficiario.getEmail().equals(emailForcaVendaCorretora.getEmailForcaVenda())) {
                            vendaResponse = new VendaResponse(
                                    0,
                                    String.format("proibido email beneficiario igual email forca venda [%s].",
                                            emailForcaVendaCorretora.getEmailForcaVenda()
                                    ),
                                    true
                            );
                            log.info("verificarErro - fim com msg");
                            return vendaResponse; //ficha suja
                        }
                        if (beneficiario.getEmail().equals(emailForcaVendaCorretora.getEmailCorretora())) {
                            vendaResponse = new VendaResponse(
                                    0,
                                    String.format("proibido email beneficiario igual email corretora [%s].",
                                            emailForcaVendaCorretora.getEmailCorretora()
                                    ),
                                    true
                            );
                            log.info("verificarErro - fim com msg");
                            return vendaResponse; //ficha suja
                        }
                    } //if(beneficiario.getEmail()!=null)
                } //for(Empresa beneficiario: vendaPF.getEmpresas())
            } //if(vendaPF.getEmpresas()!=null)
        } //else //if(emailForcaVendaCorretora == null)
        log.info("verificarErro - fim ok sem msg");
        return null; //se chegou ate aqui entao volta null = ficha limpa
    }

    public String calcularDigitoAgencia(String agencia, String banco) {

		String digito;

		switch (banco) {

		case Constantes.BRADESCO:

			int multiplicador = 2;
			int total = 0;

			for (int i = 4; i > 0; i--) {

				String digitoSelecionado = String.valueOf(agencia.charAt(i - 1));
				total += multiplicador * Integer.parseInt(digitoSelecionado);
				multiplicador++;

			}

			int resto = total % 11;
			int resultado = 11 - resto;

			if (resultado >= 10) {

				digito = "P";// += "P";

			} else {

				digito = String.valueOf(resultado);

			}
			break;

		default:
			return "";

		}
		return digito;
	}

}

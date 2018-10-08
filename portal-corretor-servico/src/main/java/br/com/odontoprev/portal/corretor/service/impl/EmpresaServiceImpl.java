package br.com.odontoprev.portal.corretor.service.impl;

import br.com.odontoprev.portal.corretor.business.BeneficiarioBusiness;
import br.com.odontoprev.portal.corretor.business.EmpresaBusiness;
import br.com.odontoprev.portal.corretor.business.SendMailAceite;
import br.com.odontoprev.portal.corretor.business.SendMailBoasVindasPME;
import br.com.odontoprev.portal.corretor.dao.*;
import br.com.odontoprev.portal.corretor.dto.*;
import br.com.odontoprev.portal.corretor.model.*;
import br.com.odontoprev.portal.corretor.service.*;
import br.com.odontoprev.portal.corretor.util.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.MaskFormatter;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static br.com.odontoprev.portal.corretor.util.Constantes.*;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class); //201810081251 - esert - troca 

    @Autowired
    EmpresaDAO empresaDAO;

    @Autowired
    VendaDAO vendaDAO;

    @Autowired
    TokenAceiteDAO tokenAceiteDAO;

    @Autowired
    LogEmailBoasVindasPMEDAO logEmailBoasVindasPMEDAO; //201805221245 - esert - COR-225 - Serviço - LOG Envio e-mail de Boas Vindas PME

    @Autowired
    EmpresaContatoDAO empresaContatoDAO; //201807241652 - esert - COR-398

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

    @Autowired
    DataUtil dataUtil; //201806201702 - esert

    @Autowired
    VendaService vendaService; //201806291953 - esert - COR-358 Serviço - Alterar serviço /empresa-dcms para atualizar o campo CD_STATUS_VENDA da tabela TBOD_VENDA.

    @Autowired
    XlsEmpresa xlsEmpresa;

    @Value("${mensagem.empresa.atualizada.dcms}")
    private String empresaAtualizadaDCMS; //201805181310 - esert - COR-160

    @Value("${mensagem.empresa.atualizada.aceite}")
    private String empresaAtualizadaAceite; //201805181310 - esert - COR-171

    @Autowired
    private TokenAceiteService tokenAceiteService; //201805181856 - esert - COR-171

    @Autowired
    ArquivoContratacaoService arquivoContratacaoService;

    @Autowired
    ArquivoContratacaoDAO arquivoContratacaoDAO;

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

            if (
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

                //201806291953 - esert - COR-358 Serviço - Alterar serviço /empresa-dcms para atualizar o campo CD_STATUS_VENDA da tabela TBOD_VENDA.
                List<TbodVenda> listTbodVenda = vendaDAO.findByTbodEmpresaCdEmpresa(tbEmpresa.getCdEmpresa());
                if (listTbodVenda != null) {
                    if (listTbodVenda.size() > 0) {
                        TbodVenda tbodVenda = listTbodVenda.get(listTbodVenda.size() - 1); //arbitrariamente pega a ULTIMA venda como sendo a mais recente
                        if (tbodVenda != null) {
                            vendaService.atualizarStatusVenda(tbodVenda.getCdVenda(), Constantes.STATUS_VENDA_APROVADO);
                        } else {
                            throw new Exception("[updateEmpresa] tbodVenda == null");
                        }
                    } else {
                        throw new Exception("[updateEmpresa] listTbodVenda.size() == 0");
                    }
                } else {
                    throw new Exception("[updateEmpresa] listTbodVenda == null");
                }

                List<TbodVida> vidas = beneficiarioBusiness.buscarVidasPorEmpresa(tbEmpresa.getCdEmpresa());

                if (vidas != null && !vidas.isEmpty()) {
                    XlsVidas xlsVidas = new XlsVidas();
                    xlsVidas.gerarVidasXLS(vidas, tbEmpresa);
                }

                //201805091745 - esert
                //201805101609 - esert - criar servico independente para Email Boas Vindas PME vide Fernando@ODPV em 20180510
                //201805101941 - esert - excluido param cnpj
                ResponseEntity<EmpresaDcms> res = this.sendMailBoasVindasPME(empresaDcms.getCdEmpresa());
                log.info("res[sendMailBoasVindasPME(" + empresaDcms.getCdEmpresa() + ")]:[" + res.toString() + "]"); //201806201636 - esert
            } else {
                throw new Exception("CdEmpresa nao relacionado com CNPJ!");
            }

        } catch (Exception e) {
            log.error("EmpresaServiceImpl :: Erro ao atualizar empresaDcms. Detalhe: [" + e.getMessage() + "]");
            return new EmpresaResponse(0, "Erro ao cadastrar empresaDcms. Favor, entre em contato com o suporte. Detalhe: [" + e.getMessage() + "]");
        }

        return new EmpresaResponse(tbEmpresa.getCdEmpresa(), empresaAtualizadaDCMS);

    }

    //201805091745 - esert
    @SuppressWarnings("unused")
    //201806201634 - linha 178 da warning de (dead code) mas tbodEmpresa sera null se findOne() nao achar cdEmpresa
    //201805101941 - esert - excluido param cnpj
    //201805101947 - esert - service para reenvio de emails vide Fernando@ODPV
    //201805221057 - esert - refactor sendEmailBoasVindasPME para sendMailBoasVindasPME mantendo padrao
    @Override
    public ResponseEntity<EmpresaDcms> sendMailBoasVindasPME(Long cdEmpresa) {
        Long longDiaVencimentoFatura = 0L;
        Date dateDataVigencia = null;

        List<String> listaEmails = new ArrayList<>(); //201806211921 - esert - correcao bug email varios destinatarios - alterado escopo da variavel de (classe) para (metodo)
        String emailForcaVenda = ""; //201806211921 - esert - correcao bug email varios destinatarios - alterado escopo da variavel de (classe) para (metodo)

        try {
            log.info("cdEmpresa:[" + cdEmpresa + "]");

            TbodEmpresa tbodEmpresa = empresaDAO.findOne(cdEmpresa);
            listaEmails.add(tbodEmpresa.getEmail());

            if (tbodEmpresa == null) {
                log.info("tbodEmpresa==null");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (tbodEmpresa.getEmail() == null) {
                log.info("tbodEmpresa.getEmail()==null");
                return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
            }

            log.info("tbodEmpresa.getEmpDcms():[" + tbodEmpresa.getEmpDcms() + "]");

            List<TbodVenda> tbodVendas = vendaDAO.findByTbodEmpresaCdEmpresa(cdEmpresa);

            if (tbodVendas != null) {
                log.info("tbodVendas.size():[" + tbodVendas.size() + "]"); //201805221233 - esert
                if (tbodVendas.size() > 0) { //201805221233 - esert - protecao

                    for (TbodVenda tbodVenda : tbodVendas) {
                        //201805101616 - esert - arbitragem : fica com a ultima venda suposta mais recente
                        longDiaVencimentoFatura = tbodVenda.getFaturaVencimento();
                        log.info("longDiaVencimentoFatura:[" + longDiaVencimentoFatura + "]");
                        dateDataVigencia = tbodVenda.getDtVigencia();
                        log.info("dateDataVenda:[" + (new SimpleDateFormat("dd/MM/yyyy")).format(dateDataVigencia) + "]");

                        if (emailForcaVenda.isEmpty()) {
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
            log.info("dateDataVenda:[" + (new SimpleDateFormat("dd/MM/yyyy")).format(dateDataVigencia) + "]");

            String strDiaVencimentoFatura = String.valueOf(100L + longDiaVencimentoFatura).substring(1, 3);
            log.info("strDiaVencimentoFatura:[" + strDiaVencimentoFatura + "]");

            //201805101739 - esert - funcao isEffectiveDate copíada do App na versao abaixo porem usa DataVenda ao inves de CurrentDate vide Camila@ODPV
            //http://git.odontoprev.com.br/esteira-digital/est-portalcorretor-app/blob/sprint6/VendasOdontoPrev/app/src/main/assets/app/pmeFaturaController.js
            //String strDataVigencia = DataUtil.isEffectiveDate(longDiaVencimentoFatura, dateDataVenda); //201806141632 - esert - alterar retorno de String para Date para deixar formatacao para o uso final
            SimpleDateFormat sdf_ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy"); //201806141640 - esert - formato data para email
            //String strDataVigencia = sdf_ddMMyyyy.format(dataUtil.isEffectiveDate(longDiaVencimentoFatura, dateDataAceite)); //201806141640 - esert - formatar data para email //201806201630 - esert - DataUtil dinamico
            String strDataVigencia = sdf_ddMMyyyy.format(dateDataVigencia);

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

            String stringListaEmails = ""; //201806201303 - esert - concatena lista de emails de destino MailBoasVindasPME
            for (String email : listaEmails) {
                if (stringListaEmails.length() > 0) {
                    stringListaEmails += ",";
                }
                stringListaEmails += email; //201806201303 - esert - concatena lista de emails de destino MailBoasVindasPME
            }
            int stringListaEmails_length = stringListaEmails.length();
            if (stringListaEmails_length > 500) {
                stringListaEmails_length = 500; //201806212037 - esert - novo limite tamanho campo EMAIL varchar 500 vide CORRET.TBOD_LOG_EMAIL_BVPME.MOD.EMAIL500.201806212029.sql
            }
            stringListaEmails = stringListaEmails.substring(0, stringListaEmails_length);

            //201805221245 - esert - COR-225 - Serviço - LOG Envio e-mail de Boas Vindas PME
            TbodLogEmailBoasVindasPME tbodLogEmailBoasVindasPME = new TbodLogEmailBoasVindasPME();
            tbodLogEmailBoasVindasPME.setCdEmpresa(tbodEmpresa.getCdEmpresa());
            tbodLogEmailBoasVindasPME.setRazaoSocial(tbodEmpresa.getRazaoSocial());
            //tbodLogEmailBoasVindasPME.setEmail(tbodEmpresa.getEmail());
            tbodLogEmailBoasVindasPME.setEmail(stringListaEmails); //201806201303 - esert - concatena lista de emails de destino MailBoasVindasPME
            tbodLogEmailBoasVindasPME.setDtEnvio(new Date());
            logEmailBoasVindasPMEDAO.save(tbodLogEmailBoasVindasPME);

            EmpresaDcms empresaDcms = new EmpresaDcms();
            empresaDcms.setCdEmpresa(tbodEmpresa.getCdEmpresa());
            empresaDcms.setEmpDcms(tbodEmpresa.getEmpDcms());
            empresaDcms.setCnpj(tbodEmpresa.getCnpj()); //201805221106 - esert - COR-160 - refactor - semm qg no cnpj
            //empresaDcms.setEmail(tbodEmpresa.getEmail()); //201805221106 - esert - COR-160 - refactor - inc campo especifico para email
            empresaDcms.setEmail(stringListaEmails); //201806201303 - esert - concatena lista de emails de destino MailBoasVindasPME
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

            if (cnpj.length() == 14) {

                tbodEmpresa = empresaDAO.findByCnpj(mask.valueToString(cnpj));

                if (tbodEmpresa != null) {
                    cnpjDados.setCdEmpresa(tbodEmpresa.getCdEmpresa());
                    cnpjDados.setRazaoSocial(tbodEmpresa.getRazaoSocial());
                    cnpjDados.setCnpj(cnpj);
                    cnpjDados.setEmpDcms(tbodEmpresa.getEmpDcms()); //201805102039 - esert - COR-169
                } else {
                    tbodEmpresa = empresaDAO.findByCnpj(cnpj);
                    if (tbodEmpresa == null) {
                        cnpjDados.setObservacao("Cnpj [" + cnpj + "] não encontrado na base !!! [" + (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date().getTime())) + "]");
                        cnpjDados.setObservacaoLoteDcms(CNPJ_NAO_ENCONTRADO);
                    } else {
                        cnpjDados.setCdEmpresa(tbodEmpresa.getCdEmpresa());
                        cnpjDados.setRazaoSocial(tbodEmpresa.getRazaoSocial());
                        cnpjDados.setCnpj(cnpj);
                        cnpjDados.setEmpDcms(tbodEmpresa.getEmpDcms()); //201805102039 - esert - COR-169
                    }
                }
            } else {
                cnpjDados.setObservacao("Cnpj é obrigatório informar 14 digitos!!!");
                cnpjDados.setObservacaoLoteDcms(CNPJ_INVALIDO);
            }

        } catch (org.springframework.dao.IncorrectResultSizeDataAccessException e) {

            cnpjDados.setObservacao("Encontrado +1 cnpj na base!!!");
            cnpjDados.setObservacaoLoteDcms(CNPJ_DUPLICADO);
            return cnpjDados;

        } catch (Exception e) {
            cnpjDados.setObservacao(e.getMessage());
            cnpjDados.setObservacaoLoteDcms(e.getMessage());
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

            if (cnpj.length() == 14) {

                tbodEmpresa = empresaDAO.findByCnpj(mask.valueToString(cnpj));

                if (tbodEmpresa == null) {
                    tbodEmpresa = empresaDAO.findByCnpj(cnpj);
                }

                if (tbodEmpresa != null) {
                    cnpjDadosAceite = translateTbodEmpresaToCnpjDadosAceite(cnpjDadosAceite, tbodEmpresa);

                    List<TbodVenda> listTbodVenda = vendaDAO.findByTbodEmpresaCdEmpresa(tbodEmpresa.getCdEmpresa());

                    if (listTbodVenda != null) {
                        Date maiorDataVenda = null;
                        Long maiorCdVenda = null;
                        for (TbodVenda tbodVendaItem : listTbodVenda) {
                            if (
                                    maiorDataVenda == null
                                            ||
                                            maiorCdVenda == null
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

                    if (tbodVenda != null) {

                        cnpjDadosAceite.setCdVenda(tbodVenda.getCdVenda());
                        cnpjDadosAceite.setDtVenda(tbodVenda.getDtVenda()); //201805111519 - esert - informativo de desempate

//						tbodTokenAceite = tokenAceiteDAO.findByTbodVendaCdVenda(tbodVenda.getCdVenda());
                        List<TbodTokenAceite> listTbodTokenAceite = tokenAceiteDAO.findTokenPorVenda(tbodVenda.getCdVenda()); //201805211602 - esert - pega ultimo token da venda
                        if (listTbodTokenAceite != null) {
                            if (listTbodTokenAceite.size() > 0) {
                                if (listTbodTokenAceite.size() == 1) {
                                    tbodTokenAceite = listTbodTokenAceite.get(listTbodTokenAceite.size() - 1); //pega unico
                                } else {
                                    Date maiorDtEnvio = (new GregorianCalendar(1900, 1, 1).getTime());
                                    for (TbodTokenAceite itemTokenAceite : listTbodTokenAceite) {
                                        if (itemTokenAceite.getDtEnvio().getTime() > maiorDtEnvio.getTime()) { //fica
                                            maiorDtEnvio = itemTokenAceite.getDtEnvio();
                                            tbodTokenAceite = itemTokenAceite; //pega maior data envio
                                        }
                                    }
                                } //else //if(listTbodTokenAceite.size()==1)

                                if (tbodTokenAceite != null) {
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
            log.error("Exception e:[" + e.getMessage() + "]");
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

        if (tbodTokenAceite != null) {

            if (cnpjDadosAceite == null) {
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

        if (tbodEmpresa != null) {

            if (cnpjDadosAceite == null) {
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

            if (
                    empresaEmail.getCdEmpresa() == null
                            ||
                            empresaEmail.getCdEmpresa() == 0
                    ) {
                return (new EmpresaResponse(empresaEmail.getCdEmpresa(), "parametro CdEmpresa obrigatorio!"));
            }

            if (
                    empresaEmail.getCdVenda() == null
                            ||
                            empresaEmail.getCdVenda() == 0
                    ) {
                return (new EmpresaResponse(empresaEmail.getCdEmpresa(), "parametro CdVenda obrigatorio!")); //201805171124 - esert
            }

            if (
                    empresaEmail.getEmail() == null
                            ||
                            empresaEmail.getEmail().isEmpty()
                    ) {
                return (new EmpresaResponse(0, "parametro Email obrigatorio !"));
            }

            tbEmpresa = empresaDAO.findOne(empresaEmail.getCdEmpresa());

            if (tbEmpresa != null) {
                tbEmpresa.setEmail(empresaEmail.getEmail());
                empresaDAO.save(tbEmpresa);

                TbodVenda tbodVenda = vendaDAO.findOne(empresaEmail.getCdVenda());
                if (tbodVenda == null) {
                    return (new EmpresaResponse(empresaEmail.getCdVenda(), "TbodVenda nao encontrado para empresaEmail.getCdVenda(" + empresaEmail.getCdVenda() + ")!"));
                }

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

                if (tokenAceiteResponse != null) {
                    if (tokenAceiteResponse.getId() == 0L) {
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

    //201807241625 - esert - COR-398
    @Override
    public Empresa findByCdEmpresa(Long cdEmpresa) {
        Empresa empresa = null;

        empresa = translateTbodEmpresaToEmpresa(empresaDAO.findOne(cdEmpresa));

        List<TbodVenda> tbodVendaList = vendaDAO.findByTbodEmpresaCdEmpresa(cdEmpresa);

//		if (tbodVendaList == null || tbodVendaList.size() == 0 || tbodVendaList.size() > 1){
//			log.error("Não achou venda para Empresa" + "[tbodVendaList == null || tbodVendaList.size() == 0 || tbodVendaList.size() > 1]");
//			return null;
//		}
        //TODO 2018082352 discutir esta regra de exigir uma so venda no pme que nao faz sentido
        if (tbodVendaList == null || tbodVendaList.size() == 0) {
            log.error("Não achou venda para Empresa" + "[tbodVendaList == null || tbodVendaList.size() == 0]");
            return null;
        }

        empresa.setCdStatusVenda(tbodVendaList.get(0).getTbodStatusVenda().getCdStatusVenda());

        return empresa;
    }

    //201807241625 - esert - COR-398
    private Empresa translateTbodEmpresaToEmpresa(TbodEmpresa tbodEmpresa) {
        log.info("translateTbodEmpresaToEmpresa - ini");
        Empresa empresa = null;

        if (tbodEmpresa != null) {

            if (empresa == null) {
                empresa = new Empresa();
            }

            empresa.setCdEmpresa(tbodEmpresa.getCdEmpresa());
            empresa.setCnpj(tbodEmpresa.getCnpj());
            empresa.setRazaoSocial(tbodEmpresa.getRazaoSocial());
            empresa.setNomeFantasia(tbodEmpresa.getNomeFantasia());
            empresa.setRamoAtividade(tbodEmpresa.getRamoAtividade());
            empresa.setRepresentanteLegal(tbodEmpresa.getRepresentanteLegal());
            empresa.setCpfRepresentante(tbodEmpresa.getCpfRepresentante()); //201807251530 - esert - COR-513
            empresa.setTelefone(tbodEmpresa.getTelefone());
            empresa.setCelular(tbodEmpresa.getCelular());
            if (tbodEmpresa.getContatoEmpresa() != null) { //201808071127 - esert - COR-472/COR-398
                empresa.setContatoEmpresa(tbodEmpresa.getContatoEmpresa().equals(Constantes.SIM)); //201808071127 - esert - COR-472/COR-398
            }
            empresa.setEmail(tbodEmpresa.getEmail());
            empresa.setEmpDcms(tbodEmpresa.getEmpDcms());

            if (tbodEmpresa.getTbodEndereco() != null) {
                //empresa.setEnderecoEmpresa(new Endereco()); //mock
                empresa.setEnderecoEmpresa(ConvertObjectUtil.translateTbodEnderecoToEndereco(tbodEmpresa.getTbodEndereco())); //201807241928 - esert - COR-398
            }

            if (!empresa.isContatoEmpresa()) { //201808071127 - esert - COR-472/COR-398
                if (tbodEmpresa.getCdEmpresaContato() != null) {
                    //empresa.setContactEmpresa(new ContatoEmpresa());
                    Long cdEmpresaContato = tbodEmpresa.getCdEmpresaContato();
                    TbodEmpresaContato tbodEmpresaContato = empresaContatoDAO.findOne(cdEmpresaContato);
                    ContatoEmpresa contatoEmpresa = ConvertObjectUtil.translateTbodEmpresaContatoToEmpresaContato(tbodEmpresaContato);
                    empresa.setContactEmpresa(contatoEmpresa);
                }
            } else {
                empresa.setContactEmpresa(null); //201808071127 - esert - COR-472/COR-398
            }

            List<TbodVenda> listTbodVenda = vendaDAO.findByTbodEmpresaCdEmpresa(tbodEmpresa.getCdEmpresa());
            if (listTbodVenda != null) {
                for (TbodVenda tbodVenda : listTbodVenda) {
                    if (tbodVenda.getFaturaVencimento() != null) {
                        empresa.setVencimentoFatura((long) tbodVenda.getFaturaVencimento());
                        if (tbodVenda.getDtVenda() != null) {
                            empresa.setDataVencimentoFatura(new SimpleDateFormat("dd/MM/yyyy").format(dataUtil.isEffectiveDate(tbodVenda.getFaturaVencimento(), tbodVenda.getDtVenda()))); //201807242005 - esert
                        }
                    }
                    if (tbodVenda.getDtMovimentacao() != null) {
                        empresa.setDataMovimentacao(new SimpleDateFormat("dd/MM/yyyy").format(tbodVenda.getDtMovimentacao()));
                    }
                    if (tbodVenda.getDtVigencia() != null) {
                        empresa.setDataVigencia(new SimpleDateFormat("dd/MM/yyyy").format(tbodVenda.getDtVigencia()));
                    }
                    if (tbodVenda.getDtAceite() != null) {
                        empresa.setDataAceite(new SimpleDateFormat("dd/MM/yyyy").format(tbodVenda.getDtAceite()));
                    }
                }
            }

            empresa.setCnae(tbodEmpresa.getCnae());
            empresa.setIncEstadual(tbodEmpresa.getIncEstadual());

        }
        log.info("translateTbodEmpresaToEmpresa - fim");
        return empresa;
    }

    @Override //201808081231
    public EmpresaArquivoResponse gerarArquivoEmpresa(EmpresaArquivo cdEmpresas) {

        EmpresaArquivoResponse empresaArquivoResponse = new EmpresaArquivoResponse();

        try {

            List<Long> listCdEmpresas = cdEmpresas.getListCdEmpresa();

            if (listCdEmpresas != null) {

                empresaArquivoResponse.setEmpresas(new ArrayList<EmpresaArquivoResponseItem>());

                for (Long itemCdEmpresa : listCdEmpresas) {

                    List<TbodVenda> vendaEmpresa = vendaDAO.findByTbodEmpresaCdEmpresa(itemCdEmpresa);

                    if (vendaEmpresa != null) {

                        if (vendaEmpresa.size() == 1) {

                            try {

                                xlsEmpresa.GerarEmpresaXLS(vendaEmpresa.get(0));
                                EmpresaArquivoResponseItem empresaArquivoResponseItem = new EmpresaArquivoResponseItem(itemCdEmpresa, "Arquivo gerado com sucesso.");
                                empresaArquivoResponse.getEmpresas().add(empresaArquivoResponseItem);

                            } catch (Exception e) {

                                EmpresaArquivoResponseItem empresaArquivoResponseItem = new EmpresaArquivoResponseItem(itemCdEmpresa, "Erro na geracao do arquivo: " + e);
                                empresaArquivoResponse.getEmpresas().add(empresaArquivoResponseItem);

                            }

                        } else if (vendaEmpresa.size() > 1) {

                            EmpresaArquivoResponseItem empresaArquivoResponseItem = new EmpresaArquivoResponseItem(itemCdEmpresa, "Mais de uma venda encontrada.");
                            empresaArquivoResponse.getEmpresas().add(empresaArquivoResponseItem);

                        } else if (vendaEmpresa.size() == 0) {

                            EmpresaArquivoResponseItem empresaArquivoResponseItem = new EmpresaArquivoResponseItem(itemCdEmpresa, "Empresa nao encontrada.");
                            empresaArquivoResponse.getEmpresas().add(empresaArquivoResponseItem);

                        }

                    } else {

                        EmpresaArquivoResponseItem empresaArquivoResponseItem = new EmpresaArquivoResponseItem(itemCdEmpresa, "Empresas nao encontradas (null)");
                        empresaArquivoResponse.getEmpresas().add(empresaArquivoResponseItem);

                    }
                }
            } else {

                EmpresaArquivoResponseItem empresaArquivoResponseItem = new EmpresaArquivoResponseItem(Long.valueOf(0L), "Empresas nao encontradas (null)");
                empresaArquivoResponse.getEmpresas().add(empresaArquivoResponseItem);

            }

        } catch (Exception e) {

            empresaArquivoResponse = null;
            log.error("[Erro na geracao de arquivos]");

        }

        return empresaArquivoResponse;
    }

    @Override
    @Transactional
    public EmpresaResponse updateEmpresa(Empresa empresa) throws Exception {

        log.info("updateEmpresaEmail - ini");
        TbodEmpresa tbodEmpresa;

        tbodEmpresa = empresaDAO.findOne(empresa.getCdEmpresa());

        if (tbodEmpresa == null){
            log.info("updateEmpresaEmail - empresa nao encontrada");
            return null;
        }

        if (empresa.getEmail() != null && !empresa.getEmail().isEmpty()) {

            tbodEmpresa.setEmail(empresa.getEmail());
            tbodEmpresa = empresaDAO.save(tbodEmpresa);
            log.info("updateEmpresaEmail - email alterado");

        }

        arquivoContratacaoService.createPdfPmePorEmpresa(tbodEmpresa.getCdEmpresa());

        log.info("updateEmpresaEmail - fim");
        return new EmpresaResponse(HttpStatus.OK.value(), String.format("Empresa: [%d], atualizada.", tbodEmpresa.getCdEmpresa()));

    }

    //201809251843 - esert - COR-820 Criar POST /empresa-emailaceite
	@Override
	public EmpresaResponse enviarEmpresaEmailAceite(Empresa empresa) {

        log.info("enviarEmpresaEmailAceite - ini");

        try {

        	TbodEmpresa tbodEmpresa = empresaDAO.findOne(empresa.getCdEmpresa());
            if (tbodEmpresa != null) {

                List<TbodVenda> listTbodVenda = tbodEmpresa.getTbodVendas();
                if (listTbodVenda == null || listTbodVenda.size() == 0) {
                    log.error("listTbodVenda == null || listTbodVenda.size() == 0 para empresa.getCdEmpresa(" + empresa.getCdEmpresa() + ")!");
                    return null;
                }
                
                TbodVenda tbodVenda = listTbodVenda.get(listTbodVenda.size()-1);

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

                if (tokenAceiteResponse != null) {
                    if (tokenAceiteResponse.getId() == 0L) {
                        throw new Exception("enviarEmpresaEmailAceite :: Erro ao gerar token ou enviar email aceite ! na chamada de addTokenAceite( CdVenda([" + tbodVenda.getCdVenda() + "])); tokenAceiteResponse.getMensagem([" + tokenAceiteResponse.getMensagem() + "]); tokenAceiteResponse.getId([" + tokenAceiteResponse.getId() + "])"); //201805181916 - esert - COR-171
                    }
                } else {
                    throw new Exception("enviarEmpresaEmailAceite : tokenAceiteResponse != null");
                }

            } else {
                log.error("CdEmpresa [" + empresa.getCdEmpresa() + "] nao encontrado !"); //201705172015 - esert
            	return null; //NoContent //201809251905 - esert
            }

        } catch (Exception e) {
            log.error("enviarEmpresaEmailAceite - Erro:[" + e.getMessage() + "]");
            return new EmpresaResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro [" + e.getMessage() + "][" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + "]");
        }

        log.info("enviarEmpresaEmailAceite - fim");
        return new EmpresaResponse(HttpStatus.OK.value(), String.format("Empresa: [%d], email enviado.", empresa.getCdEmpresa()));
	}

	//201810051800 - esert - COR-861:Serviço - Receber / Retornar Planilha
	@Override
	public FileUploadLoteDCMSResponse processarLoteDCMS(FileUploadLoteDCMS fileUploadLoteDCMS) {
		FileUploadLoteDCMSResponse fileUploadLoteDCMSResponse = new FileUploadLoteDCMSResponse();
		
		File fileXLSReq = this.convertBase64ToFile( 
				fileUploadLoteDCMS.getArquivoBase64(), 
				fileUploadLoteDCMS.getNomeArquivo(),
				fileUploadLoteDCMS.getCaminhoArquivo() //201810052115 - esert - ferramenta para testes locais via postman
				);

		List<EmpresaDcmsEntrada> listEmpresaDCMSReq = this.convertXLSReqToListEmpresaDCMS(fileXLSReq);
		
		List<EmpresaDcmsEntrada> listEmpresaDCMSRes = empresaBusiness.processarLoteDCMS(listEmpresaDCMSReq);

		File fileXLSRes = this.convertListEmpresaDCMSToXLSRes( 
				listEmpresaDCMSRes, 
				fileUploadLoteDCMS.getNomeArquivo()
				);

		@SuppressWarnings("unused")
		String arquivoBase64Res = convertFileToBase64(fileXLSRes);

		//201810051716 - esert - dummy para teste yago
		fileUploadLoteDCMSResponse.setArquivoBase64(fileUploadLoteDCMS.getArquivoBase64());
		fileUploadLoteDCMSResponse.setNomeArquivo(fileUploadLoteDCMS.getNomeArquivo());
		fileUploadLoteDCMSResponse.setTamanho(fileUploadLoteDCMS.getTamanho());
		fileUploadLoteDCMSResponse.setTipoConteudo(fileUploadLoteDCMS.getTipoConteudo());
		
		return fileUploadLoteDCMSResponse;
	}

	//201810051810 - esert
	private File convertBase64ToFile(String arquivoBase64, String nomeArquivo, String caminhoArquivo) {
		File file = null;
		byte[] byteArray = null;
		if(caminhoArquivo==null || caminhoArquivo.isEmpty()) { //201810052115 - esert - ferramenta para testes locais via postman
			byteArray = Base64.getDecoder().decode(arquivoBase64);
			try (OutputStream stream = new FileOutputStream(nomeArquivo)) {
			    try {
					stream.write(byteArray);
				    file = new File(nomeArquivo);
				} catch (IOException e) {
					log.error("IOException", e);
				}
			} catch (FileNotFoundException e1) {
				log.error("FileNotFoundException", e1);
			} catch (IOException e1) {
				log.error("IOException", e1);
			}
		} else {
			String caminhoNomeArquivo = caminhoArquivo + "/" + nomeArquivo; //201810052115 - esert - ferramenta para testes locais via postman
			file = new File(caminhoNomeArquivo); //201810052115 - esert - ferramenta para testes locais via postman
		}
		return file;
	}

	//201810051900 - esert - COR-861:Serviço - Receber / Retornar Planilha
	@SuppressWarnings("deprecation")
	private List<EmpresaDcmsEntrada> convertXLSReqToListEmpresaDCMS(File fileXLSReq) {
		log.info("convertXLSReqToListEmpresaDCMS - ini");
		List<EmpresaDcmsEntrada> listEmpresaDcms = null;
		
		if(fileXLSReq==null) {
			return null;
		}

		log.info("fileXLSReq:[{}]", fileXLSReq.getName());

		//h t t p s : //www. mkyong. com /java /apache-poi-reading-and-writing-excel-file-in-java/
        try {

            FileInputStream excelFile = new FileInputStream(fileXLSReq);
            @SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            listEmpresaDcms = new ArrayList<EmpresaDcmsEntrada>();
            
            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                
                EmpresaDcmsEntrada empresaDcmsEntrada = new EmpresaDcmsEntrada(); 

                while (cellIterator.hasNext()) {

                    //CD_VENDA //01
                    Cell currentCell1 = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell1.getCellType() == CellType.STRING.getCode()) {
                    	log.debug(currentCell1.getStringCellValue() + "--");
                    	if(currentCell1.getStringCellValue().equals("CD_VENDA")) {
                    		continue; //pula linha de cabessalho e vai pra prochima //201810052154
                    	}
                        empresaDcmsEntrada.setCdVenda(Long.getLong(currentCell1.getStringCellValue()));
                    } else if (currentCell1.getCellType() == CellType.NUMERIC.getCode()) {
                    	log.debug(currentCell1.getNumericCellValue() + "--");
                        empresaDcmsEntrada.setCdVenda(Long.getLong(String.valueOf(currentCell1.getNumericCellValue())));
                    }

                    if(!cellIterator.hasNext()) { 
                    	continue; //201810052200 - esert - celula vazia acabou linha pula para proxima linha                     	
                    }

                    //CD_EMPRESA //02
                    Cell currentCell2 = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell2.getCellType() == CellType.STRING.getCode()) {
                    	log.debug(currentCell2.getStringCellValue() + "--");
                    	if(currentCell2.getStringCellValue().equals("CD_EMPRESA")) {
                    		continue; //pula linha de cabessalho e vai pra prochima //201810052154
                    	}
                        empresaDcmsEntrada.setCdEmpresa((Long.getLong(currentCell2.getStringCellValue())));
                    } else if (currentCell2.getCellType() == CellType.NUMERIC.getCode()) {
                    	log.debug(currentCell2.getNumericCellValue() + "--");
                        empresaDcmsEntrada.setCdEmpresa(Long.getLong(String.valueOf(currentCell1.getNumericCellValue())));
                    }

                    if(!cellIterator.hasNext()) { 
                    	continue; //201810052200 - esert - celula vazia acabou linha pula para proxima linha                     	
                    }

                    //CNPJ_CLIENTE //03
                    Cell currentCell3 = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell3.getCellType() == CellType.STRING.getCode()) {
                    	log.debug(currentCell3.getStringCellValue() + "--");
                    	if(currentCell3.getStringCellValue().equals("CNPJ_CLIENTE")) {
                    		continue; //pula linha de cabessalho e vai pra prochima //201810052154
                    	}
                        empresaDcmsEntrada.setCnpj(currentCell3.getStringCellValue());
                    } else if (currentCell3.getCellType() == CellType.NUMERIC.getCode()) {
                        log.debug(currentCell3.getNumericCellValue() + "--");
                        empresaDcmsEntrada.setCnpj(String.valueOf(currentCell3.getNumericCellValue()));
                    }
                    
                    if(!cellIterator.hasNext()) { 
                    	continue; //201810052200 - esert - celula vazia acabou linha pula para proxima linha                     	
                    }
                    
                    //RAZAO_SOCIAL_CLIENTE //04
                    Cell currentCell4 = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell4.getCellType() == CellType.STRING.getCode()) {
                        log.debug(currentCell4.getStringCellValue() + "--");
                    	if(currentCell4.getStringCellValue().equals("RAZAO_SOCIAL_CLIENTE")) {
                    		continue; //pula linha de cabessalho e vai pra prochima //201810052154
                    	}
                        empresaDcmsEntrada.setRazaoSocial(currentCell4.getStringCellValue());
                    } else if (currentCell4.getCellType() == CellType.NUMERIC.getCode()) {
                        log.debug(currentCell4.getNumericCellValue() + "--");
                        empresaDcmsEntrada.setRazaoSocial(String.valueOf(currentCell4.getNumericCellValue()));
                    }

                    if(!cellIterator.hasNext()) { 
                    	continue; //201810052200 - esert - celula vazia acabou linha pula para proxima linha                     	
                    }
                    
                    //CAD_DCMS //05
                    Cell currentCell5 = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell5.getCellType() == CellType.STRING.getCode()) {
                        log.debug(currentCell5.getStringCellValue() + "--");
                    	if(currentCell5.getStringCellValue().equals("CAD_DCMS")) {
                    		continue; //pula linha de cabessalho e vai pra prochima //201810052154
                    	}
                        empresaDcmsEntrada.setEmpDcms(currentCell5.getStringCellValue());
                    } else if (currentCell5.getCellType() == CellType.NUMERIC.getCode()) {
                        log.debug(currentCell5.getNumericCellValue() + "--");
                        empresaDcmsEntrada.setEmpDcms(String.valueOf(currentCell5.getNumericCellValue()));
                    }

                    break;
                    
                } //while (cellIterator.hasNext())
                
                String msgValidaEmpresaDcmsEntrada = validaEmpresaDcmsEntrada(empresaDcmsEntrada);
                if(msgValidaEmpresaDcmsEntrada!=null && !msgValidaEmpresaDcmsEntrada.trim().isEmpty()) {
                	log.info("linha invalida; msgValidaEmpresaDcmsEntrada:[{}]; empresaDcmsEntrada:[{}]", 
                			msgValidaEmpresaDcmsEntrada, 
                			empresaDcmsEntrada
                			);
                	//linha invalida = retornar motivo
                	empresaDcmsEntrada.setRetorno(Constantes.ERRO);
                	empresaDcmsEntrada.setMensagemRetorno(msgValidaEmpresaDcmsEntrada);
                }
                
            	listEmpresaDcms.add(empresaDcmsEntrada);

            } //while (iterator.hasNext())
            
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException", e);
            return null;
        } catch (IOException e) {
            log.error("IOException", e);
            return null;
        }

		//listEmpresaDcms = new ArrayList<EmpresaDcmsEntrada>();
		
		//201810051920 - esert - fake
		EmpresaDcmsEntrada empresaDcmsCnpjRepetido = new EmpresaDcmsEntrada();
        empresaDcmsCnpjRepetido.setCnpj("12.061.697/0001-90");
        empresaDcmsCnpjRepetido.setEmpDcms("353768");
		listEmpresaDcms.add(empresaDcmsCnpjRepetido);
		
		//201810051920 - esert - fake
		EmpresaDcmsEntrada empresaDcmsComCodDcms = new EmpresaDcmsEntrada();
        empresaDcmsComCodDcms.setCnpj("25.332.122/0001-06");
        empresaDcmsComCodDcms.setEmpDcms("123456");
		listEmpresaDcms.add(empresaDcmsComCodDcms);

        EmpresaDcmsEntrada empresaComCnpjInvalido = new EmpresaDcmsEntrada();
        empresaComCnpjInvalido.setCnpj("25.332.000/0000-00");
        empresaComCnpjInvalido.setEmpDcms("123456");
        listEmpresaDcms.add(empresaComCnpjInvalido);

        EmpresaDcmsEntrada empresaValida = new EmpresaDcmsEntrada();
        empresaValida.setCnpj("28.984.416/0001-00");
        empresaValida.setEmpDcms("123456");
        listEmpresaDcms.add(empresaValida);

		return listEmpresaDcms;
	}

	//201810081550 - esert - COR-861:Servico Rec/Ret Plan
	private String validaEmpresaDcmsEntrada(EmpresaDcmsEntrada empresaDcmsEntrada) {
		String retorno = "";
		if(empresaDcmsEntrada==null) {
			retorno += ",OBJETO NULO";
		} else {
			if(empresaDcmsEntrada.getCnpj()==null){
				retorno += ",CNPJ_CLIENTE NULO";
			} else {
				if(empresaDcmsEntrada.getCnpj().trim().isEmpty()){
					retorno += ",CNPJ_CLIENTE VAZIO";
				}
			}
			if(empresaDcmsEntrada.getEmpDcms()==null){
				retorno += ",CAD_DCMS NULO";
			} else {
				if(empresaDcmsEntrada.getEmpDcms().trim().isEmpty()){
					retorno += ",CAD_DCMS VAZIO";
				}
			}
		}
		if(retorno!=null) {
			if(!retorno.trim().isEmpty()) {
				retorno = retorno.substring(1,retorno.length());
			}
		}
		return retorno;
	}

	//201810051900 - esert - COR-861:Serviço - Receber / Retornar Planilha
	private File convertListEmpresaDCMSToXLSRes(List<EmpresaDcmsEntrada> listEmpresaDCMSRes, String nomeArquivo) {
		final File file = new File(nomeArquivo);

        log.info("convertListEmpresaDCMSToXLSRes - ini");

        try {

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(nomeArquivo); //usa nome do arquivo para nomear a aba/sheet

            HSSFRow rowhead = sheet.createRow((short) 0);
            //CD_VENDA //01
            rowhead.createCell(0).setCellValue("CD_VENDA");//01
            //CD_EMPRESA //02
            rowhead.createCell(1).setCellValue("CD_EMPRESA");//02
            //CNPJ_CLIENTE //03
            rowhead.createCell(2).setCellValue("CNPJ_CLIENTE");//03
            //RAZAO_SOCIAL_CLIENTE //04
            rowhead.createCell(3).setCellValue("RAZAO_SOCIAL_CLIENTE");//04
            //CAD_DCMS //05
            rowhead.createCell(4).setCellValue("CAD_DCMS");//05
            //RETORNO //06 //"OK" ou "ERRO"
            rowhead.createCell(5).setCellValue("RETORNO");//06
            //MENSAGEM_RETORNO //07 //"CNPJ Duplicado na Base"; "CNPJ não encontrado"; "Erro no serviço"...
            rowhead.createCell(6).setCellValue("MENSAGEM_RETORNO");//07

            int rowCount = 0;

            for (EmpresaDcmsEntrada item : listEmpresaDCMSRes) {

                rowCount++;
                Row row = sheet.createRow(rowCount);

                //CD_VENDA //01
                row.createCell(0).setCellValue(Objects.toString(item.getCdVenda(),""));//01
                //CD_EMPRESA //02
                row.createCell(1).setCellValue(Objects.toString(item.getCdEmpresa(),""));//02
                //CNPJ_CLIENTE //03
                row.createCell(2).setCellValue(Objects.toString(item.getCnpj(),""));//03
                //RAZAO_SOCIAL_CLIENTE //04
                row.createCell(3).setCellValue(Objects.toString(item.getRazaoSocial(),""));//04
                //CAD_DCMS //05
                row.createCell(4).setCellValue(Objects.toString(item.getEmpDcms(),""));//05
                //RETORNO //06 //"OK" ou "ERRO"
                row.createCell(5).setCellValue(Objects.toString(item.getRetorno(),""));//06
                //MENSAGEM_RETORNO //07 //"CNPJ Duplicado na Base"; "CNPJ não encontrado"; "Erro no serviço"...
                row.createCell(6).setCellValue(Objects.toString(item.getMensagemRetorno(),""));//07

            }

            log.info("for (... item : listEmpresaDCMSRes); rowCount=[" + rowCount + "]");

            ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
            workbook.write(fileOut);
            //fileOut.close();
            workbook.close();

            @SuppressWarnings("resource")
			FileOutputStream fos = new FileOutputStream(file);
            fos.write(fileOut.toByteArray());
            
            log.info("convertListEmpresaDCMSToXLSRes - fim");
            return file;

        } catch (Exception e) {
            log.info("convertListEmpresaDCMSToXLSRes - erro");
            String msgErro = "gerarCorretoraTotalVidasPMEXLS; Erro; Message:[" + e.getMessage() + "]; Cause:[" + e.getCause() + "]";
            //throw new Exception(msgErro, e);
            log.error(msgErro, e);
            return null;
        }
	}
	
	//201810051810 - esert

	private String convertFileToBase64(File fileXLSRes) {
		String arquivoBase64 = null;
		byte[] byteArray = new byte[(int)fileXLSRes.length()];
		FileInputStream fis;
		try {
			fis = new FileInputStream(fileXLSRes);
			fis.read(byteArray);
			Base64.getEncoder().encodeToString(byteArray);
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException", e);
		} catch (IOException e) {
			log.error("IOException", e);
		}
		return arquivoBase64;
	}

}
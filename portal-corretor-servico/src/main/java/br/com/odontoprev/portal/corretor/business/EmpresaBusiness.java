package br.com.odontoprev.portal.corretor.business;

import br.com.odontoprev.portal.corretor.dao.*;
import br.com.odontoprev.portal.corretor.dto.*;
import br.com.odontoprev.portal.corretor.model.*;
import br.com.odontoprev.portal.corretor.service.EmpresaService;
import br.com.odontoprev.portal.corretor.util.XlsEmpresa;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.ManagedBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static br.com.odontoprev.portal.corretor.util.Constantes.*;

@ManagedBean
public class EmpresaBusiness {

    private static final Log log = LogFactory.getLog(EmpresaBusiness.class);

    @Autowired
    TipoEnderecoDAO tipoEnderecoDao;

    @Autowired
    EnderecoDAO enderecoDao;

    @Autowired
    EmpresaDAO empresaDao;

    @Autowired
    PlanoDAO planoDao;

    @Autowired
    VendaDAO vendaDao;

    @Autowired
    ForcaVendaDAO forcaVendaDAO;

    @Autowired
    EmpresaContatoDAO empresaContatoDAO;

    @Autowired
    XlsEmpresa xlsEmpresa; //201806281739 - esert - COR-348 rollback vendapme
    
    @Autowired
    private EmpresaService empresaService;

    @Transactional(rollbackFor = {Exception.class})
    //201806120946 - gmazzi@zarp - rollback vendapme //201806261820 - esert - merge from sprint6_rollback
    public EmpresaResponse salvarEmpresaEnderecoVenda(Empresa empresa) {
        log.info("salvarEmpresaEnderecoVenda - ini");
        TbodEmpresa tbEmpresa = new TbodEmpresa();

        try {
            TbodEndereco tbEndereco = new TbodEndereco();
            Endereco endereco = empresa.getEnderecoEmpresa();

            tbEndereco.setLogradouro(endereco.getLogradouro());
            tbEndereco.setBairro(endereco.getBairro());
            tbEndereco.setCep(endereco.getCep());
            tbEndereco.setCidade(endereco.getCidade());
            tbEndereco.setNumero(endereco.getNumero());
            tbEndereco.setUf(endereco.getEstado());
            tbEndereco.setComplemento(endereco.getComplemento());

            if (endereco.getTipoEndereco() != null) {
                TbodTipoEndereco tbTipoEndereco = tipoEnderecoDao.findOne(endereco.getTipoEndereco());
                tbEndereco.setTbodTipoEndereco(tbTipoEndereco);
            }

            tbEndereco = enderecoDao.save(tbEndereco);

            tbEmpresa.setCnpj(empresa.getCnpj());
            tbEmpresa.setIncEstadual(empresa.getIncEstadual());
            tbEmpresa.setRamoAtividade(empresa.getRamoAtividade());
            tbEmpresa.setRazaoSocial(empresa.getRazaoSocial());
            tbEmpresa.setNomeFantasia(empresa.getNomeFantasia());
            tbEmpresa.setRepresentanteLegal(empresa.getRepresentanteLegal());
            tbEmpresa.setCpfRepresentante(empresa.getCpfRepresentante()); //201807251530 - esert - COR-513
            tbEmpresa.setContatoEmpresa(empresa.isContatoEmpresa() == true ? SIM : NAO);
            tbEmpresa.setTelefone(empresa.getTelefone());
            tbEmpresa.setCelular(empresa.getCelular());
            tbEmpresa.setEmail(empresa.getEmail());
            tbEmpresa.setCnae(empresa.getCnae());
            tbEmpresa.setTbodEndereco(tbEndereco);
            tbEmpresa = empresaDao.save(tbEmpresa);

            if (empresa.getPlanos() != null && !empresa.getPlanos().isEmpty()) {
                for (Plano plano : empresa.getPlanos()) {
                    TbodPlano tbPlano = new TbodPlano();
                    tbPlano = planoDao.findByCdPlano(plano.getCdPlano());

                    TbodVenda tbVenda = new TbodVenda();
                    tbVenda.setDtVenda(new Date());
                    tbVenda.setFaturaVencimento(empresa.getVencimentoFatura());

                    SimpleDateFormat sdf_ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy"); //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
                    if (empresa.getDataVigencia() != null && !empresa.getDataVigencia().isEmpty()) { //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
                        tbVenda.setDtVigencia(sdf_ddMMyyyy.parse(empresa.getDataVigencia())); //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
                    }
                    if (empresa.getDataMovimentacao() != null && !empresa.getDataMovimentacao().isEmpty()) { //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
                        tbVenda.setDtMovimentacao(sdf_ddMMyyyy.parse(empresa.getDataMovimentacao())); //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
                    }

                    tbVenda.setTbodEmpresa(tbEmpresa);
                    tbVenda.setTbodPlano(tbPlano);
                    vendaDao.save(tbVenda);
                }
            }

            //XlsEmpresa xlsEmpresa = new XlsEmpresa();
            //xlsEmpresa.GerarEmpresaXLS(tbEmpresa, empresa, null);

        } catch (Exception e) {
            log.error("EmpresaServiceImpl :: Erro ao cadastrar empresa. Detalhe: [" + e.getMessage() + "]");
            return new EmpresaResponse(0, "Erro ao cadastrar empresa. Favor, entre em contato com o suporte.");
        }

        log.info("salvarEmpresaEnderecoVenda - fim");
        return new EmpresaResponse(tbEmpresa.getCdEmpresa(), "Empresa cadastrada.");
    }

    @Transactional(rollbackFor = {Exception.class}) //201806281838 - esert - COR-348
    public EmpresaResponse salvarEmpresaEndereco(Empresa empresa, VendaPME vendaPME) {
        log.info("salvarEmpresaEndereco - ini");
        TbodEmpresa tbodEmpresa = new TbodEmpresa();

        try {
            TbodEndereco tbodEndereco = new TbodEndereco();
            Endereco endereco = empresa.getEnderecoEmpresa();

            tbodEndereco.setLogradouro(endereco.getLogradouro());
            tbodEndereco.setBairro(endereco.getBairro());
            tbodEndereco.setCep(endereco.getCep());
            tbodEndereco.setCidade(endereco.getCidade());
            tbodEndereco.setNumero(endereco.getNumero());
            tbodEndereco.setUf(endereco.getEstado());
            tbodEndereco.setComplemento(endereco.getComplemento());

            if (endereco.getTipoEndereco() != null) {
                TbodTipoEndereco tbTipoEndereco = tipoEnderecoDao.findOne(endereco.getTipoEndereco());
                tbodEndereco.setTbodTipoEndereco(tbTipoEndereco);
            }

            tbodEndereco = enderecoDao.save(tbodEndereco);

            /****  contato empresa ****/
            TbodEmpresaContato tbodContatoEmpresa = null;
            if (empresa.getContactEmpresa() != null) {
                tbodContatoEmpresa = new TbodEmpresaContato();
                ContatoEmpresa contatoEmpresa = empresa.getContactEmpresa();

                tbodContatoEmpresa.setCelular(contatoEmpresa.getCelular() == null ? " " : contatoEmpresa.getCelular());
                tbodContatoEmpresa.setEmail(contatoEmpresa.getEmail() == null ? " " : contatoEmpresa.getEmail());
                tbodContatoEmpresa.setNome(contatoEmpresa.getNome() == null ? " " : contatoEmpresa.getNome());
                tbodContatoEmpresa.setTelefone(contatoEmpresa.getTelefone() == null ? " " : contatoEmpresa.getTelefone());
                tbodContatoEmpresa = empresaContatoDAO.save(tbodContatoEmpresa);
                tbodEmpresa.setCdEmpresaContato(tbodContatoEmpresa.getCdContato());
            }

            tbodEmpresa.setCnpj(empresa.getCnpj());
            tbodEmpresa.setIncEstadual(empresa.getIncEstadual());
            tbodEmpresa.setRamoAtividade(empresa.getRamoAtividade());
            tbodEmpresa.setRazaoSocial(empresa.getRazaoSocial());
            tbodEmpresa.setNomeFantasia(empresa.getNomeFantasia());
            tbodEmpresa.setRepresentanteLegal(empresa.getRepresentanteLegal());
            tbodEmpresa.setCpfRepresentante(empresa.getCpfRepresentante()); //201807251530 - esert - COR-513
            tbodEmpresa.setContatoEmpresa(empresa.isContatoEmpresa() == true ? SIM : NAO);
            tbodEmpresa.setTelefone(empresa.getTelefone());
            tbodEmpresa.setCelular(empresa.getCelular());
            tbodEmpresa.setEmail(empresa.getEmail());
            tbodEmpresa.setTbodEndereco(tbodEndereco);
            tbodEmpresa.setCnae(empresa.getCnae());
            tbodEmpresa = empresaDao.save(tbodEmpresa);

            /***  dados forca venda ***/
            //TbodForcaVenda tbodForcaVenda = forcaVendaDAO.findOne(vendaPME.getCdForcaVenda()); //201810051831 - esert - comentado tbodForcaVenda pq nao precisa mais
            //TODO: Remover geracao arq //a geração de arquivo foi movida para metodo TokenAceiteServiceImpl.updateTokenAceite(TokenAceite)
            //xlsEmpresa.GerarEmpresaXLS(tbodEmpresa, empresa, tbodForcaVenda);

        } catch (Exception e) {
            String msgErro = "EmpresaBusiness.salvarEmpresaEndereco :: Erro ao cadastrar empresa; Message:[" + e.getMessage() + "]; Cause:[" + e.getCause() != null ? e.getCause().getMessage() : "NuLL" + "]";
            log.error(msgErro);
            return new EmpresaResponse(0, msgErro);
        }

        log.info("salvarEmpresaEndereco - fim");
        return new EmpresaResponse(tbodEmpresa.getCdEmpresa(), "Empresa cadastrada.");
    }

	public List<EmpresaDcmsEntrada> processarLoteDCMS(List<EmpresaDcmsEntrada> listEmpresaDCMSReq) {

        log.info("processarLoteDCMS() - ini");
        List<EmpresaDcmsEntrada> listEmpresaDcmsRetorno = null;
		
		if(listEmpresaDCMSReq==null) {
            log.info("processarLoteDCMS() listEmpresaDCMSReq==null - erro");
            return null;
		}
		
		listEmpresaDcmsRetorno = new ArrayList<EmpresaDcmsEntrada>();
		
		for(EmpresaDcmsEntrada empresaDcmsReq : listEmpresaDCMSReq) {

            EmpresaDcmsEntrada empresaDcmsRetorno = new EmpresaDcmsEntrada();
			CnpjDados cnpjDados;

			try {

                if (empresaDcmsReq.getRetorno() != null && empresaDcmsReq.getRetorno().equals(ERRO)) {

                    empresaDcmsRetorno.setRazaoSocial(empresaDcmsReq.getRazaoSocial());
                    empresaDcmsRetorno.setCnpj(empresaDcmsReq.getCnpj());
                    empresaDcmsRetorno.setRetorno(ERRO);
                    empresaDcmsRetorno.setMensagemRetorno(empresaDcmsReq.getMensagemRetorno());

                } else {

                    if (empresaDcmsReq.getCnpj() == null){
                        empresaDcmsRetorno.setRetorno(ERRO);
                        empresaDcmsRetorno.setMensagemRetorno("CNPJ NAO INFORMADO");
                        continue;
                    }

                    cnpjDados = empresaService.findDadosEmpresaByCnpj(empresaDcmsReq.getCnpj().replaceAll("[^0-9]", ""));

                    if (cnpjDados.getCdEmpresa() != null) {

                        if (cnpjDados.getEmpDcms() == null) {

                            empresaDcmsReq.setCdEmpresa(cnpjDados.getCdEmpresa());
                            log.info("processarLoteDCMS() - update empresa - ini");
                            EmpresaResponse empresaResponse = empresaService.updateEmpresa(empresaDcmsReq);

                            if (empresaResponse.getId() == 0) {
                                log.info("processarLoteDCMS() - update empresa error");
                                empresaDcmsRetorno.setRetorno(ERRO);
                                empresaDcmsRetorno.setCdEmpresa(cnpjDados.getCdEmpresa());
                                empresaDcmsRetorno.setMensagemRetorno(empresaResponse.getMensagem());

                            } else {

                                log.info("processarLoteDCMS() - update empresa sucesso");
                                empresaDcmsRetorno.setCdEmpresa(cnpjDados.getCdEmpresa());
                                empresaDcmsRetorno.setRetorno(OK);
                                empresaDcmsRetorno.setMensagemRetorno(empresaResponse.getMensagem());
                            }

                            log.info("processarLoteDCMS() - update empresa - fim");

                        } else {

                            log.info("processarLoteDCMS() - Empresa ja possui cd_dcms");
                            empresaDcmsRetorno.setCdEmpresa(cnpjDados.getCdEmpresa());
                            empresaDcmsRetorno.setMensagemRetorno(CNPJ_POSSUI_CD_DCMS);
                            empresaDcmsRetorno.setEmpDcms(cnpjDados.getEmpDcms());
                            empresaDcmsRetorno.setRetorno(ERRO);

                        }

                        } else {

                            log.info("processarLoteDCMS() - Empresa nao encontrada");
                            empresaDcmsRetorno.setRetorno(ERRO);
                            empresaDcmsRetorno.setMensagemRetorno(cnpjDados.getObservacaoLoteDcms());

                        }
                    }

                } catch(Exception e){
                    log.error(e);
                    empresaDcmsRetorno.setRetorno(ERRO);
                    empresaDcmsRetorno.setMensagemRetorno(e.getMessage());
                }

                empresaDcmsRetorno.setCnpj(empresaDcmsReq.getCnpj());
                empresaDcmsRetorno.setRazaoSocial(empresaDcmsReq.getRazaoSocial());
                listEmpresaDcmsRetorno.add(empresaDcmsRetorno);

		}

        log.info("processarLoteDCMS() - fim");
        return listEmpresaDcmsRetorno;
	}

}
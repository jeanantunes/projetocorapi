package br.com.odontoprev.portal.corretor.service.impl;

import static br.com.odontoprev.portal.corretor.enums.StatusVendaEnum.CRIT_ENVIO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.DashboardPropostaDAO;
import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dto.DashboardPropostaPF;
import br.com.odontoprev.portal.corretor.dto.DashboardPropostaPFResponse;
import br.com.odontoprev.portal.corretor.dto.DashboardPropostaPME;
import br.com.odontoprev.portal.corretor.dto.DashboardPropostaPMEResponse;
import br.com.odontoprev.portal.corretor.dto.DashboardResponse;
import br.com.odontoprev.portal.corretor.enums.StatusForcaVendaEnum;
import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
import br.com.odontoprev.portal.corretor.service.DashboardPropostaService;

@Service
public class DashboardPropostaServiceImpl implements DashboardPropostaService {

	private static final Log log = LogFactory.getLog(DashboardPropostaServiceImpl.class);

	@Autowired
	private DashboardPropostaDAO dashboardPropostaDAO;

	@Autowired
	private ForcaVendaDAO forcaVendaDao;

	//201808021440 - esert - COR-496 Corretora / COR-497 ForcaVenda
	@Override
	public DashboardPropostaPMEResponse buscaPropostaPorStatusPME(long status, String cnpjCpf) {

		log.info("[buscaPropostaPorStatusPME]");

		DashboardPropostaPMEResponse response = new DashboardPropostaPMEResponse();
		List<DashboardPropostaPME> propostasPME = new ArrayList<DashboardPropostaPME>();

		try {

			List<Object[]> objects = new ArrayList<Object[]>();

			// findAll
			if (status == 0) {

				if (cnpjCpf.length() > 11) {
					objects = dashboardPropostaDAO.findAllDashboardPropostasPMEByStatusCnpjCOR(0L, cnpjCpf);

				} else {
					objects = dashboardPropostaDAO.findAllDashboardPropostasPMEByStatusCpfFV(0L, cnpjCpf);
				}

			} else {

				if (cnpjCpf.length() > 11) {
					objects = dashboardPropostaDAO.findAllDashboardPropostasPMEByStatusCnpjCOR(status, cnpjCpf);

				} else {
					objects = dashboardPropostaDAO.findAllDashboardPropostasPMEByStatusCpfFV(status, cnpjCpf);
				}
			}

			for (Object object : objects) {
				Object[] obj = (Object[]) object;

				Timestamp ts = (Timestamp) obj[4];  // venda.DT_VENDA //4
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(ts.getTime());
				Date d = cal.getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

				DashboardPropostaPME dashboardPropostaPME = new DashboardPropostaPME();
				dashboardPropostaPME.setCdEmpresa(obj[0] != null ? ((BigDecimal)obj[0]).longValue() : null); // emp.CD_EMPRESA //0
				dashboardPropostaPME.setCnpj(obj[1] != null ? String.valueOf(obj[1]) : ""); // emp.CNPJ //1
				dashboardPropostaPME.setNome(obj[2] != null ? String.valueOf(obj[2]) : ""); // emp.RAZAO_SOCIAL //2
				dashboardPropostaPME.setNomeFantasia(obj[3] != null ? String.valueOf(obj[3]) : ""); // emp.NOME_FANTASIA //3 // COR-488 yalm 201807271221
				dashboardPropostaPME.setDataVenda(sdf.format(d));
				dashboardPropostaPME.setStatusVenda(obj[5] != null ? String.valueOf(obj[5]) : ""); // status.DESCRICAO //5
				dashboardPropostaPME.setCdStatusVenda(obj[6] != null ? ((BigDecimal)obj[6]).longValue() : null); // status.CD_STATUS_VENDA //6 // yalm - 201808012050 - COR-508
				dashboardPropostaPME.setValor(obj[7] != null ? Double.parseDouble(obj[7].toString()) : 0); // SUM(plano.valor_anual + plano.valor_mensal) //7
				propostasPME.add(dashboardPropostaPME);
			}
			
			// Muda status venda e monta lista de criticas retornadas do dcms
			// 201803131540
	//		this.mudarStatusVendaPME(propostasPME);
			
			response.setDashboardPropostasPME(propostasPME);
			
		} catch (Exception e) {
			log.error("Erro ao buscar Propostas PME por status :: Detalhe: [" + e.getMessage() + "]");
			return new DashboardPropostaPMEResponse();
		}

		return response;
	}

	private void mudarStatusVendaPME(List<DashboardPropostaPME> propostasPME) {
		
		log.info("mudarStatusVendaPME");
		
		List<Object[]> objects = dashboardPropostaDAO.buscarCriticasPME();

		for (DashboardPropostaPME proposta : propostasPME) {
			
			for (Object object : objects) {
				Object[] obj = (Object[]) object;

				Long cdEmpresaRetorno = obj[0] != null ? new Long(String.valueOf(obj[0])) : null;
				
				if(proposta.getCdEmpresa().equals(cdEmpresaRetorno)) {
					proposta.setStatusVenda(obj[1] != null ? String.valueOf(obj[1]) : "");
				}
			}
		}
		
	}

	//201808021440 - esert - COR-496 Corretora / COR-497 ForcaVenda
	@Override
	public DashboardPropostaPFResponse buscaPropostaPorStatusPF(long status, String cnpjCpf) {

		log.info("[buscaPropostaPorStatusPF]");

		DashboardPropostaPFResponse response = new DashboardPropostaPFResponse();
		List<DashboardPropostaPF> propostasPF = new ArrayList<DashboardPropostaPF>();

		try {

			List<Object[]> objects = new ArrayList<Object[]>();

			// findAll
			if (status == 0) {

				if (cnpjCpf.length() > 11) {
					objects = dashboardPropostaDAO.findDashboardPropostaPFByStatusCnpjCOR(0L, cnpjCpf);
				} else {
					objects = dashboardPropostaDAO.findDashboardPropostaPFByStatusCpfFV(0L, cnpjCpf);
				}

			} else {

				if (cnpjCpf.length() > 11) {
					objects = dashboardPropostaDAO.findDashboardPropostaPFByStatusCnpjCOR(status, cnpjCpf);
				} else {
					objects = dashboardPropostaDAO.findDashboardPropostaPFByStatusCpfFV(status, cnpjCpf);
				}
			}

			for (Object object : objects) {
				Object[] obj = (Object[]) object;

				DashboardPropostaPF dashboardPropostaPF = new DashboardPropostaPF();
				dashboardPropostaPF.setCdVenda(obj[0] != null ? new Long(String.valueOf(obj[0])) : null);
				dashboardPropostaPF.setCpf(obj[1] != null ? String.valueOf(obj[1]) : "");
				dashboardPropostaPF.setPropostaDcms(obj[2] != null ? String.valueOf(obj[2]) : "");
				dashboardPropostaPF.setNome(obj[3] != null ? String.valueOf(obj[3]) : "");
				dashboardPropostaPF.setStatusVenda(obj[4] != null ? String.valueOf(obj[4]) : "");
				dashboardPropostaPF.setCdStatusVenda(obj[5] != null ? ((BigDecimal)obj[5]).longValue() : null); // yalm - 201808012050 - COR-508
				dashboardPropostaPF.setValor(obj[6] != null ? Double.parseDouble(obj[6].toString()) : 0);
				propostasPF.add(dashboardPropostaPF);
			}

			// Muda status venda e monta lista de criticas retornadas do dcms
			// 201803130907
//			this.mudarStatusVendaCriticadoPF(propostasPF);

			response.setDashboardPropostasPF(propostasPF);

		} catch (Exception e) {
			log.error("Erro ao buscar Propostas PF por status :: Detalhe: [" + e.getMessage() + "]");
			return new DashboardPropostaPFResponse();
		}

		return response;
	}

	private void mudarStatusVendaCriticadoPF(List<DashboardPropostaPF> propostasPF) {

		log.info("mudarStatusVendaCriticadoPF");
		
		List<Object[]> objects = dashboardPropostaDAO.buscaPorCriticaPF();
		
		for (DashboardPropostaPF proposta : propostasPF) {
			
			if (proposta.getPropostaDcms() == null || "".equals(proposta.getPropostaDcms())) {
				// Muda apenas o retorno, nao atualiza na base
				proposta.setStatusVenda(CRIT_ENVIO.getDescricao());
				
			} else {
			
				for (Object object : objects) {
					Object[] obj = (Object[]) object;
	
					String nrAtendimento = obj[0] != null ? String.valueOf(obj[0]) : null;
					
					if(proposta.getPropostaDcms().equals(nrAtendimento)) {
						
						proposta.setDsErroRegistro(obj[1] != null ? String.valueOf(obj[1]) : "");
						
						if (proposta.getDsErroRegistro() != null && !"".equals(proposta.getDsErroRegistro())) {
							List<String> criticas = new ArrayList<String>();
							String[] criticasArr = proposta.getDsErroRegistro().split("/");
							for (String critica : criticasArr) {
								criticas.add(critica);
							}
							proposta.setCriticas(criticas);
						}
						break;
					}
				}
			}
		}
		

//		List<Object[]> objects = new ArrayList<Object[]>();
//
//		for (DashboardPropostaPF proposta : propostasPF) {
//
//			if (proposta.getPropostaDcms() == null || "".equals(proposta.getPropostaDcms())) {
//				// Muda apenas o retorno, nao atualiza na base
//				proposta.setStatusVenda(CRIT_ENVIO.getDescricao());
//			} else {
//
//				objects = dashboardPropostaDAO.buscaPorCriticaPFporNumeroAtendimento(proposta.getPropostaDcms());
//
//				if (objects != null && !objects.isEmpty()) {
//
//					// proposta.setStatusVenda("Critica negocio");
//
//					for (Object object : objects) {
//						proposta.setDsErroRegistro(object != null ? String.valueOf(object) : "");
//
//						if (proposta.getDsErroRegistro() != null && !"".equals(proposta.getDsErroRegistro())) {
//							List<String> criticas = new ArrayList<String>();
//							String[] criticasArr = proposta.getDsErroRegistro().split("/");
//							for (String critica : criticasArr) {
//								criticas.add(critica);
//							}
//							proposta.setCriticas(criticas);
//						}
//					}
//				}
//			}
//		}

	}

	@Override
	public DashboardPropostaPFResponse buscaPorCriticaPF(String cnpj, String cpf) {

		log.info("[buscaPorCriticaPF]");

		DashboardPropostaPFResponse response = new DashboardPropostaPFResponse();
		List<DashboardPropostaPF> propostasPF = new ArrayList<DashboardPropostaPF>();

		try {

			List<Object[]> objects = new ArrayList<Object[]>();

			// findAll
			if (cnpj != null && cpf != null) {
				log.info("[buscaTodasPorCriticaPF]");
				objects = dashboardPropostaDAO.buscaTodasPorCriticaPF(cnpj, cpf);
			}
			if (cnpj == null && cpf != null) {
				log.info("[buscaPorCriticaPFporCPF]");
				objects = dashboardPropostaDAO.buscaPorCriticaPFporCPF(cpf);
			}
			if (cnpj != null && cpf == null) {
				log.info("[buscaPorCriticaPFporCNPJ]");
				objects = dashboardPropostaDAO.buscaPorCriticaPFporCNPJ(cnpj);
			}

			for (Object object : objects) {
				Object[] obj = (Object[]) object;

				DashboardPropostaPF dashboardPropostaPF = new DashboardPropostaPF();
				dashboardPropostaPF.setAtendimento(obj[0] != null ? (String) obj[0] : "");
				dashboardPropostaPF.setDsErroRegistro(obj[1] != null ? String.valueOf(obj[1]) : "");
				dashboardPropostaPF.setCodOdonto(obj[2] != null ? String.valueOf(obj[2]) : "");
				dashboardPropostaPF.setNome(obj[3] != null ? String.valueOf(obj[3]) : "");
				dashboardPropostaPF.setNrImportacao(obj[4] != null ? String.valueOf(obj[4]) : "");
				dashboardPropostaPF.setForca(obj[5] != null ? String.valueOf(obj[5]) : "");
				dashboardPropostaPF.setCpf(obj[6] != null ? String.valueOf(obj[6]) : "");
				dashboardPropostaPF.setCorretora(obj[7] != null ? String.valueOf(obj[7]) : "");
				dashboardPropostaPF.setCnpj(obj[8] != null ? String.valueOf(obj[8]) : "");

				propostasPF.add(dashboardPropostaPF);
			}

			response.setDashboardPropostasPF(propostasPF);

		} catch (Exception e) {
			log.error("Erro ao buscar Critica PF por status :: Detalhe: [" + e.getMessage() + "]");
			return new DashboardPropostaPFResponse();
		}

		return response;
	}

	@Override
	public DashboardPropostaPMEResponse buscaPorCriticaPME(String cnpj, String cpf) {

		log.info("[buscaPorCriticaPF]");

		DashboardPropostaPMEResponse response = new DashboardPropostaPMEResponse();
		List<DashboardPropostaPME> propostasPME = new ArrayList<DashboardPropostaPME>();

		try {

			List<Object[]> objects = new ArrayList<Object[]>();

			// findAll
			if (cnpj != null && cpf != null) {
				objects = dashboardPropostaDAO.buscaTodasPorCriticaPME(cnpj, cpf);
			}
			if (cnpj == null && cpf != null) {
				objects = dashboardPropostaDAO.buscaPorCriticaPMEporCPF(cpf);
			}
			if (cnpj != null && cpf == null) {
				objects = dashboardPropostaDAO.buscaPorCriticaPMEporCNPJ(cnpj);
			}

			for (Object object : objects) {
				Object[] obj = (Object[]) object;
				DashboardPropostaPME dashboardPropostaPME = new DashboardPropostaPME();
				dashboardPropostaPME.setCnpj(obj[0] != null ? String.valueOf(obj[0]) : "");
				dashboardPropostaPME.setCorretora(obj[1] != null ? String.valueOf(obj[1]) : "");
				dashboardPropostaPME.setCpf(obj[2] != null ? String.valueOf(obj[2]) : "");
				dashboardPropostaPME.setForca(obj[3] != null ? String.valueOf(obj[3]) : "");
				dashboardPropostaPME.setCdEmpresa(obj[4] != null ? new Long(String.valueOf(obj[4])) : null);
				dashboardPropostaPME.setEmpDcms(obj[5] != null ? String.valueOf(obj[5]) : "");
				dashboardPropostaPME.setNome(obj[6] != null ? String.valueOf(obj[6]) : "");
				dashboardPropostaPME.setCriticas(obj[7] != null ? String.valueOf(obj[7]) : "");
				
				propostasPME.add(dashboardPropostaPME);
			}

			response.setDashboardPropostasPME(propostasPME);

		} catch (Exception e) {
			log.error("Erro ao buscar Critica PF por status :: Detalhe: [" + e.getMessage() + "]");
			return new DashboardPropostaPMEResponse();
		}

		return response;
	}

	@Override
	public DashboardResponse buscarForcaVendaAguardandoAprovacaoByCdEmpresa(long cdCorretora) {

		log.info("[buscarForcaVendaAguardandoAprovacaoByCdEmpresa]");

		DashboardResponse dashboardResponse = new DashboardResponse();

		try {

			long cdStatusForcaVenda = StatusForcaVendaEnum.AGUARDANDO_APRO.getCodigo();

			List<TbodForcaVenda> forcaVendas = forcaVendaDao
					.findByTbodStatusForcaVendaCdStatusForcaVendasAndTbodCorretoraCdCorretora(cdStatusForcaVenda,
							cdCorretora);

			if (forcaVendas == null) {
				dashboardResponse.setCountForcaVendaAprovacao(0);
			} else {
				dashboardResponse.setCountForcaVendaAprovacao(forcaVendas.size());
			}

		} catch (Exception e) {
			log.error("Erro ao buscar quantidade ForcaVenda Aguardando Aprovacao por Corretora :: Detalhe: ["
					+ e.getMessage() + "]");
			return new DashboardResponse();
		}

		return dashboardResponse;

	}

}

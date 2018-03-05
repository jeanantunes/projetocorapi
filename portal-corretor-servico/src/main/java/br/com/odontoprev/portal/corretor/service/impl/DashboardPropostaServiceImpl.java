package br.com.odontoprev.portal.corretor.service.impl;

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

	@Override
	public DashboardPropostaPMEResponse buscaPropostaPorStatusPME(long status, String cnpj) {

		log.info("[buscaPropostaPorStatusPME]");

		DashboardPropostaPMEResponse response = new DashboardPropostaPMEResponse();
		List<DashboardPropostaPME> propostasPME = new ArrayList<DashboardPropostaPME>();
		
		try {

			List<Object[]> objects = new ArrayList<Object[]>();

			// findAll
			if (status == 0) {

				objects = dashboardPropostaDAO.findAllDashboardPropostasPME(cnpj);

			} else {

				objects = dashboardPropostaDAO.findDashboardPropostaPMEByStatus(status, cnpj);
			}

			for (Object object : objects) {
				Object[] obj = (Object[]) object;

				Timestamp ts = (Timestamp) obj[3];
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(ts.getTime());
				Date d = cal.getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

				DashboardPropostaPME dashboardPropostaPME = new DashboardPropostaPME();
				dashboardPropostaPME.setCdEmpresa(obj[0] != null ? new Long(String.valueOf(obj[0])) : null);
				dashboardPropostaPME.setCnpj(obj[1] != null ? String.valueOf(obj[1]) : "");
				dashboardPropostaPME.setNome(obj[2] != null ? String.valueOf(obj[2]) : "");
				dashboardPropostaPME.setDataVenda(sdf.format(d));
				dashboardPropostaPME.setStatusVenda(obj[4] != null ? String.valueOf(obj[4]) : "");
				dashboardPropostaPME.setValor(obj[5] != null ? Double.parseDouble(obj[5].toString()) : 0);
				propostasPME.add(dashboardPropostaPME);
			}
			response.setDashboardPropostasPME(propostasPME);
		} catch (Exception e) {
			log.error("Erro ao buscar Propostas PME por status :: Detalhe: [" + e.getMessage() + "]");
			return new DashboardPropostaPMEResponse();
		}

		return response;
	}

	@Override
	public DashboardPropostaPFResponse buscaPropostaPorStatusPF(long status, String cpf) {

		log.info("[buscaPropostaPorStatusPF]");

		DashboardPropostaPFResponse response = new DashboardPropostaPFResponse();
		List<DashboardPropostaPF> propostasPF = new ArrayList<DashboardPropostaPF>();
		
		try {
			
			List<Object[]> objects = new ArrayList<Object[]>();

			// findAll
			if (status == 0) {

				objects = dashboardPropostaDAO.findAllDashboardPropostasPF(cpf);

			} else {

				objects = dashboardPropostaDAO.findDashboardPropostaPFByStatus(status, cpf);
			}

			for (Object object : objects) {
				Object[] obj = (Object[]) object;

				DashboardPropostaPF dashboardPropostaPF = new DashboardPropostaPF();
				dashboardPropostaPF.setCdVenda(obj[0] != null ? new Long(String.valueOf(obj[0])) : null);
				dashboardPropostaPF.setCpf(obj[1] != null ? String.valueOf(obj[1]) : "");
				dashboardPropostaPF.setPropostaDcms(obj[2] != null ? String.valueOf(obj[2]) : "");
				dashboardPropostaPF.setNome(obj[3] != null ? String.valueOf(obj[3]) : "");
				dashboardPropostaPF.setStatusVenda(obj[4] != null ? String.valueOf(obj[4]) : "");
				dashboardPropostaPF.setValor(obj[5] != null ? Double.parseDouble(obj[5].toString()) : 0);
				propostasPF.add(dashboardPropostaPF);
			}

			response.setDashboardPropostasPF(propostasPF);

		} catch (Exception e) {
			log.error("Erro ao buscar Propostas PF por status :: Detalhe: [" + e.getMessage() + "]");
			return new DashboardPropostaPFResponse();
		}

		return response;
	}
	@Override
	public DashboardPropostaPFResponse buscaPorCriticaPF(String cnpj, String cpf) {

		log.info("[buscaPorCriticaPF]");

		DashboardPropostaPFResponse response = new DashboardPropostaPFResponse();
		List<DashboardPropostaPF> propostasPF = new ArrayList<DashboardPropostaPF>();
		
		try {
			
			List<Object[]> objects = new ArrayList<Object[]>();

			// findAll
			if(cnpj!= null && cpf != null) {
				log.info("[buscaTodasPorCriticaPF]");
				objects = dashboardPropostaDAO.buscaTodasPorCriticaPF(cnpj, cpf);
			}if(cnpj == null && cpf != null) {
				log.info("[buscaPorCriticaPFporCPF]");
				objects = dashboardPropostaDAO.buscaPorCriticaPFporCPF(cpf);
			}if(cnpj!= null && cpf == null) {
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
			if(cnpj!= null && cpf != null) {
				objects = dashboardPropostaDAO.buscaTodasPorCriticaPME(cnpj, cpf);
			}if(cnpj == null && cpf != null) {
				objects = dashboardPropostaDAO.buscaPorCriticaPMEporCPF(cpf);
			}if(cnpj!= null && cpf == null) {
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
			
			List<TbodForcaVenda> forcaVendas = forcaVendaDao.findByTbodStatusForcaVendaCdStatusForcaVendasAndTbodCorretoraCdCorretora(cdStatusForcaVenda, cdCorretora);
			
			if(forcaVendas == null) {
				dashboardResponse.setCountForcaVendaAprovacao(0);
			}
			else {
				dashboardResponse.setCountForcaVendaAprovacao(forcaVendas.size());
			}
			
		} catch (Exception e) {
			log.error("Erro ao buscar quantidade ForcaVenda Aguardando Aprovacao por Corretora :: Detalhe: [" + e.getMessage() + "]");
			return new DashboardResponse();
		}
		
		return dashboardResponse;
		
	}

}

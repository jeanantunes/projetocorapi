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
import br.com.odontoprev.portal.corretor.dto.DashboardPropostaPF;
import br.com.odontoprev.portal.corretor.dto.DashboardPropostaPFResponse;
import br.com.odontoprev.portal.corretor.dto.DashboardPropostaPME;
import br.com.odontoprev.portal.corretor.dto.DashboardPropostaPMEResponse;
import br.com.odontoprev.portal.corretor.service.DashboardPropostaService;

@Service
public class DashboardPropostaServiceImpl implements DashboardPropostaService {

	private static final Log log = LogFactory.getLog(DashboardPropostaServiceImpl.class);

	@Autowired
	private DashboardPropostaDAO dashboardPropostaDAO;

	@Override
	public DashboardPropostaPMEResponse buscaPropostaPorStatusPME(long status) {

		log.info("[buscaPropostaPorStatusPME]");

		DashboardPropostaPMEResponse response = new DashboardPropostaPMEResponse();
		List<DashboardPropostaPME> propostasPME = new ArrayList<DashboardPropostaPME>();
		
		try {

			List<Object[]> objects = new ArrayList<Object[]>();

			// findAll
			if (status == 0) {

				objects = dashboardPropostaDAO.findAllDashboardPropostasPME();

			} else {

				objects = dashboardPropostaDAO.findDashboardPropostaPMEByStatus(status);
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
	public DashboardPropostaPFResponse buscaPropostaPorStatusPF(long status) {

		log.info("[buscaPropostaPorStatusPF]");

		DashboardPropostaPFResponse response = new DashboardPropostaPFResponse();
		List<DashboardPropostaPF> propostasPF = new ArrayList<DashboardPropostaPF>();
		
		try {
			
			List<Object[]> objects = new ArrayList<Object[]>();

			// findAll
			if (status == 0) {

				objects = dashboardPropostaDAO.findAllDashboardPropostasPF();

			} else {

				objects = dashboardPropostaDAO.findDashboardPropostaPFByStatus(status);
			}

			for (Object object : objects) {
				Object[] obj = (Object[]) object;

				DashboardPropostaPF dashboardPropostaPF = new DashboardPropostaPF();
				dashboardPropostaPF.setCdVenda(obj[0] != null ? new Long(String.valueOf(obj[0])) : null);
				dashboardPropostaPF.setCpf(obj[1] != null ? String.valueOf(obj[1]) : "");
				dashboardPropostaPF.setPropostaDcms(obj[2] != null ? String.valueOf(obj[2]) : "");
				dashboardPropostaPF.setNome(obj[3] != null ? String.valueOf(obj[3]) : "");
				dashboardPropostaPF.setStatusVenda(obj[4] != null ? String.valueOf(obj[4]) : "");

				propostasPF.add(dashboardPropostaPF);
			}

			response.setDashboardPropostasPF(propostasPF);

		} catch (Exception e) {
			log.error("Erro ao buscar Propostas PF por status :: Detalhe: [" + e.getMessage() + "]");
			return new DashboardPropostaPFResponse();
		}

		return response;
	}

}

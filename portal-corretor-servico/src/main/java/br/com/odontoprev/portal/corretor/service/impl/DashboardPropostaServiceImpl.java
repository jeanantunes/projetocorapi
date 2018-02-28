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
import br.com.odontoprev.portal.corretor.dto.DashboardPropostaPME;
import br.com.odontoprev.portal.corretor.dto.DashboardPropostaResponse;
import br.com.odontoprev.portal.corretor.service.DashboardPropostaService;

@Service
public class DashboardPropostaServiceImpl implements DashboardPropostaService {
	
	private static final Log log = LogFactory.getLog(DashboardPropostaServiceImpl.class);

	@Autowired
	private DashboardPropostaDAO dashboardPropostaDAO;
	
	@Override
	public DashboardPropostaResponse buscaPropostaPorStatusPME(long status) {
		
		log.info("[buscaPropostaPorStatusPME]");
		
		DashboardPropostaResponse response = new DashboardPropostaResponse();
		List<DashboardPropostaPME> propostasPME = new ArrayList<DashboardPropostaPME>();
		try {
			List<Object[]> objects = new ArrayList<Object[]>();
			objects = dashboardPropostaDAO.dashboardPropostaByStatus(status);
			
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
			
		}catch (Exception e) {
			log.error("Erro ao buscar Propostas PME por status :: Detalhe: [" + e.getMessage() + "]");
			return new DashboardPropostaResponse();
		}
		
		return response;
	}

	@Override
	public DashboardPropostaResponse buscaPropostaPorStatusPF(long status) {
		return null;
	}
	
	

}

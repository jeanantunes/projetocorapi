package br.com.odontoprev.portal.corretor.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.PropostaDAO;
import br.com.odontoprev.portal.corretor.dto.DashBoardProposta;
import br.com.odontoprev.portal.corretor.dto.PropostaPF;
import br.com.odontoprev.portal.corretor.dto.PropostaPME;
import br.com.odontoprev.portal.corretor.dto.PropostasDashBoard;
import br.com.odontoprev.portal.corretor.model.TbodPlano;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.service.PropostaService;

@Service
public class PropostaServiceImpl implements PropostaService {

	private static final Log log = LogFactory.getLog(PropostaServiceImpl.class);

	@Autowired
	PropostaDAO propostaDAO;

	@Override
	public PropostasDashBoard findPropostasByFiltro(DashBoardProposta dashBoardProposta) throws ParseException {
		
		log.info("[PropostaServiceImpl::findPropostasByFiltro]");
		
		List<TbodVenda> vendas = propostaDAO.propostasByFiltro(dashBoardProposta.getDtInicio(), dashBoardProposta.getDtFim(), dashBoardProposta.getCdCorretora(), dashBoardProposta.getCdForcaVenda());
		
		PropostasDashBoard propostasDashBoard = new PropostasDashBoard();
		PropostaPME propostaPME = new PropostaPME();
		PropostaPF propostaPF = new PropostaPF();
		
		List<TbodVenda> vendasPME = new ArrayList<TbodVenda>();
		List<TbodVenda> vendasPF = new ArrayList<TbodVenda>();
		for (TbodVenda venda : vendas) {
			if(venda.getTbodForcaVenda() != null &&  venda.getTbodEmpresa() == null) {
				vendasPF.add(venda);
			}else if(venda.getTbodForcaVenda() == null &&  venda.getTbodEmpresa() != null) {
				vendasPME.add(venda);
			}
		}
		
		// PME
		long valorSomaPME = 0L;
		int quantPME = 0;
		for(TbodVenda tbodVendaPME : vendasPME) {
			TbodPlano plano = tbodVendaPME.getTbodPlano();
			BigDecimal valor = plano.getValorMensal().add(plano.getValorAnual()); // FIXME - Retornar somente um valor
			long valorLong = valor.longValueExact();
			
			valorSomaPME += valorLong;
			quantPME++;
		}
		propostaPME.setValor(new BigDecimal(valorSomaPME));
		propostaPME.setQuantidade(quantPME);
		
		// PF
		long valorSomaPF = 0L;
		int quantPF = 0;
		for(TbodVenda tbodVendaPF : vendasPF) {
			TbodPlano plano = tbodVendaPF.getTbodPlano();
			BigDecimal valor = plano.getValorMensal().add(plano.getValorAnual()); // FIXME - Retornar somente um valor
			long valorLong = valor.longValueExact();
				
			valorSomaPF += valorLong;
			quantPF++;
		}
		propostaPF.setValor(new BigDecimal(valorSomaPF));
		propostaPF.setQuantidade(quantPF);
		
		propostasDashBoard.setPropostaPF(propostaPF);
		propostasDashBoard.setPropostaPME(propostaPME);
		return propostasDashBoard;
	}

}

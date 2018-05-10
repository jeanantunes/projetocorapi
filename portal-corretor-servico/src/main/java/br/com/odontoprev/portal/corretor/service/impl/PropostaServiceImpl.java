package br.com.odontoprev.portal.corretor.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.PropostaDAO;
import br.com.odontoprev.portal.corretor.dao.TxtImportacaoDAO;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.dao.ViewCorSumarioVendaDAO;
import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.DashBoardProposta;
import br.com.odontoprev.portal.corretor.dto.PropostaCritica;
import br.com.odontoprev.portal.corretor.dto.PropostaPF;
import br.com.odontoprev.portal.corretor.dto.PropostaPME;
import br.com.odontoprev.portal.corretor.dto.PropostasDashBoard;
import br.com.odontoprev.portal.corretor.dto.TxtImportacao;
import br.com.odontoprev.portal.corretor.dto.VendaCritica;
import br.com.odontoprev.portal.corretor.model.TbodPlano;
import br.com.odontoprev.portal.corretor.model.TbodResponsavelContratual;
import br.com.odontoprev.portal.corretor.model.TbodTxtImportacao;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.model.TbodVendaVida;
import br.com.odontoprev.portal.corretor.model.TbodVida;
import br.com.odontoprev.portal.corretor.model.ViewCorSumarioVenda;
import br.com.odontoprev.portal.corretor.service.PropostaService;
import br.com.odontoprev.portal.corretor.util.ConvertObjectUtil;

@Service
public class PropostaServiceImpl implements PropostaService {

	private static final Log log = LogFactory.getLog(PropostaServiceImpl.class);

	@Autowired
	PropostaDAO propostaDAO;
	
	@Autowired
//	@Qualifier("ViewCorSumarioVendaDAOImpl")
	ViewCorSumarioVendaDAO vendaDAO;

	@Autowired
	VendaDAO vendaDAOCritica;

	@Autowired
	TxtImportacaoDAO txtImportacaoDAO;
	
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
		long quantidadeVidasPME = 0L;
		for(TbodVenda tbodVendaPME : vendasPME) {
			TbodPlano plano = tbodVendaPME.getTbodPlano();
			BigDecimal valor = plano.getValorMensal().add(plano.getValorAnual()); // FIXME - Retornar somente um valor
			long valorLong = valor.longValueExact();			
			int qtdVidas = tbodVendaPME.getTbodVendaVidas().size();
			//Date dtVenda = tbodVendaPME.getDtVenda();
			quantidadeVidasPME+=qtdVidas;
			valorSomaPME += valorLong*qtdVidas;
			quantPME++;
		}
		propostaPME.setValor(new BigDecimal(valorSomaPME));
		propostaPME.setQuantidade(Long.valueOf(quantPME));
		propostaPME.setQuantidadeVidas(quantidadeVidasPME);
		
		
		// PF
		long valorSomaPF = 0L;
		int quantPF = 0;
		long quantidadeVidasPF = 0L;
		for(TbodVenda tbodVendaPF : vendasPF) {
			TbodPlano plano = tbodVendaPF.getTbodPlano();
			BigDecimal valor = plano.getValorMensal().add(plano.getValorAnual()); // FIXME - Retornar somente um valor
			long valorLong = valor.longValueExact();
			int qtdVidas = tbodVendaPF.getTbodVendaVidas().size();
			quantidadeVidasPF+=qtdVidas;
			valorSomaPF += valorLong*qtdVidas;
			quantPF++;
		}
		propostaPF.setValor(new BigDecimal(valorSomaPF));
		propostaPF.setQuantidade(quantPF);
		propostaPF.setQuantidadeVidas(quantidadeVidasPF);
		
		propostasDashBoard.setPropostaPF(propostaPF);
		propostasDashBoard.setPropostaPME(propostaPME);
		return propostasDashBoard;
	}
	
	
	public List<ViewCorSumarioVenda> findViewCorSumarioByFiltro(DashBoardProposta dashBoardProposta) throws ParseException {
		return vendaDAO.viewCorSumarioVendasByFiltro(dashBoardProposta.getDtInicio(), dashBoardProposta.getDtFim(), dashBoardProposta.getCdCorretora(), dashBoardProposta.getCdForcaVenda(), dashBoardProposta.getCpf(), dashBoardProposta.getCnpj(), dashBoardProposta.getDtVenda());		
	}

	//201805081530 - esert
	@Override
	public PropostaCritica buscarPropostaCritica(String cd_venda) {
		PropostaCritica propostaCritica = null;

		long longCdVenda = -1;
		try {
			longCdVenda = Long.parseLong(cd_venda);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		TbodVenda tbodVenda = vendaDAOCritica.findOne(longCdVenda);

		if(tbodVenda != null) {
			propostaCritica = new PropostaCritica();
			
			VendaCritica vendaCritica = new VendaCritica();

			//deve ser a primeira atribuicao //201805091200 - esert
			vendaCritica = ConvertObjectUtil.translateTbodVendaToVendaCritica(tbodVenda, vendaCritica);
						
			
			vendaCritica.setDadosBancariosVenda(
					ConvertObjectUtil.translateTbodVendaDadosBancariosToDadosBancariosVenda(
							tbodVenda));
			
			
			vendaCritica.setPropostaDcms(tbodVenda.getPropostaDcms());

			
			TbodResponsavelContratual tbodResponsavelContratual = tbodVenda.getTbodResponsavelContratual();
			vendaCritica.setResponsavelContratual(
					ConvertObjectUtil.translateTbodResponsavelContratualToResponsavelContratual(
							tbodResponsavelContratual));
						
			
			List<TbodVendaVida> tbodVendaVidas = tbodVenda.getTbodVendaVidas();
			if(tbodVendaVidas!=null) {
				List<Beneficiario> vidas = new ArrayList<Beneficiario>();
				for (TbodVendaVida tbodVendaVida : tbodVendaVidas) {
					if(tbodVendaVida.getTbodVida()!=null) {
						TbodVida tbodVida = tbodVendaVida.getTbodVida();
						if(tbodVida!=null) {
							vidas.add(
									ConvertObjectUtil.translateTbodVidaToBeneficiario(
											tbodVida));
						}
					}
				}
				vendaCritica.setTitulares(vidas);
				
			}
			
			TbodPlano tbodPlano = tbodVenda.getTbodPlano();
			vendaCritica.setPlano(
					ConvertObjectUtil.translateTbodPlanoToPlano(
							tbodPlano));
			
			List<TxtImportacao> listTxtImportacao = new ArrayList<TxtImportacao>();
			List<TbodTxtImportacao> listTbodTxtImportacao = txtImportacaoDAO.findByNrAtendimento(tbodVenda.getPropostaDcms());
			for (TbodTxtImportacao tbodTxtImportacao : listTbodTxtImportacao) {
				listTxtImportacao.add(
						ConvertObjectUtil.translateTbodTxtImportacaoToTxtImportacao(
								tbodTxtImportacao));
			}
			vendaCritica.setCriticas(listTxtImportacao);
			
			propostaCritica.setVenda(vendaCritica);
		}
		
		return propostaCritica;
	}

}

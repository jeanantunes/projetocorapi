package br.com.odontoprev.portal.corretor.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import br.com.odontoprev.portal.corretor.dto.DadosBancariosVenda;
import br.com.odontoprev.portal.corretor.dto.DashBoardProposta;
import br.com.odontoprev.portal.corretor.dto.Plano;
import br.com.odontoprev.portal.corretor.dto.PropostaCritica;
import br.com.odontoprev.portal.corretor.dto.PropostaPF;
import br.com.odontoprev.portal.corretor.dto.PropostaPME;
import br.com.odontoprev.portal.corretor.dto.PropostasDashBoard;
import br.com.odontoprev.portal.corretor.dto.ResponsavelContratual;
import br.com.odontoprev.portal.corretor.dto.TxtImportacao;
import br.com.odontoprev.portal.corretor.dto.Venda2;
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
			
			Venda2 venda = new Venda2();
			
			venda.setDadosBancariosVenda(
					ConvertObjectUtil.translateTbodVendaDadosBancariosToDadosBancarios(
							tbodVenda));
			
			venda.setPropostaDcms(tbodVenda.getPropostaDcms());

			venda.setTipoPagamento(tbodVenda.getTipoPagamento());
			
			TbodResponsavelContratual tbodResponsavelContratual = tbodVenda.getTbodResponsavelContratual();
			if(tbodResponsavelContratual!=null) {
				ResponsavelContratual responsavelContratual = new ResponsavelContratual();
	//			(tbodResponsavelContratual.getCdResponsavelContratual());
				responsavelContratual.setCelular(tbodResponsavelContratual.getCelular());
				responsavelContratual.setCpf(tbodResponsavelContratual.getCpf());
				if(tbodResponsavelContratual.getDataNascimento()!=null) {
					responsavelContratual.setDataNascimento((new SimpleDateFormat("dd/MM/yyyy")).format(tbodResponsavelContratual.getDataNascimento()));
				}
				responsavelContratual.setEmail(tbodResponsavelContratual.getEmail());
				responsavelContratual.setNome(tbodResponsavelContratual.getNome());
				responsavelContratual.setSexo(tbodResponsavelContratual.getSexo());
				
				responsavelContratual.setEndereco(
						ConvertObjectUtil.translateTbodEnderecoToEnderecoProposta(
								tbodResponsavelContratual.getTbodEndereco()));
				
				venda.setResponsavelContratual(responsavelContratual);
			}
			
			propostaCritica.setVenda(venda);
			
			List<TbodVendaVida> vendaVidas = tbodVenda.getTbodVendaVidas();
			if(vendaVidas!=null) {
				List<Beneficiario> vidas = new ArrayList<Beneficiario>();
				for (TbodVendaVida tbodVendaVida : vendaVidas) {
					if(tbodVendaVida.getTbodVida()!=null) {
						TbodVida tbodVida = tbodVendaVida.getTbodVida();
						if(tbodVida!=null) {
							vidas.add(
									ConvertObjectUtil.translateTbodVidaToBeneficiario(
											tbodVida));
						}
					}
				}
				propostaCritica.setVidas(vidas);
			}
			
			TbodPlano tbodPlano = tbodVenda.getTbodPlano();
			if(tbodPlano!=null) {
				Plano plano = new Plano();
				plano.setCdPlano(tbodPlano.getCdPlano());
//				plano.setCdPlano(Long.parseLong(tbodPlano.getCodigo()));
				plano.setDescricao(tbodPlano.getNomePlano());
				plano.setSigla(tbodPlano.getSigla());
				plano.setTitulo(tbodPlano.getTitulo());
				if(tbodPlano.getTbodTipoPlano()!=null) {
					plano.setTipo(String.valueOf(tbodPlano.getTbodTipoPlano().getCdTipoPlano()));
				}				
				if(tbodPlano.getValorMensal()!=null) {
					plano.setValor(tbodPlano.getValorMensal().toString());
				}
//				plano.setValor(tbodPlano.getValorAnual());
				propostaCritica.setPlano(plano);
			}
			
			List<TxtImportacao> listTxtImportacao = new ArrayList<TxtImportacao>();
			List<TbodTxtImportacao> listTbodTxtImportacao = txtImportacaoDAO.findByNrAtendimento(venda.getPropostaDcms());
			for (TbodTxtImportacao tbodTxtImportacao : listTbodTxtImportacao) {
				TxtImportacao txtImportacao = new TxtImportacao();
				txtImportacao.setNrAtendimento(tbodTxtImportacao.getNrAtendimento());
				txtImportacao.setDsErroRegistro(tbodTxtImportacao.getDsErroRegistro());
				listTxtImportacao.add(txtImportacao);
			}
			propostaCritica.setCriticas(listTxtImportacao);
		}
		
		return propostaCritica;
	}

}

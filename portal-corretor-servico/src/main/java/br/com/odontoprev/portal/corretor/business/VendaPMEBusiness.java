package br.com.odontoprev.portal.corretor.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.ManagedBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.dto.EmpresaResponse;
import br.com.odontoprev.portal.corretor.dto.Plano;
import br.com.odontoprev.portal.corretor.dto.TokenAceite;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaPME;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;
import br.com.odontoprev.portal.corretor.service.TokenAceiteService;

@ManagedBean
public class VendaPMEBusiness {

	private static final Log log = LogFactory.getLog(VendaPMEBusiness.class);
	
	@Autowired
	EmpresaBusiness empresaBusiness;
	
	@Autowired
	VendaPFBusiness vendaPFBusiness;
	
	@Autowired
	CorretoraBusiness corretoraBusiness;
	
	@Autowired
	TokenAceiteService tokenAceiteService;
	
	@Transactional(rollbackFor={Exception.class}) //201806120946 - gmazzi@zarp - rollback vendapme //201806261820 - esert - merge from sprint6_rollback
	public VendaResponse salvarVendaPMEComEmpresasPlanosTitularesDependentes(VendaPME vendaPME) {

		log.info("[salvarVendaPMEComEmpresasPlanosTitularesDependentes]");

		Venda venda = new Venda();
		VendaResponse vendaResponse = null;

		try {		

			for (Empresa empresa : vendaPME.getEmpresas()) {
				
				this.buscarDadosCorretoraParaArquivoEmpresa(vendaPME.getCdForcaVenda(), empresa);
				
				EmpresaResponse empresaResponse = empresaBusiness.salvarEmpresaEndereco(empresa,vendaPME );

				if(empresaResponse.getId() == 0) {
					throw new Exception(empresaResponse.getMensagem());
				}
				
				for (Plano plano : empresa.getPlanos()) {
					
					venda = new Venda();
					venda.setTitulares(new ArrayList<Beneficiario>());
					venda.setCdEmpresa(empresaResponse.getId());
					
					venda.setCdPlano(plano.getCdPlano());
//					List<Plano> planos = new ArrayList<Plano>();
//					planos.add(plano);
//					venda.setPlanos(planos);
					
					venda.setDataVenda(new Date());
					venda.setCdStatusVenda(1L); //TODO Alterar para status aguardando aprovacao para ser atualizado posteriormente 05.03.18 as 15:36
					venda.setFaturaVencimento(empresa.getVencimentoFatura());
										
					SimpleDateFormat sdf_ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy"); //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
					if(empresa.getDataVigencia()!=null && !empresa.getDataVigencia().isEmpty()) { //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
						venda.setDataVigencia(sdf_ddMMyyyy.parse(empresa.getDataVigencia())); //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
					}
					if(empresa.getDataMovimentacao()!=null && !empresa.getDataMovimentacao().isEmpty()) { //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
						venda.setDataMovimentacao(sdf_ddMMyyyy.parse(empresa.getDataMovimentacao())); //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
					}

					venda.setCdForcaVenda(vendaPME.getCdForcaVenda());
					
					for (Beneficiario titular : vendaPME.getTitulares()) {
					
						if(
							titular.getCnpj().equals(empresa.getCnpj())
							&&
							titular.getCdPlano() == plano.getCdPlano()
						){
							venda.getTitulares().add(titular);
						}
						
					} //for (Beneficiario titular : titulares)

					vendaResponse = vendaPFBusiness.salvarVendaComTitularesComDependentes(venda,Boolean.FALSE);
					
					log.info("salvarVendaPMEComEmpresasPlanosTitularesDependentes :: cadastrada vendaResponse.getId:[" + vendaResponse.getId() + "].");
					
				} //for (Plano plano : empresa.getPlanos())
				
			} //for (Empresa empresa : empresas)
			
			
			if(vendaResponse.getId() != 0){
				//Chamada servico token
				TokenAceite tokenAceite = new TokenAceite();
				tokenAceite.setCdVenda(vendaResponse.getId());
				tokenAceiteService.addTokenAceite(tokenAceite);
			}
			
		} catch (Exception e) {
			log.error("salvarVendaPMEComEmpresasPlanosTitularesDependentes :: Erro ao cadastrar venda CdVenda:[" + venda.getCdVenda() + "]. Detalhe: [" + e.getMessage() + "]");
			
			String msg = "Erro ao cadastrar venda PME";
			return new VendaResponse(0, msg);
		}

		return vendaResponse;
	}

	private void buscarDadosCorretoraParaArquivoEmpresa(Long cdForcaVenda, Empresa empresa) {
		
		//Busca dados da corretora para arquivo de empresa - jalves 090320181011
		if(cdForcaVenda != null && cdForcaVenda != 0) {
			Corretora corretora = corretoraBusiness.buscarCorretoraPorForcaVenda(cdForcaVenda);
			
			if(corretora != null) {
				empresa.setCnpjCorretora(corretora.getCnpj());
				empresa.setNomeCorretora(corretora.getRazaoSocial());
			}
		}
	}

}

package br.com.odontoprev.portal.corretor.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.ManagedBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.dto.ArquivoContratacao;
import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.EmailForcaVendaCorretora;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.dto.EmpresaResponse;
import br.com.odontoprev.portal.corretor.dto.Plano;
import br.com.odontoprev.portal.corretor.dto.TokenAceite;
import br.com.odontoprev.portal.corretor.dto.TokenAceiteResponse;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaPME;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;
import br.com.odontoprev.portal.corretor.service.ArquivoContratacaoService;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;
import br.com.odontoprev.portal.corretor.service.TokenAceiteService;
import br.com.odontoprev.portal.corretor.util.Constantes;

@ManagedBean
public class VendaPMEBusiness {

	private static final Logger log = LoggerFactory.getLogger(VendaPMEBusiness.class);
	
	@Autowired
	EmpresaBusiness empresaBusiness;
	
	@Autowired
	VendaPFBusiness vendaPFBusiness;
	
	@Autowired
	CorretoraBusiness corretoraBusiness;
	
	@Autowired
	TokenAceiteService tokenAceiteService;
	
	@Autowired
	ArquivoContratacaoService arquivoContratacaoService; //201808232052 - esert - COR-617 servico gerar pdf contratacao pme

	@Autowired
	private ForcaVendaService forcaVendaService; //201810101622 - esert - COR-883:Serviço - Alterar POST/vendapme Validar E-mail (Com TDD 200)

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

					//venda.setCdStatusVenda(1L); //TODO Alterar para status aguardando aprovacao para ser atualizado posteriormente 05.03.18 as 15:36
					//venda.setCdStatusVenda(Constantes.STATUS_VENDA_ENVIADO); //1 //201807051747 - esert - (COR-357 Serviço - Definição de Códigos/Status)
					venda.setCdStatusVenda(Constantes.STATUS_VENDA_AGUARDANDO);

					venda.setFaturaVencimento(empresa.getVencimentoFatura());
										
					SimpleDateFormat sdf_ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy"); //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
					if(empresa.getDataVigencia()!=null && !empresa.getDataVigencia().isEmpty()) { //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
						venda.setDataVigencia(sdf_ddMMyyyy.parse(empresa.getDataVigencia())); //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
					}
					if(empresa.getDataMovimentacao()!=null && !empresa.getDataMovimentacao().isEmpty()) { //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
						venda.setDataMovimentacao(sdf_ddMMyyyy.parse(empresa.getDataMovimentacao())); //201806141829 - esert - (COR-303 Modificar Servico /vendapme)
					}

					venda.setCdForcaVenda(vendaPME.getCdForcaVenda());
					
					venda.setPlataforma(vendaPME.getPlataforma()); //201807201122 - esert - COR-431 
					
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
				//Chama servico gerar e salvar pdf pme
				ArquivoContratacao arquivoContratacao = arquivoContratacaoService.createPdfPmePorVenda(vendaResponse.getId());
				if(
					arquivoContratacao!=null 
					&& 
					arquivoContratacao.getArquivoBase64()!=null 
					&& 
					!arquivoContratacao.getArquivoBase64().trim().isEmpty()
				) {
					log.info(String.format("PDF Detalhe Contratação PME gerado com sucesso e tamanho:[%d]", arquivoContratacao.getTamanhoArquivo()));
				} else {
					log.info("Falha ao gerar PDF Detalhe Contratação PME.");					
				}
				
				//Chamada servico token
				TokenAceite tokenAceite = new TokenAceite();
				tokenAceite.setCdVenda(vendaResponse.getId());
				TokenAceiteResponse tokenAceiteResponse = tokenAceiteService.addTokenAceite(tokenAceite);
				if(tokenAceiteResponse.getId()==HttpStatus.OK.value()) {
					log.info(String.format("TokenAceite gerado com sucesso para CdVenda:[%s] CdToken:[%s]", tokenAceiteResponse.getCdVenda(), tokenAceiteResponse.getCdToken()));
				} else {
					log.info(String.format("Falha ao gerar TokenAceite com Mensagem:[%s]", tokenAceiteResponse.getMensagem()));					
				}
			}
			
		} catch (Exception e) {
			log.error("salvarVendaPMEComEmpresasPlanosTitularesDependentes :: Erro ao cadastrar venda CdVenda:[" + venda.getCdVenda() + "]. Detalhe: [" + e.getMessage() + "]");
			
			String msg = "Erro ao cadastrar venda PME";
			return new VendaResponse(0, msg);
		}

		return vendaResponse;
	}

	@Transactional(rollbackFor={Exception.class}) //201806281838 - esert - COR-348
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

	//201810101622 - esert - COR-883:Serviço - Alterar POST/vendapme Validar E-mail (Com TDD 200)
	public VendaResponse verificarErro(VendaPME vendaPME) {
		log.info("verificarErro - ini");
		VendaResponse vendaResponse = null; //nenhum erro de validacao
		EmailForcaVendaCorretora emailForcaVendaCorretora = forcaVendaService.findByCdForcaVendaEmail(vendaPME.getCdForcaVenda());
		if(emailForcaVendaCorretora == null) {
			log.error("emailForcaVendaCorretora == null para vendaPME.getCdForcaVenda():[{}]", vendaPME.getCdForcaVenda());
		} else {
			if(vendaPME.getEmpresas()!=null) {
				for(Empresa empresa: vendaPME.getEmpresas()){
					if(empresa.getEmail()!=null) {
						if(empresa.getEmail().equals(emailForcaVendaCorretora.getEmailForcaVenda())) {
							vendaResponse = new VendaResponse(
									0, 
									String.format("proibido email empresa igual email forca venda [%s].", 
											emailForcaVendaCorretora.getEmailForcaVenda()
											), 
									true
									);
							log.info("verificarErro - fim com msg");
							return vendaResponse; //ficha suja
						}
						if(empresa.getEmail().equals(emailForcaVendaCorretora.getEmailCorretora())) {
							vendaResponse = new VendaResponse(
									0, 
									String.format("proibido email empresa igual email corretora [%s].", 
											emailForcaVendaCorretora.getEmailCorretora()
											), 
									true
									);
							log.info("verificarErro - fim com msg");
							return vendaResponse; //ficha suja
						}
					} //if(empresa.getEmail()!=null)
				} //for(Empresa empresa: vendaPME.getEmpresas())
			} //if(vendaPME.getEmpresas()!=null)
		} //else //if(emailForcaVendaCorretora == null)
		log.info("verificarErro - fim ok sem msg");
		return null; //se chegou ate aqui entao volta null = ficha limpa
	}

}

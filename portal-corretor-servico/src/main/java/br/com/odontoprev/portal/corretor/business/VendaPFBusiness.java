package br.com.odontoprev.portal.corretor.business;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.ManagedBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.odontoprev.portal.corretor.dao.EmpresaDAO;
import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dao.PlanoDAO;
import br.com.odontoprev.portal.corretor.dao.StatusVendaDAO;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioResponse;
import br.com.odontoprev.portal.corretor.dto.Proposta;
import br.com.odontoprev.portal.corretor.dto.PropostaResponse;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;
import br.com.odontoprev.portal.corretor.model.TbodEmpresa;
import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
import br.com.odontoprev.portal.corretor.model.TbodPlano;
import br.com.odontoprev.portal.corretor.model.TbodStatusVenda;
import br.com.odontoprev.portal.corretor.model.TbodVenda;

@ManagedBean
public class VendaPFBusiness {

	private static final Log log = LogFactory.getLog(VendaPFBusiness.class);
	
	@Autowired
	VendaDAO vendaDao;
	
	@Autowired
	EmpresaDAO empresaDao;
	
	@Autowired
	PlanoDAO planoDao;
	
	@Autowired
	ForcaVendaDAO forcaVendaDao;

	@Autowired
	StatusVendaDAO statusVendaDao;
	
	@Autowired
	BeneficiarioBusiness beneficiarioBusiness;
	
	public VendaResponse salvarVendaComTitularesComDependentes(Venda venda) {

		log.info("[salvarVendaPFComTitularesComDependentes]");

		TbodVenda tbVenda = new TbodVenda();

		try {		
			
			if(venda != null) {
				if(venda.getCdVenda() != null) {
					tbVenda = vendaDao.findOne(venda.getCdVenda());					
				}
			}

			if(tbVenda == null) {
				throw new Exception("Venda CdVenda:[" + venda.getCdVenda() + "] não existe!");
			}
			
			tbVenda.setCdVenda(venda.getCdVenda());
			
			if(venda.getCdEmpresa() != null) {
				TbodEmpresa tbodEmpresa = empresaDao.findOne(venda.getCdEmpresa());
				tbVenda.setTbodEmpresa(tbodEmpresa);
			}

			if(venda.getCdPlano() != null) {
				TbodPlano tbodPlano = planoDao.findByCdPlano(venda.getCdPlano());
				tbVenda.setTbodPlano(tbodPlano);
			}

			if(venda.getCdForcaVenda() != null) {
				TbodForcaVenda tbodForcaVenda = forcaVendaDao.findOne(venda.getCdForcaVenda()); 
				tbVenda.setTbodForcaVenda(tbodForcaVenda);
			}
			
			tbVenda.setDtVenda(venda.getDataVenda());
			
			//TbodVendaVida tbodVendaVida = null;
			//tbVenda.getTbodVendaVida().setCdVendaVida((Long) null);
			
			if(venda.getCdStatusVenda() != null) {
				TbodStatusVenda tbodStatusVenda = statusVendaDao.findOne(venda.getCdStatusVenda());
				tbVenda.setTbodStatusVenda(tbodStatusVenda);
			}
			
			tbVenda.setFaturaVencimento(venda.getFaturaVencimento());
			
			tbVenda = vendaDao.save(tbVenda);
			
			for (Beneficiario titular : venda.getTitulares()) {
				titular.setCdVenda(tbVenda.getCdVenda());
			}

			BeneficiarioResponse beneficiarioResponse = beneficiarioBusiness.salvarTitularComDependentes(venda.getTitulares());
			
			if(beneficiarioResponse.getId() == 0) {
				throw new Exception(beneficiarioResponse.getMensagem());
			}
			
		} catch (Exception e) {
			log.error("salvarVendaPFComTitularesComDependentes :: Erro ao cadastrar venda CdVenda:[" + venda.getCdVenda() + "]. Detalhe: [" + e.getMessage() + "]");
			
			String msg = "Erro ao cadastrar venda ";
			if(venda.getCdVenda() != null) {
				msg += ", CdVenda:["+ venda.getCdVenda() +"]";
			} else {
				msg += ", CdVenda:[null]";
			}
			if(venda.getCdEmpresa() != null) {
				msg += ", CdEmpresa:["+ venda.getCdEmpresa() +"]";
			} else {
				msg += ", CdEmpresa:[null]";
			}
			if(venda.getCdForcaVenda() != null) {
				msg += ", CdForcaVenda:["+ venda.getCdForcaVenda() +"]";
			} else {
				msg += ", CdForcaVenda:[null]";
			}
			if(venda.getCdPlano() != null) {
				msg += ", CdPlano:["+ venda.getCdPlano() +"].";
			} else {
				msg += ", CdPlano:[null].";
			}
			return new VendaResponse(0, msg);
		}

		return new VendaResponse(tbVenda.getCdVenda(), "Venda cadastrada CdVenda:["+ tbVenda.getCdVenda() +"].");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String chamarWSLegadoPropostaPOST(Proposta proposta){

		String URLAPI_HOST = "https://api-it1.odontoprev.com.br:8243/dcss";
		String URLAPI_SERV = "/vendas/1.0/proposta";
				
		PropostaResponse propostaResponse = new RestTemplate().postForObject(
				(URLAPI_HOST + URLAPI_SERV), 
				proposta, 
				PropostaResponse.class
				);
		
		if(propostaResponse == null) {
			return "Login não encontrado";
		}

		return propostaResponse.getCodigoProposta();
	}

}

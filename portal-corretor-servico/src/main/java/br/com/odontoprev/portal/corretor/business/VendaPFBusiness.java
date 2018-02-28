package br.com.odontoprev.portal.corretor.business;

import java.util.ArrayList;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import br.com.odontoprev.portal.corretor.dao.EmpresaDAO;
import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dao.PlanoDAO;
import br.com.odontoprev.portal.corretor.dao.StatusVendaDAO;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioProposta;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioResponse;
import br.com.odontoprev.portal.corretor.dto.CorretoraProposta;
import br.com.odontoprev.portal.corretor.dto.DadosBancariosProposta;
import br.com.odontoprev.portal.corretor.dto.EnderecoProposta;
import br.com.odontoprev.portal.corretor.dto.Proposta;
import br.com.odontoprev.portal.corretor.dto.PropostaResponse;
import br.com.odontoprev.portal.corretor.dto.TipoCobrancaProposta;
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

	private RestTemplate restTemplate = null;
	
	@PostConstruct
	private void init() {		
		if(restTemplate  == null)
			restTemplate = new RestTemplate();
	}
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
		PropostaResponse propostaResponse = null;
		
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
			
			//Dados Bancarios
			if(venda.getTipoConta() != null) {
				tbVenda.setTipoConta(venda.getTipoConta());
			}
			if(venda.getBanco() != null) {
				tbVenda.setBanco(venda.getBanco());
			}
			if(venda.getAgencia() != null) {
				String ag = (venda.getAgencia().replace("-", "")).substring(0,venda.getAgencia().length()-1);
				String agDV = (venda.getAgencia().replace("-", "")).substring(venda.getAgencia().length()-1);
				tbVenda.setAgencia(ag);
				tbVenda.setAgenciaDv(agDV);
			}
			if(venda.getConta() != null) {
				String c = (venda.getConta().replace("-", "")).substring(0,venda.getConta().length()-1);
				String cDv = (venda.getConta().replace("-", "")).substring(venda.getConta().length()-1);
				tbVenda.setConta(c);
				tbVenda.setContaDv(cDv);
			}
			if(venda.getTipoPagamento() != null) {
				tbVenda.setTipoPagamento(venda.getTipoPagamento());
			}
			tbVenda = vendaDao.save(tbVenda);
			
			for (Beneficiario titular : venda.getTitulares()) {
				titular.setCdVenda(tbVenda.getCdVenda());
			}

			BeneficiarioResponse beneficiarioResponse = beneficiarioBusiness.salvarTitularComDependentes(venda.getTitulares());
			
			if(beneficiarioResponse.getId() == 0) {
				throw new Exception(beneficiarioResponse.getMensagem());
			}
			
			Proposta proposta = atribuirVendaPFParaProposta(venda);
						
			Gson gson = new Gson();
			String propostaJson = gson.toJson(proposta);			
			log.info("chamarWSLegadoPropostaPOST; propostaJson:[" + propostaJson + "];");

			propostaResponse = chamarWSLegadoPropostaPOST(proposta);
			if(propostaResponse != null) {
				log.info("chamarWSLegadoPropostaPOST; propostaResponse:[" + propostaResponse.getNumeroProposta() + "]");
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

		return new VendaResponse(tbVenda.getCdVenda(), "Venda cadastrada CdVenda:["+ tbVenda.getCdVenda() +"]; NumeroProposta:[" + propostaResponse.getNumeroProposta() + "].");
	}

	private Proposta atribuirVendaPFParaProposta(Venda venda) {
		Proposta proposta = new Proposta();

		TbodForcaVenda tbodForcaVenda = forcaVendaDao.findOne(venda.getCdForcaVenda()); 
		
		proposta.setCorretora(new CorretoraProposta());
		proposta.getCorretora().setCodigo(tbodForcaVenda.getTbodCorretora().getCdCorretora());
		proposta.getCorretora().setCnpj(tbodForcaVenda.getTbodCorretora().getCnpj());
		proposta.getCorretora().setNome(tbodForcaVenda.getTbodCorretora().getNome());

		proposta.setCodigoEmpresaDCMS("CodigoEmpresaDCMS"); //TODO tem na PF ?
		proposta.setCodigoCanalVendas((long)225712); //TODO tem na PF ?		
		proposta.setCodigoUsuario((long)225713); //TODO de onde ?
		
		proposta.setTipoCobranca(new TipoCobrancaProposta());
		proposta.getTipoCobranca().setCodigo((long)231248); //Informar o que vem do serviço da empresa
		proposta.getTipoCobranca().setSigla("BO"); //BO = Boleto / DA = Debito automatico
		
		proposta.setDadosBancarios(new DadosBancariosProposta());
		if(venda.getBanco() != null) {
			proposta.getDadosBancarios().setCodigoBanco(venda.getBanco());
		}
		if(venda.getAgencia() != null) {
			String ag = (venda.getAgencia().replace("-", "")).substring(0,venda.getAgencia().length()-1);
			String agDV = (venda.getAgencia().replace("-", "")).substring(venda.getAgencia().length()-1);
			proposta.getDadosBancarios().setAgencia(ag); 
			proposta.getDadosBancarios().setAgenciaDV(agDV); 
		}
		if(venda.getTipoConta() != null) {
			proposta.getDadosBancarios().setTipoConta(venda.getTipoConta());
			String c = (venda.getConta().replace("-", "")).substring(0,venda.getConta().length()-1);
			String cDv = (venda.getConta().replace("-", "")).substring(venda.getConta().length()-1);
			proposta.getDadosBancarios().setConta(c);
			proposta.getDadosBancarios().setContaDV(cDv);
		}

		proposta.setBeneficiarios(new ArrayList<>());
		
		for (Beneficiario titular : venda.getTitulares()) {
			
			BeneficiarioProposta beneficiarioPropostaTitular = new BeneficiarioProposta();
			beneficiarioPropostaTitular.setNome(titular.getNome());
			beneficiarioPropostaTitular.setCpf(titular.getCpf());
			beneficiarioPropostaTitular.setSexo(titular.getSexo());
			beneficiarioPropostaTitular.setDataNascimento(titular.getDataNascimento());
			beneficiarioPropostaTitular.setNomeMae(titular.getNomeMae());
			beneficiarioPropostaTitular.setCelular(titular.getCelular());
			beneficiarioPropostaTitular.setEmail(titular.getEmail());
			beneficiarioPropostaTitular.setCodigoPlano(venda.getCdPlano().toString());
			beneficiarioPropostaTitular.setTitular(true); //TRUE PARA DEPENDENTE
			
			beneficiarioPropostaTitular.setEndereco(new EnderecoProposta());
			beneficiarioPropostaTitular.getEndereco().setCep(titular.getEndereco().getCep());
			beneficiarioPropostaTitular.getEndereco().setLogradouro(titular.getEndereco().getLogradouro());
			beneficiarioPropostaTitular.getEndereco().setNumero(titular.getEndereco().getNumero());
			beneficiarioPropostaTitular.getEndereco().setComplemento(titular.getEndereco().getComplemento());
			beneficiarioPropostaTitular.getEndereco().setBairro(titular.getEndereco().getBairro());
			beneficiarioPropostaTitular.getEndereco().setCidade(titular.getEndereco().getCidade());
			beneficiarioPropostaTitular.getEndereco().setEstado(titular.getEndereco().getEstado());
			
			proposta.getBeneficiarios().add(beneficiarioPropostaTitular);
			
			for (Beneficiario dependente : titular.getDependentes()) {

				BeneficiarioProposta beneficiarioPropostaDependente = new BeneficiarioProposta();
				beneficiarioPropostaDependente.setNome(dependente.getNome());
				beneficiarioPropostaDependente.setCpf(dependente.getCpf());
				beneficiarioPropostaDependente.setSexo(dependente.getSexo());
				beneficiarioPropostaDependente.setDataNascimento(dependente.getDataNascimento());
				beneficiarioPropostaDependente.setNomeMae(dependente.getNomeMae());
				beneficiarioPropostaDependente.setCelular(dependente.getCelular());
				beneficiarioPropostaDependente.setEmail(dependente.getEmail());
				beneficiarioPropostaDependente.setCodigoPlano(venda.getCdPlano().toString());
				beneficiarioPropostaDependente.setTitular(false); //FALSE PARA DEPENDENTE
				
				beneficiarioPropostaDependente.setEndereco(new EnderecoProposta());
				beneficiarioPropostaDependente.getEndereco().setCep(titular.getEndereco().getCep());
				beneficiarioPropostaDependente.getEndereco().setLogradouro(titular.getEndereco().getLogradouro());
				beneficiarioPropostaDependente.getEndereco().setNumero(titular.getEndereco().getNumero());
				beneficiarioPropostaDependente.getEndereco().setComplemento(titular.getEndereco().getComplemento());
				beneficiarioPropostaDependente.getEndereco().setBairro(titular.getEndereco().getBairro());
				beneficiarioPropostaDependente.getEndereco().setCidade(titular.getEndereco().getCidade());
				beneficiarioPropostaDependente.getEndereco().setEstado(titular.getEndereco().getEstado());
				
				proposta.getBeneficiarios().add(beneficiarioPropostaTitular);

			} //for (Beneficiario dependente : titular.getDependentes())
			
		} //for (Beneficiario titular : venda.getTitulares())

		return proposta;
	}

	@SuppressWarnings({ })
	private PropostaResponse chamarWSLegadoPropostaPOST(Proposta proposta){

		String URLAPI = "https://api-it1.odontoprev.com.br:8243/dcss/vendas/1.0/proposta";
				
		ResponseEntity<PropostaResponse> propostaRet = new RestTemplate().postForEntity(
			URLAPI, 
			proposta, 
			PropostaResponse.class
		);
		
		if(propostaRet != null) {
			log.info("chamarWSLegadoPropostaPOST; propostaRet.getStatusCode():[" + propostaRet.getStatusCode() + "];");
		}

		if(propostaRet == null 
			|| (propostaRet.getStatusCode() == HttpStatus.FORBIDDEN)
			|| (propostaRet.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
			|| (propostaRet.getStatusCode() == HttpStatus.BAD_REQUEST)
		) {
			return null;
		}
				
		return propostaRet.getBody();
	}

}

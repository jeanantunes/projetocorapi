package br.com.odontoprev.portal.corretor.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.DadosBancariosVenda;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.dto.Endereco;
import br.com.odontoprev.portal.corretor.dto.EnderecoProposta;
import br.com.odontoprev.portal.corretor.dto.JsonContatoEmpresaPME;
import br.com.odontoprev.portal.corretor.dto.JsonDependentesPF;
import br.com.odontoprev.portal.corretor.dto.JsonEmpresaPME;
import br.com.odontoprev.portal.corretor.dto.JsonEnderecoPME;
import br.com.odontoprev.portal.corretor.dto.JsonPlanoPME;
import br.com.odontoprev.portal.corretor.dto.JsonTitularesPF;
import br.com.odontoprev.portal.corretor.dto.JsonTitularesPME;
import br.com.odontoprev.portal.corretor.dto.JsonVendaPF;
import br.com.odontoprev.portal.corretor.dto.JsonVendaPME;
import br.com.odontoprev.portal.corretor.dto.Plano;
import br.com.odontoprev.portal.corretor.dto.ResponsavelContratual;
import br.com.odontoprev.portal.corretor.dto.TxtImportacao;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaCritica;
import br.com.odontoprev.portal.corretor.dto.VendaPME;
import br.com.odontoprev.portal.corretor.model.TbodEndereco;
import br.com.odontoprev.portal.corretor.model.TbodPlano;
import br.com.odontoprev.portal.corretor.model.TbodResponsavelContratual;
import br.com.odontoprev.portal.corretor.model.TbodTxtImportacao;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.model.TbodVida;

@Service
public class ConvertObjectUtil {

	public String ConvertObjectToJson(Venda vendaPF, VendaPME vendaPME) throws JsonProcessingException {		
		return vendaPF != null ? jsonPF(vendaPF) : jsonPME(vendaPME);		
	}

	/*** cópia fiel do json enviado PME ***/
	private String jsonPME(VendaPME vendaPME) throws JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonVendaPME jsonVendaPME = new JsonVendaPME();
		jsonVendaPME.setCdForcaVenda(vendaPME.getCdForcaVenda());
		
		List<JsonEmpresaPME> empresas = new ArrayList<JsonEmpresaPME>();
		List<JsonPlanoPME> planos = new ArrayList<JsonPlanoPME>();		
		
		List<JsonTitularesPME> titulares = new ArrayList<JsonTitularesPME>();		
		List<JsonDependentesPF> dependentes = new ArrayList<JsonDependentesPF>();
		
		for (Empresa emp : vendaPME.getEmpresas()) {
			
			JsonEmpresaPME jsonEmpresaPME = new JsonEmpresaPME();
			jsonEmpresaPME.setStatus("PRONTA");
			jsonEmpresaPME.setCnpj(emp.getCnpj());
			jsonEmpresaPME.setCnae(emp.getCnae());
			jsonEmpresaPME.setRazaoSocial(emp.getRazaoSocial());
			jsonEmpresaPME.setIncEstadual(emp.getIncEstadual());
			jsonEmpresaPME.setRamoAtividade(emp.getRamoAtividade());
			jsonEmpresaPME.setNomeFantasia(emp.getNomeFantasia());
			jsonEmpresaPME.setRepresentanteLegal(emp.getRepresentanteLegal());
			jsonEmpresaPME.setContatoEmpresa(true);
			jsonEmpresaPME.setTelefone(emp.getTelefone());
			jsonEmpresaPME.setCelular(emp.getCelular());
			jsonEmpresaPME.setEmail(emp.getEmail());
			jsonEmpresaPME.setVencimentoFatura(String.valueOf(emp.getVencimentoFatura()));
			//jsonEmpresaPME.setCpfRepresentante("00501786872");
			empresas.add(jsonEmpresaPME);
						
			JsonEnderecoPME endereco = new JsonEnderecoPME();
			endereco.setCep(emp.getEnderecoEmpresa().getCep());
			endereco.setLogradouro(emp.getEnderecoEmpresa().getLogradouro());
			endereco.setNumero(emp.getEnderecoEmpresa().getNumero());
			endereco.setComplemento(emp.getEnderecoEmpresa().getComplemento());
			endereco.setBairro(emp.getEnderecoEmpresa().getBairro());
			endereco.setCidade(emp.getEnderecoEmpresa().getCidade());
			endereco.setEstado(emp.getEnderecoEmpresa().getEstado());			
			jsonEmpresaPME.setEnderecoEmpresa(endereco);
			
			JsonContatoEmpresaPME contatoEmpresa = new JsonContatoEmpresaPME();
			contatoEmpresa.setCelular(emp.getContactEmpresa().getCelular());
			contatoEmpresa.setEmail(emp.getContactEmpresa().getEmail());
			contatoEmpresa.setNome(emp.getContactEmpresa().getNome());
			contatoEmpresa.setTelefone(emp.getContactEmpresa().getTelefone());
			jsonEmpresaPME.setContactEmpresa(contatoEmpresa);
			
			for (Plano plano : emp.getPlanos()) {
				JsonPlanoPME jsonPlanoPME = new JsonPlanoPME();
				jsonPlanoPME.setCdPlano(plano.getCdPlano());
				planos.add(jsonPlanoPME);	
				jsonEmpresaPME.setPlanos(planos);
			}
			
			jsonVendaPME.setEmpresas(jsonEmpresaPME);
		}
		
		for (Beneficiario t : vendaPME.getTitulares()) {
			
			JsonTitularesPME benef = new JsonTitularesPME();
			benef.setPfPj(t.getPfPj());
			benef.setCelular(t.getCelular());
			benef.setCpf(t.getCpf());
			benef.setDataNascimento(t.getDataNascimento());
			benef.setEmail(t.getEmail());
			benef.setNome(t.getNome());
			benef.setNomeMae(t.getNomeMae());
			benef.setSexo(t.getSexo());
			benef.setCdPlano(t.getCdPlano());
			benef.setCdVenda(t.getCdVenda());
			titulares.add(benef);
			jsonVendaPME.setTitulares(titulares);
			
			JsonEnderecoPME endereco = new JsonEnderecoPME();
			endereco.setCep(t.getEndereco().getCep());			
			endereco.setNumero(t.getEndereco().getNumero());
			endereco.setComplemento(t.getEndereco().getComplemento());
			endereco.setBairro(t.getEndereco().getBairro());
			endereco.setCidade(t.getEndereco().getCidade());
			endereco.setEstado(t.getEndereco().getEstado());			
			benef.setEndereco(endereco);
						
			for (Beneficiario d : t.getDependentes()) {
				JsonDependentesPF depend = new JsonDependentesPF();			
				depend.setCdVida(d.getCdVida());
				depend.setCdTitular(d.getCdTitular());
				depend.setCelular(d.getCelular());
				depend.setCpf(d.getCpf());
				depend.setDataNascimento(d.getDataNascimento());
				depend.setEmail(d.getEmail());
				depend.setNome(d.getNome());
				depend.setNomeMae(d.getNomeMae());
				depend.setSexo(d.getSexo());
				depend.setpFpJ(d.getPfPj());
				dependentes.add(depend);	
				benef.setDependentes(dependentes);
			}			
		}
		
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonVendaPME);
	}
	
	/*** cópia fiel do json enviado PF ***/
	private String jsonPF(Venda vendaPF) throws JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonVendaPF jsonVendaPF = new JsonVendaPF();
		jsonVendaPF.setCdPlano(vendaPF.getCdPlano());
		jsonVendaPF.setCdForcaVenda(vendaPF.getCdForcaVenda());
		
		List<JsonTitularesPF> titulares = new ArrayList<JsonTitularesPF>();
		
		List<JsonDependentesPF> dependentes = new ArrayList<JsonDependentesPF>();
		
		for (Beneficiario t : vendaPF.getTitulares()) {
			
			JsonTitularesPF benef = new JsonTitularesPF();
			benef.setCelular(t.getCelular());
			benef.setCpf(t.getCpf());
			benef.setDataNascimento(t.getDataNascimento());
			benef.setEmail(t.getEmail());
			benef.setNome(t.getNome());
			benef.setNomeMae(t.getNomeMae());
			benef.setSexo(t.getSexo());
			titulares.add(benef);
			jsonVendaPF.setTitulares(titulares);
			
			Endereco endereco = new Endereco();
			endereco.setCep(t.getEndereco().getCep());
			endereco.setLogradouro(t.getEndereco().getLogradouro());
			endereco.setNumero(t.getEndereco().getNumero());
			endereco.setComplemento(t.getEndereco().getComplemento());
			endereco.setBairro(t.getEndereco().getBairro());
			endereco.setCidade(t.getEndereco().getCidade());
			endereco.setEstado(t.getEndereco().getEstado());
			endereco.setTipoEndereco(t.getEndereco().getTipoEndereco());
			benef.setEndereco(endereco);
			
			DadosBancariosVenda dadosBancarios = new DadosBancariosVenda();
			dadosBancarios.setCodigoBanco(t.getDadosBancarios().getCodigoBanco());
			dadosBancarios.setAgencia(t.getDadosBancarios().getAgencia());
			dadosBancarios.setTipoConta(t.getDadosBancarios().getTipoConta());
			dadosBancarios.setConta(t.getDadosBancarios().getConta());
			benef.setDadosBancarios(dadosBancarios);			
			
			benef.setDependentes(dependentes);
			
			for (Beneficiario d : t.getDependentes()) {
				JsonDependentesPF depend = new JsonDependentesPF();			
				depend.setCdVida(d.getCdVida());
				depend.setCdTitular(d.getCdTitular());
				depend.setCelular(d.getCelular());
				depend.setCpf(d.getCpf());
				depend.setDataNascimento(d.getDataNascimento());
				depend.setEmail(d.getEmail());
				depend.setNome(d.getNome());
				depend.setNomeMae(d.getNomeMae());
				depend.setSexo(d.getSexo());
				depend.setpFpJ(d.getPfPj());
				dependentes.add(depend);	
			}			
		}
		
		ResponsavelContratual responsavelContratual = new ResponsavelContratual();
		responsavelContratual.setNome(vendaPF.getResponsavelContratual().getNome());
		responsavelContratual.setDataNascimento(vendaPF.getResponsavelContratual().getDataNascimento());
		responsavelContratual.setCpf(vendaPF.getResponsavelContratual().getCpf());
		responsavelContratual.setDataNascimento(vendaPF.getResponsavelContratual().getDataNascimento());
		responsavelContratual.setEmail(vendaPF.getResponsavelContratual().getEmail());
		responsavelContratual.setCelular(vendaPF.getResponsavelContratual().getCelular());
		responsavelContratual.setSexo(vendaPF.getResponsavelContratual().getSexo());		
		
		EnderecoProposta enderecoProposta = new EnderecoProposta();
		enderecoProposta.setCep(vendaPF.getResponsavelContratual().getEndereco().getCep());
		enderecoProposta.setLogradouro(vendaPF.getResponsavelContratual().getEndereco().getLogradouro());
		enderecoProposta.setNumero(vendaPF.getResponsavelContratual().getEndereco().getNumero());
		enderecoProposta.setComplemento(vendaPF.getResponsavelContratual().getEndereco().getComplemento());
		enderecoProposta.setBairro(vendaPF.getResponsavelContratual().getEndereco().getBairro());
		enderecoProposta.setCidade(vendaPF.getResponsavelContratual().getEndereco().getCidade());
		enderecoProposta.setEstado(vendaPF.getResponsavelContratual().getEndereco().getEstado());
		responsavelContratual.setEndereco(enderecoProposta);
		
		jsonVendaPF.setResponsavelContratual(responsavelContratual);
		
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonVendaPF);
	}

	//201805082104 - esert
	public static Endereco translateTbodEnderecoToEndereco(TbodEndereco tbodEndereco) {
		Endereco endereco = null;
		if(tbodEndereco!=null) {
			endereco = new Endereco();
			endereco.setBairro(tbodEndereco.getBairro());
			endereco.setCep(tbodEndereco.getCep());
			endereco.setCidade(tbodEndereco.getCidade());
			endereco.setComplemento(tbodEndereco.getComplemento());
			endereco.setLogradouro(tbodEndereco.getLogradouro());
			endereco.setNumero(tbodEndereco.getNumero());
			endereco.setEstado(tbodEndereco.getUf());
			if(tbodEndereco.getTbodTipoEndereco()!=null) {
				endereco.setTipoEndereco(tbodEndereco.getTbodTipoEndereco().getCdTipoEndereco());
			}
		}
		return endereco;
	}

	//201805082104 - esert
	public static EnderecoProposta translateTbodEnderecoToEnderecoProposta(TbodEndereco tbodEndereco) {
		EnderecoProposta endereco = null;
		if(tbodEndereco!=null) {
			endereco = new EnderecoProposta();
			endereco.setBairro(tbodEndereco.getBairro());
			endereco.setCep(tbodEndereco.getCep());
			endereco.setCidade(tbodEndereco.getCidade());
			endereco.setComplemento(tbodEndereco.getComplemento());
			endereco.setLogradouro(tbodEndereco.getLogradouro());
			endereco.setNumero(tbodEndereco.getNumero());
			endereco.setEstado(tbodEndereco.getUf());
		}
		return endereco;
	}

	//201805082112 - esert
	//201805091130 - esert
	//201805091243 - esert
	public static Beneficiario translateTbodVidaToBeneficiario(TbodVida tbodVida) {
		Beneficiario beneficiario = null;
		if(tbodVida!=null) {
			beneficiario = new Beneficiario();
			beneficiario.setCdVida(tbodVida.getCdVida());
			beneficiario.setCdTitular(0); //???
			beneficiario.setCelular(tbodVida.getCelular());
			beneficiario.setCpf(tbodVida.getCpf());
			beneficiario.setCnpj(null); //???
			
			if(tbodVida.getDataNascimento()!=null) {
				try {
					beneficiario.setDataNascimento(
							DataUtil.dateToStringParse(
									tbodVida.getDataNascimento()
							)
					);
				} catch (Exception e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
				}
			}
			
			beneficiario.setEmail(tbodVida.getEmail());
			beneficiario.setNome(tbodVida.getNome());
			beneficiario.setNomeMae(tbodVida.getNomeMae());
			beneficiario.setPfPj(tbodVida.getPfPj());
			beneficiario.setSexo(tbodVida.getSexo());
			
			if(tbodVida.getTbodPlanoVidas()!=null) {
				if(tbodVida.getTbodPlanoVidas().size()>0) {
					if(tbodVida.getTbodPlanoVidas().get(0)!=null) {
						beneficiario.setCdPlano(
								Long.parseLong(
										tbodVida.getTbodPlanoVidas().get(0).getCdPlano().toString()
								)
						);
					}
				}
			}
			
			if(tbodVida.getTbodEndereco()!=null) {
				beneficiario.setEndereco(
						ConvertObjectUtil.translateTbodEnderecoToEndereco(
								tbodVida.getTbodEndereco()
						)
				);
			}
		}
		return beneficiario;
	}

	//201805082118 - esert
	//201805091155 - esert
	public static DadosBancariosVenda translateTbodVendaDadosBancariosToDadosBancariosVenda(TbodVenda tbodVenda) {
		boolean temAlgumDadoBancario = false;
		DadosBancariosVenda dadosBancariosVenda = new DadosBancariosVenda();
		
		String agencia = null;
		if(tbodVenda.getAgencia() != null) {
			agencia = tbodVenda.getAgencia();
			temAlgumDadoBancario = true;
		}
		if(tbodVenda.getAgenciaDv() != null) {
			agencia = agencia.concat("-").concat(tbodVenda.getAgenciaDv());
			temAlgumDadoBancario = true;
		}
		dadosBancariosVenda.setAgencia(agencia);
		
		String conta = null;
		if(tbodVenda.getConta()!=null) {
			conta = tbodVenda.getConta();
			temAlgumDadoBancario = true;
		}
		if(tbodVenda.getContaDv()!=null) {
			conta = conta.concat("-").concat(tbodVenda.getContaDv());
			temAlgumDadoBancario = true;
		}		
		dadosBancariosVenda.setConta(conta);
		
		if(tbodVenda.getTipoConta()!=null) {
			dadosBancariosVenda.setTipoConta(tbodVenda.getTipoConta());
			temAlgumDadoBancario = true;
		}

		if(tbodVenda.getBanco()!=null) {
			dadosBancariosVenda.setCodigoBanco(tbodVenda.getBanco());
			temAlgumDadoBancario = true;
		}

		if(!temAlgumDadoBancario) {
			dadosBancariosVenda = null;
		}
		
		return dadosBancariosVenda;
	}

	//201805082120 - esert	
	public static ResponsavelContratual translateTbodResponsavelContratualToResponsavelContratual(
		TbodResponsavelContratual tbodResponsavelContratual) {
		ResponsavelContratual responsavelContratual = null;
		if(tbodResponsavelContratual!=null) {
			responsavelContratual = new ResponsavelContratual();
	//			(tbodResponsavelContratual.getCdResponsavelContratual());
			responsavelContratual.setCelular(tbodResponsavelContratual.getCelular());
			responsavelContratual.setCpf(tbodResponsavelContratual.getCpf());
			if(tbodResponsavelContratual.getDataNascimento()!=null) {
				try {
					responsavelContratual.setDataNascimento(
							DataUtil.dateToStringParse(
									tbodResponsavelContratual.getDataNascimento()
							)
					);
				} catch (Exception e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
				}
			}
			responsavelContratual.setEmail(tbodResponsavelContratual.getEmail());
			responsavelContratual.setNome(tbodResponsavelContratual.getNome());
			responsavelContratual.setSexo(tbodResponsavelContratual.getSexo());
			
			responsavelContratual.setEndereco(
					ConvertObjectUtil.translateTbodEnderecoToEnderecoProposta(
							tbodResponsavelContratual.getTbodEndereco()));
		}
		return responsavelContratual;
	}

	//201805091049 - esert
	public static Plano translateTbodPlanoToPlano(TbodPlano tbodPlano) {
		Plano plano = null;
		if(tbodPlano!=null) {
			plano = new Plano();
			plano.setCdPlano(tbodPlano.getCdPlano());
//			plano.setCdPlano(Long.parseLong(tbodPlano.getCodigo()));
			plano.setDescricao(tbodPlano.getNomePlano());
			plano.setSigla(tbodPlano.getSigla());
			plano.setTitulo(tbodPlano.getTitulo());
			if(tbodPlano.getTbodTipoPlano()!=null) {
				plano.setTipo(String.valueOf(tbodPlano.getTbodTipoPlano().getCdTipoPlano()));
			}				
			if(tbodPlano.getValorMensal()!=null) {
				plano.setValor(tbodPlano.getValorMensal().toString());
			}
//			plano.setValor(tbodPlano.getValorAnual());
		}
		return plano;
	}

	//201805091059 - esert
	public static TxtImportacao translateTbodTxtImportacaoToTxtImportacao(TbodTxtImportacao tbodTxtImportacao) {
		TxtImportacao txtImportacao = null;
		if(tbodTxtImportacao!=null) {
			txtImportacao = new TxtImportacao();
			txtImportacao.setNrAtendimento(tbodTxtImportacao.getNrAtendimento());
			txtImportacao.setDsErroRegistro(tbodTxtImportacao.getDsErroRegistro());
		}
		return txtImportacao;
	}

	//201805091158 - esert
	public static VendaCritica translateTbodVendaToVendaCritica(TbodVenda tbodVenda, VendaCritica venda) {
		if(tbodVenda!=null) {
			
			if(venda==null) {
				venda = new VendaCritica();
			}
			
			venda.setCdVenda(tbodVenda.getCdVenda());
			
			if(tbodVenda.getTbodEmpresa()!=null) {
				venda.setCdEmpresa(tbodVenda.getTbodEmpresa().getCdEmpresa());
			}
			
			if(tbodVenda.getTbodPlano()!=null) {
				venda.setCdPlano(tbodVenda.getTbodPlano().getCdPlano());
			}
			
			if(tbodVenda.getTbodForcaVenda()!=null) {
				venda.setCdForcaVenda(tbodVenda.getTbodForcaVenda().getCdForcaVenda());
			}
			
			venda.setDataVenda(tbodVenda.getDtVenda());
			
			if(tbodVenda.getTbodStatusVenda()!=null) {
				venda.setCdStatusVenda(tbodVenda.getTbodStatusVenda().getCdStatusVenda());
			}
	
			venda.setFaturaVencimento(tbodVenda.getFaturaVencimento());
			
			venda.setTipoPagamento(tbodVenda.getTipoPagamento());
	
			venda.setCdDCSSUsuario(null); //???
	
			venda.setTipoPagamento(tbodVenda.getTipoPagamento());
		}
		return venda;
	}	
	
}

package br.com.odontoprev.portal.corretor.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.DadosBancariosVenda;
import br.com.odontoprev.portal.corretor.dto.Endereco;
import br.com.odontoprev.portal.corretor.dto.EnderecoProposta;
import br.com.odontoprev.portal.corretor.dto.JsonDependentesPF;
import br.com.odontoprev.portal.corretor.dto.JsonTitularesPF;
import br.com.odontoprev.portal.corretor.dto.JsonVendaPF;
import br.com.odontoprev.portal.corretor.dto.ResponsavelContratual;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaPME;
import br.com.odontoprev.portal.corretor.model.TbodEndereco;
import br.com.odontoprev.portal.corretor.model.TbodResponsavelContratual;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.model.TbodVida;

@Service
public class ConvertObjectUtil {

	public String ConvertObjectToJson(Venda vendaPF, VendaPME vendaPME) throws JsonProcessingException {
		
		return jsonPF(vendaPF);		
	}

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
		
		ResponsavelContratual responsavelContratual = new ResponsavelContratual();
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
		Endereco endereco = new Endereco();
		if(tbodEndereco!=null) {
			endereco = new Endereco();
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

	//201805082104 - esert
	public static EnderecoProposta translateTbodEnderecoToEnderecoProposta(TbodEndereco tbodEndereco) {
		EnderecoProposta endereco = new EnderecoProposta();
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
	public static Beneficiario translateTbodVidaToBeneficiario(TbodVida tbodVida) {
		Beneficiario beneficiario = null;
		if(tbodVida!=null) {
			beneficiario = new Beneficiario();
			beneficiario.setCelular(tbodVida.getCelular());
			beneficiario.setCpf(tbodVida.getCpf());
			beneficiario.setEmail(tbodVida.getEmail());
			beneficiario.setNome(tbodVida.getNome());
			beneficiario.setNomeMae(tbodVida.getNomeMae());
			beneficiario.setPfPj(tbodVida.getPfPj());
			beneficiario.setSexo(tbodVida.getSexo());
//			beneficiario.setCdPlano(tbodVida.getSiglaPlano());
			if(tbodVida.getTbodEndereco()!=null) {
				beneficiario.setEndereco(
						ConvertObjectUtil.translateTbodEnderecoToEndereco(
								tbodVida.getTbodEndereco()));
			}
		}
		return beneficiario;
	}

	//201805082118 - esert
	public static DadosBancariosVenda translateTbodVendaDadosBancariosToDadosBancarios(TbodVenda tbodVenda) {
		DadosBancariosVenda dadosBancariosVenda = new DadosBancariosVenda();
		
		String agencia = null;
		if(tbodVenda.getAgencia() != null) {
			agencia = tbodVenda.getAgencia();
		}
		if(tbodVenda.getAgenciaDv() != null) {
			agencia = agencia.concat("-").concat(tbodVenda.getAgenciaDv());
		}
		dadosBancariosVenda.setAgencia(agencia);
		
		String conta = null;
		if(tbodVenda.getConta()!=null) {
			conta = tbodVenda.getConta();
		}
		if(tbodVenda.getContaDv()!=null) {
			conta = conta.concat("-").concat(tbodVenda.getContaDv());
		}		
		dadosBancariosVenda.setConta(conta);
		
		dadosBancariosVenda.setTipoConta(tbodVenda.getTipoConta());
		
		dadosBancariosVenda.setCodigoBanco(tbodVenda.getBanco());
		
		return dadosBancariosVenda;
	}

	public static ResponsavelContratual translateTbodResponsavelContratualToResponsavelContratual(
		TbodResponsavelContratual tbodResponsavelContratual) {
		ResponsavelContratual responsavelContratual = null;
		if(tbodResponsavelContratual!=null) {
			responsavelContratual = new ResponsavelContratual();
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
		}
		return responsavelContratual;
	}	
	
}

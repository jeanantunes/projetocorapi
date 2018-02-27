package br.com.odontoprev.portal.corretor.service.impl;

import java.math.BigDecimal;

import javax.persistence.NonUniqueResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.BancoContaDAO;
import br.com.odontoprev.portal.corretor.dao.CorretoraBancoDAO;
import br.com.odontoprev.portal.corretor.dao.CorretoraDAO;
import br.com.odontoprev.portal.corretor.dao.EnderecoDAO;
import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.CorretoraResponse;
import br.com.odontoprev.portal.corretor.dto.Endereco;
import br.com.odontoprev.portal.corretor.dto.Login;
import br.com.odontoprev.portal.corretor.model.TbodBancoConta;
import br.com.odontoprev.portal.corretor.model.TbodCorretora;
import br.com.odontoprev.portal.corretor.model.TbodCorretoraBanco;
import br.com.odontoprev.portal.corretor.model.TbodEndereco;
import br.com.odontoprev.portal.corretor.model.TbodLogin;
import br.com.odontoprev.portal.corretor.model.TbodTipoEndereco;
import br.com.odontoprev.portal.corretor.service.CorretoraService;

@Service
public class CorretoraServiceImpl implements CorretoraService {

	private static final Log log = LogFactory.getLog(CorretoraServiceImpl.class);

	@Autowired
	CorretoraDAO corretoraDao;

	@Autowired
	EnderecoDAO enderecoDao;

	@Autowired
	BancoContaDAO bancoContaDAO;

	@Autowired
	CorretoraBancoDAO corretoraBancoDAO;

	@Override
	public CorretoraResponse addCorretora(Corretora corretora) {
		return this.saveCorretora(corretora);
	}

	@Override
	public CorretoraResponse addCorretor(Corretora corretora) {
		return this.saveCorretora(corretora);
	}

	private CorretoraResponse saveCorretora(Corretora corretora) {

		TbodEndereco tbEndereco = new TbodEndereco();
		Endereco endereco = corretora.getEnderecoCorretora();

		tbEndereco.setLogradouro(endereco.getLogradouro() == null ? " " : endereco.getLogradouro());
		tbEndereco.setBairro(endereco.getBairro() == null ? " " : endereco.getBairro());
		tbEndereco.setCep(endereco.getCep() == null ? " " : endereco.getCep());
		tbEndereco.setCidade(endereco.getCidade() == null ? " " : endereco.getCidade());
		tbEndereco.setNumero(endereco.getNumero() == null ? " " : endereco.getNumero());
		tbEndereco.setUf(endereco.getEstado() == null ? " " : endereco.getEstado());
		tbEndereco = enderecoDao.save(tbEndereco);

		TbodBancoConta conta = new TbodBancoConta();
		conta.setAgencia(
				corretora.getConta().getCodigoAgencia() == null ? " " : corretora.getConta().getCodigoAgencia());
		conta.setConta(corretora.getConta().getNumeroConta() == null ? " " : corretora.getConta().getNumeroConta());
		conta.setCodigoBanco(new BigDecimal(corretora.getConta().getCodigoBanco()));
		conta = bancoContaDAO.save(conta);

		TbodCorretora tbCorretora = new TbodCorretora();
		tbCorretora.setAtivo("S"); // TODO Onde pega esse campo?
		tbCorretora.setCpfResponsavel("0000000"); // TODO Onde pega esse campo?
		tbCorretora.setDataAbertura(corretora.getDataAbertura());
		tbCorretora.setRazaoSocial(corretora.getRazaoSocial() == null ? " " : corretora.getRazaoSocial());
		tbCorretora.setSimplesNacional(corretora.isSimplesNacional() == true ? "S" : "N");
		tbCorretora.setStatusCnpj(corretora.isStatusCnpj() == true ? "S" : "N");
		tbCorretora.setNome(corretora.getRazaoSocial() == null ? " " : corretora.getRazaoSocial()); // TODO Nome do
																									// Corretor?
		tbCorretora.setCodigo("000"); // TODO Onde pega esse codigo?
		tbCorretora.setCnpj(corretora.getCnpj() == null ? " " : corretora.getCnpj());
		tbCorretora.setEmail(corretora.getEmail() == null ? " " : corretora.getEmail());
		tbCorretora.setTelefone(corretora.getTelefone() == null ? " " : corretora.getTelefone());
		tbCorretora.setRegional("1"); // TODO Onde pega a Regional?
		tbCorretora.setCelular(corretora.getCelular() == null ? " " : corretora.getCelular());
		tbCorretora.setTbodEndereco(tbEndereco);
		tbCorretora.setNomeRepresentanteLegal1(" ");
		tbCorretora.setNomeRepresentanteLegal1(" ");

		TbodCorretoraBanco corretoraBanco = new TbodCorretoraBanco();
		corretoraBanco.setCnae(corretora.getCnae() == null ? " " : corretora.getCnae());
		corretoraBanco.setCpfResponsavelLegal1(" ");
		corretoraBanco.setCpfResponsavelLegal2(" ");
		corretoraBanco.setTbodBancoConta(conta);

		if (!corretora.getRepresentantes().isEmpty()) {
			tbCorretora.setNomeRepresentanteLegal1(corretora.getRepresentantes().get(0).getNome());
			corretoraBanco.setCpfResponsavelLegal1(corretora.getRepresentantes().get(0).getNome());

			if (corretora.getRepresentantes().size() > 1) {
				tbCorretora.setNomeRepresentanteLegal2(corretora.getRepresentantes().get(1).getNome());
				corretoraBanco.setCpfResponsavelLegal2(corretora.getRepresentantes().get(1).getNome());
			}

		}

		tbCorretora = corretoraDao.save(tbCorretora);

		corretoraBanco.setTbodCorretora(tbCorretora);
		corretoraBanco = corretoraBancoDAO.save(corretoraBanco);

		return new CorretoraResponse(tbCorretora.getCdCorretora());

	}

	@Override
	public Corretora buscaCorretoraPorCnpj(String cnpj) {

		log.info("[CorretoraServiceImpl::buscaCorretoraPorCnpj]");
		Corretora corretora = new Corretora();
		 try {
			TbodCorretora tbodCorretora = corretoraDao.findByCnpj(cnpj);
			
			// Corretora
			corretora.setCdCorretora(tbodCorretora.getCdCorretora());
			corretora.setCnpj(tbodCorretora.getCnpj());
			corretora.setRazaoSocial(tbodCorretora.getRazaoSocial());
			corretora.setCnae(tbodCorretora.getCnae());
			corretora.setTelefone(tbodCorretora.getTelefone());
			corretora.setCelular(tbodCorretora.getCelular());
			corretora.setEmail(tbodCorretora.getEmail());
			if(tbodCorretora.getStatusCnpj() != null) {			
				corretora.setStatusCnpj(tbodCorretora.getStatusCnpj().equals("S") ? true : false);
			}
			if(tbodCorretora.getSimplesNacional() != null) {				
				corretora.setSimplesNacional(tbodCorretora.getSimplesNacional().equals("S") ? true : false);
			}
			corretora.setDataAbertura(tbodCorretora.getDataAbertura());
			// Endereço
			TbodEndereco tbodEndereco = tbodCorretora.getTbodEndereco();
			if(tbodEndereco != null) {			
				Endereco endereco = new Endereco();
				endereco.setCep(tbodEndereco.getCep());
				endereco.setLogradouro(tbodEndereco.getLogradouro());
				endereco.setNumero(tbodEndereco.getNumero());
				endereco.setComplemento(tbodEndereco.getComplemento());
				endereco.setBairro(tbodEndereco.getBairro());
				endereco.setCidade(tbodEndereco.getCidade());
				endereco.setEstado(tbodEndereco.getUf());
				TbodTipoEndereco tbodTipoEndereco = tbodEndereco.getTbodTipoEndereco();
				if(tbodTipoEndereco != null) {					
					endereco.setTipoEndereco(tbodTipoEndereco.getCdTipoEndereco());
				}
				corretora.setEnderecoCorretora(endereco);
			}
			// Login
			if(tbodCorretora.getTbodLogin() != null) {	
				Login login = new Login();
				TbodLogin tbodLogin = tbodCorretora.getTbodLogin();
				login.setCdLogin(tbodLogin.getCdLogin()); 
				login.setCdTipoLogin(tbodLogin.getCdTipoLogin());
				login.setFotoPerfilB64(tbodLogin.getFotoPerfilB64());
				login.setSenha(tbodLogin.getSenha());
				corretora.setLogin(login);
			}
		 }  catch (NonUniqueResultException e) {
			 log.error("buscaCorretoraPorCnpj :: Detalhe: [ Mais de um registro encontraco com o CNPJ informado]");
		 } 	catch (Exception e) {
	            log.error("buscaCorretoraPorCnpj :: Detalhe: [" + e.getMessage() + "]");
	            return new Corretora();
	        }
		return corretora;
	}
}

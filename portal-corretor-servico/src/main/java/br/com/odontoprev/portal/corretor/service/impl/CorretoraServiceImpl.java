package br.com.odontoprev.portal.corretor.service.impl;

import static br.com.odontoprev.portal.corretor.util.Constantes.ATIVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NonUniqueResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.BancoContaDAO;
import br.com.odontoprev.portal.corretor.dao.CorretoraBancoDAO;
import br.com.odontoprev.portal.corretor.dao.CorretoraDAO;
import br.com.odontoprev.portal.corretor.dao.EnderecoDAO;
import br.com.odontoprev.portal.corretor.dao.LoginDAO;
import br.com.odontoprev.portal.corretor.dto.Conta;
import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.CorretoraResponse;
import br.com.odontoprev.portal.corretor.dto.Endereco;
import br.com.odontoprev.portal.corretor.dto.Login;
import br.com.odontoprev.portal.corretor.dto.Representante;
import br.com.odontoprev.portal.corretor.model.TbodBancoConta;
import br.com.odontoprev.portal.corretor.model.TbodCorretora;
import br.com.odontoprev.portal.corretor.model.TbodCorretoraBanco;
import br.com.odontoprev.portal.corretor.model.TbodEndereco;
import br.com.odontoprev.portal.corretor.model.TbodLogin;
import br.com.odontoprev.portal.corretor.model.TbodTipoEndereco;
import br.com.odontoprev.portal.corretor.service.CorretoraService;
import br.com.odontoprev.portal.corretor.util.Constantes;
import br.com.odontoprev.portal.corretor.util.DataUtil;

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
	
	@Autowired
	LoginDAO loginDao;

	@Override
	//@Transactional
	public CorretoraResponse addCorretora(Corretora corretora) {
		
		log.info("[addCorretora]");
		
		TbodCorretora tbCorretora = new TbodCorretora();

		try {

			//Endereco
			TbodEndereco tbEndereco = new TbodEndereco();
			Endereco endereco = corretora.getEnderecoCorretora();
			if(endereco != null) {
				tbEndereco.setCep(endereco.getCep() == null ? " " : endereco.getCep());
				tbEndereco.setLogradouro(endereco.getLogradouro() == null ? " " : endereco.getLogradouro());
				tbEndereco.setNumero(endereco.getNumero() == null ? " " : endereco.getNumero());
				tbEndereco.setComplemento(endereco.getComplemento() == null ? endereco.getComplemento() : " ");
				tbEndereco.setBairro(endereco.getBairro() == null ? " " : endereco.getBairro());
				tbEndereco.setCidade(endereco.getCidade() == null ? " " : endereco.getCidade());
				tbEndereco.setUf(endereco.getEstado() == null ? " " : endereco.getEstado());
				tbEndereco = enderecoDao.save(tbEndereco);				
			}
			
			//Dados Bancarios
			TbodBancoConta tbBancoConta = new TbodBancoConta();
			if(corretora.getConta() != null) {
				tbBancoConta.setAgencia(corretora.getConta().getCodigoAgencia() == null ? " " : corretora.getConta().getCodigoAgencia());
				tbBancoConta.setConta(corretora.getConta().getNumeroConta() == null ? " " : corretora.getConta().getNumeroConta());
				tbBancoConta.setCodigoBanco(new BigDecimal(corretora.getConta().getCodigoBanco()));
				tbBancoConta = bancoContaDAO.save(tbBancoConta);
			}
			
			//Login
			TbodLogin tbLogin = new TbodLogin();
			if(corretora.getLogin() != null) {
				tbLogin.setSenha(corretora.getLogin().getSenha());
				tbLogin.setCdTipoLogin(1L); //TODO
				tbLogin = loginDao.save(tbLogin);
				tbCorretora.setTbodLogin(tbLogin);
			}
			
			TbodCorretoraBanco tbCorretoraBanco = new TbodCorretoraBanco();
			tbCorretoraBanco.setCnae(corretora.getCnae() == null ? " " : corretora.getCnae());
			tbCorretoraBanco.setCpfResponsavelLegal1(" ");
			tbCorretoraBanco.setCpfResponsavelLegal2(" ");
			tbCorretoraBanco.setTbodBancoConta(tbBancoConta);
			
			//Dados da Corretora
			tbCorretora.setAtivo(ATIVO); // TODO Onde pega esse campo?
//			tbCorretora.setCpfResponsavel("0000000"); // TODO Onde pega esse campo?
			tbCorretora.setCnpj(corretora.getCnpj() == null ? " " : corretora.getCnpj());
			tbCorretora.setRazaoSocial(corretora.getRazaoSocial() == null ? " " : corretora.getRazaoSocial());
			tbCorretora.setCnae(corretora.getCnae());
			tbCorretora.setSimplesNacional(corretora.getSimplesNacional());
			tbCorretora.setDataAbertura(DataUtil.dateParse(corretora.getDataAbertura()));
			tbCorretora.setStatusCnpj(corretora.getStatusCnpj());
			tbCorretora.setNome(corretora.getRazaoSocial() == null ? " " : corretora.getRazaoSocial());
			tbCorretora.setCodigo("000"); // TODO Onde pega esse codigo?

			tbCorretora.setTelefone(corretora.getTelefone() == null ? " " : corretora.getTelefone());
			tbCorretora.setCelular(corretora.getCelular() == null ? " " : corretora.getCelular());
			tbCorretora.setEmail(corretora.getEmail() == null ? " " : corretora.getEmail());
			tbCorretora.setRegional("1"); // TODO Onde pega a Regional?
			tbCorretora.setTbodEndereco(tbEndereco);

			tbCorretora.setTemSusep(corretora.getTemSusep());
			tbCorretora.setCodigoSusep(corretora.getCodigoSusep());

			//Representantes
			if (corretora.getRepresentantes() != null && !corretora.getRepresentantes().isEmpty()) {
				tbCorretora.setNomeRepresentanteLegal1(corretora.getRepresentantes().get(0).getNome());
				tbCorretoraBanco.setCpfResponsavelLegal1(corretora.getRepresentantes().get(0).getCpf());

				if (corretora.getRepresentantes().size() > 1) {
					tbCorretora.setNomeRepresentanteLegal2(corretora.getRepresentantes().get(1).getNome());
					tbCorretoraBanco.setCpfResponsavelLegal2(corretora.getRepresentantes().get(1).getCpf());
				}
			}
			
			tbCorretora = corretoraDao.save(tbCorretora);

			tbCorretoraBanco.setTbodCorretora(tbCorretora);
			tbCorretoraBanco = corretoraBancoDAO.save(tbCorretoraBanco);

		} catch (Exception e) {
			log.error("addCorretora :: Detalhe: [" + e.getMessage() + "]");
			return new CorretoraResponse(0, "addCorretora; Erro:[" + e.getMessage() + "]");
		}

		return new CorretoraResponse(tbCorretora.getCdCorretora(), "Corretora [" + tbCorretora.getCnpj() + "] cadastrada!");
	}

	@Override
	public CorretoraResponse updateCorretoraLogin(Corretora corretora) { //201807181255 - esert - COR-319
		
		log.info("[updateCorretoraLogin - ini]");
		
		TbodCorretora tbodCorretora;
		
		try {
			
			if(corretora.getCdCorretora() == 0) {
				throw new Exception("CdCorretora nao informado. Corretora nao atualizada!");
			}
			
			tbodCorretora = corretoraDao.findOne(corretora.getCdCorretora());
			
			if(tbodCorretora != null) {

//201807181551 - 2kill - desnecessario
//				tbodCorretora.setTelefone(corretora.getTelefone());
//				tbodCorretora.setCelular(corretora.getCelular());
//				tbodCorretora.setEmail(corretora.getEmail());
				
				//Update Login
				///if (corretora.getLogin() != null && corretora.getLogin().getSenha() != null && corretora.getLogin().getSenha() != "") {
				if (corretora.getSenha() != null && !corretora.getSenha().isEmpty()) {
					
					TbodLogin tbLogin = null;
	
					if (tbodCorretora.getTbodLogin() == null) {
						log.info("updateCorretoraLogin - criando novo TbodLogin para tbCorretora.getTbodLogin():["
								+ tbodCorretora.getCdCorretora() + "], getCpf():[" + tbodCorretora.getCdCorretora()
								+ "] pq TbodLogin() == null.");
						tbLogin = new TbodLogin();
					} else {
						if (tbodCorretora.getTbodLogin().getCdLogin() == null) {
							log.info(" updateCorretoraLogin - criando novo TbodLogin para tbCorretora.getTbodLogin():["
									+ tbodCorretora.getCdCorretora() + "], getCnpj():[" + tbodCorretora.getCnpj()
									+ "] pq TbodLogin().getCdLogin() == null.");
							tbLogin = new TbodLogin();
						} else {
							tbLogin = loginDao.findOne(tbodCorretora.getTbodLogin().getCdLogin());
							if (tbLogin == null) {
								log.info("updateCorretoraLogin - criando novo TbodLogin para tbCorretora.getCdCorretora():["
										+ tbodCorretora.getCdCorretora() + "], getCnpj():[" + tbodCorretora.getCnpj()
										+ "] pq TbodLogin().getCdLogin():[" + tbodCorretora.getTbodLogin().getCdLogin()
										+ "] NAO ENCONTRADO.");
								tbLogin = new TbodLogin();
							}
						}
					}
	
					log.info("[updateCorretoraLogin - loginDao.save()]");
					tbLogin.setCdTipoLogin(Constantes.TIPO_LOGIN_UM); // TODO
					//tbLogin.setSenha(corretora.getLogin().getSenha());
					tbLogin.setSenha(corretora.getSenha()); //208107181400 - esert - COR-319
					tbLogin = loginDao.save(tbLogin);
					tbodCorretora.setTbodLogin(tbLogin);
				}
				
				//Update Dados Bancarios - Confirmar com Fernando os campos 
//				if (corretora.getConta() != null && corretora.getConta().get != null && corretora.getLogin().getSenha() != "") {
//				}
	
				log.info("[updateCorretoraLogin - corretoraDao.save()]");
				tbodCorretora = corretoraDao.save(tbodCorretora);
				
				log.info("[updateCorretoraLogin - fim]");
				return new CorretoraResponse(tbodCorretora.getCdCorretora(),
						"Corretora atualizada. CNPJ [" + tbodCorretora.getCnpj() + "]");
			}
			else {
				log.info("[updateCorretoraLogin - fim]");
				return new CorretoraResponse(corretora.getCdCorretora(),
						"Corretora não encontrada. cdCorretora [" + corretora.getCdCorretora() + "]");
			}
			
		} catch (Exception e) {
			log.info("[updateCorretoraLogin - erro]");
			log.error("updateCorretora :: Detalhe: [" + e.getMessage() + "]");
			return new CorretoraResponse(0, "updateCorretora; Erro:[" + e.getMessage() + "]");
		}
		
	}

	@Override
	public Corretora buscaCorretoraPorCnpj(String cnpj) throws Exception {

		log.info("[buscaCorretoraPorCnpj]");
		
		Corretora corretora = new Corretora();
		
		try {
			TbodCorretora tbCorretora = corretoraDao.findByCnpj(cnpj);

			if (tbCorretora == null) {
				log.error("buscaCorretoraPorCnpj :: cnpj:[" + cnpj + "] len:[" + cnpj.length() + "] não encontrado.");
				return null;
			}

			// Corretora
			corretora.setCdCorretora(tbCorretora.getCdCorretora());
			corretora.setCnpj(tbCorretora.getCnpj());
			corretora.setRazaoSocial(tbCorretora.getRazaoSocial());
			corretora.setCnae(tbCorretora.getCnae());
			if (tbCorretora.getSimplesNacional() != null) {
				corretora.setSimplesNacional(tbCorretora.getSimplesNacional());
			}
			
			corretora.setDataAbertura(DataUtil.dateToStringParse(tbCorretora.getDataAbertura()));
			if (tbCorretora.getStatusCnpj() != null) {
				corretora.setStatusCnpj(tbCorretora.getStatusCnpj());
			}
			corretora.setTelefone(tbCorretora.getTelefone());
			corretora.setCelular(tbCorretora.getCelular());
			corretora.setEmail(tbCorretora.getEmail());

			// Endereco
			TbodEndereco tbEndereco = tbCorretora.getTbodEndereco();
			if (tbEndereco != null) {
				Endereco endereco = new Endereco();
				endereco.setCep(tbEndereco.getCep());
				endereco.setLogradouro(tbEndereco.getLogradouro());
				endereco.setNumero(tbEndereco.getNumero());
				endereco.setComplemento(tbEndereco.getComplemento());
				endereco.setBairro(tbEndereco.getBairro());
				endereco.setCidade(tbEndereco.getCidade());
				endereco.setEstado(tbEndereco.getUf());
				TbodTipoEndereco tbodTipoEndereco = tbEndereco.getTbodTipoEndereco();
				if (tbodTipoEndereco != null) {
					endereco.setTipoEndereco(tbodTipoEndereco.getCdTipoEndereco());
				}
				corretora.setEnderecoCorretora(endereco);
			}

			corretora.setTemSusep(tbCorretora.getTemSusep());
			corretora.setCodigoSusep(tbCorretora.getCodigoSusep());
			
			List<Representante> representantes = new ArrayList<Representante>();
			Representante representante;

			//Representantes
			//rever campos nas tabelas 201803091426
			if(tbCorretora.getNomeRepresentanteLegal1() != null && !tbCorretora.getNomeRepresentanteLegal1().isEmpty()) {
				representante = new Representante();
				representante.setNome(tbCorretora.getNomeRepresentanteLegal1());
				representante.setCpf(tbCorretora.getCpfResponsavel());
				representantes.add(representante);
			}
			if(tbCorretora.getNomeRepresentanteLegal2() != null && !tbCorretora.getNomeRepresentanteLegal2().isEmpty()) {
				representante = new Representante();
				representante.setNome(tbCorretora.getNomeRepresentanteLegal2());
				representante.setCpf(tbCorretora.getCpfResponsavel2());
				representantes.add(representante);
			}
			corretora.setRepresentantes(representantes);

			//Dados Bancarios
			if(tbCorretora.getTbodCorretoraBancos() != null && !tbCorretora.getTbodCorretoraBancos().isEmpty()) {

				if(tbCorretora.getTbodCorretoraBancos().get(0).getTbodBancoConta() != null) {
					Conta conta = new Conta();
					conta.setCdBancoConta(tbCorretora.getTbodCorretoraBancos().get(0).getTbodBancoConta().getCdBancoConta());
					conta.setCodigoBanco(String.valueOf(tbCorretora.getTbodCorretoraBancos().get(0).getTbodBancoConta().getCodigoBanco()));
					conta.setCodigoAgencia(tbCorretora.getTbodCorretoraBancos().get(0).getTbodBancoConta().getAgencia());
					conta.setNumeroConta(tbCorretora.getTbodCorretoraBancos().get(0).getTbodBancoConta().getConta());
					corretora.setConta(conta);
				}
			}
			
			// Login
			if (tbCorretora.getTbodLogin() != null) {
				Login login = new Login();
				TbodLogin tbLogin = tbCorretora.getTbodLogin();
				login.setCdLogin(tbLogin.getCdLogin());
				login.setCdTipoLogin(tbLogin.getCdTipoLogin());
				login.setFotoPerfilB64(tbLogin.getFotoPerfilB64());
				login.setSenha(tbLogin.getSenha());
				login.setUsuario(tbCorretora.getCnpj());
				corretora.setLogin(login);
			}
			
		} catch (NonUniqueResultException e) {

			log.error("buscaCorretoraPorCnpj :: Detalhe: [ Mais de um registro encontraco com o CNPJ informado]");
			throw new Exception("buscaCorretoraPorCnpj :: Detalhe: [ Mais de um registro encontraco com o CNPJ informado]", e);

		} catch (Exception e) {

			log.error("buscaCorretoraPorCnpj :: Detalhe: [" + e.getMessage() + "]");
			throw new Exception("buscaCorretoraPorCnpj :: Detalhe: [" + e.getMessage() + "]", e);

		}

		return corretora;
	}

	@Override
	public CorretoraResponse updateCorretoraDados(Corretora corretora) {

		log.info("[updateCorretoraDados - ini]");

		TbodCorretora tbodCorretora;

		try {

			tbodCorretora = corretoraDao.findOne(corretora.getCdCorretora());

			if(tbodCorretora != null) {

				//Update Email
				if (corretora.getEmail() != null && !corretora.getEmail().isEmpty()) {

					log.info("[updateCorretoraDados - tbodCorretora.setEmail(corretora.getEmail())]");
					tbodCorretora.setEmail(corretora.getEmail());
				}

				log.info("[updateCorretoraDados - corretoraDao.save()]");
				tbodCorretora = corretoraDao.save(tbodCorretora);

				log.info("[updateCorretoraDados - fim]");
				return new CorretoraResponse(tbodCorretora.getCdCorretora(),
						"Corretora atualizada. CNPJ [" + tbodCorretora.getCnpj() + "]");
			}
			else {

				log.info("[updateCorretoraDados - fim]");
				return null;

			}

		} catch (Exception e) {
			log.info("[updateCorretoraDados - erro]");
			log.error("updateCorretoraDados :: Detalhe: [" + e.getMessage() + "]");
			return new CorretoraResponse(0, "updateCorretoraDados; Erro:[" + e.getMessage() + "]");
		}

	}

}

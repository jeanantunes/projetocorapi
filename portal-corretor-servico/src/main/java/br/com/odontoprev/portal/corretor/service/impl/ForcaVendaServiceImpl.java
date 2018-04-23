package br.com.odontoprev.portal.corretor.service.impl;

import static br.com.odontoprev.portal.corretor.enums.StatusForcaVendaEnum.AGUARDANDO_APRO;
import static br.com.odontoprev.portal.corretor.enums.StatusForcaVendaEnum.ATIVO;
import static br.com.odontoprev.portal.corretor.enums.StatusForcaVendaEnum.PRE_CADASTRO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.odontoprev.portal.corretor.dao.CorretoraDAO;
import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dao.LoginDAO;
import br.com.odontoprev.portal.corretor.dao.StatusForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.DCSSLoginResponse;
import br.com.odontoprev.portal.corretor.dto.Endereco;
import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.dto.ForcaVendaResponse;
import br.com.odontoprev.portal.corretor.enums.StatusForcaVendaEnum;
import br.com.odontoprev.portal.corretor.exceptions.ApiTokenException;
import br.com.odontoprev.portal.corretor.model.TbodCorretora;
import br.com.odontoprev.portal.corretor.model.TbodEndereco;
import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
import br.com.odontoprev.portal.corretor.model.TbodLogin;
import br.com.odontoprev.portal.corretor.model.TbodStatusForcaVenda;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;
import br.com.odontoprev.portal.corretor.util.Constantes;
import br.com.odontoprev.portal.corretor.util.DataUtil;

@Service
public class ForcaVendaServiceImpl implements ForcaVendaService {


	private static final Log log = LogFactory.getLog(ForcaVendaServiceImpl.class);

	@Autowired
	private CorretoraDAO corretoraDao;

	@Autowired
	private StatusForcaVendaDAO statusForcaVendaDao;

	@Autowired
	private ForcaVendaDAO forcaVendaDao;

	@Autowired
	private LoginDAO loginDao;

	@Autowired
	private ApiManagerTokenServiceImpl apiManagerTokenService;

	@Value("${DCSS_URL}")
	private String dcssUrl;
	
	@Value("${DCSS_URL_PROD}")
	private String dcssUrlProd;
	
	@Value("${DCSS_CODIGO_CANAL_VENDAS}")
	private String dcss_codigo_canal_vendas;
	

	private DCSSLoginResponse postIntegracaoForcaDeVendaDcss(ForcaVenda forca) throws ApiTokenException {

		DCSSLoginResponse dCSSLoginResponse = null;
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Authorization", "Bearer " + apiManagerTokenService.getToken());

		final Map<String, Object> forcaMap = new HashMap<>();

		final RestTemplate restTemplate = new RestTemplate();
		
		forcaMap.put("nome", forca.getNome() == null ? "n/a" : forca.getNome());
		forcaMap.put("email", forca.getEmail() == null ? "n/a" : forca.getEmail());
		forcaMap.put("telefone", forca.getCelular() == null ? "n/a" : forca.getCelular());
		forcaMap.put("nomeEmpresa", forca.getNomeEmpresa() == null ? "n/a" : forca.getNomeEmpresa());
		forcaMap.put("login", forca.getCpf() == null ? "n/a" : forca.getCpf());
		forcaMap.put("rg", forca.getRg() == null ? "n/a" : forca.getRg());
		forcaMap.put("cpf", forca.getCpf() == null ? "n/a" : forca.getCpf());
		forcaMap.put("cargo", forca.getCargo() == null ? "n/a" : forca.getCargo());
		forcaMap.put("responsavel", forca.getResponsavel() == null ? "n/a" : forca.getResponsavel());
		forcaMap.put("nomeGerente", forca.getNomeGerente() == null ? "n/a" : forca.getNomeGerente());
		forcaMap.put("senha", forca.getSenha() == null ? "" : forca.getSenha());
		forcaMap.put("canalVenda", dcss_codigo_canal_vendas);
		
		HttpEntity<?> request = new HttpEntity<Map<String, Object>>(forcaMap, headers);
		
		ResponseEntity<DCSSLoginResponse> response = restTemplate.postForEntity((dcssUrl + "/usuario/1.0/"), request,
				DCSSLoginResponse.class);
		
		dCSSLoginResponse = response.getBody();
		
		log.info("postIntegracaoForcaDeVendaDcss; DCSSLoginResponse.getCodigo():["+ dCSSLoginResponse.getCodigo() +"];");
		
		return dCSSLoginResponse;

	}

	private void putIntegracaoForcaDeVendaDcss(ForcaVenda forca, String status) throws ApiTokenException {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + apiManagerTokenService.getToken());

		final Map<String, Object> forcaMap = new HashMap<>();

		final RestTemplate restTemplate = new RestTemplate();
		forcaMap.put("nome", forca.getNome());
		forcaMap.put("email", forca.getEmail());
		forcaMap.put("telefone", forca.getCelular());
		forcaMap.put("nomeEmpresa", forca.getNomeEmpresa());
		forcaMap.put("login", forca.getCpf());
		forcaMap.put("rg", forca.getRg());
		forcaMap.put("cpf", forca.getCpf());
		forcaMap.put("cargo", forca.getCargo());
		forcaMap.put("responsavel", forca.getResponsavel());
		forcaMap.put("nomeGerente", forca.getNomeGerente());
		forcaMap.put("senha", forca.getSenha());
		forcaMap.put("canalVenda", forca.getCdForcaVenda());		
		forcaMap.put("statusUsuario", status == "INATIVO" ? "I" : "A");
		HttpEntity<?> request = new HttpEntity<Map<String, Object>>(forcaMap, headers);
		//restTemplate.exchange((dcssUrl + "/usuario/1.0/"), HttpMethod.PUT, request, ForcaVenda.class);
		restTemplate.exchange((dcssUrlProd + "/usuario/1.0/"), HttpMethod.PUT, request, ForcaVenda.class);

	}

	@Override
	public ForcaVendaResponse updateForcaVenda(ForcaVenda forcaVenda) {
		try{
			TbodForcaVenda tbForcaVenda = forcaVendaDao.findOne(forcaVenda.getCdForcaVenda());
			if (tbForcaVenda != null) {
				
				if(forcaVenda.getCpf()==null) {
					forcaVenda.setCpf(tbForcaVenda.getCpf());
				}
	
				tbForcaVenda.setNome(forcaVenda.getNome());
				tbForcaVenda.setCpf(forcaVenda.getCpf());
				tbForcaVenda.setDataNascimento(DataUtil.dateParse(forcaVenda.getDataNascimento()));
				tbForcaVenda.setCelular(forcaVenda.getCelular());
				tbForcaVenda.setEmail(forcaVenda.getEmail());
				tbForcaVenda.setCargo(forcaVenda.getCargo());
				tbForcaVenda.setDepartamento(forcaVenda.getDepartamento());
				if (forcaVenda.getCorretora() != null && forcaVenda.getCorretora().getCdCorretora() > 0) {
					TbodCorretora tbCorretora = corretoraDao.findOne(forcaVenda.getCorretora().getCdCorretora());
					tbForcaVenda.setTbodCorretora(tbCorretora);
				}
	
				// Grava senha na tabela de login na tela de Aguardando Aprovacao
				if (forcaVenda.getSenha() != null && forcaVenda.getSenha() != "") {
					TbodLogin tbLogin = null;
					// tbLogin.setCdLogin(tbForcaVenda.getTbodLogin().getCdLogin());
	
					if (tbForcaVenda.getTbodLogin() == null) {
						log.info("updateForcaVenda - criando novo TbodLogin para tbForcaVenda.getCdForcaVenda():["
								+ tbForcaVenda.getCdForcaVenda() + "], getCpf():[" + tbForcaVenda.getCpf()
								+ "] pq TbodLogin() == null.");
						tbLogin = new TbodLogin();
					} else {
						if (tbForcaVenda.getTbodLogin().getCdLogin() == null) {
							log.info(" updateForcaVenda - criando novo TbodLogin para tbForcaVenda.getCdForcaVenda():["
									+ tbForcaVenda.getCdForcaVenda() + "], getCpf():[" + tbForcaVenda.getCpf()
									+ "] pq TbodLogin().getCdLogin() == null.");
							tbLogin = new TbodLogin();
						} else {
							tbLogin = loginDao.findOne(tbForcaVenda.getTbodLogin().getCdLogin());
							if (tbLogin == null) {
								log.info("updateForcaVenda - criando novo TbodLogin para tbForcaVenda.getCdForcaVenda():["
										+ tbForcaVenda.getCdForcaVenda() + "], getCpf():[" + tbForcaVenda.getCpf()
										+ "] pq TbodLogin().getCdLogin():[" + tbForcaVenda.getTbodLogin().getCdLogin()
										+ "] NAO ENCONTRADO.");
								tbLogin = new TbodLogin();
							}
						}
					}
	
					tbLogin.setCdTipoLogin((long) 1); // TODO
					tbLogin.setSenha(forcaVenda.getSenha());
					tbLogin = loginDao.save(tbLogin);
					tbForcaVenda.setTbodLogin(tbLogin);
				}
	
				tbForcaVenda = forcaVendaDao.save(tbForcaVenda);
				
				if (tbForcaVenda.getCodigoDcssUsuario() != null) {
					this.putIntegracaoForcaDeVendaDcss(forcaVenda,null);
					/*return new ForcaVendaResponse(tbForcaVenda.getCdForcaVenda(),
							"ForcaVenda atualizada. CPF [" + forcaVenda.getCpf() + "]");*/
				}
				
				return new ForcaVendaResponse(tbForcaVenda.getCdForcaVenda(),
						"ForcaVenda atualizada. CPF [" + forcaVenda.getCpf() + "]");
				
				/*this.putIntegracaoForcaDeVendaDcss(forcaVenda,null);
				return new ForcaVendaResponse(tbForcaVenda.getCdForcaVenda(),
						"ForcaVenda atualizada. CPF [" + forcaVenda.getCpf() + "]");*/
	
			} else {
				return new ForcaVendaResponse(forcaVenda.getCdForcaVenda(),
						"ForcaVenda não encontrada. cdForcaVenda [" + forcaVenda.getCdForcaVenda() + "]");
			}
		}catch(Exception e) {
			String msgErro = "updateForcaVenda; Erro:[" + e.getMessage() + "]"; 
			log.error(msgErro);
			return new ForcaVendaResponse(0, "updateForcaVenda; Erro:[" + msgErro + "]");
		}
	}

	@Override
	public ForcaVendaResponse addForcaVenda(ForcaVenda forcaVenda) {

		log.info("[addForcaVenda]");

		TbodForcaVenda tbForcaVenda = new TbodForcaVenda();
		TbodStatusForcaVenda tbStatusForcaVenda = new TbodStatusForcaVenda();

		try {

			final List<TbodForcaVenda> forcas = forcaVendaDao.findByCpf(forcaVenda.getCpf());
			if (forcas != null && !forcas.isEmpty()) {
				throw new Exception("CPF [" + forcaVenda.getCpf() + "] já existe!");
			}

			tbForcaVenda.setNome(forcaVenda.getNome());
			tbForcaVenda.setCpf(forcaVenda.getCpf());
			tbForcaVenda.setDataNascimento(DataUtil.dateParse(forcaVenda.getDataNascimento()));
			tbForcaVenda.setCelular(forcaVenda.getCelular());
			tbForcaVenda.setEmail(forcaVenda.getEmail());
			// Nas telas de pre-cadastro forca e pre-cadastro forca pela
			// corretora o status eh inativo
			tbForcaVenda.setAtivo(Constantes.INATIVO);
			tbForcaVenda.setCargo(forcaVenda.getCargo());
			tbForcaVenda.setDepartamento(forcaVenda.getDepartamento());
			if (forcaVenda.getCorretora() != null && forcaVenda.getCorretora().getCdCorretora() > 0) {
				TbodCorretora tbCorretora = corretoraDao.findOne(forcaVenda.getCorretora().getCdCorretora());
				tbForcaVenda.setTbodCorretora(tbCorretora);
			}

			// Grava senha na tabela de login na tela de Aguardando Aprovacao
			if (forcaVenda.getSenha() != null && forcaVenda.getSenha() != "") {
				TbodLogin tbLogin = new TbodLogin();
				tbLogin.setCdTipoLogin((long) 1); // TODO
				tbLogin.setSenha(forcaVenda.getSenha());
				tbLogin = loginDao.save(tbLogin);
				tbForcaVenda.setTbodLogin(tbLogin);

				// Se vier o campo senha, eh pre-cadastro forca
				tbStatusForcaVenda = statusForcaVendaDao.findOne(AGUARDANDO_APRO.getCodigo());
			} else {
				// Sem senha, pre-cadastro forca pela corretora
				tbStatusForcaVenda = statusForcaVendaDao.findOne(PRE_CADASTRO.getCodigo());
			}

			tbForcaVenda.setTbodStatusForcaVenda(tbStatusForcaVenda);
			tbForcaVenda = forcaVendaDao.save(tbForcaVenda);
			// integracaoForcaDeVendaDcss(forcaVenda); //Chamar no PUT

		} catch (final Exception e) {
			log.error(e);
			log.error("Erro ao cadastrar forcaVenda :: Detalhe: [" + e.getMessage() + "]");
			return new ForcaVendaResponse(0, "Erro ao cadastrar forcaVenda. Detalhe: [" + e.getMessage() + "]");
		}

		return new ForcaVendaResponse(tbForcaVenda.getCdForcaVenda(),
				"ForcaVenda cadastrada. CPF [" + forcaVenda.getCpf() + "]");
	}

	@Override
	public ForcaVenda findForcaVendaByCpf(String cpf) {

		log.info("[findForcaVendaByCpf]");

		final ForcaVenda forcaVenda = new ForcaVenda();

		final List<TbodForcaVenda> entities = forcaVendaDao.findByCpf(cpf);

		if (entities != null && !entities.isEmpty()) {
			final TbodForcaVenda tbForcaVenda = entities.get(0);

			forcaVenda.setCdForcaVenda(tbForcaVenda.getCdForcaVenda());
			forcaVenda.setNome(tbForcaVenda.getNome());
			forcaVenda.setCelular(tbForcaVenda.getCelular());
			forcaVenda.setEmail(tbForcaVenda.getEmail());
			forcaVenda.setCpf(tbForcaVenda.getCpf());
			forcaVenda.setAtivo("S".equals(tbForcaVenda.getAtivo()) ? true : false);

			String dataStr = "00/00/0000";
			final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				dataStr = sdf.format(tbForcaVenda.getDataNascimento());
			} catch (final Exception e) {
				log.error("Erro ao formatar data de nascimento :: Detalhe: [" + e.getMessage() + "]");
			}
			forcaVenda.setDataNascimento(dataStr);

			forcaVenda.setCargo(tbForcaVenda.getCargo());
			forcaVenda.setDepartamento(tbForcaVenda.getDepartamento());

			final String statusForca = tbForcaVenda.getTbodStatusForcaVenda() != null
					? tbForcaVenda.getTbodStatusForcaVenda().getDescricao() : "";
			forcaVenda.setStatusForcaVenda(statusForca);

			if (tbForcaVenda.getTbodCorretora() != null) {

				final TbodCorretora tbCorretora = tbForcaVenda.getTbodCorretora();
				final Corretora corretora = new Corretora();

				corretora.setCdCorretora(tbCorretora.getCdCorretora());
				corretora.setCnpj(tbCorretora.getCnpj());
				corretora.setRazaoSocial(tbCorretora.getRazaoSocial());

				forcaVenda.setCorretora(corretora);
			}

			final String senha = tbForcaVenda.getTbodLogin() != null ? tbForcaVenda.getTbodLogin().getSenha() : "";
			forcaVenda.setSenha(senha);
		}

		return forcaVenda;

	}

	@Override
	public ForcaVendaResponse findAssocForcaVendaCorretora(Long cdForcaVenda, String cnpj) {

		log.info("[findAssocForcaVendaCorretora]");

		TbodForcaVenda tbForcaVenda;

		try {

			tbForcaVenda = forcaVendaDao.findByCdForcaVendaAndTbodCorretoraCnpj(cdForcaVenda, cnpj);

			if (tbForcaVenda == null) {
				throw new Exception("ForcaVenda e Corretora nao associadas!");
			}

		} catch (final Exception e) {
			log.error("Erro ao verificar associacao ForcaVendaCorretora :: Detalhe: [" + e.getMessage() + "]");
			return new ForcaVendaResponse(0,
					"Erro ao verificar associacao ForcaVendaCorretora. Detalhe: [" + e.getMessage() + "].");
		}

		return new ForcaVendaResponse(1, "ForcaVenda [" + tbForcaVenda.getCpf() + "] esta associada a corretora ["
				+ tbForcaVenda.getTbodCorretora().getCnpj() + "]");

	}

	@Override
	public ForcaVendaResponse updateForcaVendaLogin(ForcaVenda forcaVenda) {

		log.info("[updateForcaVendaLogin]");

		TbodForcaVenda tbForcaVenda = new TbodForcaVenda();

		try {

			if (forcaVenda.getCdForcaVenda() == null) {
				throw new Exception("cdForcaVenda nao informado. Atualizacao nao realizada!");
			}

			tbForcaVenda = forcaVendaDao.findOne(forcaVenda.getCdForcaVenda());

			if (tbForcaVenda == null) {
				throw new Exception("cdForcaVenda nao encontrado. Atualizacao nao realizada!");
			}

			if (forcaVenda.getNome() != null && !forcaVenda.getNome().isEmpty()) {
				tbForcaVenda.setNome(forcaVenda.getNome());
			}
			if (forcaVenda.getCelular() != null && !forcaVenda.getCelular().isEmpty()) {
				tbForcaVenda.setCelular(forcaVenda.getCelular());
			}
			if (forcaVenda.getEmail() != null && !forcaVenda.getEmail().isEmpty()) {
				tbForcaVenda.setEmail(forcaVenda.getEmail());
			}

			final TbodStatusForcaVenda tbStatusForcaVenda = statusForcaVendaDao.findOne(ATIVO.getCodigo());
			tbForcaVenda.setTbodStatusForcaVenda(tbStatusForcaVenda);
			tbForcaVenda.setAtivo(Constantes.ATIVO);

			if (forcaVenda.getSenha() != null && !forcaVenda.getSenha().isEmpty()) {
				TbodLogin tbLogin = null;
				// tbLogin.setCdLogin(tbForcaVenda.getTbodLogin().getCdLogin());

				if (tbForcaVenda.getTbodLogin() == null) {
					log.info("criando novo TbodLogin para tbForcaVenda.getCdForcaVenda():["
							+ tbForcaVenda.getCdForcaVenda() + "], getCpf():[" + tbForcaVenda.getCpf()
							+ "] pq TbodLogin() == null.");
					tbLogin = new TbodLogin();
				} else {
					if (tbForcaVenda.getTbodLogin().getCdLogin() == null) {
						log.info("criando novo TbodLogin para tbForcaVenda.getCdForcaVenda():["
								+ tbForcaVenda.getCdForcaVenda() + "], getCpf():[" + tbForcaVenda.getCpf()
								+ "] pq TbodLogin().getCdLogin() == null.");
						tbLogin = new TbodLogin();
					} else {
						tbLogin = loginDao.findOne(tbForcaVenda.getTbodLogin().getCdLogin());
						if (tbLogin == null) {
							log.info("criando novo TbodLogin para tbForcaVenda.getCdForcaVenda():["
									+ tbForcaVenda.getCdForcaVenda() + "], getCpf():[" + tbForcaVenda.getCpf()
									+ "] pq TbodLogin().getCdLogin():[" + tbForcaVenda.getTbodLogin().getCdLogin()
									+ "] NAO ENCONTRADO.");
							tbLogin = new TbodLogin();
						}
					}
				}

				tbLogin.setCdTipoLogin((long) 1); // TODO
				tbLogin.setSenha(forcaVenda.getSenha());
				tbLogin = loginDao.save(tbLogin);
				tbForcaVenda.setTbodLogin(tbLogin);
			}
			// Atualiza forcaVenda e associa login
			forcaVenda = this.adaptEntityToDto(tbForcaVenda, forcaVenda);
			DCSSLoginResponse reponseDCSSLogin = this.postIntegracaoForcaDeVendaDcss(forcaVenda);
			tbForcaVenda.setCodigoDcssUsuario(reponseDCSSLogin.getCodigo());
			tbForcaVenda = forcaVendaDao.save(tbForcaVenda);

		} catch (final Exception e) {
			log.error("Erro ao atualizar ForcaVendaLogin :: Detalhe: [" + e.getMessage() + "]");
			return new ForcaVendaResponse(0, "Erro ao atualizar ForcaVendaLogin :: Detalhe: [" + e.getMessage() + "].");
		}

		return new ForcaVendaResponse(1, "ForcaVendaLogin atualizado! [" + tbForcaVenda.getCpf() + "]");
	}

	private ForcaVenda adaptEntityToDto(TbodForcaVenda tbForcaVenda, ForcaVenda forcaVenda) {

		forcaVenda.setCdForcaVenda(tbForcaVenda.getCdForcaVenda());
		forcaVenda.setNome(tbForcaVenda.getNome());
		forcaVenda.setCelular(tbForcaVenda.getCelular());
		forcaVenda.setEmail(tbForcaVenda.getEmail());
		
		if(tbForcaVenda.getTbodCorretora() != null) {
			TbodCorretora tbodCorretora = tbForcaVenda.getTbodCorretora();   
			Corretora corretora = new Corretora();
			corretora.setCnpj(tbodCorretora.getCnpj());			
			corretora.setRazaoSocial(tbodCorretora.getRazaoSocial());
			corretora.setCnae(tbodCorretora.getCnae());
			corretora.setTelefone(tbodCorretora.getTelefone());
			corretora.setCelular(tbodCorretora.getCelular());
			corretora.setEmail(tbodCorretora.getEmail());
			corretora.setStatusCnpj(tbodCorretora.getStatusCnpj());
			corretora.setSimplesNacional(tbodCorretora.getSimplesNacional());
			try {
				corretora.setDataAbertura(DataUtil.dateToStringParse(tbodCorretora.getDataAbertura()));
			} catch (Exception e) {
				log.error("Erro ao converter data. Detalhe: [" + e.getMessage() + "]");
				corretora.setDataAbertura("00/00/0000");
			}
			forcaVenda.setNomeEmpresa(tbodCorretora.getNome());
			
			if(tbodCorretora.getTbodEndereco() != null) {
				TbodEndereco tbodEndereco = tbodCorretora.getTbodEndereco();
				Endereco endereco = new Endereco();
				endereco.setCep(tbodEndereco.getCep());
				endereco.setLogradouro(tbodEndereco.getLogradouro());
				//TODO
				corretora.setEnderecoCorretora(endereco);
			}
			
			//201803051930 desligado
//			if(tbodCorretora.getTbodCorretoraBancos() != null
//			&& tbodCorretora.getTbodCorretoraBancos().get(0) != null 
//			&& tbodCorretora.getTbodCorretoraBancos().get(0).getTbodBancoConta() != null) {
//				TbodBancoConta tbodBancoConta = tbodCorretora.getTbodCorretoraBancos().get(0).getTbodBancoConta();
//				Conta conta = new Conta();
//				conta.setCodigoBanco(tbodBancoConta.getCodigoBanco().toString());
//				conta.setCodigoAgencia(tbodBancoConta.getAgencia());
//				conta.setNumeroConta(tbodBancoConta.getConta());
//				corretora.setConta(conta);
//			}
			
			String responsavel = null;
			if(tbodCorretora.getNomeRepresentanteLegal1() != null 
			&& !tbodCorretora.getNomeRepresentanteLegal1().isEmpty()) {
				responsavel = tbodCorretora.getNomeRepresentanteLegal1();
			} else if(tbodCorretora.getNomeRepresentanteLegal2() != null 
			&& !tbodCorretora.getNomeRepresentanteLegal2().isEmpty()) {
				responsavel = tbodCorretora.getNomeRepresentanteLegal2();
			}
			forcaVenda.setResponsavel(responsavel);
			
			forcaVenda.setCorretora(corretora);
		}
		
		//TODO
		//empresa
		//gerente
		//responsavel
		
		forcaVenda.setStatusForcaVenda(tbForcaVenda.getTbodStatusForcaVenda().getDescricao());
		forcaVenda.setCpf(tbForcaVenda.getCpf());
		forcaVenda.setAtivo(tbForcaVenda.getAtivo() == "S" ? true : false);
		forcaVenda.setDepartamento(tbForcaVenda.getDepartamento());
		forcaVenda.setCargo(tbForcaVenda.getCargo());
		forcaVenda.setSenha(tbForcaVenda.getTbodLogin() != null ? tbForcaVenda.getTbodLogin().getSenha() : "");

		return forcaVenda;
	}

	@Override
	public List<ForcaVenda> findForcaVendasByCdStatusForcaCdCorretora(Long cdStatusForcaVenda, Long cdCorretora) {

		log.info("[findForcaVendasByCdCorretoraStatusAprovacao]");

		List<TbodForcaVenda> forcaVendasEmAprovacao = new ArrayList<TbodForcaVenda>();
		final List<ForcaVenda> forcasVendas = new ArrayList<ForcaVenda>();

		try {
			forcaVendasEmAprovacao = forcaVendaDao
					.findByTbodStatusForcaVendaCdStatusForcaVendasAndTbodCorretoraCdCorretora(cdStatusForcaVenda,
							cdCorretora);

			if (!forcaVendasEmAprovacao.isEmpty()) {
				for (final TbodForcaVenda tbodForcaVenda : forcaVendasEmAprovacao) {
					final ForcaVenda forcaVenda = new ForcaVenda();
					forcasVendas.add(adaptEntityToDtoUpdated(tbodForcaVenda, forcaVenda));
				}
			}
		} catch (final Exception e) {
			log.error("Erro ao buscar ForcaVenda em aprovacao :: Detalhe: [" + e.getMessage() + "]");
			return new ArrayList<ForcaVenda>();
		}

		return forcasVendas;
	}
	
	 @Override
	 public List<ForcaVenda> findForcaVendasByForcaCdCorretora(Long cdCorretora) {
	        log.info("[findForcaVendasByForcaCdCorretora]");

	        List<TbodForcaVenda> listTbodForcaVenda = new ArrayList<TbodForcaVenda>();
	        final List<ForcaVenda> forcasVendas = new ArrayList<ForcaVenda>();

	        try {
	            listTbodForcaVenda = forcaVendaDao.findByTbodCorretoraCdCorretora(cdCorretora);

	            if (!listTbodForcaVenda.isEmpty()) {
	                for (final TbodForcaVenda tbodForcaVenda : listTbodForcaVenda) {
	                    final ForcaVenda forcaVenda = new ForcaVenda();
	                    forcasVendas.add(adaptEntityToDto(tbodForcaVenda, forcaVenda));
	                }
	            }


	        } catch (final Exception e) {
	            log.error("Erro ao buscar ForcaVenda pelo codigo corretora :: Detalhe: [" + e.getMessage() + "]");
	            return new ArrayList<ForcaVenda>();
	        }

	        return forcasVendas;
	    }

	public ForcaVenda adaptEntityToDtoUpdated(TbodForcaVenda tbForcaVenda, ForcaVenda forcaVenda) {
		forcaVenda.setCdForcaVenda(tbForcaVenda.getCdForcaVenda());
		forcaVenda.setAtivo(tbForcaVenda.getAtivo() == "S" ? true : false);
		forcaVenda.setCargo(tbForcaVenda.getCargo());
		forcaVenda.setCelular(tbForcaVenda.getCelular());
		forcaVenda.setCpf(tbForcaVenda.getCpf());

		forcaVenda.setDepartamento(tbForcaVenda.getDepartamento());
		forcaVenda.setEmail(tbForcaVenda.getEmail());
		forcaVenda.setNome(tbForcaVenda.getNome());
		forcaVenda.setStatusForcaVenda(tbForcaVenda.getTbodStatusForcaVenda().getDescricao());

		String dataStr = "00/00/0000";
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			dataStr = sdf.format(tbForcaVenda.getDataNascimento());
		} catch (final Exception e) {
			log.error("Erro ao formatar data de nascimento :: Detalhe: [" + e.getMessage() + "]");
		}
		forcaVenda.setDataNascimento(dataStr);

		final Corretora corretora = new Corretora();
		corretora.setCdCorretora(tbForcaVenda.getTbodCorretora().getCdCorretora());
		corretora.setCnpj(tbForcaVenda.getTbodCorretora().getCnpj());
		corretora.setCnae(tbForcaVenda.getTbodCorretora().getCnae());
		corretora.setRazaoSocial(tbForcaVenda.getTbodCorretora().getRazaoSocial());
		corretora.setTelefone(tbForcaVenda.getTbodCorretora().getTelefone());
		corretora.setCelular(tbForcaVenda.getTbodCorretora().getCelular());
		corretora.setEmail(tbForcaVenda.getTbodCorretora().getEmail());
		corretora.setStatusCnpj(tbForcaVenda.getTbodCorretora().getStatusCnpj());
		corretora.setSimplesNacional(tbForcaVenda.getTbodCorretora().getSimplesNacional());
		try {
			corretora.setDataAbertura(DataUtil.dateToStringParse(tbForcaVenda.getTbodCorretora().getDataAbertura()));
		} catch (Exception e) {
			corretora.setDataAbertura("00/00/0000");
			log.error("Erro ao converter data. Detalhe: [" + e.getMessage() + "]");
		}

		final Endereco enderecoCorretora = new Endereco();
		enderecoCorretora.setLogradouro(tbForcaVenda.getTbodCorretora().getTbodEndereco().getLogradouro());
		enderecoCorretora.setNumero(tbForcaVenda.getTbodCorretora().getTbodEndereco().getNumero());
		enderecoCorretora.setComplemento(tbForcaVenda.getTbodCorretora().getTbodEndereco().getComplemento());
		enderecoCorretora.setBairro(tbForcaVenda.getTbodCorretora().getTbodEndereco().getBairro());
		enderecoCorretora.setCidade(tbForcaVenda.getTbodCorretora().getTbodEndereco().getCidade());
		enderecoCorretora.setEstado(tbForcaVenda.getTbodCorretora().getTbodEndereco().getUf());
		enderecoCorretora.setCep(tbForcaVenda.getTbodCorretora().getTbodEndereco().getCep());
		corretora.setEnderecoCorretora(enderecoCorretora);

		final String senha = tbForcaVenda.getTbodLogin() != null ? tbForcaVenda.getTbodLogin().getSenha() : "";
		forcaVenda.setSenha(senha);

		forcaVenda.setCorretora(corretora);
		return forcaVenda;
	}

	@Override
	public ForcaVendaResponse updateForcaVendaStatusByCpf(ForcaVenda forcaVenda) {

		log.info("[updateForcaVendaStatusByCpf]");

		List<TbodForcaVenda> tbForcaVendas = new ArrayList<TbodForcaVenda>();
		TbodForcaVenda tbForcaVenda = new TbodForcaVenda();
		String ativoAnterior = "";
		String statusAnterior = "";

		try {

			if (forcaVenda == null || forcaVenda.getCpf() == null || forcaVenda.getCpf().isEmpty()) {
				throw new Exception("CPF ForcaVenda nao informado. Atualizacao nao realizada!");
			}

			// O retorno eh uma lista, mas nao deve existir mais de uma forca de
			// venda na
			// base com o mesmo cpf
			tbForcaVendas = forcaVendaDao.findByCpf(forcaVenda.getCpf());

			if (tbForcaVendas.isEmpty()) {
				throw new Exception("ForcaVenda nao encontrada. Atualizacao nao realizada!");
			} else if (tbForcaVendas.size() > 1) {
				throw new Exception("CPF duplicado. Atualizacao nao realizada!");
			}

			tbForcaVenda = tbForcaVendas.get(0);
			ativoAnterior = tbForcaVenda.getAtivo();
			statusAnterior = tbForcaVenda.getTbodStatusForcaVenda().getDescricao();

			if(Constantes.ATIVO.equals(ativoAnterior) 
					&& StatusForcaVendaEnum.ATIVO.getCodigo() == tbForcaVenda.getTbodStatusForcaVenda().getCdStatusForcaVendas()) {
				//throw new Exception("CPF ja esta ativo! Nenhuma acao realizada!");
				log.info("CPF [" + tbForcaVenda.getCpf() + "] ja esta ativo! Nenhuma acao realizada!");
			}

			// Ativando ForcaVenda
			tbForcaVenda.setAtivo(Constantes.ATIVO);
			final TbodStatusForcaVenda tbStatusForcaVenda = statusForcaVendaDao.findOne(ATIVO.getCodigo());
			tbForcaVenda.setTbodStatusForcaVenda(tbStatusForcaVenda);

			tbForcaVenda = forcaVendaDao.save(tbForcaVenda);

			//TODO //201803041824 tratar erro no dcss e fazer rollback da alteracao de status
			ForcaVenda forcaVendaParaDCSS = this.adaptEntityToDto(tbForcaVenda, forcaVenda); //201803041824 inc esert para fernando
			DCSSLoginResponse reponseDCSSLogin = this.postIntegracaoForcaDeVendaDcss(forcaVendaParaDCSS); //201803041824 inc esert para fernando
			if(reponseDCSSLogin != null && reponseDCSSLogin.getCodigo() != null) {
				tbForcaVenda.setCodigoDcssUsuario(reponseDCSSLogin.getCodigo()); //201803052006 inc esert
				tbForcaVenda = forcaVendaDao.save(tbForcaVenda); //201803052006 inc esert
			} else {
				throw new Exception("updateForcaVendaStatusByCpf: reponseDCSSLogin.getCodigo() == null."); //201803052006 inc esert
			}

		} catch (final Exception e) {
			log.error("Erro ao atualizar ForcaVendaStatus :: Message: [" + e.getMessage() + "]");
			return new ForcaVendaResponse(0, "Erro ao atualizar ForcaVendaStatus :: Message: [" + e.getMessage() + "].");
		}

		return new ForcaVendaResponse(1, "ForcaVendaLogin atualizado!" 
				+ " Cpf:[" + tbForcaVenda.getCpf() + "];"
				+ " CodigoDcssUsuario:[" + tbForcaVenda.getCodigoDcssUsuario() + "];"
				+ " De:[" + ativoAnterior +"-"+ statusAnterior + "];"
				+ " Para:[ " + tbForcaVenda.getAtivo() +"-"+ tbForcaVenda.getTbodStatusForcaVenda().getDescricao() + "].");
	}

	@Override
	public ForcaVendaResponse updateForcaVendaStatusByCpf(ForcaVenda forcaVenda, String opcaoStatus) throws Exception {

		log.info("[updateForcaVendaStatusByCpf - status: " + opcaoStatus);
		
		if (forcaVenda == null || forcaVenda.getCpf() == null || forcaVenda.getCpf().isEmpty()) {
			throw new Exception("CPF ForcaVenda nao informado. Atualizacao nao realizada!");
		}
		
		List<TbodForcaVenda> tbForcaVendas = new ArrayList<TbodForcaVenda>();
		
		/**** O retorno eh uma lista, mas nao deve existir mais de uma forca de venda na base com o mesmo cpf ****/
		tbForcaVendas = forcaVendaDao.findByCpf(forcaVenda.getCpf());

		if (tbForcaVendas.isEmpty()) {
			throw new Exception("ForcaVenda nao encontrada. Atualizacao nao realizada!");
		} else if (tbForcaVendas.size() > 1) {
			throw new Exception("CPF duplicado. Atualizacao nao realizada!");
		}		
		
		try {
			TbodForcaVenda tbForcaVenda = new TbodForcaVenda();
			tbForcaVenda = tbForcaVendas.get(0);
			/***** status excluir ou reprovar - ativo = N *****/
			tbForcaVenda.setAtivo(Constantes.INATIVO);
			/***** status excluir ou reprovar - cdCorretora = null *****/			
			tbForcaVenda.setTbodCorretora(null);			
			/***** status reprovar ou excluir *****/
			final TbodStatusForcaVenda tbStatusForcaVenda = statusForcaVendaDao
					.findOne(opcaoStatus == "REPROVAR" ? StatusForcaVendaEnum.PENDENTE.getCodigo()
							: StatusForcaVendaEnum.INATIVO.getCodigo());
			tbForcaVenda.setTbodStatusForcaVenda(tbStatusForcaVenda);
			/***** udate *****/
			tbForcaVenda = forcaVendaDao.save(tbForcaVenda);
			
			/*** DCSS ***/
			try {		
				this.putIntegracaoForcaDeVendaDcss(forcaVenda, "INATIVO");
			} catch (Exception e) {
				log.error("Erro na integração Forca de Venda Dcss :: Message: [" + e.getMessage() + "]");
				return new ForcaVendaResponse(0, "Erro na integração Forca de Venda Dcss (putIntegracaoForcaDeVendaDcss) :: Message: [" + e.getMessage() + "].");
			}
			
		} catch (Exception e) {
			log.error("Erro ao atualizar status Forca Venda (reprovar ou excluir) :: Message: [" + e.getMessage() + "]");
			return new ForcaVendaResponse(0, "Erro ao atualizar status Forca Venda (reprovar ou excluir) :: Message: [" + e.getMessage() + "].");
		}
		
		return new ForcaVendaResponse(1, "ForcaVendaLogin atualizado!" 
				+ " Cpf:[" + forcaVenda.getCpf() + "];");
	}
}
